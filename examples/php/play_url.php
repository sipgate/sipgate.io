<?php

// Create new DOM Document for the response
$dom = new DOMDocument('1.0', 'UTF-8');

// Add response child
$response = $dom->createElement('Response');
$dom->appendChild($response);

// Add play command as child in response
$play = $dom->createElement('Play');

// Add a url target and append it to the play command
$url = $dom->createElement('Url','http://www.example.com/example.wav');

$play->appendChild($url);

$response->appendChild($play);

header('Content-type: application/xml');
echo $dom->saveXML();
