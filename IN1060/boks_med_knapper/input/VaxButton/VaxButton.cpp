#include <VaxButton.h>
#include <NeoPixelLED.h>

/**
 * @brief Oppretter et nytt objekt av klassen VaxButton med Member Initializer List.
 * @param pin GPIO-pinen knappen er koblet til.
 * @param npLED et objekt av klassen NeoPixelLED (LED-stripen som skal brukes).
 */
VaxButton::VaxButton(int pin, NeoPixelLED& npLED) : _btn(pin, true, true), _npLED(npLED)
{
    _pin = pin;
    _btn.attachClick(handleClick, this); // forteller OneButton-knappen hva den skal gjoere ved hvert trykk 
}

/**
 * @brief Callback-funksjon med C++-pointer tilbake til this, altsaa knappen.
 * Kaller startLED-funksjonen naar knappen trykkes, med hvitt lys som default.
 * @param ptr C-style pointer til instansen av denne klassen
 */
void VaxButton::handleClick(void *ptr) 
{
  VaxButton *thingPtr = (VaxButton *) ptr;
  thingPtr->startLED(0, 255, 255, 255);
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
 * @param pin 
 */
void VaxButton::startLED(int index, int r, int g, int b)
{
    _npLED.signal(index, r, g, b);
}