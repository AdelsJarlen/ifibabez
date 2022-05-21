
#include "led.h"

#define LED_PIN A3
#define LED_COUNT 3

// Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);

LED led(3, 3);
int brightness = 0;
int radius = 255;

void setup() {
  // put your setup code here, to run once:
  led.signal(1);
  // strip.begin();           // INITIALIZE NeoPixel strip object (REQUIRED)
  // strip.show();            // Turn OFF all pixels ASAP
  // strip.setBrightness(150); // Set BRIGHTNESS to about 1/5 (max = 255)
}

void loop() {
  // put your main code here, to run repeatedly:
  /*strip.clear();
  strip.setBrightness(50);
  strip.setPixelColor(0, 255, 0, 0);
  strip.show();
  delay(500);
  strip.clear();
  strip.setBrightness(100);
  strip.setPixelColor(1, 0,255,0);
  strip.show();
  delay(500);
  strip.clear();
  strip.setBrightness(200);
  strip.setPixelColor(2, 0,0,255);
  strip.show();
  delay(500);*/
    
  /*for (int i = 0; i < 360; i++) {                         // 360 degrees of an imaginary circle.

    float angle = radians(i);                             // Converts degrees to radians.

    brightness = (radius / 2) + (radius / 2) * sin(angle);      // Generates points on a sign wave.
    strip.setBrightness(brightness);
    strip.setPixelColor(0, 255, 255, 255);                    // Sends sine wave information to pin 9.
    strip.show();
    // Serial.println(brightness);                           // "Serial Monitor" or "Serial Plotter" information.
    delay(15);          // Delay between each point of sine wave.
    
  }

  if (radius <= 0) {
    
  } else {
    radius = radius - 15;
  }*/
}
