sipgate.io connect
==================

This README shows how to integrate sipgate.io connect into your application:
* Variables to pass into the signup process
* Form template to get you started

Please refer to the general [sipgate.io README](https://github.com/sipgate/sipgate.io/blob/master/README.md)
to learn which information sipgate.io passes to your application and how.

Feel free to check out the [sipgate.io connect demo page](https://demo.sipgate.io/connect) as well as the
[sipgate.io demo page](https://demo.sipgate.io).


Variables to pass in the POST request
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
<form method="get" action="https://sipgate.io/de/connect">
    <p>
	    Connect this application with telephony so that calls are immediately displayed here
	</p>
	<input type="hidden" name="url" value="URL .IO SHOULD POST TO">
	<input type="hidden" name="successUrl" value="URL TO REDIRECT TO AFTER SUCCESSFULL CONNECTION">
	<input type="hidden" name="siteName" value="NAME OF YOUR APPLICATION">
    <br>
    <button class="btn btn-primary btn-lg">Connect with telephony</button>
</form>
```

Please make sure to replace the values of the hidden fields with correct ones.

A word about security
=====================

Authentication
--------------

We plan to add authentication tokens in the near future.


Help us make it better
======================

Please tell us how we can improve sipgate.io connect. If you have a specific feature request, found a bug or would like to add an example, please use contact stefan@sipgate.io. Thank you!
