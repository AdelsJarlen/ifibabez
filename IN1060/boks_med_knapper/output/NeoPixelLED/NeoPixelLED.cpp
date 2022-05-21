#include "NeoPixelLED.h"

NeoPixelLED::NeoPixelLED(int led_count, int pin) 
{
  _led_count = led_count;
  _pin = pin;
  Adafruit_NeoPixel strip(_led_count, _pin, NEO_GRB + NEO_KHZ800);
};

// Naar signaliserer til 
void NeoPixelLED::signal(int index, int r, int g, int b)
{
  int interval = 15;
  long startTime = millis();
  int brightness = 0;
  int radius = 255;

  while (radius > 0) {
    
    // 360 grader av en tenkt sirkel
    for (int i = 0; i < 360; i++) {                         
  
        long currentTime = millis();

        // setter LED paa indeks index til oppgitt farge
        strip.setPixelColor(index, r, g, b);                        

         // konverterer grad til radianer
        float angle = radians(i);      
    
        // kjoerer kun innenfor et gitt intervall
        // uten delay() for aa ikke sperre traaden
        if (startTime - currentTime >= interval) {

          // regner ut punktet vha. sinuskurven som utgjoer lysstyrken
          brightness = (radius / 2) + (radius / 2) * sin(angle);      
          
          // sender lysstyrken og oppgitt farge til NeoPixel-objektet 
          strip.setBrightness(brightness);

          // turn on the LED                  
          strip.show();

          // 
          startTime = millis();
        }
    }

    if (radius <= 0) {
      // skrur av den leden som er i bruk
      strip.setPixelColor(index, 0, 0, 0);
      return;
    } else {
      radius = radius - 40;
    }
  }
};

void NeoPixelLED::off() 
{
  strip.clear();
}