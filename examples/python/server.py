#!/usr/bin/env python

from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import urlparse
import logging

logging.basicConfig(level=logging.DEBUG)

class MegaAwesomePythonServer(BaseHTTPRequestHandler):

    def do_POST(self):
        length = int(self.headers.getheader('Content-Length'))
        data = urlparse.parse_qs(self.rfile.read(length))

        logging.debug("from: " + data.get("from")[0])
        logging.debug("to: " + data.get("to")[0])
        logging.debug("direction: " + data.get("direction")[0])

        self.send_response(200)
        self.end_headers()
        self.wfile.write("http://xkcd.com/353/")


server = HTTPServer(('', 3000), MegaAwesomePythonServer)
server.serve_forever()
