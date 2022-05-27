---
title: Teknisk rapport – IN1060
author: Ifibabez
creator: Ifibabez
keywords: [IN1060, Arduino, programmering, C++, interaksjonsdesign, Universitetet i Oslo]
---

# Teknisk rapport – IN1060



## Innholdsfortegnelse

[TOC]

<div style="page-break-after:always" /> 

# 1. Introduksjon

## 1.1 Kort om gruppen

**Gruppenavn:** <span style="color:orange;">**Ifibabez**</span>

**Medlemmer:** <span style="color:orange;">**Julie Haukås, Martine Røsand Hernæs, Synne Markmanrud, Ola Juul Holm og Jørgen Andresen Osberg** </span>

**Brukergruppe:** <span style="color:orange;">**Kvinnelige helsearbeidere på Ullern Vaksinesenter i alder 20 til 30 år** (nå også vaksinesenter for bydel Frogner og Vestre Aker)</span> 

**Våre utvalgte brukere:** <span style="color:orange;">**En vernepleier og en sykepleier ved vaksinesenteret.**</span>

**Prosjektside:**  **https://www.uio.no/studier/emner/matnat/ifi/IN1060/v22/prosjekter-var-2022/ifibabez/**



## 1.2 Kort om krav og funksjonalitet

Den endelige tekniske løsningen for å lage en fungerende artefakt vi kunne filme og vise til brukerne er et resultat av flere iterasjoner og mye eksperimentering med ulike komponenter og 3D-printede elementer. 

<img align="right" src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/Bilder av prototype/IMG_3102.JPG" alt="IMG_3102" style="zoom:12.5%;padding:45px;"/>Som man kan se i bildet og i videoen vår, består løsningen av en 3D-printet boks med tre lysende mekaniske knapper som representerer de tre vaksine-typene våre brukere ville ha med i prosjektet. Måten boksen skal fungere på er forholdsvis grei å forstå, men inneholder en del steg:

1) Sykepleieren/helsearbeideren ved innregistrering spør innbyggeren om hvilken vaksine vedkommende vil ha `NØDVENDIG HANDLING (PRE-BETINGELSE)`
2) Sykepleieren/helsearbeideren trykker på knappen som korresponderer med innbyggerens ønske `NØDVENDIG HANDLING (PRE-BETINGELSE)`
3) Boksen registrerer knappetrykket og starter en LED-sekvens `FUNKSJONELT KRAV`
4) Boksen sender også beskjed til printeren på opptrekksrommet om hvilken vaksine som skal trekkes opp, klokkeslettet for bestillingen og nummeret på vaksinen (vaksine x av N vaksiner pr. dag) `FUNKSJONELT KRAV`
5) Boksen sender også en HTTP POST-request til en server for å oppdatere et regneark med oversikt over dagens vaksinebestillinger `IKKE KRAV, MEN ØNSKET TILLEGGSFUNKSJONALITET`
6) Knappen som ble trykket på slutter å lyse når timeren på lyssekvensen er ute, evt. starter hele prosessen fra steg 3-5 på nytt dersom det kommer en ny bestilling av samme type `FUNKSJONELT KRAV`

Steg 3-6 beskriver de endelige ønskene fra brukerne våre som vi har jobbet for å implementere gjennom en kombinasjon av passende komponenter og Arduino-kode. I de neste seksjonene gir vi en grundig beskrivelse av alle komponenter og all kode som inngår i prosjektet.

<div style="page-break-after:always" /> 

# 2. Komponenter

For å få boksen til å fungere i henhold til brukernes ønsker og våre spesifikasjoner visste vi allerede tidlig i prosessen at vi ville trenge minst:

| Nødvendige komponenter                                       |
| ------------------------------------------------------------ |
| 3 Arduino-kompatible knapper (pushbuttons)                   |
| 3 LED-dioder, helst RGB                                      |
| 1 buzzer/piezo eller 1 høyttalerelement                      |
| 1 batteri, gjerne oppladbart                                 |
| 1 WiFi-modul (evt. Bluetooth eller Bluetooth Low Energy)     |
| 1 prototype-breadboard (perfboard) vi kunne lodde på         |
| 3 plast-keycaps til knappene                                 |
| 3 custom fester mellom keycaps og pushbuttons                |
| X kabler og passende loddetinn/flussmiddel til lodding av komponentene |



## 2.1 Tinkercad-schematic

