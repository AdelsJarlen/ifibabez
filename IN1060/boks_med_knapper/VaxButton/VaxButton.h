#ifndef VaxButton_h
#define VaxButton_h

#include <OneButton.h>
#include <NeoPixelLED.h>
#include <VaxBuzzer.h>
#include <WifiManager.h>

/**
 * @brief Klassedefinisjon for VaxButton. 
 * Holder styr paa det custom NeoPixelLED-objektet, AdaptedButton 
 * og initialiserer en OneButton-knapp med automatisk debouncing
 * for aa styre begge.
 */
class VaxButton 
{
    public:
        VaxButton(int pin, char* vaxType, NeoPixelLED& npLED, VaxBuzzer& buzzer, WifiManager& wifiManager); // konstrukoer
        void tick(); 
        void startLED(int index, int r, int g, int b);
        void playTone();
    ;

    private:
        int _pin;
        char* _vaxType;
        NeoPixelLED& _npLED;
        OneButton _btn;
        VaxBuzzer& _buzzer;
        WifiManager& _wifiManager;
        static void handleClick(void *ptr);
    ;
};

#endif