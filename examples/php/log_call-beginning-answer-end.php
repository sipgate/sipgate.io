<?php
/* Log the beginnings and ends of calls
 */

$event = $_POST['event'];           // "newCall" (beginning), "answer" or "hangup" (end)
$callId = $_POST['callId'];         // unique Id of this call
$timestamp = date("d.m.Y H:i:s");   // a timestamp for the log so that calls can be identified


if ($event == 'newCall') {

    $fromNumber = $_POST['from'];     // the number of the caller
    $toNumber = $_POST['to'];         // the number on which the call was received on
    $direction = $_POST['direction'];   // the direction of the call (either "in" or "out")
	$users = $_POST['user'];				// contains an array, because group calls can have several users

    // build the log row, example:
    // 23456123 - 17.09.2014 10:05:25 - from 4921100000000 to 4921100000000 - direction: in - user: Anna Mayer - user: Marcus Satt
    $logRow = $callId . " - " . $timestamp .
        " - from " . $fromNumber . " to " . $toNumber .
        " - direction: " . $direction;

	foreach ($users as $user) {
		$logRow .= " - user: " . $user;
	}

	$logRow .= PHP_EOL;

    set_onAnswer_onHangup('http://localhost:3000/log_call-beginning-answer-end.php'); // Call this script again on hangup

} else if ($event == 'answer') {

    $user = "";

    if ($_POST['user']) { // Only incoming calls have a user
        $user = " - " . urldecode($_POST['user']);
    }

    // build the log row, example:
    // Incoming call: 23456123 - 17.09.2014 10:05:25 - Anna Mayer
    // Outgoing call: 23456123 - 17.09.2014 10:05:25
    $logRow = $callId . " - " . $timestamp . $user . PHP_EOL;

} else if ($event == 'hangup') {

    $cause = $_POST['cause'];

    // build the log row, example:
    // 23456123 - cancel - 17.09.2014 10:05:25
    $logRow = $callId . " - " . $cause . " - " . $timestamp . PHP_EOL;

}

// append the log row to the callog.txt file, make sure this file is writeable (e.g. create the file and chmod 777 it)
file_put_contents("callog.txt", $logRow, FILE_APPEND);

die("Thanks - here's a motivational squirrel for you! https://www.youtube.com/watch?v=m3d03-sSiBE");

// Create XML Response that sets Url to be called when call ends (hangup)
function set_onAnswer_onHangup($scriptUrl)
{
// Create new DOM Document for the response
    $dom = new DOMDocument('1.0', 'UTF-8');

// Add response child
    $response = $dom->createElement('Response');
    $dom->appendChild($response);

    $response->setAttribute('onAnswer', $scriptUrl);
    $response->setAttribute('onHangup', $scriptUrl);

    header('Content-type: application/xml');
    echo $dom->saveXML();
}
