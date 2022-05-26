#include <VaxButton.h>
#include <NeoPixelLED.h>

InputMonitor::InputMonitor(int btn_pin1, int btn_pin2, int btn_pin3, int led_pin1, int led_pin2, int led_pin3)
{
    NeoPixelLED led1(1, led_pin1);
    NeoPixelLED led2(1, led_pin2);
    NeoPixelLED led3(1, led_pin3);


}

void InputMonitor::refresh()
{

}

void InputMonitor::deactivate()
{
    
}