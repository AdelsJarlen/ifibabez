#include "pitches.h"
// notes in the melody:
int melody[] = {
NOTE_C4, 0, NOTE_G3, 0, NOTE_E3, NOTE_A3, 
NOTE_B3, NOTE_A3, NOTE_GS3, NOTE_AS3,
NOTE_GS3, NOTE_G3, NOTE_F3, NOTE_G3
};
// note durations: 4 = quarter note, 8 = eighth note, etc.:

int noteDurations[] = {
   4,6,4,6,4,5,5,5,5,5,5,8,8,2
};

void setup() {
   // itererer over hver note i melodien
   for (int thisNote = 0; thisNote < 14; thisNote++) {
      // fjerdedel = 1000 / 4, aattendedel = 1000/8,
      // triol = 1000/5 o.l.
      int noteDuration = 1000/noteDurations[thisNote];
      tone(13, melody[thisNote],noteDuration);
      // en liten pause mellom hver note
      delay(noteDuration +30);
   }
}

void loop() {
   // no need to repeat the melody.
}
