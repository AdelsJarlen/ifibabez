#include <WifiManager.h>

/**
 * @brief Oppretter en WifiManager med standard innstillinger.
 * Er fortrinnsvis en forenkling til bruk ved testing, altsaa naar
 * man har samme nettverksnavn, passord, domene og port
 */
WifiManager::WifiManager()
{
  // default nettverk og passord
  _ssid = "WIFI_NAME";
  _password = "WIFI_PASSWORD";

  // default NTP-server-innstillinger
  _ntp = "europe.pool.ntp.org";
  _gmtOffset = 3600;
  _dstOffset = 3600;

  // detaljer til Google Scriptet som skal brukes
  _scriptID = "AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t"
  _scriptURL = "https://script.google.com/macros/s/AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t/exec"
}

/**
 * @brief Oppretter en WifiManager med innstillinger fra argumentene
 * som sendes i metodekallet
 * @param ssid : nettverksnavnet
 * @param password : nettverkspassordet
 * @param scriptID : IDen til Google Script-webappen som skal motta requests
 */
WifiManager::WifiManager(char* ssid, char* password)
{
  // nettverksnavn og passord
  _ssid = ssid;
  _password = password;

  // default NTP-server-innstillinger
  _ntp = "europe.pool.ntp.org";
  _gmtOffset = 3600;
  _dstOffset = 3600;

  // detaljer til Google Scriptet som skal brukes
  _scriptID = "AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t"
  _scriptURL = "https://script.google.com/macros/s/AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t/exec"
}

/**
 * @brief Initer WiFi-objektet fra WiFi-klassen (WiFiClass i WiFi.h) og
 * kobler til det internettet som ble oppgitt i konstruktoeren
 */
bool WifiManager::connectToWiFi()
{
  WiFi.begin(_ssid, _password); // starter tilkoblingen

  long timeout = 30000; // timeout paa 30 sekunder

  unsigned long startTime = millis();
  
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    if (millis() - startTime > timeout) {return false}; // stopper hvis det har gaatt mer en 30 sekunder
  }
  
  return true; // tilkobling vellykket
}

/**
 * @brief Requester en URL og printer HTTP-responsen linje for linje.
 * Fortrinnsvis til bruk ved testing og debugging
 */
String WifiManager::requestURL(char* url)
{
  
  // sjekker om kortet er koblet til WiFi, kobler til hvis ikke
  if (WiFi.status() != WL_CONNECTED)
  {
    if (!connectToWiFi()) {return};
  }
  
  // oppretter klienter for aa haandtere connection, request og response
  WiFiClient client;
  HTTPClient http;

  // aapner en connection
  http.begin(client, _domain);

  // sender selve GET-requesten til domenet
  http.GET()

  // lagrer responsen som en String (OBS: kan kreve mye minne, funker ofte ikke paa vanlig Arduino Uno)
  String response = http.getString();
  
  http.end();

  return response;
}

/**
 * @brief Henter dato og klokkeslett fra en av NTP-serverne i
 * Europa. Kan brukes dersom vi faar til aa sende print request med
 * custom label/txt til printeren
 */
String WifiManager::requestTime()
{

  // sjekker om WiFi er koblet til
  if (WiFi.status() != WL_CONNECTED)
  {
    if (!connectToWiFi()) {return};
  }

  // setter tidssonen og ntp-serveren
  configTime(_gmtOffset, _dstOffset, _ntp);

  // henter ut en Timestructure som epochTime fra NTP-serveren
  struct tm timeinfo;

  // konverterer til localtime med vaare tidssone-innstillinger
  if (!getLocalTime(&timeinfo)) {return};

  // konverterer streamen fra timeinfo til en character buffer
  char timeCharBuff[50];
  strftime(timeCharBuff, sizeof(timeCharBuff), "%d/%m/%Y kl. %H:%M", &timeinfo);

  // konverterer charbufferen til en String i standardformat (dd/mm/YYYY kl. HH:MM)
  String timeString(timeCharBuff);

  return timeString;
}

/**
 * @brief Sender en POST-request til Google Script med
 * vaksinetype og vaksinenummer
 * @param vaxType : vaksinetypen som har blitt trykket, sendes fra knappen
 */
bool WifiManager::sendUpdateRequest(char * vaxType, int vaxNumber)
{
  // sjekker om WiFi fortsatt er koblet til, kobler til på nytt hvis ikke
  if (WiFi.status() != WL_CONNECTED)
  {
    if (!connectToWiFi()) {return false};
  }
 
  // oppretter klienter for aa haandtere connections, requests og responses
  WiFiClient client;
  HTTPClient http;

  // aapner en connection
  http.begin(client, _scriptURL);
  
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
  return (httpResponseCode > 0);
}

/**
 * @brief Kobler fra WiFi
 */
void WifiManager::disconnect()
{
  WiFi.disconnect();
}
