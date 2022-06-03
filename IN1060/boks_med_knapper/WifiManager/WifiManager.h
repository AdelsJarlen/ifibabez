#ifndef WifiManager_h
#define WifiManager_h

#include <WiFi.h>
#include <time.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <HardwareSerial.h>

class WifiManager
{
    public:
        WifiManager(HardwareSerial& hwSerial);
        WifiManager(char * ssid, char * password, char * domain, int port, HardwareSerial& hwSerial);
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
        char * _scriptID;
        char * _scriptURL;
        int _port;
        int _vaxNumber;
        HardWareSerial& _hwSerial;
        unsigned long _gmtOffset;
        unsigned int _dstOffset;
    ;
};

#endif
