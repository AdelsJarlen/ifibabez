#include <OneButton.h>
#include <Adafruit_NeoPixel.h>

#define BUTTON_1 13
#define BUTTON_2 12
#define BUTTON_3 11

#define LED_1 A2
#define LED_2 A1
#define LED_3 A3

#define LED_COUNT 1

#define BUZZER A4

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

  Serial.begin(115200);
  Serial.println("... starter serial");

  btn1.attachClick(startLED1);
  btn2.attachClick(startLED2);
  btn3.attachClick(startLED3);
  
  led1.begin();
  led1.setBrightness(255);
  led1.setPixelColor(0, 0,0,0);
  led1.show();

  led2.begin();
  led2.setBrightness(255);
  led2.setPixelColor(0, 0,0,0);
  led2.show();

  led3.begin();
  led3.setBrightness(255);
  led3.setPixelColor(0, 0,0,0);
  led3.show();

  state1 = false;
  state2 = false;
  state3 = false;

}

void loop() {
  btn1.tick();
  btn2.tick();
  btn3.tick();
  
}

static void startLED1() {
  Serial.println("Button 1 clicked!");
  if (state1 == false) {
      state1 = true;
      led1.setPixelColor(0, 255,255,255);
      led1.show();
   } else {
      led1.setPixelColor(0, 0,0,0);
      led1.show();
      state1 = false;
   }

}

static void startLED2() {
  Serial.println("Button 2 clicked!");
   if (state2 == false) {
      state2 = true;
      led2.setPixelColor(0, 255,255,255);
      led2.show();
   } else {
      led2.setPixelColor(0, 0,0,0);
      led2.show();
      state2 = false;
   }
}

static void startLED3() {
  Serial.println("Button 3 clicked!");
   if (state3 == false) {
      state3 = true;
      led3.setPixelColor(0, 255,255,255);
      led3.show();
   } else {
      led3.setPixelColor(0, 0,0,0);
      led3.show();
      state3 = false;
   }
}
