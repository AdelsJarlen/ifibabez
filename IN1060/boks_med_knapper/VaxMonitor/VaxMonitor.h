#ifndef VaxMonitor_h
#define VaxMonitor_h

#include <OneButton.h>
#include <VaxLED.h>
#include <VaxBuzzer.h>
#include <WifiManager.h>

/**
 * @brief Klassedefinisjon for VaxButton. 
 * Holder styr paa det custom VaxLED-objektet, AdaptedButton 
 * og initialiserer en OneButton-knapp med automatisk debouncing
 * for aa styre begge.
 */
class VaxMonitor 
{
    public:
        VaxMonitor(int[] pins, VaxLED& npLED, VaxBuzzer& buzzer, WifiManager& wifiManager); // konstruktoer
        void refresh(); 
    ;

    private:
        int[] _pins;
        
        VaxLED& _npLED;
        OneButton _btn1;
        OneButton _btn2;
        OneButton _btn3;
        VaxBuzzer& _buzzer;
        WifiManager& _wifiManager;

        static void handleClick(void *ptr);
        void startLED(int index, int r, int g, int b);
        void playTone();
    ;
};

#endif