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

<img align="right" src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/Bilder av prototype/IMG_3102.JPG" alt="IMG_3102" style="zoom:12.5%;padding:65px;"/>Som man kan se i bildet og i videoen vår, består løsningen av en 3D-printet boks med tre lysende mekaniske knapper som representerer de tre vaksine-typene våre brukere ville ha med i prosjektet. Måten boksen skal fungere på er forholdsvis grei å forstå, men inneholder en del steg:

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

<img align="right" src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/esp32-boards.jpg" style="zoom:50%;padding:20px 45px;" />Likevel fant vi etter hvert ut av at det allerede har kommet ut en langt kraftigere chip fra Espressif som er kjernen i en rekke nye development boards. Disse støtter alle Arduino-kode og kan programmeres fra `Arduino IDE` eller `PlatformIO`. Blant kortene fant vi for eksempel **Espressifs** egne DEVKIT, **WeMos**' LOLIN32, Huzzah32 fra **Adafruit** og SparkFun Electronics **ESP32-S2 Thing Plus**. Vi landet på sistnevnte av tre grunner:

1) En komplett Thing Plus er faktisk billigere enn en ekstra WiFi-modul til Arduino, selv om den tilbyr langt mer minne og funksjonalitet
2) Thing Plus støtter LiPo-batterier som kan lades gjennom USB-C-porten på kontrolleren
3) Thing Plus har også innebygget støtter for Over-The-Air-programmering (OTA) av boksen, slik at vi ville kunne laste opp ny kode til kortet helt uten å ta boksen fra hverandre og på avstand mens vi tester boksen på vaksinesenteret.

Den største forskjellen på disse kortene og Arduino-kortene er at sistnevnte er basert på den norskutviklede **AVR**-arkitekturen som eies og utvikles av **Atmel** (i dag under **Microchip Technology**). Dette gjør at visse Arduino-funksjoner ikke eksisterer *out-of-the-box*, men vi snakket med vår gruppelærer før vi bestilte kortet og fikk beskjed om at det var greit ettersom programmet vårt fortsatt skrives i Arduino og like gjerne kunne ha vært deployed på et Arduino-kort. De ekstra funksjonene som kommer med Thing Plus og det faktum at kortet faktisk koster betydelig mindre enn tilleggsmodulene til Arduino *sealed the deal* for vår del.

> Vårt endelige valg ble: **SparkFun Electronics ESP32-S2 WROOM Thing Plus (WRL-17743)**



<img src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_20220523_162237.jpg" alt="IMG_20220523_162237" style="zoom:25%;" />



### 2.2.2 Utfordring 2: Valg av LED-lys

Til boksen ville vi også trenge minst tre LED-dioder som ga en form for *backlight* til vaksineknappene. Et av de viktige feedback-ønskene fra brukerne våre var nemlig at knappen skulle lyse i et gitt intervall, gjerne "så lenge som mulig", hver gang den ble trykket ned. På denne måten ville brukerne enkelt se om knappen faktisk var aktivert uten at den behøvde å lage for mye støy eller forstyrre arbeidsflyten ytterligere.

<img align="right" src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/1260-00.jpg" alt="Angled shot of 4 Flora RGB Smart NeoPixel version 2." style="zoom:50%;padding:0px 35px;" />Etter litt frem og tilbake landet vi på at **Adafruits** økosystem for *sewable microelectronics*, kalt **NeoPixel**, kanskje kunne være løsningen. NeoPixel er en familie små og billige LED-dioder som krever relativt lite strøm og som er lette å kontrollere gjennom AdaFruits custom klasser. De viktigste fordelene for oss her var at:

1) Diodene var veldig små og kunne sannsynligvis passe fint under eller inni knappene
2) Diodene hadde full RGB-funksjonalitet, men trengte likevel kun 1 `IO-pin` til data for å styre farger og lysstyrke
3) Diodene var billige og hadde kort leveringstid

> Vårt endelige valg ble: **Adafruit NeoPixel Flora v2**



