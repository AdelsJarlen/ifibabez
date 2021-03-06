#ifndef VaxMonitor_h
#define VaxMonitor_h

#include <OneButton.h>
#include <VaxLED.h>
#include <VaxBuzzer.h>
#include <VaxWifiManager.h>

/**
 * @brief Klassedefinisjon for VaxMonitor. 
 * Holder styr paa alle objektene av VaxLED-klassen, VaxBuzzeren
 * og VaxWifiManageren. Oppretter instanser av OneButton med riktig funksjonskall
 * til hver vaksinetype.
 */
class VaxMonitor 
{
    public:
        VaxMonitor(uint8_t pin1, uint8_t pin2, uint8_t pin3, VaxLED& led1, VaxLED& led2, VaxLED& led2, VaxBuzzer& buzzer, VaxWifiManager& VaxWifiManager); // konstruktoer
        void startup();
        void refresh(); // sjekker status paa knappene
    ;

    private:        
        VaxBuzzer& _buzzer;

        VaxWifiManager& _VaxWifiManager;

        VaxLED& _led1;
        VaxLED& _led2;
        VaxLED& _led3;

        OneButton _btn1;
        OneButton _btn2;
        OneButton _btn3;

        int _vaxNumber;

        static void btn1Clicked();
        static void btn2Clicked();
        static void btn3Clicked();
    ;
};

#endif