Under er en av de første **Tinkercad**-schematicene vi satt sammen for å vise hvordan en grunnleggende Arduino-løsning for den lokale funksjonaliteten ville kunne se ut, altså kun hvordan de fysiske komponentene inne i boksen ville snakke med hverandre. Tegningen viser `3 enkle LED-dioder ` (bare ensfargede LED-dioder fremfor RGB for å forenkle koblingene), `3 standard pushbuttons` (fra Starter Kit-et) samt `1 piezo-element` (også fra Starter Kit-et). Disse er koblet enkelt til `5V` og GND og gjennom resistorer der det er nødvendig. I henhold til Arduino-boken fra kitet bør knappene ha `10㏀`-resistorer og LED-diodene bør ha `220Ω`-resistorer. Den høye motstanden for knappene er særlig for å hjelpe med typiske signal-problemer fra mekaniske knapper (som ofte må ytterligere forbedres gjennom `debouncing` i koden uansett).

![](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/Enkel schematic tinkercad.png)



![image-20220526183116785](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/image-20220526183116785.png)

I tillegg til komponentene som vises her ble en *termoskriver* eller en annen form for *etikettprinter* en nødvendig del av prosjektet med en gang vi gikk over til den fastfood-inspirerte designidéen om en **trykk-og-print**-artefakt i 2 deler. Som beskrevet i rapporten fikk vi ikke tid til å bestille en programmerbar termoskriver fra en 3.-part i denne omgang, men vi bestemte oss for å ta utgangspunkt i den etikettskriveren vaksinesenteret allerede hadde brukt tidligere. Her var gjenkjennbarhet og lærbarhet også viktige hensyn, ettersom det ville kunne være en fordel at brukerne var kjent med etikettskriveren og hvordan enkelt vedlikehold og bytte av etikettrull fungerte.

<div style="page-break-after:always" /> 

## 2.2 Valg av komponenter

### 2.2.1 Utfordring 1: Valg av WiFi-modul

Den første utfordringen vi støtte på da vi skulle velge ut og bestille de nødvendige komponentene for å bygge artefakten, var hvilken WiFi-modul vi skulle benytte. Et par kjappe Google-søk ga en haug med resultater fra forskjellige merker, men det var stor variasjon i hva som var tilgjengelig og i hva som egentlig støttet Arduino-kjernen og som kunne programmeres med `.ino`-filer og `C++`-biblioteker. Den aller vanligste WiFi-modulen som benyttes med Arduino Uno som mikrokontroller er kort basert på `ESP8266`-chipen fra **Espressif Systems**. Både ESP8266-modulen til Arduino og andre små utviklingskort basert på ESP8266 var tilgjengelige i norske nettbutikker, blant annet et lite men kraftig `WeMos D1 Mini`-kort fra **Luxorparts**, så disse var gode alternativer.

<img align="right" src="https://i0.wp.com/randomnerdtutorials.com/wp-content/uploads/2018/08/esp32-boards.jpg?resize=750%2C413&quality=100&strip=all&ssl=1" style="zoom:50%;padding:20px 45px;" />Likevel fant vi etter hvert ut av at det allerede har kommet ut en langt kraftigere chip fra Espressif som er kjernen i en rekke nye development boards. Disse støtter alle Arduino-kode og kan programmeres fra `Arduino IDE` eller `PlatformIO`. Blant kortene fant vi for eksempel **Espressifs** egne DEVKIT, **WeMos**' LOLIN32, Huzzah32 fra **Adafruit** og SparkFun Electronics **ESP32-S2 Thing Plus**. Vi landet på sistnevnte av tre grunner:

1) En komplett Thing Plus er faktisk billigere enn en ekstra WiFi-modul til Arduino, selv om den tilbyr langt mer minne og funksjonalitet
2) Thing Plus støtter LiPo-batterier som kan lades gjennom USB-C-porten på kontrolleren
3) Thing Plus har også innebygget støtter for Over-The-Air-programmering (OTA) av boksen, slik at vi ville kunne laste opp ny kode til kortet helt uten å ta boksen fra hverandre og på avstand mens vi tester boksen på vaksinesenteret.

Den største forskjellen på disse kortene og Arduino-kortene er at sistnevnte er basert på den norskutviklede **AVR**-arkitekturen som eies og utvikles av **Atmel** (i dag under **Microchip Technology**). Dette gjør at visse Arduino-funksjoner ikke eksisterer *out-of-the-box*, men vi snakket med vår gruppelærer før vi bestilte kortet og fikk beskjed om at det var greit ettersom programmet vårt fortsatt skrives i Arduino og like gjerne kunne ha vært deployed på et Arduino-kort. De ekstra funksjonene som kommer med Thing Plus og det faktum at kortet faktisk koster betydelig mindre enn tilleggsmodulene til Arduino *sealed the deal* for vår del.

