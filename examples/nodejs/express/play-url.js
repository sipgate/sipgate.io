var app = require('express')();
var bodyParser = require('body-parser');
var xml = require('xml');

app.use(bodyParser.urlencoded({ extended: false }));

app.post("/", function (request, response) {
	var from = request.body.from;
	var to = request.body.to;

	console.log("from: " + from);
	console.log("to: " + to);

	response.set('Content-Type', 'application/xml');
	response.send(
		xml({ Response: [
			{ Play: [
				{ Url: "http://www.example.com/example.wav" }
			] },
		] })
	);
});

app.listen(3000);
