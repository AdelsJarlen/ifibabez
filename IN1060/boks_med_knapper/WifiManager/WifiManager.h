#ifndef WifiManager_h
#define WifiManager_h

#include <WiFi.h>
#include <time.h>

class WifiManager
{
    public:
        WifiManager();
        WifiManager(char * ssid, char * password, char * domain, int port);
        void connectToWiFi();
        void requestTime();
        void requestURL();
        void sendUpdateRequest();
    ;

    private:
        char * _ssid;
        char * _password;
        char * _domain;
        char * _ntp;
        int _port;
        unsigned long _gmtOffset;
        unsigned int _dstOffset;
    ;
};

#endif