<img src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3089.JPG" alt="IMG_3089" style="zoom: 25%;" />



### 2.2.3 Utfordring 3: Valg av knapper

Da vi begynte å sette sammen komponentene til artefakten testet vi noen forskjellige knappemekanismer for å se hva som ville passe best. Første runde ble gjennomført med noen helt enkle *pushbuttons* fra Starter Kit-et, men vi skjønte etter hvert at det ville være vanskelig å 3D-printe/lage en festemekanisme for våre custom keycaps som både var solid, stabil og som ga en form for taktil, mekanisk feedback når knappen ble trykket ned. Standard-knappene var rett og slett for små. Vi prøvde videre med knappemoduler som f.eks. **SunFounders** pushbutton-modul til Arduino og Raspberry Pi med `JST`-kabler. Denne typen knapp fungerte godt og hadde en ganske riktig størrelse til keycapsene vi ønsket å bruke, men vi opplevde likevel at feedbacken brukeren fikk ved å trykke den ned ikke var helt som vi skulle ønske. En annen utfordring med disse var at modulen nå nesten ville være for stor til at vi kunne få plass til LED-diodene oppå/ved siden av i hulrommet til knappene på boksen. 

<img align="right" src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/H3b28bd282b7b4bf19bca29b9245d6456h_1445x.png" alt="Gateron Ink 2021 v2 Box" style="zoom: 25%;" />Etter litt diskusjon bestemte vi oss for å hente inspirasjon fra gaming-verdenen og teste ut hvordan det kunne fungere med mekaniske brytere *à la* de man finner på gaming-tastaturer eller generelt på mer high-end PC-tastaturer. Her er det mange tusen ulike brytere man kan velge mellom som særlig kategoriseres etter *motstand*, *travel* (avstanden bryteren må trykkes ned før den aktiveres) og *klikkelyd*. Som regel får man disse bryterne i et enkelt sort plastskall med en fjærladet, farget plast-"pinne" inni, men til vår fornøyelse finnes det også noen mer gjennomsiktige utgaver. Vi gikk til innkjøp av noen ulike varianter av disse for å teste, men landet til slutt på **Gateron Ink 2021 v2 Box** i den rosa utgaven. Disse bryterne er:

1. Noen av de billigere switchene vi kunne få tak i enkeltvis
2. Ganske stille, men gir likevel bra feedback i form av "sprett" tilbake etter hvert trykk og en liten klikkelyd
3. Relativt enkle å feste ned på en plastplate e.l. fordi de har en liten kant mellom den øvre og den nedre delen. Det er denne kanten som brukes når bryterne skal settes ned på en monteringsplate til et vanlig mekanisk tastatur.

> Vårt endelige valg ble: **Gateron Ink 2021 v2 Box (rosa)**



<img src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3087.JPG" alt="IMG_3087" style="zoom:25%;" />



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



Som man kan se av hookup-schematicen over er **ESP32-S2**-kortet stapped med funksjonalitet som vi ikke egentlig kommer til å trenge i dette prosjektet. Det er blant annet en rekke pins (14 totalt) som har innebygget støtte for capacitive touch, samt ekstra koblinger som f.eks. `VUSB` eller `VBAT` som tillater å koble direkte til eksterne `USB`-porter eller batterier, samt en egen spesialkobling kalt `Qwiic` som gjør det mulig å "daisy chaine" flere SparkFun-produkter sammen med en type `JST`-kabler uten noen form for lodding eller løse breadboards. 



## 2.5 Montering av komponenter

I prosjektet vårt er det særlig **5** funksjoner på ESP32-S2-kortet vi kommer til å bruke:  

1. Vanlige analoge og digitale `IO`-pins (**3** til LED, **3** til knapper, **1** til buzzer og evt. ekstra)
2. `JST`-koblingen for det oppladbare lithium-batteriet vårt
3. `USB-C`-koblingen både for å laste opp de første sketchene over kabel og for å lade batteriet når boksen ikke er i bruk
4. Innebygget `OTA`-funksjonalitet for å laste opp sketcher (firmware-oppdateringer) trådløst
5. Innebygget `WiFi`-funksjonalitet for å kommunisere med printeren og evt. et Google Regneark e.l. 







