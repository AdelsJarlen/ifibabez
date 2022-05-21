#ifndef LED_h
#define LED_h
#include <Adafruit_NeoPixel.h>

class LED
{
    public:
        LED(int _led_count, int _pin);
        void signal(int index);
    ;

    private:
        Adafruit_NeoPixel strip;
        int _pin;
        int _led_count;
    ;
};

#endif