> Vårt endelige valg ble: **SparkFun Electronics ESP32-S2 WROOM Thing Plus (WRL-17743)**



![IMG_20220523_162237](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_20220523_162237.jpg)



### 2.2.2 Utfordring 2: Valg av LED-lys

Til boksen ville vi også trenge minst tre LED-dioder som ga en form for *backlight* til vaksineknappene. Et av de viktige feedback-ønskene fra brukerne våre var nemlig at knappen skulle lyse i et gitt intervall, gjerne "så lenge som mulig", hver gang den ble trykket ned. På denne måten ville brukerne enkelt se om knappen faktisk var aktivert uten at den behøvde å lage for mye støy eller forstyrre arbeidsflyten ytterligere.

<img align="right" src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/1260-00.jpg" alt="Angled shot of 4 Flora RGB Smart NeoPixel version 2." style="zoom:50%;padding:0px 35px;" />Etter litt frem og tilbake landet vi på at **Adafruits** økosystem for *sewable microelectronics*, kalt **NeoPixel**, kanskje kunne være løsningen. NeoPixel er en familie små og billige LED-dioder som krever relativt lite strøm og som er lette å kontrollere gjennom AdaFruits custom klasser. De viktigste fordelene for oss her var at:

1) Diodene var veldig små og kunne sannsynligvis passe fint under eller inni knappene
2) Diodene hadde full RGB-funksjonalitet, men trengte likevel kun 1 `IO-pin` til data for å styre farger og lysstyrke
3) Diodene var billige og hadde kort leveringstid

> Vårt endelige valg ble: **Adafruit NeoPixel Flora v2**



![IMG_3089](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3089.JPG)



### 2.2.3 Utfordring 3: Valg av knapper

Da vi begynte å sette sammen komponentene til artefakten testet vi noen forskjellige knappemekanismer for å se hva som ville passe best. Første runde ble gjennomført med noen helt enkle *pushbuttons* fra Starter Kit-et, men vi skjønte etter hvert at det ville være vanskelig å 3D-printe/lage en festemekanisme for våre custom keycaps som både var solid, stabil og som ga en form for taktil, mekanisk feedback når knappen ble trykket ned. Standard-knappene var rett og slett for små. Vi prøvde videre med knappemoduler som f.eks. **SunFounders** pushbutton-modul til Arduino og Raspberry Pi med `JST`-kabler. Denne typen knapp fungerte godt og hadde en ganske riktig størrelse til keycapsene vi ønsket å bruke, men vi opplevde likevel at feedbacken brukeren fikk ved å trykke den ned ikke var helt som vi skulle ønske. En annen utfordring med disse var at modulen nå nesten ville være for stor til at vi kunne få plass til LED-diodene oppå/ved siden av i hulrommet til knappene på boksen. 

<img align="right" src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/H3b28bd282b7b4bf19bca29b9245d6456h_1445x.png" alt="Gateron Ink 2021 v2 Box" style="zoom: 25%;" />Etter litt diskusjon bestemte vi oss for å hente inspirasjon fra gaming-verdenen og teste ut hvordan det kunne fungere med mekaniske brytere *à la* de man finner på gaming-tastaturer eller generelt på mer high-end PC-tastaturer. Her er det mange tusen ulike brytere man kan velge mellom som særlig kategoriseres etter *motstand*, *travel* (avstanden bryteren må trykkes ned før den aktiveres) og *klikkelyd*. Som regel får man disse bryterne i et enkelt sort plastskall med en fjærladet, farget plast-"pinne" inni, men til vår fornøyelse finnes det også noen mer gjennomsiktige utgaver. Vi gikk til innkjøp av noen ulike varianter av disse for å teste, men landet til slutt på **Gateron Ink 2021 v2 Box** i den rosa utgaven. Disse bryterne er:

1. Noen av de billigere switchene vi kunne få tak i enkeltvis
2. Ganske stille, men gir likevel bra feedback i form av "sprett" tilbake etter hvert trykk og en liten klikkelyd
3. Relativt enkle å feste ned på en plastplate e.l. fordi de har en liten kant mellom den øvre og den nedre delen. Det er denne kanten som brukes når bryterne skal settes ned på en monteringsplate til et vanlig mekanisk tastatur.

> Vårt endelige valg ble: **Gateron Ink 2021 v2 Box (rosa)**



![IMG_3087](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3087.JPG)



## 2.3 Liste over alle komponenter

