## Teknisk rapport – IN1060

**Gruppenavn:** Ifibabez

**Medlemmer:** Julie Haukås, Martine Røsand Hernæs, Synne Markmanrud, Ola Juul Holm og Jørgen Andresen Osberg 

**Brukergruppe:** <span style="color:orange;">**Kvinnelige helsearbeidere på Ullern Vaksinesenter i alder 20 til 30 år.**</span>



### Introduksjon

Den endelige tekniske løsningen for å lage en fungerende artefakt vi kunne filme og vise til brukerne er et resultat av flere iterasjoner og mye eksperimentering med ulike komponenter og 3D-printede elementer. Som man kan se i bildet under og i videoen, består løsningen av en 3D-printet boks med en Arduino/mikrokontroller, 



### Bilde av ferdig artefakt

![IMG_3102](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/Bilder av prototype/IMG_3102.JPG)



### Tidlig Tinkercad-schematic som viser de sentrale komponentene

![](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/Enkel schematic tinkercad.png)



### Liste over alle komponenter som er brukt i den endelige artefakten

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
| 3D-printede elementer (toppknapper, bunnplate til knappene, boks og bunnplate) | //     |
| Diverse custom-kabler som er kuttet og loddet                | //     |
| Små skruer og muttere til å feste de 3D-printede elementene til hverandre | //     |



### Full hookup-schematic for mikrokontrolleren vi bruker

![graphical_datasheet_ESP32-S2_thing_plus](/Users/jorgenosberg/Library/CloudStorage/OneDrive-Personal/Universitetet i Oslo/Informatikk/2. semester/ifibabez/ifibabez/IN1060/Teknisk rapport/graphical_datasheet_ESP32-S2_thing_plus.png)

Det ble fort klart at WiFi-modulen som er ekstratilbehør til Arduino Uno faktisk kostet mer enn en komplett mikrokontroller med innebygget WiFi og BLT, så vi bestemte oss for å kjøpe en ESP32-basert mikrokontroller som kan programmeres vha. Arduino-rammeverket. Denne mikrokontrolleren, LED-diodene, et prototypingsbrett til lodding og noen ekstra knapper/switcher fikk vi ganske raskt tak i gjennom Elfa Distrelec (som vi også så at har levert de fleste komponentene til Sonen i 3. etg.).



