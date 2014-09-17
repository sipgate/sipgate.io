import time
import BaseHTTPServer


HOST_NAME = '0.0.0.0' 
PORT_NUMBER = 3000 


class MyHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_POST(s):
        s.send_response(200)
        s.send_header("Content-type", "text/html")
        s.end_headers()
	varLen = int(s.headers['Content-Length'])
        postVars = s.rfile.read(varLen)
        print postVars

if __name__ == '__main__':
    server_class = BaseHTTPServer.HTTPServer
    httpd = server_class((HOST_NAME, PORT_NUMBER), MyHandler)
    print time.asctime(), "Server Starts - %s:%s" % (HOST_NAME, PORT_NUMBER)
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    print time.asctime(), "Server Stops - %s:%s" % (HOST_NAME, PORT_NUMBER)
