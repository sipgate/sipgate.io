var https = require('https');
var fs = require('fs');

var options = {
	path: '/',
	method: 'POST',
	key: fs.readFileSync('server-key.pem'),
	cert: fs.readFileSync('server-cert.pem')
};

https.createServer(options, function (req, res) {

	req.on('data', function(d) {
		process.stdout.write(d);
	});

	res.writeHead(200);
	res.end("Node-JS-HTTPS-Server");
}).listen(3000);
