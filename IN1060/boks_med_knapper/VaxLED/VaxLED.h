#ifndef VaxLED_H
#define VaxLED_H

#include <Adafruit_NeoPixel.h>

class VaxLED
{
    public:
        VaxLED(int led_count, int pin);
        void signal(int index, int r, int g, int b);
        void off();
    ;

    private:
        Adafruit_NeoPixel flora;
        int _pin;
        int _led_count;
        int _default_brightness = 200;
    ;
};

#endif
