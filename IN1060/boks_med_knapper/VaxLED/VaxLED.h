#ifndef VaxLED_H
#define VaxLED_H

#include <Adafruit_NeoPixel.h>

/**
 * @brief Klassedefinisjon for VaxLed.
 */
class VaxLED
{
    public:
        VaxLED(int led_count, uint8_t pin);
        void signal(int index, int r, int g, int b, int i);
        void off();
    ;

    private:
        Adafruit_NeoPixel flora;
        uint8_t _pin;
        int _led_count;
        int _default_brightness = 200;
    ;
};

#endif