<img src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3094.JPG" alt="IMG_3094" style="zoom: 25%;" />

<img src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3096.JPG" alt="IMG_3096" style="zoom:25%;" />



<img src="/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/IMG_3097.JPG" alt="IMG_3097" style="zoom:25%;" />



<div style="page-break-after:always" /> 

# 3. Kode

> **Disclaimer:** Vi har skrevet all Arduino og C++-kode på engelsk fordi vi selv foretrekker å ikke blande inn norsk i selve koden. Det gjør det lettere for oss å lese koden som anvender eksterne biblioteker og gjør at all koden generelt ser mer "sammensveiset" ut. *Docstrings/kommentarer* er likevel lagt inn på norsk for at koden skal passe fint inn i rapporten.





## 3.1 LED-diodene (VaxLED)



### 3.1.1 VaxLED.h

```` c++
#ifndef VaxLED_H
#define VaxLED_H

#include <Adafruit_NeoPixel.h>

class VaxLED
{
    private:
        Adafruit_NeoPixel flora; // lagrer NeoPixel-objektet med Adafruits custom-klasse
        int _pin;
        int _led_count;
        int _default_brightness = 200;
    ;
  
  
  	public:
        VaxLED(int led_count, int pin); // konstruktoer
        void signal(int index, int r, int g, int b); // hovedfunksjon for aa starte LED-sekvens
        void off(); // skrur av LED-en helt
    ;
};

#endif
````





### 3.1.2 VaxLED.cpp

````c++
#include "VaxLED.h"

VaxLED::VaxLED(int led_count, int pin) 
{
  _led_count = led_count;
  _pin = pin;
  Adafruit_NeoPixel flora(_led_count, _pin, NEO_GRB + NEO_KHZ800);
};


void VaxLED::signal(int index, int r, int g, int b)
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

void VaxLED::off() 
{
  flora.clear();
}
````



## 3.2 Buzzeren (VaxBuzzer)



### 3.2.1 VaxBuzzer.h

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



### 3.2.2 VaxBuzzer.cpp

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



### 3.3.1 VaxButton.h

````c++
#ifndef VaxButton_h
#define VaxButton_h

#include <OneButton.h>
#include <VaxLED.h>
#include <VaxBuzzer.h>
#include <WifiManager.h>

/**
 * @brief Klassedefinisjon for VaxButton. 
 * Holder styr paa det custom VaxLED-objektet, AdaptedButton 
 * og initialiserer en OneButton-knapp med automatisk debouncing
 * for aa styre begge.
 */
class VaxButton 
{
    public:
        VaxButton(int pin, char* vaksinetype, VaxLED& npLED, VaxBuzzer& buzzer, WifiManager& wifiManager); // konstrukoer
        void tick(); 
        void startLED(int index, int r, int g, int b);
        void playTone();
    ;

    private:
        int _pin;
        char* _vaksinetype;
        VaxLED& _npLED;
        OneButton _btn;
        VaxBuzzer& _buzzer;
        WifiManager& _wifiManager;
        static void handleClick(void *ptr);
    ;
};

#endif
````





### 3.3.2 VaxButton.cpp

````c++
#include <VaxButton.h>

/**
 * @brief Oppretter et nytt objekt av klassen VaxButton med Member Initializer List.
 * @param pin : GPIO-pinen knappen er koblet til
 * @param vaksinetype : vaksinetypen knappen representerer (som char array)
 * @param npLED : en referanse til et objekt av klassen VaxLED (LED-stripen som skal brukes)
 * @param buzzer : en referanse til et objekt av klassen VaxBuzzer
 */
