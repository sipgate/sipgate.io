// Reject example - rejects all calls with reason "busy"

var http = require('http');

// requires xmlbuilder: npm install xmlbuilder
var builder = require('xmlbuilder');

http.createServer(function (req, res) {

	// create XML root tree called "response"
	var response = builder.create('response',{version: '1.0', encoding: 'UTF-8'},{})
		.ele('reject', {'reason': 'busy'})			// add "reject" with reason "busy"
		.end({ pretty: true});

	var body = response.toString();

	res.writeHead(200, {
		  'Content-Length': body.length,
		  'Content-Type': 'application/xml'
	});

	res.write(body);

	res.end();
}).listen(3000);

