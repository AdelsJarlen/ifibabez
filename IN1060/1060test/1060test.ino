#define KNAPP1 2
#define KNAPP2 5
#define KNAPP3 7

#define LED1 3
#define LED2 4
#define LED3 6

int knapp1State;
int knapp2State;
int knapp3State;

int currentTime;
int previousTime;

const long interval = 5000;
unsigned long previousMillis = 0;
unsigned long currentMillis = 0;

unsigned long knapp1Tid = 0;
unsigned long knapp2Tid = 0;
unsigned long knapp3Tid = 0;

boolean led1Aktiv = false;
boolean led2Aktiv = false;
boolean led3Aktiv = false;


void setup() {
  Serial.begin(9600);
  Serial.print("Starter serial...");
  
  pinMode(KNAPP1, INPUT_PULLUP);
  pinMode(KNAPP2, INPUT_PULLUP);
  pinMode(KNAPP3, INPUT_PULLUP);

  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  pinMode(LED3, OUTPUT);
}

void loop() {
  currentMillis = millis();
  Serial.println(currentMillis);
  
  knapp1State = digitalRead(KNAPP1);
  knapp2State = digitalRead(KNAPP2);
  knapp3State = digitalRead(KNAPP3);

  if (knapp1State == HIGH && led1Aktiv == false) {
    knapp1Tid = currentMillis;
    analogWrite(LED1, 255);
    led1Aktiv = true;
    }
  if (knapp2State == HIGH && led2Aktiv == false) {
    knapp2Tid = currentMillis;
    digitalWrite(LED2, HIGH);
    led2Aktiv = true;
    }
  if (knapp3State == HIGH && led3Aktiv == false) {
    knapp3Tid = currentMillis;
    digitalWrite(LED3, HIGH);
    led3Aktiv = true;
    }

  if (currentMillis - knapp1Tid >= interval/2) {
    analogWrite(LED1,64);
    }

  if (currentMillis - knapp1Tid >= interval) {
    digitalWrite(LED1,LOW);
    led1Aktiv = false;
    }

  if (currentMillis - knapp2Tid >= interval) {
    digitalWrite(LED2,LOW);
    led2Aktiv = false;
    }

  if (currentMillis - knapp3Tid >= interval) {
    digitalWrite(LED3,LOW);
    led3Aktiv = false;
    }
}
