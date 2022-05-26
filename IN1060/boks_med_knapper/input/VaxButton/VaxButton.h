#ifndef VaxButton_h
#define VaxButton_h

#include <OneButton.h>
#include <NeoPixelLED.h>

class VaxButton 
{
    public:
        VaxButton(int pin, NeoPixelLED& npLED);
        void tick();
        void startLED(int index, int r, int g, int b);
    ;

    private:
        int _pin;
        NeoPixelLED& _npLED;
        OneButton _btn;
        static void handleClick(void *ptr);
    ;
};

#endif