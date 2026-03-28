const express = require('express');
const mysql = require('mysql2');

const app = express();
app.use(express.json());

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'patente'
});

db.connect((err) => {
    if (err) {
        console.error(err);
        return;
    }
    console.log('Conectado ao MySQL');
});

app.get('/', (req, res) => {
    res.send('API rodando');
});

app.get('/pessoas', (req, res) => {
    db.query('SELECT * FROM PESSOA', (err, results) => {
        if (err) return res.status(500).json(err);
        res.json(results);
    });
});

app.listen(3000, () => {
    console.log('Servidor rodando em http://localhost:3000');
});
