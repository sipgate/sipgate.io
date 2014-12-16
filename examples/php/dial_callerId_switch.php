<?php
// Basic Code are from danielberlin

// Prepare variables for easier handling
$toNumber = $_POST['to'];     // the number you will call

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

//RegEx the dialnumber, you should maybe change that for you ;-)
preg_match("/(..)([1-9])[0-9]*/", $toNumber, $number_array);
// Check if it an national call, this match for germany
if ($number_array[1]!="49") {
	// Set international callerID, please edit your number
	$sendID = '49211000000';
}
// Is this an national call, check is it an landline or mobile call
elseif ($number_array[2]>"1") {
	// Set landline callerID, please edit your number
	$sendID = '49211000000';
}
else {
	// Set mobile callerID, please edit your number
	$sendID = '4915799912345';
}
// set callerId - you should change that to your desired number
$callerId->value = $sendID;
//$anonymous->value = 'true';

// the number will come from the to parameter in the uri "dial_callerId_switch.php?from=492111234567&to=4915791234567&direction=out"
$number = $dom->createElement('Number',$number_array[0]);

$dial->appendChild($callerId);
//$dial->appendChild($anonymous);
$dial->appendChild($number);

$response->appendChild($dial);

header('Content-type: application/xml');
echo $dom->saveXML();
