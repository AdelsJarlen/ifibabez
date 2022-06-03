#include <WifiManager.h>

/**
 * @brief Oppretter en WifiManager med standard innstillinger.
 * Er fortrinnsvis en forenkling til bruk ved testing, altsaa naar
 * man har samme nettverksnavn, passord, domene og port. 
 * @param hwSerial : referanse til et serial-objekt for logging
 */
WifiManager::WifiManager(HardWareSerial& hwSerial) : _hwSerial(hwSerial) 
{
  _ssid = "WIFI_NAME";
  _password = "WIFI_PASSWORD";

  _domain = "http://example.com/";
  _port = 80;

  _ntp = "europe.pool.ntp.org";
  _gmtOffset = 3600;
  _dstOffset = 3600;

  _scriptID = "AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t"
  _scriptURL = "https://script.google.com/macros/s/AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t/exec"
}

/**
 * @brief Oppretter en WifiManager med innstillinger fra argumentene
 * som sendes i metodekallet.
 * @param ssid : nettverksnavnet
 * @param password : nettverkspassordet
 * @param domain : domenet man skal koble til
 * @param scriptID : IDen til Google Script-webappen som skal motta requests
 * @param port : porten man skal bruke
 * @param hwSerial : referanse til et Serial-objekt for aa logge det som skjer
 */
WifiManager::WifiManager(char* ssid, char* password, char* domain, int port, HardWareSerial& hwSerial) : _hwSerial(hwSerial) 
{
  // WiFi network name and password:
  _ssid = ssid;
  _password = password;

  // Internet domain to request from:
  _domain = domain;
  _port = port;


  _ntp = "europe.pool.ntp.org";
  _gmtOffset = 3600;
  _dstOffset = 3600;

  _scriptID = "AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t"
  _scriptURL = "https://script.google.com/macros/s/AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t/exec"
}

/**
 * @brief Initer WiFi-objektet fra WiFi-klassen (WiFiClass i WiFi.h) og
 * kobler til det internettet som ble oppgitt i konstruktoeren.
 */
void WifiManager::connectToWiFi()
{
  
  _hwSerial.println("Connecting to WiFi network: " + String(_ssid));

  WiFi.begin(_ssid, _password);

  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    _hwSerial.print(".");
  }

  _hwSerial.println();
  _hwSerial.println("WiFi connected!");
  _hwSerial.print("IP address: ");
  _hwSerial.println(WiFi.localIP());
}

/**
 * @brief Requester en URL og printer HTTP-responsen linje for linje.
 * Fortrinnsvis til bruk ved testing og debugging.
 */
void WifiManager::requestURL()
{
  
  // sjekker om kortet er koblet til WiFi, kobler til hvis ikke
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }
  
  _hwSerial.println("Connecting to domain: " + String(_domain));

  // oppretter en WiFiClient for aa opprette en connection til domenet
  WiFiClient client;
  
  if (!client.connect(_domain, _port))
  {
    _hwSerial.println("connection failed");
    return;
  }

  _hwSerial.println("Connected!");

  // sender selve GET-requesten til domenet
  client.print((String)"GET / HTTP/1.1\r\n" +
               "Host: " + String(_domain) + "\r\n" +
               "Connection: close\r\n\r\n");
  
  // sjekker requesten opp mot et timeout-intervall (5 sekunder)
  unsigned long timeout = millis();

  while (client.available() == 0) 
  {
    if (millis() - timeout > 5000) 
    {
      _hwSerial.println(">>> Client Timeout !");
      client.stop(); // stopper klienten hvis det tar for lang tid
      return;
    }
  }

  // printer responseBodyen linje for linje
  while (client.available()) 
  {
    String line = client.readStringUntil('\r');
    _hwSerial.print(line);
  }

  _hwSerial.println();
  _hwSerial.println("closing connection");
  client.stop(); // lukker klienten naar alt er ferdig
}

/**
 * @brief Henter dato og klokkeslett fra en av NTP-serverne i
 * Europa. Kan brukes dersom vi faar til aa sende print request med
 * custom label/txt til printeren.
 */
void WifiManager::requestTime()
{

  // sjekker om WiFi er koblet til
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }

  configTime(_gmtOffset, _dstOffset, _ntp);

  // henter ut en Timestructure fra epochTime fra NTP-serveren
  struct tm timeinfo;

  if(!getLocalTime(&timeinfo)){
    _hwSerial.println("Failed to obtain time");
    return;
  }

  // skriver ut formatert tidspunkt med standardformat (dd/mm/YYYY kl. HH:MM)
  _hwSerial.println(&timeinfo, "%d/%m/%Y kl. %H:%M");
}

/**
 * @brief Sender en POST-request til Google Script med
 * vaksinetype og vaksinenummer.
 * @param vaxType : vaksinetypen som har blitt trykket, sendes fra knappen
 */
void WifiManager::sendUpdateRequest(char * vaxType, int vaxNumber)
{
  // sjekker om WiFi er koblet til
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }

  // starter en HTTPclient for aa ha lettere tilgang paa POST-requests
  HTTPClient http;

  _hwSerial.print(_scriptURL);
  _hwSerial.print("Making a request");

  // aapner en connection
  http.begin(url);
  
  // definerer at vi skal sende JSON
  http.addHeader("Content-Type", "application/json");

  // genererer et JSON-objekt med dataen vaar
  StaticJsonDocument<200> jsonDoc;

  jsonDoc["vaxType"] = vaxType;
  jsonDoc["vaxNumber"] = vaxNumber;
  
  // konverterer JSON til String som kan sendes
  String requestBody;
  serializeJson(jsonDoc, requestBody);
  
  // sender selve requesten og lagrer responskoden
  int httpResponseCode = http.POST(requestBody);

  // sjekker status paa handlingen
  if (httpResponseCode > 0) {
    _hwSerial.print("HTTP Response code: ");
    _hwSerial.println(httpResponseCode);
    String payload = http.getString();
    _hwSerial.println(payload);
  }
  else {
    _hwSerial.print("Error code: ");
    _hwSerial.println(httpResponseCode);
  }
}
