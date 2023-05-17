let express = require('express');
let app = express();
let bodyParser = require('body-parser');
let mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

let dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'pestinves'
});

dbConn.connect();

app.get('/alldata', function(req, res){
    dbConn.query('SELECT * FROM `data`', function(error, results, fields){
        if(error) throw error;
        return res.send(results);
    })
});

app.listen(3000, function() {
    console.log('Node app or API is running on port 3000');
});

module.exports = app;