#ifndef AdaptedBuzzer_h
#define AdaptedBuzzer_h

#include <pitches.h>

/**
 * @brief Klassedefinisjon for klassen AdaptedBuzzer. Holder styr paa en
 * buzzerPin og har funksjonalitet for aa spille av standard pipelyd eller en
 * gitt tone i en gitt varighet. Inneholder også en standard melodi med 
 * informasjon om tonenes varighet og en non-blocking melodi-funksjon.
 */
class AdaptedBuzzer
{
    public:
        AdaptedBuzzer(uint8_t buzzerPin);
        void playTone();
        void playTone(unsigned int frequency, unsigned long duration);
        void playMelody();
        void stopTone();
        void setPlaying(bool value);
        bool isPlaying();
        unsigned long previousMillis;
    ;

    private:
        uint8_t _buzzerPin;
        bool _playing;
        int _melody[26];
        int _noteDurations[26];
    ;
};

#endif