VaxButton::VaxButton(int pin, char* vaksinetype, VaxLED& npLED, VaxBuzzer& buzzer) : _btn(pin, true, true), _npLED(npLED), _buzzer(buzzer)
{
    _pin = pin;
    _vaksinetype = vaksinetype;
    _btn.attachClick(handleClick, this); // forteller OneButton-knappen hva den skal gjoere ved hvert trykk 
}

/**
 * @brief Callback-funksjon med C++-pointer tilbake til this, altsaa knappen.
 * Kaller startLED-funksjonen naar knappen trykkes, med hvitt lys som default.
 * Kaller ogsaa playTone()-funksjonen paa AdaptedButton med default innstillinger.
 * @param ptr : C-style pointer til instansen av denne klassen
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
 * @param index : indeks for leden i "flora"-objektet (hvis man har flere koblet i serie)
 * @param r : Roed verdi (RGB, 0-255)
 * @param g : Groenn verdi (RGB, 0-255)
 * @param b : Blaa verdi (RGB, 0-255)
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



### 3.4.1 Kort om bruk av wifi i prosjektet



### 3.4.2 WifiManager.h

````c++
#ifndef WifiManager_h
#define WifiManager_h

#include <WiFi.h>
#include <WiFiUdp.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <HardwareSerial.h>

class WifiManager
{
    public:
        WifiManager(HardWareSerial& hwSerial);
        WifiManager(char * ssid, char * password, char * domain, char * scriptID, int port, HardWareSerial& hwSerial);
        void connectToWiFi();
        void requestTime();
        void requestURL();
        void sendUpdateRequest();
    ;

    private:
        char * _ssid;
        char * _password;
        char * _domain;
        char * _ntp;
        char * _scriptID;
        char * _scriptURL;
        int _port;
        int _vaxNumber;
        HardWareSerial& _hwSerial;
        unsigned long _gmtOffset;
        unsigned int _dstOffset;
    ;
};

#endif
````



### 3.4.3 WifiManager.cpp

````c++
#include <WifiManager.h>

/**
 * @brief Oppretter en WifiManager med standard innstillinger.
 * Er fortrinnsvis en forenkling til bruk ved testing, altsaa naar
 * man har samme nettverksnavn, passord, domene og port. 
 * @param hwSerial : referanse til et serial-objekt for logging
 */
WifiManager::WifiManager(HardWareSerial& hwSerial) : _hwSerial(hwSerial) 
{
  _ssid = "WIFI_NAME";
  _password = "WIFI_PASSWORD";

  _domain = "http://example.com/";
  _ntp = "europe.pool.ntp.org";
  _port = 80;

  _scriptID = "AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t"
  _scriptURL = "https://script.google.com/macros/s/AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t/exec"

  _vaxNumber = 0;
}

/**
 * @brief Oppretter en WifiManager med innstillinger fra argumentene
 * som sendes i metodekallet.
 * @param ssid : nettverksnavnet
 * @param password : nettverkspassordet
 * @param domain : domenet man skal koble til
 * @param scriptID : IDen til Google Script-webappen som skal motta requests
 * @param port : porten man skal bruke
 * @param hwSerial : referanse til et Serial-objekt for aa logge det som skjer
 */
WifiManager::WifiManager(char * ssid, char * password, char * domain, char * scriptID, int port, HardWareSerial& hwSerial) : _hwSerial(hwSerial) 
{
  // WiFi network name and password:
  _ssid = ssid;
  _password = password;

  // Internet domain to request from:
  _domain = domain;
  _scriptID = scriptID;
  _port = port;

  _ntp = "europe.pool.ntp.org";

  _scriptID = "AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t"
  _scriptURL = "https://script.google.com/macros/s/AKfycbzYFLfeDfJZBbx-Ao-cU0IfuPxmAAsVb5hAdXM1oWZnsrWAVdPGH0OWje6Pl-3Mv_2t/exec"

  _vaxNumber = 0;
}

/**
 * @brief Initer WiFi-objektet fra WiFi-klassen (WiFiClass i WiFi.h) og
 * kobler til det internettet som ble oppgitt i konstruktoeren.
 */
