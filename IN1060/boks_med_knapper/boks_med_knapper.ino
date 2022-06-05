#include <VaxMonitor.h>

uint8_t BUTTON1_PIN = 11;
uint8_t BUTTON2_PIN = 12;
uint8_t BUTTON3_PIN = 13;

#define LED1_PIN A1
#define LED2_PIN A2
#define LED3_PIN A3

#define BUZZER_PIN A4

VaxBuzzer buzzer(BUZZER_PIN);

VaxLED led1(0, LED1_PIN);
VaxLED led2(0, LED2_PIN);
VaxLED led3(0, LED3_PIN);

VaxWifiManager VaxWifiManager();

VaxMonitor vaxMonitor(BUTTON1_PIN, BUTTON2_PIN, BUTTON3_PIN, led1, led2, led3, buzzer, VaxWifiManager);

void setup() 
{
  vaxMonitor.startup();
}

void loop() 
{
  vaxMonitor.refresh();
}
