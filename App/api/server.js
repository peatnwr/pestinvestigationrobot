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

    dbConn.query('INSERT INTO `mission`(`missionName`, `datetime`) VALUES (?, ?)', [missionName, date], function(error, results, fields) {
        dbConn.on('error', function(error) {
            console.log("เกิด Error ไอสัส", error)
        })
        return res.send(results);
    });
});

app.post('/addcoord/', function(req, res) {
    let body = req.body;
    let lat = body.lat;
    let longi = body.longi;
    let missionId = body.idMission;

    dbConn.query('INSERT INTO `coord`(`lat`, `longi`, `idMission`) VALUES (?, ?, ?)', [lat, longi, missionId], function(error, results, fields) {
        if(error) throw error;

        return res.send(results)
    })
});

app.post('/updatecoord/', function(req, res) {
    let body = req.body;
    let coordId = body.coordId;
    let lat = body.lat;
    let longi = body.longi;

    dbConn.query('UPDATE `coord` SET `lat` = ?, `longi` = ? WHERE `id` = ?', [lat, longi, coordId], function(error, results, fields) {
        if(error) throw error;

        return res.send(results)
    });
});

app.delete('/deletecoord/:coordId', function(req, res) {
    let coordId = req.params.coordId;

    dbConn.query('DELETE FROM `coord` WHERE `id` = ?', coordId, function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    });
});

app.delete('/deletemission/:missionId', function(req, res) {
    let missionId = req.params.missionId;

    dbConn.query('DELETE FROM `mission` WHERE `id` = ?', missionId, function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    });
});

app.get('/allcoord/:idMission', function(req, res) {
    let missionId = req.params.idMission

    dbConn.query('SELECT * FROM `coord` WHERE `idMission` = ? ORDER BY `id` ASC', missionId, function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    });
});

app.get('/allmission/', function(req, res) {
    dbConn.query('SELECT * FROM `mission`', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    });
});

app.get('/missioninfo/:idMission', function(req, res) {
    let missionId = req.params.idMission

    dbConn.query('SELECT * FROM `mission` WHERE `id` = ?', missionId, function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/automaticmode/', function(req, res) {
    dbConn.query('UPDATE `status` SET `mode` = 1, `forWard` = 0, `backWard` = 0, `turnLeft` = 0, `turnRight` = 0', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/manualmode/', function(req, res) {
    dbConn.query('UPDATE `status` SET `mode` = 0', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/enableforward/', function(req, res) {
    dbConn.query('UPDATE `status` SET `forWard` = 1', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/enablebackward/', function(req, res) {
    dbConn.query('UPDATE `status` SET `backWard` = 1', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/enableturnleft', function(req, res) {
    dbConn.query('UPDATE `status` SET `turnLeft` = 1', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/enableturnright/', function(req, res) {
    dbConn.query('UPDATE `status` SET `turnRight` = 1', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/disableforward/', function(req, res) {
    dbConn.query('UPDATE `status` SET `forWard` = 0', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/disablebackward/', function(req, res) {
    dbConn.query('UPDATE `status` SET `backWard` = 0', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/disableturnleft/', function(req, res) {
    dbConn.query('UPDATE `status` SET `turnLeft` = 0', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.put('/disableturnright', function(req, res) {
    dbConn.query('UPDATE `status` SET `turnRight` = 0', function(error, results, fields) {
        if(error) throw error;

        return res.send(results);
    })
});

app.listen(3000, function() {
    console.log('Node app or API is running on port 3000');
});

module.exports = app;