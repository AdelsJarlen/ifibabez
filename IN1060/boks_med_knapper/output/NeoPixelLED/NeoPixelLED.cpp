#include "NeoPixelLED.h"

NeoPixelLED::NeoPixelLED(int _led_count, int _pin) 
{
  Adafruit_NeoPixel strip(_led_count, _pin, NEO_GRB + NEO_KHZ800);
};

// Ved mottakelse av signal vil lyset pulsere i x antall sekunder.
void NeoPixelLED::signal(int index)
{
  int interval = 15;
  long startTime = millis();
  int brightness = 0;
  int radius = 255;

  while (radius > 0) {
    
    for (int i = 0; i < 360; i++) {                         // 360 degrees of an imaginary circle.
  
        long currentTime = millis();
        float angle = radians(i);                             // Converts degrees to radians.
    
        // Delay between each point of sine wave.
        if (startTime - currentTime >= interval) {
          brightness = (radius / 2) + (radius / 2) * sin(angle);      // Generates points on a sign wave.
          
          strip.setBrightness(brightness);
          strip.setPixelColor(index, 255, 255, 255);                    // Sends sine wave information to pin 9.
          strip.show();

          startTime = millis();
        }
    }

    radius = radius - 40;
  }
};

void NeoPixelLED::off() 
{
  strip.clear();
}