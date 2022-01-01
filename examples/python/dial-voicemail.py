#!/usr/bin/env python

from http.server import BaseHTTPRequestHandler, HTTPServer
import urllib.parse as urlparse
import logging
from xml.dom.minidom import Document

logging.basicConfig(level=logging.DEBUG)

class MegaAwesomePythonServer(BaseHTTPRequestHandler):

    def do_POST(self):
        length = int(self.headers.get('Content-Length'))
        data = urlparse.parse_qs(self.rfile.read(length))

        logging.debug("from: " + data.get("from")[0])
        logging.debug("to: " + data.get("to")[0])
        logging.debug("direction: " + data.get("direction")[0])

        doc = Document()
        response = doc.createElement('Response')
        dial = doc.createElement('Dial')
        voicemail = doc.createElement('Voicemail')
        dial.appendChild(voicemail)
        response.appendChild(dial)
        doc.appendChild(response)

        self.send_response(200)
        self.send_header('Content-Type', 'application/xml')
        self.end_headers()
        self.wfile.write(doc.toxml())


server = HTTPServer(('', 3000), MegaAwesomePythonServer)
server.serve_forever()
