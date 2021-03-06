#include <VaxMonitor.h>

/**
 * @brief Oppretter et nytt objekt av klassen VaxMonitor med Member Initializer List.
 * @param pin 1-3 : GPIO-pinene knappene er koblet til
 * @param led 1-3 : en referanse til et objekt av klassen VaxLED (LED-stripen som skal brukes)
 * @param buzzer : en referanse til et objekt av klassen VaxBuzzer
 */
VaxMonitor::VaxMonitor(uint8_t pin1, uint8_t pin2, uint8_t pin3, VaxLED& led1, VaxLED& led2, VaxLED& led3, VaxBuzzer& buzzer, VaxWifiManager& VaxWifiManager) : 
_led1(led1), _led2(led2), _led3(led3), _buzzer(buzzer), _VaxWifiManager(VaxWifiManager)
{   
    // oppretter alle OneButton-objektene som klassevariabler
    _btn1(pin1, true, true);
    _btn2(pin2, true, true);
    _btn3(pin3, true, true);

    _btn1.attachClick(btn1Clicked);
    _btn2.attachClick(btn2Clicked);
    _btn3.attachClick(btn3Clicked);

    _vaxNumber = 1;
}

/**
 * @brief Callback-funksjon med C++-pointer tilbake til this, altsaa knappen.
 * Kaller startLED-funksjonen naar knappen trykkes, med hvitt lys som default.
 * Kaller ogsaa playTone()-funksjonen paa AdaptedButton med default innstillinger.
 */
void VaxMonitor::btn1Clicked() 
{
  _led1.signal();
  _buzzer.playTone();
  _VaxWifiManager.sendUpdateRequest("Pfizer (0,3 ml)", _vaxNumber);
  _vaxNumber++;
}

/**
 * @brief Callback-funksjon med C++-pointer tilbake til this, altsaa knappen.
 * Kaller startLED-funksjonen naar knappen trykkes, med hvitt lys som default.
 * Kaller ogsaa playTone()-funksjonen paa AdaptedButton med default innstillinger.
 * @param ptr : C-style pointer til instansen av denne klassen
 */
void VaxMonitor::btn2Clicked() 
{
  _led2.signal();
  _buzzer.playTone();
  _VaxWifiManager.sendUpdateRequest("Moderna (0,25 ml)", _vaxNumber);
  _vaxNumber++;
}

/**
 * @brief Callback-funksjon med C++-pointer tilbake til this, altsaa knappen.
 * Kaller startLED-funksjonen naar knappen trykkes, med hvitt lys som default.
 * Kaller ogsaa playTone()-funksjonen paa AdaptedButton med default innstillinger.
 * @param ptr : C-style pointer til instansen av denne klassen
 */
void VaxMonitor::btn3Clicked() 
{
  _led3.signal();
  _buzzer.playTone();
  _VaxWifiManager.sendUpdateRequest("Moderna (0,5 ml)", _vaxNumber);
  _vaxNumber++;
}

/**
 * @brief Sjekker status paa knappen. Maa kjoeres kontinuerlig i loop(). 
 * Kaller knappens tilkoblede funksjoner naar status endres.
 */
void VaxMonitor::refresh()
{
    _btn1.tick();
    _btn2.tick();
    _btn3.tick();
}

/**
 * @brief Sjekker status paa knappen. Maa kjoeres kontinuerlig i loop(). 
 * Kaller knappens tilkoblede funksjoner naar status endres.
 */
void VaxMonitor::startup()
{
    _buzzer.playStartupSound();
    _VaxWifiManager.connectToWiFi();
}

/**
 * @brief Sjekker status paa knappen. Maa kjoeres kontinuerlig i loop(). 
 * Kaller knappens tilkoblede funksjoner naar status endres.
 */
void VaxMonitor::shutdown()
{
    _buzzer.playShutdownSound();
    _VaxWifiManager.disconnect();
}
