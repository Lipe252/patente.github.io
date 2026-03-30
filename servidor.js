const express = require("express");
const { spawn } = require("child_process");

const app = express();
app.use(express.json());

app.post("/login", (req, res) => {
  const { nome, senha } = req.body;

  const java = spawn("java", ["Core", "api"]);

  const input = JSON.stringify({
    action: "login",
    nome,
    senha
  });

  java.stdin.write(input + "\n");
  java.stdin.end();

  let data = "";

  java.stdout.on("data", (chunk) => {
    data += chunk;
  });

  java.on("close", () => {
    try {
      res.json(JSON.parse(data));
    } catch {
      res.status(500).json({ erro: "Erro no core" });
    }
  });
});

app.listen(3000, () => {
  console.log("API rodando em http://localhost:3000");
});