| Komponent                                                    | Antall |
| ------------------------------------------------------------ | ------ |
| Sparkfun S2-ESP32 Thing Plus mikrokontroller                 | 1      |
| DYMO LabelWriter 450                                         | 1      |
| Adafruit NeoPixel Flora v2 RGB LED                           | 3      |
| Gateron Ink V2 Box 2021 (pink)                               | 3      |
| Adafruit prototyping-board (kuttet til å passe ESP32-kontrolleren) | 1      |
| Sunfounder passive buzzer-modul til Arduino                  | 1      |
| Oppladbart LiPo-batteri (1200mAh) til kontrolleren           | 1      |
| Power rocker switch (Double Pole Single Throw)               | 1      |
| Slide switch/toggle switch (Single Pole Single Throw)        | 1      |
| 3D-printede elementer (toppknapper, bunnplate til knappene, boks og bunnplate) | -      |
| Diverse custom-kabler som er kuttet og loddet                | -      |
| Små skruer og muttere til å feste de 3D-printede elementene sammen | -      |



## 2.4 IO-oversikt for ESP32-S2 Thing Plus

![graphical_datasheet_ESP32-S2_thing_plus](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/graphical_datasheet_ESP32-S2_thing_plus.png)



<div style="page-break-after:always" /> 

## 2.5 Montering av komponenter

![IMG_3094](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3094.JPG)

![IMG_3096](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3096.JPG)



![IMG_3097](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3097.JPG)





# 3. Kode

> **Disclaimer:** Vi har skrevet all Arduino og C++-kode på engelsk fordi vi selv foretrekker å ikke blande inn norsk i selve koden. Det gjør det lettere for oss å lese koden som anvender eksterne biblioteker og gjør at all koden generelt ser mer "sammensveiset" ut. *Docstrings/kommentarer* er likevel lagt inn på norsk for at koden skal passe fint inn i rapporten.





## 3.1 LED-diodene (NeoPixelLED)



```` c++
#ifndef NeoPixelLED_H
#define NeoPixelLED_H

#include <Adafruit_NeoPixel.h>

class NeoPixelLED
{
    private:
        Adafruit_NeoPixel flora; // lagrer NeoPixel-objektet med Adafruits custom-klasse
        int _pin;
        int _led_count;
        int _default_brightness = 200;
    ;
  
  
  	public:
        NeoPixelLED(int led_count, int pin); // konstruktoer
        void signal(int index, int r, int g, int b); // hovedfunksjon for aa starte LED-sekvens
        void off(); // skrur av LED-en helt
    ;
};

#endif
````



````c++
#include "NeoPixelLED.h"

NeoPixelLED::NeoPixelLED(int led_count, int pin) 
{
  _led_count = led_count;
  _pin = pin;
  Adafruit_NeoPixel flora(_led_count, _pin, NEO_GRB + NEO_KHZ800);
};


void NeoPixelLED::signal(int index, int r, int g, int b)
{
  int interval = 15;
  long startTime = millis();
  int brightness = 0;
  int radius = 255;

  while (radius > 0) {
    
    // 360 grader av en tenkt sirkel
    for (int i = 0; i < 360; i++) {                         
  
        long currentTime = millis();

        // setter LED paa indeks index til oppgitt farge
        flora.setPixelColor(index, r, g, b);                        

         // konverterer grad til radianer
        float angle = radians(i);      
    
        // kjoerer kun innenfor et gitt intervall
        // uten delay() for aa ikke sperre traaden
        if (startTime - currentTime >= interval) {

          // regner ut punktet vha. sinuskurven som utgjoer lysstyrken
          brightness = (radius / 2) + (radius / 2) * sin(angle);      
          
          // sender lysstyrken og oppgitt farge til NeoPixel-objektet 
          flora.setBrightness(brightness);

          // turn on the LED                  
          flora.show();

          // oppdaterer startTime til neste runde
          startTime = millis();
        }
    }

    if (radius <= 0) {
      // skrur av den leden som er i bruk
      flora.setPixelColor(index, 0, 0, 0);
      return;
    } else {
      radius = radius - 40;
    }
  }
};

void NeoPixelLED::off() 
{
  flora.clear();
}
````



## 3.2 Buzzeren (VaxBuzzer)



````c++
#ifndef VaxBuzzer_h
#define VaxBuzzer_h

#include <pitches.h>

/**
 * @brief Klassedefinisjon for klassen VaxBuzzer. Holder styr paa en
 * buzzerPin og har funksjonalitet for aa spille av standard pipelyd eller en
 * gitt tone i en gitt varighet. Inneholder også en standard melodi med 
 * informasjon om tonenes varighet og en non-blocking melodi-funksjon.
 */
