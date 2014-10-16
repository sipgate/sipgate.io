/* This is an example on how to create a fully functional HTTP server for use
 * with the sipgate.io API based on the NuriaProject Framework.
 * See: https://github.com/NuriaProject/Framework
 */

#include <QCoreApplication>
#include <QXmlStreamWriter>
#include <nuria/httpclient.hpp>
#include <nuria/httpserver.hpp>
#include <nuria/httpnode.hpp>
#include <nuria/debug.hpp>

void mySlot (Nuria::HttpClient *client) {
	Nuria::HttpPostBodyReader *reader = client->postBodyReader ();
	if (!reader) {
		client->killConnection (400);
		return;
	}

	nLog() << "Rejecting call" << reader->fieldValue ("from") << "->" << reader->fieldValue ("to");
	client->setResponseHeader (Nuria::HttpClient::HeaderContentType, "application/xml");
	
	QXmlStreamWriter stream (client);
	stream.writeStartDocument ();
	stream.writeStartElement ("Response");
	stream.writeStartElement ("Reject");
	stream.writeEndElement (); // /Reject
	stream.writeEndElement (); // /Response
	stream.writeEndDocument ();
}

int main (int argc, char *argv[]) {
	QCoreApplication a (argc, argv);
	Nuria::HttpServer server;

	server.root ()->connectSlot ("index", mySlot);

	if (!server.listen (QHostAddress::Any, 3000)) {
		nError() << "Failed to listen on port 3000.";
		return 1;
	}

	nLog() << "Listening on all interfaces on port 3000.";
	return a.exec ();
}
