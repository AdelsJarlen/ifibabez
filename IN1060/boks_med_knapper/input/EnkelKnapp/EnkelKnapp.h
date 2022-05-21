#ifndef EnkelKnapp_H
#define EnkelKnapp_H

#include <OneButton.h>

class EnkelKnapp
{
    public:
        EnkelKnapp(int _pin);
        void signal(int index, int r, int g, int b);
        void off();
    ;

    private:
        int _pin;
        int _led_count;
        int _default_brightness = 200;
    ;
};

#endif