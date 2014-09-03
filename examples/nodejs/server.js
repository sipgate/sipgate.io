var http = require('http');

http.createServer(function (req, res) {
	req.on('data', function(d) {
		process.stdout.write(d);
	});

	res.writeHead(200);
	res.end("Node-JS-HTTP-Server");
}).listen(3000);

