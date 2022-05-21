#include "EnkelKnapp.h"
#include "NeoPixelLED.h"

/**
 * @brief Oppretter et nytt objekt av klassen EnkelKnapp.
 * @param pin GPIO-pinen knappen er koblet til.
 * @param npLED et objekt av klassen NeoPixelLED (LED-stripen som skal brukes).
 */
EnkelKnapp::EnkelKnapp(int pin, NeoPixelLED &npLED)
{
    _pin = pin;
    npLED = npLED;
    btn = OneButton(pin, true, true);
}

/**
 * @brief Sjekker status paa knappen. Maa kjoeres kontinuerlig i loop(). 
 * Kaller knappens tilkoblede funksjoner naar status endres.
 */
void EnkelKnapp::tick()
{
    btn.tick();
}

/**
 * @brief Starter LED-syklusen i NeoPixel-stripen
 * for RGB-dioden paa oppgitt indeks og med oppgitt RGB-verdi.
 * @param pin 
 */
void EnkelKnapp::startLED(int pin, int r, int g, int b)
{
    npLED.signal(pin, r, g, b);
}