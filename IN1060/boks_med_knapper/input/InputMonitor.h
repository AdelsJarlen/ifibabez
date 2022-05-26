#ifndef InputMonitor_h
#define InputMonitor_h

#include <VaxButton.h>
#include <NeoPixelLED.h>

class InputMonitor
{
    public:
        InputMonitor(int btn_pin1, int btn_pin2, int btn_pin3, int led_pin1, int led_pin2, int led_pin3);
        void refresh();
        void deactivate();
    ;

    private:
        NeoPixelLED led1;
        NeoPixelLED led2;
        NeoPixelLED led3;

        VaxButton btn1;
        VaxButton btn2;
        VaxButton btn3;
    ;
};

#endif