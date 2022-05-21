#include <pitches.h>

const int BUZZER_PIN = 11; // passiv buzzer

// definerer Game Over-melodien fra Super Mario Bros. med varighet paa hver note
int melody[] = {
NOTE_C4, 0, NOTE_G3, 0, NOTE_E3, NOTE_A3, 
NOTE_B3, NOTE_A3, NOTE_GS3, NOTE_AS3,
NOTE_GS3, NOTE_G3, NOTE_F3, NOTE_G3
};
int noteDurations[] = {
   4,6,4,6,4,5,5,5,5,5,5,8,8,2
};


void setup() {}

void loop() {
  for (int thisNote = 0; thisNote < 14; thisNote++) {
      // fjerdedel = 1000 / 4, aattendedel = 1000/8,
      // triol = 1000/5 o.l.
      int noteDuration = 1000/noteDurations[thisNote];
      tone(BUZZER_PIN, melody[thisNote],noteDuration);
      // en liten pause mellom hver note
      delay(noteDuration +30);
  }
}
