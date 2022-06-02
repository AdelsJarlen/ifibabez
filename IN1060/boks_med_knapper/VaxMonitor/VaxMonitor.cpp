#include <VaxMonitor.h>

/**
 * @brief Oppretter et nytt objekt av klassen VaxMonitor med Member Initializer List.
 * @param pin : GPIO-pinen knappen er koblet til
 * @param vaxType : vaksinetypen knappen representerer (som char array)
 * @param npLED : en referanse til et objekt av klassen VaxLED (LED-stripen som skal brukes)
 * @param buzzer : en referanse til et objekt av klassen VaxBuzzer
 */
VaxMonitor::VaxMonitor(int pin, char* vaxType, VaxLED& npLED, VaxBuzzer& buzzer, WifiManager& wifiManager) : _btn(pin, true, true), _npLED(npLED), _buzzer(buzzer), _wifiManager(wifiManager)
{
    _pin = pin;
    _vaxType = vaxType;
    _btn.attachClick(handleClick, this); // forteller OneButton-knappen hva den skal gjoere ved hvert trykk 
}

/**
 * @brief Callback-funksjon med C++-pointer tilbake til this, altsaa knappen.
 * Kaller startLED-funksjonen naar knappen trykkes, med hvitt lys som default.
 * Kaller ogsaa playTone()-funksjonen paa AdaptedButton med default innstillinger.
 * @param ptr : C-style pointer til instansen av denne klassen
 */
void VaxMonitor::handleClick(void *ptr) 
{
  VaxMonitor *vaxPtr = (VaxMonitor *) ptr;
  vaxPtr->startLED(0, 255, 255, 255);
  vaxPtr->playTone();
  vaxPtr->sendUpdateRequest();
}

/**
 * @brief Sjekker status paa knappen. Maa kjoeres kontinuerlig i loop(). 
 * Kaller knappens tilkoblede funksjoner naar status endres.
 */
void VaxMonitor::tick()
{
    _btn.tick();
}

/**
 * @brief Starter LED-syklusen i NeoPixel-stripen
 * for RGB-dioden paa oppgitt indeks og med oppgitt RGB-verdi.
 * @param index : indeks for leden i "flora"-objektet (hvis man har flere koblet i serie)
 * @param r : Roed verdi (RGB, 0-255)
 * @param g : Groenn verdi (RGB, 0-255)
 * @param b : Blaa verdi (RGB, 0-255)
 */
void VaxMonitor::startLED(int index, int r, int g, int b)
{
    _npLED.signal(index, r, g, b);
}

/**
 * @brief Spiller av en enkel tone via buzzer-objektet.
 */
void VaxMonitor::playTone()
{
    _buzzer.playTone();
}

void VaxMonitor::sendUpdateRequest()
{
    _wifiManager.sendUpdateRequest(_vaxType);
}