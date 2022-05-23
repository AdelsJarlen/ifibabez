#include <OneButton.h>
#include <Adafruit_NeoPixel.h>

#define BUTTON_1 10
#define BUTTON_2 11
#define BUTTON_3 12

#define LED_1    A2
#define LED_2    A3
#define LED_3    A4
#define LED_COUNT 1

#define BUZZER A5

Adafruit_NeoPixel led1(LED_COUNT, LED_1, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel led2(LED_COUNT, LED_2, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel led3(LED_COUNT, LED_3, NEO_GRB + NEO_KHZ800);

OneButton btn1(BUTTON_1, true, true);
OneButton btn2(BUTTON_2, true, true);
OneButton btn3(BUTTON_3, true, true);

int btn1State;
int btn2State;
int btn3State;

bool state1;
bool state2;
bool state3;

void setup() {
  pinMode(BUTTON_PIN, INPUT_PULLUP);
  Serial.begin(115200);
  Serial.println("... starter serial");
  
  btn.attachClick(handleClick);
  
  led1.begin();
  led1.clear();
  led1.setBrightness(150);

  strip2.begin();
  strip2.clear();
  strip2.setBrightness(150);

  strip3.begin();
  strip4.clear();
  strip5.setBrightness(150);
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