void WifiManager::connectToWiFi()
{
  
  _hwSerial.println("Connecting to WiFi network: " + String(_ssid));

  WiFi.begin(_ssid, _password);

  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    _hwSerial.print(".");
  }

  _hwSerial.println();
  _hwSerial.println("WiFi connected!");
  _hwSerial.print("IP address: ");
  _hwSerial.println(WiFi.localIP());
}

/**
 * @brief Requester en URL og printer HTTP-responsen linje for linje.
 * Fortrinnsvis til bruk ved testing og debugging.
 */
void WifiManager::requestURL()
{
  
  // sjekker om kortet er koblet til WiFi, kobler til hvis ikke
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }
  
  _hwSerial.println("Connecting to domain: " + String(_domain));

  // oppretter en WiFiClient for aa opprette en connection til domenet
  WiFiClient client;
  
  if (!client.connect(_domain, _port))
  {
    _hwSerial.println("connection failed");
    return;
  }

  _hwSerial.println("Connected!");

  // sender selve GET-requesten til domenet
  client.print((String)"GET / HTTP/1.1\r\n" +
               "Host: " + String(_domain) + "\r\n" +
               "Connection: close\r\n\r\n");
  
  // sjekker requesten opp mot et timeout-intervall (5 sekunder)
  unsigned long timeout = millis();

  while (client.available() == 0) 
  {
    if (millis() - timeout > 5000) 
    {
      _hwSerial.println(">>> Client Timeout !");
      client.stop(); // stopper klienten hvis det tar for lang tid
      return;
    }
  }

  // printer responseBodyen linje for linje
  while (client.available()) 
  {
    String line = client.readStringUntil('\r');
    _hwSerial.print(line);
  }

  _hwSerial.println();
  _hwSerial.println("closing connection");
  client.stop(); // lukker klienten naar alt er ferdig
}

/**
 * @brief Henter dato og klokkeslett fra en av NTP-serverne i
 * Europa. Kan brukes dersom vi faar til aa sende print request med
 * custom label/txt til printeren.
 */
void WifiManager::requestTime()
{

  // sjekker om WiFi er koblet til
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }

  // henter ut en Timestructure fra epochTime fra NTP-serveren
  struct tm timeinfo;

  if(!getLocalTime(&timeinfo)){
    _hwSerial.println("Failed to obtain time");
    return;
  }

  // skriver ut formatert tidspunkt med standardformat (dd/mm/YYYY kl. HH:MM)
  _hwSerial.println(&timeinfo, "%d/%m/%Y kl. %H:%M");
}

/**
 * @brief Sender en POST-request til Google Script med
 * vaksinetype og vaksinenummer.
 * @param vaxType : vaksinetypen som har blitt trykket, sendes fra knappen
 */
void WifiManager::sendUpdateRequest(char * vaxType)
{
  // sjekker om WiFi er koblet til
  if (WiFi.status() != WL_CONNECTED)
  {
    connectToWiFi();
  }

  // starter en HTTPclient for aa ha lettere tilgang paa POST-requests
  HTTPClient http;

  _hwSerial.print(_scriptURL);
  _hwSerial.print("Making a request");

  // aapner en connection
  http.begin(url);
  
  // definerer at vi skal sende JSON
  http.addHeader("Content-Type", "application/json");

  // genererer et JSON-objekt med dataen vaar
  StaticJsonDocument<200> jsonDoc;

  jsonDoc["vaxType"] = vaxType;
  jsonDoc["vaxNumber"] = _vaxNumber;
  
  // konverterer JSON til String som kan sendes
  String requestBody;
  serializeJson(jsonDoc, requestBody);
  
  // sender selve requesten og lagrer responskoden
  int httpResponseCode = http.POST(requestBody);

  // sjekker status paa handlingen
  if (httpResponseCode > 0) {
    _hwSerial.print("HTTP Response code: ");
    _hwSerial.println(httpResponseCode);
    String payload = http.getString();
    _hwSerial.println(payload);
  }
  else {
    _hwSerial.print("Error code: ");
    _hwSerial.println(httpResponseCode);
  }

    // oeker vaksinetelleren vaar til slutt
    _vaxNumber++;
}
````



