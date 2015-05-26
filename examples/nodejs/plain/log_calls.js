// example that shows how to get sipgate.io to also push the end of the call

var http = require('http');
var queryString = require('querystring');

// requires xmlbuilder: npm install xmlbuilder
var builder = require('xmlbuilder');

http.createServer(function (req, res) {
	if (req.method == 'POST') {
		var data = '';

		req.on('data', function(currentData) {
			data += currentData;
		});

		req.on('end', function () {
			var post = queryString.parse(data);

			if (post['event'] == 'newCall') {
				console.log('New call with id ' + post['callId'] + ' started.');

				var response = builder.create('Response',{version: '1.0', encoding: 'UTF-8'},{});
				response.att('onHangup', 'http://' + req.headers.host + '/');

				var body = response.toString();

				res.writeHead(200, {
					'Content-Length': body.length,
					'Content-Type': 'application/xml'
				});

				res.write(body);
				res.end();
			}
			else if (post['event'] == 'hangup' ) {
				console.log('Call with id ' + post['callId'] + ' ended with cause: ' + post['cause']);

				res.writeHead(200);
				res.end();
			}
		});
	}

}).listen(3000);

