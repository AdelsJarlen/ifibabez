#include <VaxButton.h>

/**
 * @brief Oppretter et nytt objekt av klassen VaxButton med Member Initializer List.
 * @param pin : GPIO-pinen knappen er koblet til
 * @param vaxType : vaksinetypen knappen representerer (som char array)
 * @param npLED : en referanse til et objekt av klassen NeoPixelLED (LED-stripen som skal brukes)
 * @param buzzer : en referanse til et objekt av klassen VaxBuzzer
 */
VaxButton::VaxButton(int pin, char* vaxType, NeoPixelLED& npLED, VaxBuzzer& buzzer, WifiManager& wifiManager) : _btn(pin, true, true), _npLED(npLED), _buzzer(buzzer), _wifiManager(wifiManager)
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
void VaxButton::handleClick(void *ptr) 
{
  VaxButton *vaxPtr = (VaxButton *) ptr;
  vaxPtr->startLED(0, 255, 255, 255);
  vaxPtr->playTone();
  vaxPtr->sendUpdateRequest();
}

/**
 * @brief Sjekker status paa knappen. Maa kjoeres kontinuerlig i loop(). 
 * Kaller knappens tilkoblede funksjoner naar status endres.
 */
void VaxButton::tick()
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
void VaxButton::startLED(int index, int r, int g, int b)
{
    _npLED.signal(index, r, g, b);
}

/**
 * @brief Spiller av en enkel tone via buzzer-objektet.
 */
void VaxButton::playTone()
{
    _buzzer.playTone();
}

void VaxButton::sendUpdateRequest()
{
    _wifiManager.sendUpdateRequest(_vaxType);
}