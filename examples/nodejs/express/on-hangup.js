var app = require('express')();
var bodyParser = require('body-parser');
var xml = require('xml');

var calls = {};

app.use(bodyParser.urlencoded({ extended: false }));

app.post("/", function (request, response) {
	var from = request.body.from;
	var to = request.body.to;
	var direction = request.body.direction;
	var callId = request.body.callId;

	calls[callId] = { "from": from, "to": to };

	console.log("call from: " + from + " to: " + to + " direction: " + direction);

	response.set('Content-Type', 'application/xml');
	response.send(
		xml({ Response: [
			{_attr: { onHangup: 'http://' + request.headers.host + '/hangup' }}
		] })
	);
});

app.post("/hangup", function (request, response) {
	var callId = request.body.callId;

	var from = calls[callId]["from"]
	var to = calls[callId]["to"]

	console.log("hang up call from: " + from + " to: " + to);

	response.send();
});

app.listen(3000);
