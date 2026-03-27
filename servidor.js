const express = require('express');

const app = express();
app.use(express.json());

// Rota de teste
app.get('/', (req, res) => {
    res.send('API rodando 🚀');
});

// Porta
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Servidor rodando em http://localhost:${PORT}`);
});