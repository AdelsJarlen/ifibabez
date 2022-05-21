#ifndef NeoPixelLED_H
#define NeoPixelLED_H

#include <Adafruit_NeoPixel.h>

class NeoPixelLED
{
    public:
        NeoPixelLED(int _led_count, int _pin);
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
