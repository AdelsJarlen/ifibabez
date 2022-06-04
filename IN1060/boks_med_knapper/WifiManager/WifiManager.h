#ifndef WifiManager_h
#define WifiManager_h

#include <WiFi.h>
#include <time.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

class WifiManager
{
    public:
        WifiManager();
        WifiManager(char * ssid, char * password, char * domain, int port);
        bool connectToWiFi();
        void disconnect();

        bool sendUpdateRequest(char* vaxType, int vaxNumber);
        String requestTime();
        String requestURL();
    ;

    private:
        // wifi settings
        char * _ssid;
        char * _password;

        // google script settings
        char * _scriptID;
        char * _scriptURL;

        // ntp server settings
        char * _ntp;
        unsigned long _gmtOffset;
        unsigned int _dstOffset;
    ;
};

#endif
