#ifndef VaxButton_h
#define VaxButton_h

#include <OneButton.h>
#include <NeoPixelLED.h>

/**
 * @brief Klassedefinisjon for VaxButton. 
 * Holder styr paa det custom NeoPixelLED-objektet og 
 * initialiserer en OneButton-knapp med automatisk debouncing
 * for aa styre LEDen.
 */
class VaxButton 
{
    public:
        VaxButton(int pin, NeoPixelLED& npLED); // konstrukoer
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