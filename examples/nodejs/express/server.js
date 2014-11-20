var app = require('express')();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false }));

app.post("/", function (request, response) {
	var from = request.body.from;
	var to = request.body.to;
	var direction = request.body.direction;

	console.log("from: " + from);
	console.log("to: " + to);
	console.log("direction: " + direction);

	response.send("So Long, and Thanks for All the Fish!");
});

app.listen(3000);
