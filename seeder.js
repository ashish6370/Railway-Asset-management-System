const mysql = require('mysql2');
const fs = require('fs');

const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'root',
  database: 'assetvault',
  multipleStatements: true
});

const sql = fs.readFileSync('seed-assets.sql', 'utf8');

connection.query(sql, (err, results) => {
  if (err) {
    console.error('Error running seed:', err);
  } else {
    console.log('Seed successful!');
  }
  connection.end();
});
