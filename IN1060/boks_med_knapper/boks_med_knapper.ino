#include <InputMonitor.h>


int BUTTON_1 = 13;
int BUTTON_2 = 12;
int BUTTON_3 = 11;

#define LED_1 A2
#define LED_2 A1
#define LED_3 A3

#define LED_COUNT 1

#define BUZZER A4

// InputMonitor inpMonitor(BUTTON_1, BUTTON_2, BUTTON_3, LED_1, LED_2, LED_3);

NeoPixelLED led1(0, LED_1);
NeoPixelLED led2(0, LED_2);
NeoPixelLED led3(0, LED_3);

VaxButton btn1(BUTTON_1, led1);
VaxButton btn2(BUTTON_2, led2);
VaxButton btn3(BUTTON_3, led3);

void setup() 
{
  Serial.begin(115200);
  Serial.print(F("... Starter serial med baud rate 115200"));  
}

void loop() 
{
  btn1.tick();
  btn2.tick();
  btn3.tick();
}
