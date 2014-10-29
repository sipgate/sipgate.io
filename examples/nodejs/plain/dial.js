// dial example - forwards all calls to voicemail

var http = require('http');

// requires xmlbuilder: npm install xmlbuilder
var builder = require('xmlbuilder');

http.createServer(function (req, res) {

	// create XML root tree called "response"
	var response = builder.create('Response',{version: '1.0', encoding: 'UTF-8'},{})
		.ele('Dial')					// add "dial" child
		.ele('Voicemail')				// add "voicemail" child
//		.ele('Number','49211000000')			// if you want, you could also redirect to a phone number
		.end({ pretty: true});

	var body = response.toString();

	res.writeHead(200, {
		  'Content-Length': body.length,
		  'Content-Type': 'application/xml'
	});

	res.header('Content-Type','application/xml').write(body);

	res.end();
}).listen(3000);

