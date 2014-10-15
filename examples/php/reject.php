<?php

// Prepare variables for easier handling
$fromNumber = $_POST['from'];     // the number of the caller

// Create new DOM Document for the response
$dom = new DOMDocument('1.0', 'UTF-8');

// Add response child
$response = $dom->createElement('Response');
$dom->appendChild($response);

// Only reject calls from a certain number, e.g. your mother-in-law
if($fromNumber == "49190666666") {
	// Add hangup command as child in response
	$hangup = $dom->createElement('Reject');

	// Create a new attribute for the hangup element and assign "busy" as value, so that the caller hears a busy-tone
	$hangupReason = $dom->createAttribute('reason');
	$hangupReason->value = 'busy';
	$hangup->appendChild($hangupReason);

	$response->appendChild($hangup);
}

Header('Content-type: text/xml');
echo $dom->saveXML();
