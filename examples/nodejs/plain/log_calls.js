// example that shows how to tell sipgate.io to also push answer and end of the call

var http = require('http');
var queryString = require('querystring');

// requires xmlbuilder: npm install xmlbuilder
var builder = require('xmlbuilder');


function formatUserOrGroup(users) {
    return Array.isArray(users)
        ? ("group(" + users + ")")
        : ("user " + users);
}

function newCallToString(newCall) {
    var s = (newCall['direction'] === "in") 
        ? (formatUserOrGroup(newCall['user[]']) + ' receives call ')
        : newCall['user[]'] + ' makes call ';

    s += newCall['from'] + " -> " + newCall['to']

    return s;
}

http.createServer(function (req, res) {

    if (req.method == 'POST') {
        var data = '';

        req.on('data', function (currentData) {
            data += currentData;
        });

        req.on('end', function () {
            var post = queryString.parse(data);

            if (post['event'] == 'newCall') {
                console.log("Call " + post['callId'] + ' created: ' + newCallToString(post));

                var response = builder.create('Response', {version: '1.0', encoding: 'UTF-8'}, {});
                response.att('onAnswer', 'http://' + req.headers.host + '/');
                response.att('onHangup', 'http://' + req.headers.host + '/');

                var body = response.toString();

                res.writeHead(200, {
                    'Content-Length': body.length,
                    'Content-Type': 'application/xml'
                });

                res.write(body);
                res.end();
            }
            else if (post['event'] == 'answer') {
                
                var logRow = 'Call with id ' + post['callId'] + ' was answered';

                if (post['user']) { // Only incoming calls can have a user
                    logRow += ' by ' + post['user'];
                }
                console.log(logRow);

                res.writeHead(200);
                res.end();
            }
            else if (post['event'] == 'hangup') {
                console.log('Call ' + post['callId'] + ' ended with cause: ' + post['cause']);

                res.writeHead(200);
                res.end();
            }
        });
    }

}).listen(3000);

