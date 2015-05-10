<?php
/* Log the beginnings of calls
 */

$event = $_POST['event'];           // is either "newCall" (beginning) or "hangup" (end)


if ($event == 'newCall') {

  // Prepare variables for easier handling
  $fromNumber = $_POST['from'];     // the number of the caller
  $toNumber = $_POST['to'];         // the number on which the call was received on
  $direction = $_POST['direction']; // the direction of the call (either "in" or "out")
  $callId = $_POST['callId'];       // unique Id of this call
  $timestamp = date("d.m.Y H:i:s"); // a timestamp for the log so that calls can be uniquely identified

  // build the log row, example:
  // 23456123 - 17.09.2014 10:05:25 - from 4921100000000 to 4921100000000 - direction: in
    $logRow = $callId . " - " . $timestamp .
      " - from " . $fromNumber . " to " . $toNumber .
      " - direction: " . $direction . PHP_EOL;

  // append the log row to the callog.txt file, make sure this file is writeable (e.g. create the file and chmod 777 it)
  file_put_contents("callog.txt",$logRow,FILE_APPEND);

  die("Thanks - here's a motivational squirrel for you! https://www.youtube.com/watch?v=m3d03-sSiBE");

}
