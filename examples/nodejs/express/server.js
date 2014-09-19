var app = require('express')();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false }));

app.post("/", function (request, response) {
	var from = request.body.from;
	var to = request.body.to;

	console.log("from: " + from);
	console.log("to: " + to);

	response.send("So Long, and Thanks for All the Fish!");
});

app.listen(3000);
