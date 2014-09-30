# simquadrat Push-API

This README documents the simquadrat Push-API functionality. A demo page can be found [here](https://api.simquadrat.de).

## Checklist

* [Order a simquadrat SIM](https://www.simquadrat.de)
* [Book the Push-API feature](https://www.simquadrat.de/feature-store/push-api)
* [Enter your URL in the simquadrat dashboard](https://www.simquadrat.de/dashboard)
* Hack away

***

## Basics

### The POST request

Our API sends a simple POST request with an `application/x-www-form-urlencoded` payload. The payload contains the following parameters:

Parameter | Description
--------- | -----------
from      | The calling number (e.g. `"492111234567"` or `"anonymous"`)
to        | The called number (e.g. `"4915791234567"`)

That's all!

#### cURL

You can simulate this POST request and test your server with a simple cURL command:

```shell
curl -X POST --data "from=492111234567&to=4915791234567" http://localhost:3000
```

***

## Server Examples

We compiled a collection of examples to get you started:

* [Clojure](https://github.com/sipgate/Push-API/tree/master/examples/clojure)
* [Common Lisp](https://github.com/sipgate/Push-API/tree/master/examples/commonlisp)
* [Go](https://github.com/sipgate/Push-API/tree/master/examples/go)
* [Java](https://github.com/sipgate/Push-API/tree/master/examples/java)
* [Node.js](https://github.com/sipgate/Push-API/tree/master/examples/nodejs)
* [PHP](https://github.com/sipgate/Push-API/tree/master/examples/php)
* [Perl](https://github.com/sipgate/Push-API/tree/master/examples/perl)
* [Prolog](https://github.com/sipgate/Push-API/tree/master/examples/prolog)
* [Python](https://github.com/sipgate/Push-API/tree/master/examples/python)
* [Ruby](https://github.com/sipgate/Push-API/tree/master/examples/ruby)
* [Scala](https://github.com/sipgate/Push-API/tree/master/examples/scala)

***

## A word about security

### HTTP vs. HTTPS

We strongly encourage you to use a HTTPS server. Although we support plain HTTP connections we do not recommend pushing sensitive call details over unencrypted connections. By default, the API does not accept [self-signed certificates](http://stackoverflow.com/a/10176685), but you can enable them in the dashboard.

### Authentication

The API supports HTTP Basic Authentication. You can include your username and password within the URL (e.g. `https://username:password@example.com:8080`).

***

## Help us make it better

Please tell us how we can make the API better. If you have a specific feature request or if you found a bug or would like to add an example, please use [GitHub Issues](https://github.com/sipgate/Push-API/issues) or fork these docs and send a [pull request](https://github.com/sipgate/Push-API/pulls) with your improvements.

