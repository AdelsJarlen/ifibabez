#ifndef VaxBuzzer_h
#define VaxBuzzer_h

#include <pitches.h>

/**
 * @brief Klassedefinisjon for klassen VaxBuzzer. Holder styr paa en
 * buzzerPin og har funksjonalitet for aa spille av standard pipelyd eller en
 * gitt tone i en gitt varighet. Inneholder også en standard melodi med 
 * informasjon om tonenes varighet og en melodi-funksjon.
 */
class VaxBuzzer
{
    public:
        VaxBuzzer(uint8_t buzzerPin);
        void playTone();
        void playTone(unsigned int frequency, unsigned long duration);
        void stopTone();
        void playStartupSound();
        void playShutdownSound();
        unsigned long previousMillis;
    ;

    private:
        void playMelody(int[] melody, int[] noteDurations);
        void setPlaying(bool value);
        bool isPlaying();
        uint8_t _buzzerPin;
        bool _playing;
    ;
};

#endif