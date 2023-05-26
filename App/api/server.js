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

dbConn.connect((err) => {
    if(err){
        console.error('Error connecting to the database:', err);
    } else {
        console.log('Connected to the database')
    }
});

app.post('/addmission/', function(req, res) {
    let body = req.body;
    let missionName = body.missionName;
    let date = body.date;

    dbConn.query('INSERT INTO `date`(`missionName`, `datetime`) VALUES (?, ?)', [missionName, date], function(error, results, fields) {
        dbConn.on('error', function(error) {
            console.log("เกิด Error ไอสัส", error)
        })
        return res.send(results);
    });
});

app.get('/allmission/', function(req, res) {
    dbConn.query('SELECT * FROM `date`', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    });
});

app.listen(3000, function() {
    console.log('Node app or API is running on port 3000');
});

module.exports = app;