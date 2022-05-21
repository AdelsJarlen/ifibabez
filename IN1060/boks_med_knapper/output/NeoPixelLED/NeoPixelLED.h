#ifndef NeoPixelLED_H
#define NeoPixelLED_H

#include <Adafruit_NeoPixel.h>

class NeoPixelLED
{
    public:
        NeoPixelLED(int led_count, int pin);
        void signal(int index, int r, int g, int b);
        void off();
    ;

    private:
        Adafruit_NeoPixel strip;
        int _pin;
        int _led_count;
        int _default_brightness = 200;
    ;
};

#endif
