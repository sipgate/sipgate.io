<?php
// Basic code is from danielberlin

// Prepare variables for easier handling
$toNumber = $_POST['to'];	  // the number you dialed

// Create new DOM document for the response
$dom = new DOMDocument('1.0', 'UTF-8');

// Add response child
$response = $dom->createElement('Response');
$dom->appendChild($response);

// Add dial command as child in response
$dial = $dom->createElement('Dial');

// Create a new attribute for the callerId element
$callerId = $dom->createAttribute('callerId');
//$anonymous = $dom->createAttribute('anonymous');

// RegEx the dialed number, you should maybe change that for you ;-)
preg_match("/(..)([1-9])[0-9]*/", $toNumber, $number_array);

// Check if it's an international call (assuming Germany here)
if ($number_array[1] != '49') {
  // Set callerId for international calls, please edit your number
  $sendId = '49211000000';
}
// For a national call, check if it's a landline or mobile call
elseif ($number_array[2] > '1') {
  // Set landline callerId, please edit your number
  $sendId = '49211000000';
}
else {
  // Set mobile callerId as default, please edit your number
  $sendId = '4915799912345';
}
// apply to callerId
$callerId->value = $sendId;
//$anonymous->value = 'true';

// the number is derived from the 'to' parameter in the URI "dial_callerId_switch.php?from=492111234567&to=4915791234567&direction=out"
$number = $dom->createElement('Number', $toNumber);

$dial->appendChild($callerId);
$dial->appendChild($number);

$response->appendChild($dial);

header('Content-type: application/xml');
echo $dom->saveXML();
