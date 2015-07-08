<?php

// Create new DOM Document for the response
$dom = new DOMDocument('1.0', 'UTF-8');

// Add response child
$response = $dom->createElement('Response');
$dom->appendChild($response);

$response->setAttribute('onAnswer', 'http://localhost:3000/hangup');
$response->setAttribute('onHangup', 'http://localhost:3000/hangup');

header('Content-type: application/xml');
echo $dom->saveXML();