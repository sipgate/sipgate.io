<?php

// Prepare variables for easier handling
$fromNumber = $_POST['from'];		// the number of the caller
$toNumber = $_POST['to'];			// the number on which the call was received on
$timestamp = date("d.m.Y H:i:s");	// a timestamp for the log so that calls can be uniquely identified

// build the log row, example:
// 17.09.2014 10:05:25 - from 4921100000000 to 4921100000000
$logRow = $timestamp . " - from " . $fromNumber . " to " . $toNumber . PHP_EOL;

// append the log row to the callog.txt file, make sure this file is writeable (e.g. create the file and chmod 777 it)
file_put_contents("callog.txt",$logRow,FILE_APPEND);
