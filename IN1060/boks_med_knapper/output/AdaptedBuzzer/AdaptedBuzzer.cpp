#include <AdaptedBuzzer.h>
#include <pitches.h>

/**
 * @brief Oppretter et nytt AdaptedBuzzer-objekt paa gitt pin.
 * Definerer ogsaa notene i melodien og deres varighet.
 * @param buzzerPin GPIO-pinen buzzeren er koblet til
 */
AdaptedBuzzer::AdaptedBuzzer(uint8_t buzzerPin)
{
    _buzzerPin = buzzerPin; // assigner buzzerPinen
    _playing = false;
 
    int _melody[26] = { // definerer melodien
        NOTE_E5, NOTE_E5, NOTE_E5,
        NOTE_E5, NOTE_E5, NOTE_E5,
        NOTE_E5, NOTE_G5, NOTE_C5, NOTE_D5,
        NOTE_E5,
        NOTE_F5, NOTE_F5, NOTE_F5, NOTE_F5,
        NOTE_F5, NOTE_E5, NOTE_E5, NOTE_E5, NOTE_E5,
        NOTE_E5, NOTE_D5, NOTE_D5, NOTE_E5,
        NOTE_D5, NOTE_G5
        };

    int _noteDurations[26] = { // og meloditonenes varighet
        8, 8, 4,
        8, 8, 4,
        8, 8, 8, 8,
        2,
        8, 8, 8, 8,
        8, 8, 8, 16, 16,
        8, 8, 8, 8,
        4, 4
    };

    unsigned long previousMillis = 0; // variabel for non-blocking pauser
}

/**
 * @brief Spiller av en standardtone fra pitches.h (C4) med varighet paa 500 ms.
 */
void AdaptedBuzzer::playTone()
{
    unsigned long startTime = millis();
    tone(_buzzerPin, NOTE_C4, 500);
    unsigned long endTime = millis();
   
    while (endTime - startTime < 500) {
        setPlaying(true);
        endTime = millis();
    }

    setPlaying(false);
}

/**
 * @brief Spiller av en gitt tone med en gitt varighet.
 * @param frequency : tonen fra pithces.h som skal spilles av
 * @param duration : varighet i millisekunder
 */
void AdaptedBuzzer::playTone(unsigned int frequency, unsigned long duration)
{
    unsigned long startTime = millis();
    tone(_buzzerPin, frequency, duration);
    unsigned long endTime = millis();
   
    while (endTime - startTime < 500) {
        setPlaying(true);
        endTime = millis();
    }

    setPlaying(false);
}

/**
 * @brief Spiller en melodi med non-blocking timing.
 * Inspirert av Arduinos eget "blink without delay"-eksempel.
 */
void AdaptedBuzzer::playMelody()
{
    int pauseBetweenNotes;
    unsigned long currentMillis = millis();
    for (int thisNote = 0; thisNote < sizeof(_melody); thisNote++) {
    
    // to calculate the note duration, take one second divided by the note type.
    //e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
        
        if (currentMillis - previousMillis >= pauseBetweenNotes) {
            previousMillis = currentMillis;
            
            int noteDuration = 1000 / _noteDurations[thisNote];

            tone(_buzzerPin, _melody[thisNote], noteDuration);

            // to distinguish the notes, set a minimum time between them.
            // the note's duration + 30% seems to work well:
            pauseBetweenNotes = noteDuration * 1.30;

            // stop the tone playing:
            noTone(_buzzerPin);
        }
    }
}

/**
 * @brief Kaller noTone() paa buzzeren for aa stoppe tonen.
 */
void AdaptedBuzzer::stopTone() 
{
    noTone(_buzzerPin);
}

/**
 * @brief Endrer verdien av playing.
 * @param value : ny verdi
 */
void AdaptedBuzzer::setPlaying(bool value)
{
    _playing = value;
}

/**
 * @brief Sjekker om buzzeren spiller en tone.
 * @return true : hvis tone spilles
 * @return false : hvis buzzeren er av
 */
bool AdaptedBuzzer::isPlaying()
{
    return _playing;
}