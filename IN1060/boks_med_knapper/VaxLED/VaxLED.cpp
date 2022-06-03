#include "VaxLED.h"

VaxLED::VaxLED(int led_count, uint8_t pin) 
{
  _led_count = led_count;
  _pin = pin;
  Adafruit_NeoPixel flora(_led_count, _pin, NEO_GRB + NEO_KHZ800);
};

// Naar signaliserer til 
void VaxLED::signal(int index, int r, int g, int b)
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
        flora.setPixelColor(index, r, g, b);                        

         // konverterer grad til radianer
        float angle = radians(i);      
    
        // kjoerer kun innenfor et gitt intervall
        // uten delay() for aa ikke sperre traaden
        if (startTime - currentTime >= interval) {

          // regner ut punktet vha. sinuskurven som utgjoer lysstyrken
          brightness = (radius / 2) + (radius / 2) * sin(angle);      
          
          // sender lysstyrken og oppgitt farge til NeoPixel-objektet 
          flora.setBrightness(brightness);

          // turn on the LED                  
          flora.show();

          // oppdaterer startTime til neste runde
          startTime = millis();
        }
    }

    if (radius <= 0) {
      // skrur av den leden som er i bruk
      flora.setPixelColor(index, 0, 0, 0);
      return;
    } else {
      radius = radius - 40;
    }
  }
};

void VaxLED::off() 
{
  flora.clear();
}