class VaxBuzzer
{
    private:
        uint8_t _buzzerPin;
        bool _playing;
        int _melody[26];
        int _noteDurations[26];
    ;
  
  	public:
        VaxBuzzer(uint8_t buzzerPin);
        void playTone();
        void playTone(unsigned int frequency, unsigned long duration);
        void playMelody();
        void stopTone();
        void setPlaying(bool value);
        bool isPlaying();
        unsigned long previousMillis;
    ;
};

#endif
````



````c++
#include <VaxBuzzer.h>
#include <pitches.h>

/**
 * @brief Oppretter et nytt VaxBuzzer-objekt paa gitt pin.
 * Definerer ogsaa notene i melodien og deres varighet.
 * @param buzzerPin GPIO-pinen buzzeren er koblet til
 */
VaxBuzzer::VaxBuzzer(uint8_t buzzerPin)
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
void VaxBuzzer::playTone()
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
void VaxBuzzer::playTone(unsigned int frequency, unsigned long duration)
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
void VaxBuzzer::playMelody()
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
void VaxBuzzer::stopTone() 
{
    noTone(_buzzerPin);
}

/**
 * @brief Endrer verdien av playing.
 * @param value : ny verdi
 */
void VaxBuzzer::setPlaying(bool value)
{
    _playing = value;
}

/**
 * @brief Sjekker om buzzeren spiller en tone.
 * @return true : hvis tone spilles
 * @return false : hvis buzzeren er av
 */
bool VaxBuzzer::isPlaying()
{
    return _playing;
}
````



## 3.3 Knappene (VaxButton)

````c++
#ifndef VaxButton_h
#define VaxButton_h

#include <OneButton.h>
#include <NeoPixelLED.h>
#include <VaxBuzzer.h>

/**
 * @brief Klassedefinisjon for VaxButton. 
 * Holder styr paa det custom NeoPixelLED-objektet, AdaptedButton 
 * og initialiserer en OneButton-knapp med automatisk debouncing
 * for aa styre begge.
 */
class VaxButton 
{
    private:
        int _pin;
        NeoPixelLED& _npLED;
        OneButton _btn;
        VaxBuzzer& _buzzer;
        static void handleClick(void *ptr);
    ;
  
  	public:
        VaxButton(int pin, NeoPixelLED& npLED, VaxBuzzer& buzzer); // konstrukoer
        void tick(); 
        void startLED(int index, int r, int g, int b);
        void playTone();
    ;

    
};

#endif
````



````c++
#include <VaxButton.h>

/**
 * @brief Oppretter et nytt objekt av klassen VaxButton med Member Initializer List.
 * @param pin GPIO-pinen knappen er koblet til.
 * @param npLED et objekt av klassen NeoPixelLED (LED-stripen som skal brukes).
 */
VaxButton::VaxButton(int pin, NeoPixelLED& npLED, VaxBuzzer& buzzer) : _btn(pin, true, true), _npLED(npLED), _buzzer(buzzer)
{
    _pin = pin;
    _btn.attachClick(handleClick, this); // forteller OneButton-knappen hva den skal gjoere ved hvert trykk 
}

/**
 * @brief Callback-funksjon med C++-pointer tilbake til this, altsaa knappen.
 * Kaller startLED-funksjonen naar knappen trykkes, med hvitt lys som default.
 * Kaller ogsaa playTone()-funksjonen paa AdaptedButton med default innstillinger.
 * @param ptr C-style pointer til instansen av denne klassen
 */
void VaxButton::handleClick(void *ptr) 
{
  VaxButton *thingPtr = (VaxButton *) ptr;
  thingPtr->startLED(0, 255, 255, 255);
  thingPtr->playTone();
}

/**
 * @brief Sjekker status paa knappen. Maa kjoeres kontinuerlig i loop(). 
 * Kaller knappens tilkoblede funksjoner naar status endres.
 */
void VaxButton::tick()
{
    _btn.tick();
}

/**
 * @brief Starter LED-syklusen i NeoPixel-stripen
 * for RGB-dioden paa oppgitt indeks og med oppgitt RGB-verdi.
 * @param pin 
 */
void VaxButton::startLED(int index, int r, int g, int b)
{
    _npLED.signal(index, r, g, b);
}

/**
 * @brief Spiller av en enkel tone via buzzer-objektet.
 */
void VaxButton::playTone()
{
    _buzzer.playTone();
}
````





## 3.4 WiFi-innstillinger (WifiManager)



````c++
````



````c++
````





````js
````





## Programkode 



