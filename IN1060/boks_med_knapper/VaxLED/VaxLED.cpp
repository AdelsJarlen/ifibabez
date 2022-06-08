#include "VaxLED.h"

/**
 * @brief Oppretter et nytt VaxLED-objekt.
 * @param led_count : antall NeoPixel i stripen, hvis man evt. seriekobler flere
 * @param pin : pinen LEDen er koblet til
 */
VaxLED::VaxLED(int led_count, uint8_t pin) 
{
  _led_count = led_count;
  _pin = pin;
  Adafruit_NeoPixel flora(_led_count, _pin, NEO_GRB + NEO_KHZ800);
};

/**
 * @brief Regner ut lysstyrke som maa times med non-blocking kode
 * i hovedsketchen. Baserer seg paa en tenkt sirkel og bruker radianene
 * for aa fade ihht. en sinuskurve.
 * @param index : indeks i LED-stripen (hvis man evt. seriekobler to eller flere)
 * @param r : roed verdi (0-255)
 * @param g : groenn verdi (0-255)
 * @param b : blaa verdi (0-255)
 */
void VaxLED::signal(int index, int r, int g, int b, int i)
{               
  int interval = 15;
  long duration = 15000;
  long startTime = millis();

  while (millis() - startTime < duration) {
    
    // 360 grader av en tenkt sirkel
    for (int i = 0; i < 360; i++) {                         
  
        long currentTime = millis();

        // setter LED paa indeks index til oppgitt farge
        flora.setPixelColor(index, r, g, b);                        

        // konverterer grad/vinkel til radianer
        float angle = radians(i);      

              // kjoerer kun innenfor et gitt intervall
              // uten delay() for aa ikke sperre traaden
              if (startTime - currentTime >= interval) {

        // regner ut punktet vha. sinuskurven som utgjoer lysstyrken
        int brightness = (255 / 2) + (255 / 2) * sin(angle);      
        
        // sender lysstyrken og oppgitt farge til NeoPixel-objektet 
        flora.setBrightness(brightness);

        // skrur paa ny lysstyrke og evt. ny farge                 
        flora.show();

          // oppdaterer startTime til neste runde
          startTime = millis();
        }
    }
  }
};

/**
 * @brief Skrur av LED-dioden. Virker litt ulogisk aa bruke show(), 
 * men clear() setter kun RGB-verdien til 0,0,0 - oppdaterer ikke alltid LED-status
 */
void VaxLED::off() 
{
  flora.clear();
  flora.show(); 
}