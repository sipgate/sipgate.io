sipgate.io
==========

This README documents [sipgate.io](https://www.sipgate.io), a real-time telephony API by [sipgate](http://www.sipgate.de). There's a [demo page](https://demo.sipgate.io), [code examples](https://github.com/sipgate/sipgate.io/tree/master/examples), and a [newsletter](http://mailing.sipgate.de/f/42695-161642/).

Table of Contents
------------

* [sipgate.io](#sipgateio)
* [Requirements](#requirements)
  *   [Usage with sipgate team](#usage-with-sipgate-team)
  *   [Usage with sipgate basic](#usage-with-sipgate-basic)
  *   [Usage with simquadrat](#usage-with-simquadrat)
*   [The POST request](#the-post-request)
  *   [New call](#new-call)
  *   [Answer](#answer)
  *   [Call hangup](#call-hangup)
    *   [Hangup causes](#hangup-causes)
  *   [DTMF](#dtmf)
*   [The XML response](#the-xml-response)
  *   [Dial](#dial)
  *   [Play](#play) 
  *   [Gather](#gather)
  *   [Reject](#reject)
  *   [Hangup](#hangup)
  *   [onAnswer](#onanswer)
  *   [onHangup](#onhangup)
  *   [More to come](#more-to-come)
*   [Code Examples](#code-examples)
*   [FAQ](#faq)
*   [Troubleshooting](#troubleshooting)
  *   [sipgate.io Log](#sipgateio-log)
  *   [How do I inspect network traffic?](#how-do-i-inspect-network-traffic)
  *   [A word about security](#a-word-about-security)
  *   [HTTP vs. HTTPS](#http-vs-https) 
  *   [Authentication](#authentication)
  *   [Emergency calls](#emergency-calls)
*   [Help us make it better](#help-us-make-it-better)


Requirements
------------

### Usage with sipgate team

* [x] Book sipgate.io in your team account (incurs monthly cost) or [request access to our developer program (free!)](http://goo.gl/forms/8TS8kQj6kx)
* [x] [After receiving the confirmation mail enter an URL for incoming/outgoing calls in sipgate team settings](https://secure.live.sipgate.de/settings/sipgateio)

### Usage with sipgate basic

* [x] [Create a free sipgate basic account](https://www.sipgate.de/basic)
* [x] [Book the sipgate.io feature](https://www.sipgate.de/basic/feature-store/sipgate.io-s)
* [x] [Enter an URL for incoming/outgoing calls in the dashboard](https://console.sipgate.com/webhooks/urls)

### Usage with simquadrat

* [x] [Order a simquadrat SIM](https://www.simquadrat.de)
* [x] [Book the sipgate.io feature](https://www.simquadrat.de/feature-store/sipgate.io-s)
* [x] [Enter an URL for incoming/outgoing calls in the dashboard](https://console.sipgate.com/webhooks/urls)


The POST request
================

When enabled, sipgate.io sends POST requests with an `application/x-www-form-urlencoded` payload for each of call involving your sipgate account. Depending on the type of request it contains the following parameters:

### New call

Parameter    | Description
------------ | -----------
event        | "newCall"
from         | The calling number (e.g. `"492111234567"` or `"anonymous"`)
to           | The called number (e.g. `"4915791234567"`)
direction    | The direction of the call (either `"in"` or `"out"`)
callId       | A unique alphanumeric identifier to match events to specific calls (This `callId` is the same as the `session_id`, should you [initiate the call via the sipgate XML-RPC API](http://book.sipgate.io/content/click2call.html))
user[]       | The sipgate user(s) involved. It is the name of the calling user when direction is `"out"`, or of the users receiving the call when direction is `"in"`. Group calls may be received by multiple users. In that case a `"user[]"` parameter is set for each of these users. It is always `"user[]"` (not `"user"`), even if only one user is involved.
userId[]     | The IDs of sipgate user(s) involved (e.g. `w0`).
fullUserId[] | The full IDs of sipgate user(s) involved (e.g. `1234567w0`).

You can simulate this POST request and test your server with a cURL command:

```sh
curl -X POST --data "event=newCall&from=492111234567&to=4915791234567&direction=in&callId=123456&user[]=Alice&user[]=Bob&userId[]=w0&userId[]=w1&fullUserId[]=1234567w0&fullUserId[]=1234567w1" http://localhost:3000
```


Optional Parameter | Description
-------------------|------------
diversion          | If a call was diverted before it reached sipgate.io this contains the originally dialed number.

### Answer

If you set the ["onAnswer" attribute](#onanswer) sipgate.io will push an answer-event, when
a call is answered by the other party.

Parameter       | Description
--------------- | -----------
event           | "answer"
callId          | Same as in newCall-event for a specific call
user            | Name of the user who answered this call. Only incoming calls can have this parameter
userId          | The ID of sipgate user(s) involved (e.g. `w0`).
fullUserId      | The full ID of sipgate user(s) involved (e.g. `1234567w0`).
from            | The calling number (e.g. `"492111234567"` or `"anonymous"`)
to              | The called number (e.g. `"4915791234567"`)
direction       | The direction of the call (either `"in"` or `"out"`)
answeringNumber | The number of the answering destination. Useful when redirecting to multiple destinations

You can simulate this POST request and test your server with a cURL command:

```sh
curl -X POST --data "event=answer&callId=123456&user=John+Doe&userId=w0&fullUserId=1234567w0&from=492111234567&to=4915791234567&direction=in&answeringNumber=21199999999" http://localhost:3000
```

Optional Parameter | Description
-------------------|------------
diversion          | If a call was diverted before it reached sipgate.io this contains the originally dialed number.


### Call hangup

If you set the ["onHangup" attribute](#onhangup) sipgate.io will push a hangup-event
when the call ends.

Parameter       | Description
--------------- | -----------
event           | "hangup"
cause           | The cause for the hangup event (see [table](#hangup-causes) below)
callId          | Same as in newCall-event for a specific call
from            | The calling number (e.g. `"492111234567"` or `"anonymous"`)
to              | The called number (e.g. `"4915791234567"`)
direction       | The direction of the call (either `"in"` or `"out"`)
answeringNumber | The number of the answering destination. Useful when redirecting to multiple destinations

You can simulate this POST request and test your server with a cURL command:

```sh
curl -X POST --data "event=hangup&cause=normalClearing&callId=123456&from=492111234567&to=4915791234567&direction=in&answeringNumber=4921199999999" http://localhost:3000
```

Optional Parameter | Description
-------------------|------------
diversion          | If a call was diverted before it reached sipgate.io this contains the originally dialed number.

#### Hangup causes

Hangups can occur due to these causes:

Cause           | Description
--------------- | -----------
normalClearing  | One of the participants hung up after the call was established
busy            | The called party was busy
cancel          | The caller hung up before the called party picked up
noAnswer        | The called party rejected the call (e.g. through a DND setting)
congestion      | The called party could not be reached
notFound        | The called number does not exist or called party is offline
forwarded       | The call was forwarded to a different party


### DTMF

If you ["gather"](#gather) users' dtmf reactions, this result is pushed as an event to the url specified in the ["onData" attribute](#ondata) with the following parameters: 

Parameter | Description
--------- | -----------
event     | "dtmf"
dtmf      | Digit(s) the user has entered. If no input is received, the value of dtmf will be empty.
callId    | Same as in newCall-event for a specific call

You can simulate this POST request and test your server with a cURL command:

```sh
curl -X POST --data "event=dtmf&dtmf=1&callId=123456" http://localhost:3000
```


The XML response
============
After sending the POST request sipgate.io will accept an XML response to determine what to do. Make sure to set ```application/xml``` in the ```Content-Type``` header of your response.

sipgate.io currently supports the following responses for incoming and outgoing calls:

Action            | Description
----------------- | -----------
[Dial](#dial)     | Send call to voicemail or external number
[Play](#play)     | Play a sound file
[Gather](#gather) | Collects digits that a caller enters with the telephone keypad.
[Reject](#reject) | Reject call or pretend to be busy
[Hangup](#hangup) | Hang up the call

Additional to actions, the response can specify urls which shall be called by sipgate.io on certain call-events. Specify these urls via xml-attributes in the response-tag.

Url                   | Description
--------------------- | -----------
[onAnswer](#onanswer) | Receives a POST-request as soon as someone answers the call. The response to that request is discarded.
[onHangup](#onhangup) | Receives a POST-request as soon as the call ends for whatever reason. The response to that request is discarded.

Dial
----

Redirect the call and alter your caller id ([call charges apply](https://www.simquadrat.de/tarife/mobile)). Calls with ```direction=in``` can be redirected to up to 5 targets.

Attribute | Possible values                                              | Default value
--------- | ------------------------------------------------------------ | -------------
callerId  | Number in [E.164](http://en.wikipedia.org/wiki/E.164) format | Account settings
anonymous | true, false                                                  | Account / phone settings

Possible targets for the dial command:

Target    | Description
--------- | -----------
Number    | Send call to an external number (has to be in [E.164](http://en.wikipedia.org/wiki/E.164) format)
Voicemail | Send call to [voicemail](https://www.simquadrat.de/feature-store/voicemail) (feature has to be booked)


**Example 1: Redirect call**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Dial>
		<Number>4915799912345</Number>
	</Dial>
</Response>
```

**Example 2: Send call to voicemail**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Dial>
		<Voicemail />
	</Dial>
</Response>
```

**Example 3: Suppress phone number**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Dial anonymous="true">
		<Number>4915799912345</Number>
	</Dial>
</Response>
```

**Example 4: Set custom caller id for outgoing call**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Dial callerId="492111234567">
		<!-- Originally dialed number, extracted from POST request -->
		<Number>4915799912345</Number>
	</Dial>
</Response>
```

**Example 5: Redirect incoming call to multiple destinations**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Dial>
		<Number>4915799912345</Number>
		<Number>492111234567</Number>
	</Dial>
</Response>
```

When the call is answered, the resulting answer-event reports the answering destination in a field called ```answeringNumber```.

Play
----

Play a given sound file. Afterwards the call is delivered as it would have been without playing the sound file.

Target    | Description
--------- | -----------
Url       | Play a sound file from a given URL


**Example 1: Play a sound file**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Play>
		<Url>http://example.com/example.wav</Url>
	</Play>
</Response>
```

**Please note:** Currently the sound file needs to be a mono 16bit PCM WAV file with a sampling rate of 8kHz. You can use conversion tools like the open source audio editor [Audacity](http://audacity.sourceforge.net/) to convert any sound file to the correct format.

Linux users might want to use ```mpg123``` to convert the file:
```sh
mpg123 --rate 8000 --mono -w output.wav input.mp3
```

Gather
----

Gather collects digits that a caller enters with the telephone keypad. The onData attribute is mandatory and takes an absolute URL as a value.

Attribute | Possible values | Default value
--------- | --------------- | -------------
type      | dtmf | dtmf
onData    | absolute URL | -
maxDigits | integer >= 1 | 1
timeout (ms) | integer >= 1 | 2000

**Nesting Rules**

The following verbs can be nested within ```<Gather>```:

* [Play](#play) The timeout starts after the sound file has finished playing. After the first digit is received the audio will stop playing.

**Example 1: DTMF with sound file and additional parameters**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Gather onData="http://localhost:3000/dtmf" maxDigits="3" timeout="10000">
		<Play>
			<Url>https://example.com/example.wav</Url>
		</Play>
	</Gather>
</Response>
```

Reject
------

Pretend to be busy or block unwanted calls.

Attribute | Possible values | Default value
--------- | --------------- | -------------
reason    | rejected, busy  | rejected


**Example 1: Reject call**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Reject />
</Response>
```

**Example 2: Reject call signaling busy**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Reject reason="busy" />
</Response>
```

Hangup
------

Hang up calls

**Example: Hang up call**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Hangup />
</Response>
```

onAnswer
------------

**Example: Request notification for call being answered**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response onAnswer="http://localhost:3000/answer" />
```

onHangup
------------

**Example: Request notification for call hangup**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Response onHangup="http://localhost:3000/hangup" />
```

More to come
------------
Stay tuned...



Code Examples
===============

To get you started we maintain server examples for:

* [Node.js](https://github.com/sipgate/sipgate.io/tree/master/examples/nodejs)
* [PHP](https://github.com/sipgate/sipgate.io/tree/master/examples/php)

There are also examples in:

* [C++](https://github.com/sipgate/sipgate.io/tree/master/examples/c++)
* [Clojure](https://github.com/sipgate/sipgate.io/tree/master/examples/clojure)
* [Common Lisp](https://github.com/sipgate/sipgate.io/tree/master/examples/commonlisp)
* [Go](https://github.com/sipgate/sipgate.io/tree/master/examples/go)
* [Java](https://github.com/sipgate/sipgate.io/tree/master/examples/java)
* [Perl](https://github.com/sipgate/sipgate.io/tree/master/examples/perl)
* [Prolog](https://github.com/sipgate/sipgate.io/tree/master/examples/prolog)
* [Python](https://github.com/sipgate/sipgate.io/tree/master/examples/python)
* [Ruby](https://github.com/sipgate/sipgate.io/tree/master/examples/ruby)
* [Scala](https://github.com/sipgate/sipgate.io/tree/master/examples/scala)

FAQ
===============

What happens when a call is transferred?
--------------

The transferred call is a new call. Scenario: Jennifer calls Doc Brown, he answers and then transfers the call to Marty and she picks up. 
 Here's what sipgate.io sends to your server:

1. newCall (user: Doc, callId: 12111955)
2. answer (user: Doc, callId: 12111955)
  * [Doc dials *3 \<Marty's extension\>]
3. newCall (user: Marty, callId: 21102015, origCallId: 12111955, diversion: \<Doc's number\>, from: \<Jennifer's number\>)
4. answer (user: Marty, callId: 21102015, origCallId: 12111955, diversion|answeringNumber: \<Doc's number\>, from: \<Jennifer's number\>)
5. hangup (user: Doc, callId: 12111955)
6. hangup (user: Marty, callId: 21102015, origCallId: 12111955, diversion|answeringNumber: \<Doc's number\>, from: \<Jennifer's number\>)

As you can see, the ```callId``` changes with the transfer along with a new field ```origCallId```.

This is what sipgate.io sends, in case Marty does not pick up:

1. newCall (user: Doc, callId: 12111955)
2. answer (user: Doc, callId: 12111955)
  * [Doc dials *3 \<Marty's extension\>]
3. newCall (user: Marty, callId: 21102015, origCallId: 12111955, diversion: \<Doc's number\>, from: \<Jennifer's number\>)
4. hangup (user: Doc, callId: 12111955)
5. hangup (user: Marty, callId: 21102015, origCallId: 12111955, diversion: \<Doc's number\>, from: \<Jennifer's number\>)

How is forwarding signaled?
--------------

The forwarded call is handled as a new call. Let's assume the previous scenario: Jennifer calls Doc Brown but his line is busy so the call is forwarded to Marty who in turn picks up the call.  
Here are the pushes sipgate.io will send to your server:

1. newCall (user: Doc, callId: 12111955)
2. hangup (cause: forwarded, callId: 12111955)
3. newCall (user: Marty, callId: 21102015, origCallId: 12111955, diversion: \<Doc's number\>, from: \<Jennifer's number\>)
4. answer (user: Marty, callId: 21102015, origCallId: 12111955, diversion: \<Doc's number\>, from: \<Jennifer's number\>)
5. hangup (user: Marty, callId: 21102015, origCallId: 12111955, diversion: \<Doc's number\>, from: \<Jennifer's number\>)

I get a lot of event during forwards and transfers, do I have to pay for all of them?
-------------------------
The answer is: It depends.  
If you transfer or forward a call within the same sipgate account then the answer is no. But if you forward or transfer a call to a different sipgate account or an external number then those pushes are being billed to your account.


Troubleshooting
===============

sipgate.io Log
--------------

You can enable logging for debugging purposes from your dashboard. You will find each request and the corresponding response in the logging table.

How do I inspect network traffic?
---------------------------------

You can use ```ngrep``` to inspect the incoming requests on your side:
```sh
sudo ngrep -dany -Wbyline port 3000
```



A word about security
=====================

HTTP vs. HTTPS
--------------

We strongly encourage you to use a HTTPS server. Although we support plain HTTP connections we do not recommend pushing sensitive call details over unencrypted connections. By default sipgate.io does not accept [self-signed certificates](http://stackoverflow.com/a/10176685). If you use simquadrat or sipgate basic, you can allow self-signed certs in your dashboard. In sipgate team you cannot make an exception.

Furthermore, you can add your public certificate to protect the connection against man-in-the-middle attacks. Your certificate is validated by our server.

In order to work your certificate has to be in the following format
```
-----BEGIN CERTIFICATE-----
MIIDXTCCAkWgAwIBAgIJAJC1HiIAZAiIMA0GCSqGSIb3Df
BAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEwHwYDVx
aWRnaXRzIFB0eSBMdGQwHhcNMTExMjMxMDg1OTQ0WhcNMT
A .... MANY LINES LIKE THAT ....
JjyzfN746vaInA1KxYEeI1Rx5KXY8zIdj6a7hhphpj2E04
C3Fayua4DRHyZOLmlvQ6tIChY0ClXXuefbmVSDeUHwc8Yu
B7xxt8BVc69rLeHV15A0qyx77CLSj3tCx2IUXVqRs5mlSb
vA==
-----END CERTIFICATE-----
```

Authentication
--------------

sipgate.io supports HTTP Basic Authentication. You can include your username and password within the URL (e.g. `https://username:password@example.com:8080`).

Emergency calls
===============

sipgate.io does not process emergency calls. Emergency calls are immediately put through to emergency services.


Help us make it better
======================

Please tell us how we can improve sipgate.io. If you have a specific feature request, found a bug or would like to add an example, please use [GitHub Issues](https://github.com/sipgate/sipgate.io/issues) or fork these docs and send a [pull request](https://github.com/sipgate/sipgate.io/pulls) with your improvements.
