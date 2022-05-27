#include <WifiManager.h>

/**
 * @brief Oppretter en WifiManager med standard innstillinger.
 * Er fortrinnsvis en forenkling til bruk ved testing, altsaa naar
 * man har samme nettverksnavn, passord, domene og port. 
 */
WifiManager::WifiManager() 
{
  // WiFi network name and password:
  _ssid = "Oppe IAV4";
  _password = "pappabetaler";

  // Internet domain to request from:
  _domain = "https://www.aftenposten.no/";
  _ntp = "europe.pool.ntp.org";
  _port = 80;
}

/**
 * @brief Oppretter en WifiManager med innstillinger fra argumentene
 * som sendes i metodekallet.
 * @param ssid : nettverksnavnet
 * @param password : nettverkspassordet
 * @param domain : domenet man skal koble til
 * @param port : porten man skal bruke
 */
WifiManager::WifiManager(char * ssid, char * password, char * domain, int port) 
{
  // WiFi network name and password:
  _ssid = ssid;
  _password = password;

  // Internet domain to request from:
  _domain = domain;
  _port = port;

  _ntp = "europe.pool.ntp.org";
}

/**
 * @brief Initer WiFi-objektet fra WiFi-klassen (WiFiClass i WiFi.h) og
 * kobler til det internettet som ble oppgitt i konstruktoeren.
 */
void WifiManager::connectToWiFi()
{
  
  Serial.println("Connecting to WiFi network: " + String(_ssid));

  WiFi.begin(_ssid, _password);

  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("WiFi connected!");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

/**
 * @brief 
 * 
 */
void WifiManager::requestURL()
{
  
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }
  
  Serial.println("Connecting to domain: " + String(_domain));

  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  if (!client.connect(_domain, _port))
  {
    Serial.println("connection failed");
    return;
  }

  Serial.println("Connected!");

  // This will send the request to the server
  client.print((String)"GET / HTTP/1.1\r\n" +
               "Host: " + String(_domain) + "\r\n" +
               "Connection: close\r\n\r\n");
  
  unsigned long timeout = millis();

  while (client.available() == 0) 
  {
    if (millis() - timeout > 5000) 
    {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
  }

  // Read all the lines of the reply from server and print them to Serial
  while (client.available()) 
  {
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }

  Serial.println();
  Serial.println("closing connection");
  client.stop();
}

/**
 * @brief 
 * 
 */
void WifiManager::requestTime()
{
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }

  struct tm timeinfo;
  if(!getLocalTime(&timeinfo)){
    Serial.println("Failed to obtain time");
    return;
  }

  Serial.println(&timeinfo, "%A, %B %d %Y %H:%M:%S");
}

void WifiManager::sendUpdateRequest(char * vaxType)
{
  
}
