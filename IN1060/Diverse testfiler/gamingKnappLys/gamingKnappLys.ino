#include <OneButton.h>
#include <Adafruit_NeoPixel.h>


#define BUTTON_PIN A4
#define LED_PIN    A3
#define LED_COUNT 3
Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);

OneButton btn(BUTTON_PIN, true, true);
int buttonState;
bool state;

void setup() {
  pinMode(BUTTON_PIN, INPUT_PULLUP);
  Serial.begin(115200);
  Serial.println("... starter serial");
  btn.attachClick(handleClick);
  strip.begin();
  strip.clear();
  strip.setBrightness(150);
  state = true;
}

void loop() {
  // put your main code here, to run repeatedly:
  btn.tick();
  
}

static void handleClick() {
  Serial.println("Clicked!");
  
  if (state == true) {
      Serial.println("knapp, state er true");
      strip.clear();
      strip.setPixelColor(0, 255, 0, 0);
      strip.setPixelColor(1, 0, 255, 0);
      strip.setPixelColor(2, 0, 0, 255);
      strip.show();
      state = false;
    } else if (state == false) {
      Serial.println("knapp, state er false");
      strip.clear();
      strip.setPixelColor(0, 0, 255, 0);
      strip.setPixelColor(1, 0, 0, 255);
      strip.setPixelColor(2, 255, 0, 0);
      strip.show();
      state = true;
    }
}
