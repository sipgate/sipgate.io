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
to        | The called number (e.g. `"492111234567"`)

That's all!

#### cURL

You can simulate this POST request and test your server with a simple cURL command:

```shell
curl -X POST --no-check-certificate\
--data "from=492111234567&to=492111234567" https://example.com:3000
```

***

## Examples

We compiled a collection of examples to get you started:

* [Java](https://github.com/sipgate/Push-API/tree/master/examples/java)
* [Node.js](https://github.com/sipgate/Push-API/tree/master/examples/node.js)
* [Perl](https://github.com/sipgate/Push-API/tree/master/examples/perl)
* [PHP](https://github.com/sipgate/Push-API/tree/master/examples/php)
* [Python](https://github.com/sipgate/Push-API/tree/master/examples/python)
* [Ruby](https://github.com/sipgate/Push-API/tree/master/examples/ruby)

***

## A word about security

### HTTP vs. HTTPS

We strongly encourage you to use a HTTPS server. Although we support plain HTTP connections we do not recommend pushing sensitive call details over unencrypted connections. The API accepts [self-signed certificates](http://stackoverflow.com/a/10176685) to support secured connections.

### Authentication

The API supports HTTP Basic Authentication. You can include your username and password within the URL (e.g. `https://username:password@example.com:8080`).

***

## Help us make it better

Please tell us how we can make the API better. If you have a specific feature request or if you found a bug, please use [GitHub Issues](https://github.com/sipgate/Push-API/issues) or fork these docs and send a [pull request](https://github.com/sipgate/Push-API/pulls) with your improvements.

