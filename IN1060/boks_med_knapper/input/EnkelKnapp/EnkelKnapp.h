#ifndef EnkelKnapp_h
#define EnkelKnapp_h
#include <OneButton.h>
#include "NeoPixelLED.h"

class EnkelKnapp 
{
    public:
        EnkelKnapp(int pin, NeoPixelLED npLED);
        void tick();
        void startLED(int index, int r, int g, int b);
    ;

    private:
        int _pin;
        NeoPixelLED npLED;
        OneButton btn;
    ;
};

#endif