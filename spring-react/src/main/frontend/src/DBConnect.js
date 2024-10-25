const mysql = require('mysql');

const conn = mysql.createConnection({
    host: '1.220.247.78',
    port: '3307',
    user: 'final_2405_team3_user',
    password: '1234',
    database: 'final_2405_team3'
});

conn.connect((err) => {
    if (err) console.log(err);
    else console.log('Connected to the database');
});

module.exports = conn;

const express = require('express');
const db = require('./database/db');

const app = express();
const port = 3001;

app.get('/', (req, res) => {
    res.send('Server Succes');
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});