### 3.4.4 Google script



![image-20220527212752064](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/image-20220527212752064.png)



````js
function doGet(e){
  Logger.log("--- doGet ---");
 
 var vaxType = "", vaxNumber = 0;
 
  try {
    
    // lagrer verdiene fra ESP32-requesten
    vaxType = e.parameters.vaxType;
    vaxNumber = e.parameters.vaxNumber;
 
    // kaller lagre-funksjonen for aa legge inn verdiene i regnearket
    saveData(vaxType, vaxNumber);
 
    return ContentService.createTextOutput("Wrote:\n  vaxType: " + vaxType + "\n  vaxNumber: " + vaxNumber);
 
  } catch(error) { 
    Logger.log(error);    
    return ContentService.createTextOutput("error occured...." + error.message 
                                            + "\n" + new Date() 
                                            + "\nvaxType: " + vaxType +
                                            + "\nvaxNumber: " + vaxNumber);
  }  
}
 

function saveData(vaxType, vaxNumber){
  Logger.log("--- save_data ---"); 
 
 
  try {

    // oppretter et nytt DateTime-objekt for aa lagre dato og klokkeslett for requesten
    var dateTime = new Date();
    var formattedDate = Utilities.formatDate(dateTime, SpreadsheetApp.getActive().getSpreadsheetTimeZone(), 'dd/mm/yyyy');
    var formattedTime = Utilities.formatDate(dateTime, SpreadsheetApp.getActive().getSpreadsheetTimeZone(), 'HH:mm')
 
    // URLen til Google-regnearket der resultatene skal loggfoeres
    var ss = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1XnFOaI3ALkc4_oeiKNlrGAXcazPyHHj6WqELLz3wmyA/edit#");
    var dataLoggerSheet = ss.getSheetByName("Hovedark");
 
    // hent forrige rad som ble endret og gaa 1 videre
    var row = dataLoggerSheet.getLastRow() + 1;

    // hopper over header-raden
    if (row == 1) {return;}
 
    // vi er naa kommet til neste ledige rad som ikke er headerne, saa vi kan sette inn dataen
    dataLoggerSheet.getRange("A" + row).setValue(formattedDate); // DATO
    dataLoggerSheet.getRange("B" + row).setValue(formattedTime); // KLOKKESLETT
    dataLoggerSheet.getRange("C" + row).setValue(vaxType); // Vaksinetype
    dataLoggerSheet.getRange("D" + row).setValue(vaxNumber); // Vaksinenummer (vaksine nummer N i dag)
  }
 
  catch(error) {
    Logger.log(JSON.stringify(error));
  }
 
  Logger.log("--- saveData end---"); 
}
````



![image-20220527212705899](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/image-20220527212705899.png)





## 3.5 OTA-innstillinger

````c++
/*
 * OTAWebUpdater.ino Example from ArduinoOTA Library
 * Rui Santos 
 * Complete Project Details https://randomnerdtutorials.com
 */

#include <WiFi.h>
#include <WiFiClient.h>
#include <WebServer.h>
#include <ESPmDNS.h>
#include <Update.h>

const char* host = "esp32";
const char* ssid = "REPLACE_WITH_YOUR_SSID";
const char* password = "REPLACE_WITH_YOUR_PASSWORD";

WebServer server(80);

/*
 * Login page
 */
