#ifndef VaxWifiManager_h
#define VaxWifiManager_h

#include <WiFi.h>
#include <time.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

class VaxWifiManager
{
    public:
        // konstruktoerer
        VaxWifiManager(); // default internett hvis man bruker samme hele tiden
        VaxWifiManager(char * ssid, char * password); // for aa koble til et annet nett

        // kobler til eller fra
        bool connectToWiFi();
        void disconnect();

        // sender requests til Google scriptet, NTP-server eller oppgitt URL
        bool sendUpdateRequest(char* vaxType, int vaxNumber);
        String requestTime();
        String requestURL();
    ;

    private:
        // nettverksinnstillinger
        char * _ssid;
        char * _password;

        // google script-innstillinger
        char * _scriptID;
        char * _scriptURL;

        // ntp server-innstillinger
        char * _ntp;
        unsigned long _gmtOffset;
        unsigned int _dstOffset;
    ;
};

#endif
