#ifndef NP_LED_H
#define NP_LED_H
#include <Adafruit_NeoPixel.h>

class NP_LED
{
    public:
        NP_LED(int _led_count, int _pin);
        void signal(int index);
    ;

    private:
        Adafruit_NeoPixel strip;
        int _pin;
        int _led_count;
        int _default_brightness = 200;
    ;
};

#endif
