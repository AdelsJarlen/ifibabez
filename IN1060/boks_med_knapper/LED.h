#ifndef LED_H
#define LED_H
#include <Adafruit_NeoPixel.h>

class LED
{
    public:
        void signal(int index);
    ;

    private:
        Adafruit_NeoPixel strip;
    ;
};

#endif