const char* loginIndex = 
 "<form name='loginForm'>"
    "<table width='20%' bgcolor='A09F9F' align='center'>"
        "<tr>"
            "<td colspan=2>"
                "<center><font size=4><b>ESP32 Login Page</b></font></center>"
                "<br>"
            "</td>"
            "<br>"
            "<br>"
        "</tr>"
        "<td>Username:</td>"
        "<td><input type='text' size=25 name='userid'><br></td>"
        "</tr>"
        "<br>"
        "<br>"
        "<tr>"
            "<td>Password:</td>"
            "<td><input type='Password' size=25 name='pwd'><br></td>"
            "<br>"
            "<br>"
        "</tr>"
        "<tr>"
            "<td><input type='submit' onclick='check(this.form)' value='Login'></td>"
        "</tr>"
    "</table>"
"</form>"
"<script>"
    "function check(form)"
    "{"
    "if(form.userid.value=='admin' && form.pwd.value=='admin')"
    "{"
    "window.open('/serverIndex')"
    "}"
    "else"
    "{"
    " alert('Error Password or Username')/*displays error message*/"
    "}"
    "}"
"</script>";
 
/*
 * Server Index Page
 */
 
const char* serverIndex = 
"<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>"
"<form method='POST' action='#' enctype='multipart/form-data' id='upload_form'>"
   "<input type='file' name='update'>"
        "<input type='submit' value='Update'>"
    "</form>"
 "<div id='prg'>progress: 0%</div>"
 "<script>"
  "$('form').submit(function(e){"
  "e.preventDefault();"
  "var form = $('#upload_form')[0];"
  "var data = new FormData(form);"
  " $.ajax({"
  "url: '/update',"
  "type: 'POST',"
  "data: data,"
  "contentType: false,"
  "processData:false,"
  "xhr: function() {"
  "var xhr = new window.XMLHttpRequest();"
  "xhr.upload.addEventListener('progress', function(evt) {"
  "if (evt.lengthComputable) {"
  "var per = evt.loaded / evt.total;"
  "$('#prg').html('progress: ' + Math.round(per*100) + '%');"
  "}"
  "}, false);"
  "return xhr;"
  "},"
  "success:function(d, s) {"
  "console.log('success!')" 
 "},"
 "error: function (a, b, c) {"
 "}"
 "});"
 "});"
 "</script>";

/*
 * setup function
 */
void setup(void) {
  Serial.begin(115200);

  // Connect to WiFi network
  WiFi.begin(ssid, password);
  Serial.println("");

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  /*use mdns for host name resolution*/
  if (!MDNS.begin(host)) { //http://esp32.local
    Serial.println("Error setting up MDNS responder!");
    while (1) {
      delay(1000);
    }
  }
  Serial.println("mDNS responder started");
  /*return index page which is stored in serverIndex */
  server.on("/", HTTP_GET, []() {
    server.sendHeader("Connection", "close");
    server.send(200, "text/html", loginIndex);
  });
  server.on("/serverIndex", HTTP_GET, []() {
    server.sendHeader("Connection", "close");
    server.send(200, "text/html", serverIndex);
  });
  /*handling uploading firmware file */
  server.on("/update", HTTP_POST, []() {
    server.sendHeader("Connection", "close");
    server.send(200, "text/plain", (Update.hasError()) ? "FAIL" : "OK");
    ESP.restart();
  }, []() {
    HTTPUpload& upload = server.upload();
    if (upload.status == UPLOAD_FILE_START) {
      Serial.printf("Update: %s\n", upload.filename.c_str());
      if (!Update.begin(UPDATE_SIZE_UNKNOWN)) { //start with max available size
        Update.printError(Serial);
      }
    } else if (upload.status == UPLOAD_FILE_WRITE) {
      /* flashing firmware to ESP*/
      if (Update.write(upload.buf, upload.currentSize) != upload.currentSize) {
        Update.printError(Serial);
      }
    } else if (upload.status == UPLOAD_FILE_END) {
      if (Update.end(true)) { //true to set the size to the current progress
        Serial.printf("Update Success: %u\nRebooting...\n", upload.totalSize);
      } else {
        Update.printError(Serial);
      }
    }
  });
  server.begin();
}

void loop(void) {
  server.handleClient();
  delay(1);
}

````



## 3.6 Programkode 



