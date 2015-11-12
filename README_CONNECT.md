sipgate.io connect
==================

sipgate.io extends your web application with real-time telephony data: We push call meta data to your web server for each call to a sipgate.io phone number. That way your application can display a caller's contact info and history, among other things. [See more information and use cases](https://www.sipgate.io)

Add the sipgate.io connect button to offer your customers this additional value in the easist way possible. 

This README shows how to integrate sipgate.io connect into your application:
* Variables to pass into the signup process
* Form template to get you started

Please refer to the general [sipgate.io README](https://github.com/sipgate/sipgate.io/blob/master/README.md)
to learn which information sipgate.io passes to your application and how.

Feel free to check out the [sipgate.io connect demo page](https://demo.sipgate.io/connect) as well as the
[sipgate.io demo page](https://demo.sipgate.io).


Variables to pass in the GET request
================

Parameter  | Description
---------- | -----------
url        | sipgate.io will pass call meta data to this URL
successUrl | After signup we offer your users a link to this URL to return to your application
siteName   | The name of your application. We refer to your application several times throughout signup


Button and Form Template
================

You can copy+paste this form for calling sipgate.io connect:

```html
<form method="GET" action="https://sipgate.io/de/connect">
    <p>
	    Connect this application with telephony so that calls are immediately displayed here
	</p>
	<input type="hidden" name="url" value="https://example.com/io-target/">
	<input type="hidden" name="successUrl" value="https://example.com/connected-to-io">
	<input type="hidden" name="siteName" value="YourAwesomeApplication">
    <br>
    <button class="btn btn-primary btn-lg">Connect with telephony</button>
</form>
```

Please make sure to replace the values of the hidden fields with correct ones.

Help us make it better
======================

Please tell us how we can improve sipgate.io connect. If you have a specific feature request, found a bug or would like to add an example, please use contact support@sipgate.io. Thank you!
