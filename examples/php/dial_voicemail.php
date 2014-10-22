<?php

// Create new DOM Document for the response
$dom = new DOMDocument('1.0', 'UTF-8');

// Add response child
$response = $dom->createElement('Response');
$dom->appendChild($response);

// Add dial command as child in response
$dial = $dom->createElement('Dial');

// Add a voicemail target and append it to the dial command
$voicemail = $dom->createElement('Voicemail');
$dial->appendChild($voicemail);

$response->appendChild($dial);

Header('Content-type: text/xml');
echo $dom->saveXML();
