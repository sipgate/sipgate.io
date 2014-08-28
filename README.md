## Push-API

Die simquadrat [Push-API](https://www.simquadrat.de/feature-store/push-api) ist die erste vollwertige Mobilfunk-API. Helfen Sie uns die API besser zu machen indem Sie sich über die [GitHub Issues](https://github.com/sipgate/Push-API/issues) an der Weiterentwicklung beteiligen oder schicken Sie uns neuen oder besseren Beispielcode über [Pull Requests](https://github.com/sipgate/Push-API/pulls).

### So geht's

Um die API zu nutzen müssen Sie einen Webservice auf einem eigenen Server aufsetzen, der aus dem Internet erreichbar ist. Wenn Sie das [Push-API Feature](https://www.simquadrat.de/feature-store/push-api) im [Feature Store](https://www.simquadrat.de/feature-store/) gebucht haben können Sie die URL Ihres Web Services eintragen und erhalten sofort Benachrichtigungen an die URL bei allen eingehenden Anrufen. Zum Testen können Sie z.B. folgendes PHP-Script verwenden:

```HTML+PHP
<?php
  file_put_contents("/tmp/pushapi.txt", json_encode($_POST));
?>
```

wenn Sie die URL für dieses Script im simquadrat dashboard eintragen und dann eine Rufnummer Ihres simquadrat-Accounts anrufen wird in der Datei die angerufene Nummer und die Nummer des Anrufers erscheinen.

### Variablen

Folgende Variablen werden per POST an Ihr Script übergeben:

#### to
Die angerufene Nummer im E164 Format ( z.B. `"492111234567"` ) 

#### from
Die Nummer des Anrufers im E164 Format ( z.B. `"492111234567"` ) Wenn die nummer unterdrückt wird ist der Wert `"anonymous"`
