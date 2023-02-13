//LIBRARY
#include <Wire.h>
#include <MechaQMC5883.h> //https://kku.world/gtuns
#include <TinyGPS++.h> //https://kku.world/4o8g3
#include <SoftwareSerial.h>
#include <TimeLib.h>

MechaQMC5883 qmc;
TinyGPSPlus gps;

//GPIO PINS
//LEFT PINS
int leftIn1 = 2;
int leftIn2 = 3;
int leftEn = 6;
int leftHall = 19;

//RIGHT PINS
int rightIn1 = 4;
int rightIn2 = 5;
int rightEn = 7;
int rightHall = 18;

//ULTRA SONIC
int echo = 12;
const int trig = 13;

//GPS MODULE
static const int gpsRxPin = 9, gpsTxPin = 8;
static const uint32_t GPSBaud = 9600;

//VALUES
volatile int hallCounterLeft = 0;
volatile int hallCounterRight = 0;

unsigned long previousMillis = 0;
const long interval = 1000;

SoftwareSerial ss(gpsRxPin, gpsRxPin);

void setup() {
  //SERIAL BEGIN SETTING
  Serial.begin(9600);
  ss.begin(GPSBaud);

  //PINS SETUP
  pinMode(leftIn1, OUTPUT);
  pinMode(leftIn2, OUTPUT);
  pinMode(leftEn, OUTPUT);
  pinMode(leftHall, INPUT);

  pinMode(rightIn1, OUTPUT);
  pinMode(rightIn2, OUTPUT);
  pinMode(rightEn, OUTPUT);
  pinMode(rightHall, INPUT);

  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);

  //COMPASS SETUP
  Wire.begin();
  qmc.init();

  //ATTACH AN INTERRUPT TO THE HALL SENSOR PIN
  attachInterrupt(digitalPinToInterrupt(leftHall), hallInterruptLeft, RISING);
  attachInterrupt(digitalPinToInterrupt(rightHall), hallInterruptRight, RISING);
  
  setTime(11, 59, 0, 1, 1, 20);
}

void loop() {
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= interval){
    previousMillis = currentMillis;

    int sec = (currentMillis / 1000) % 60;
    int min = (currentMillis / (1000 * 60)) % 60;
    int hour = (currentMillis / (1000 * 60 * 60)) % 24;

    Serial.print(hour);
    Serial.print(":");
    Serial.print(min);
    Serial.print(":");
    Serial.print(sec);
    Serial.println();
  }
}