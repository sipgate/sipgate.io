var app = require('express')();
var bodyParser = require('body-parser');
var xml = require('xml');

app.use(bodyParser.urlencoded({ extended: false }));

app.post("/", function (request, response) {
	var from = request.body.from;
	var to = request.body.to;
	var direction = request.body.direction;

	console.log("from: " + from);
	console.log("to: " + to);
	console.log("direction: " + direction);

	response.set('Content-Type', 'application/xml');
	response.send(
		xml({ Response: [
			{ Dial: [
				{ Voicemail: null }
			] },
		] })
	);
});

app.listen(3000);
