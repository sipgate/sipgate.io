<?php

// Create new DOM Document for the response
$dom = new DOMDocument('1.0', 'UTF-8');

// Add response child
$response = $dom->createElement('Response');
$dom->appendChild($response);

// Add dial command as child in response
$dial = $dom->createElement('Dial');

// Create a new attribute for the callerId element
$callerId = $dom->createAttribute('callerId');
//$anonymous = $dom->createAttribute('anonymous');

// set callerId - you should change that to your desired number
$callerId->value = '4915799912345';
//$anonymous->value = 'true';

// Add a number target and append it to the dial command, we're calling '+49211000000' - you should maybe change that... ;-)
$number = $dom->createElement('Number','49211000000');

$dial->appendChild($callerId);
//$dial->appendChild($anonymous);
$dial->appendChild($number);

$response->appendChild($dial);

header('Content-type: application/xml');
echo $dom->saveXML();
