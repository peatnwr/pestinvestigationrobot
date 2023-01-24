//LIBRARY
#include <Wire.h>
#include <MechaQMC5883.h>
#include <TinyGPS++.h>
#include <SoftwareSerial.h>

MechaQMC5883 qmc;
TinyGPSPlus gps;

//GPIO PINS
//LEFT PINS
int leftIn1 = 2;
int leftIn2 = 3;
int leftEn = 6;
int leftHall = A0;

//RIGHT PINS
int rightIn1 = 4;
int rightIn2 = 5;
int rightEn = 7;
int rightHall = A1;

//ULTRA SONIC
int echo = 12;
const int trig = 13;

//GPS MODULE
static const int gpsRxPin = 9, gpsTxPin = 8;
static const uint32_t GPSBaud = 9600;

SoftwareSerial ss(gpsRxPin, gpsRxPin);

void setup() {
  //SERIAL BEGIN SETTING
  Serial.begin(9600);
  ss.begin(GPSBaud);

  //PINS SETUP
  pinMode(leftIn1, OUTPUT);
  pinMode(leftIn2, OUTPUT);
  pinMode(leftEn, OUTPUT);

  pinMode(rightIn1, OUTPUT);
  pinMode(rightIn2, OUTPUT);
  pinMode(rightEn, OUTPUT);

  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);

  //COMPASS SETUP
  Wire.begin();
  qmc.init();
}

void loop() {
  // put your main code here, to run repeatedly:
}

void ultrasonic() {
  long duration;

  digitalWrite(trig, LOW);
  delayMicroseconds(1);
  digitalWrite(trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(trig, LOW);

  duration = pulseIn(echo, HIGH);
  return duration * 0.034 / 2;
}

void forwardCom() {
  analogWrite(leftEn, 255);
  analogWrite(rightEn, 255);
  digitalWrite(leftIn1, HIGH);
  digitalWrite(leftIn2, LOW);
  digitalWrite(rightIn1, HIGH);
  digitalWrite(rightIn2, LOW);
}

void turnLeftCom() {
  analogWrite(leftEn, 255);
  analogWrite(rightEn, 255);
  digitalWrite(leftIn1, HIGH);
  digitalWrite(leftIn2, LOW);
  digitalWrite(rightIn1, LOW);
  digitalWrite(rightIn2, HIGH);

}

void turnRightCom() {
  analogWrite(leftEn, 255);
  analogWrite(rightEn, 255);
  digitalWrite(leftIn1, LOW);
  digitalWrite(leftIn2, HIGH);
  digitalWrite(rightIn1, HIGH);
  digitalWrite(rightIn2, LOW);
}

void backwardCom () {
  analogWrite(leftEn, 255);
  analogWrite(rightEn, 255);
  digitalWrite(leftIn1, LOW);
  digitalWrite(leftIn2, HIGH);
  digitalWrite(rightIn1, LOW);
  digitalWrite(rightIn2, HIGH);
}

void hallLeft() {
  int val = digitalRead(A0);
  return val;
}

void hallRight() {
  int val = digitalRead(A1);
  return val;
}

void comPass() {
  //https://kku.world/g4cvb
  int x, y, z;
  int azimuth;

  qmc.read(&x, &y, &z, &azimuth);

  if (y > 2060 && x < -1200 && x > -1340) {
    Serial.println("N");
  }
  else if (y < 800 && y > 700 && x < 0 && x > -200) {
    Serial.println("W");
  }
  else if (y < 750 && y > 550 && x < -2500) {
    Serial.println("E");
  }
  else if (y < -450 && y > -650 && x < -1000 && x > -1200) {
    Serial.println("S");
  }
  else{
    Serial.println("Error Alert!!");
  }
}

void gpsModule() {
  //https://kku.world/g8ltl
  while (ss.available() > 0) {
    gps.encode(ss.read());
    if (gps.location.isUpdated()) {
      // Latitude in degrees (double)
      Serial.print("Latitude= ");
      Serial.print(gps.location.lat(), 6);
      // Longitude in degrees (double)
      Serial.print(" Longitude= ");
      Serial.println(gps.location.lng(), 6);

      // Raw latitude in whole degrees
      Serial.print("Raw latitude = ");
      Serial.print(gps.location.rawLat().negative ? "-" : "+");
      Serial.println(gps.location.rawLat().deg);
      // ... and billionths (u16/u32)
      Serial.println(gps.location.rawLat().billionths);

      // Raw longitude in whole degrees
      Serial.print("Raw longitude = ");
      Serial.print(gps.location.rawLng().negative ? "-" : "+");
      Serial.println(gps.location.rawLng().deg);
      // ... and billionths (u16/u32)
      Serial.println(gps.location.rawLng().billionths);

      // Raw date in DDMMYY format (u32)
      Serial.print("Raw date DDMMYY = ");
      Serial.println(gps.date.value());

      // Year (2000+) (u16)
      Serial.print("Year = ");
      Serial.println(gps.date.year());
      // Month (1-12) (u8)
      Serial.print("Month = ");
      Serial.println(gps.date.month());
      // Day (1-31) (u8)
      Serial.print("Day = ");
      Serial.println(gps.date.day());

      // Raw time in HHMMSSCC format (u32)
      Serial.print("Raw time in HHMMSSCC = ");
      Serial.println(gps.time.value());

      // Hour (0-23) (u8)
      Serial.print("Hour = ");
      Serial.println(gps.time.hour());
      // Minute (0-59) (u8)
      Serial.print("Minute = ");
      Serial.println(gps.time.minute());
      // Second (0-59) (u8)
      Serial.print("Second = ");
      Serial.println(gps.time.second());
      // 100ths of a second (0-99) (u8)
      Serial.print("Centisecond = ");
      Serial.println(gps.time.centisecond());

      // Raw speed in 100ths of a knot (i32)
      Serial.print("Raw speed in 100ths/knot = ");
      Serial.println(gps.speed.value());
      // Speed in knots (double)
      Serial.print("Speed in knots/h = ");
      Serial.println(gps.speed.knots());
      // Speed in miles per hour (double)
      Serial.print("Speed in miles/h = ");
      Serial.println(gps.speed.mph());
      // Speed in meters per second (double)
      Serial.print("Speed in m/s = ");
      Serial.println(gps.speed.mps());
      // Speed in kilometers per hour (double)
      Serial.print("Speed in km/h = ");
      Serial.println(gps.speed.kmph());

      // Raw course in 100ths of a degree (i32)
      Serial.print("Raw course in degrees = ");
      Serial.println(gps.course.value());
      // Course in degrees (double)
      Serial.print("Course in degrees = ");
      Serial.println(gps.course.deg());

      // Raw altitude in centimeters (i32)
      Serial.print("Raw altitude in centimeters = ");
      Serial.println(gps.altitude.value());
      // Altitude in meters (double)
      Serial.print("Altitude in meters = ");
      Serial.println(gps.altitude.meters());
      // Altitude in miles (double)
      Serial.print("Altitude in miles = ");
      Serial.println(gps.altitude.miles());
      // Altitude in kilometers (double)
      Serial.print("Altitude in kilometers = ");
      Serial.println(gps.altitude.kilometers());
      // Altitude in feet (double)
      Serial.print("Altitude in feet = ");
      Serial.println(gps.altitude.feet());

      // Number of satellites in use (u32)
      Serial.print("Number os satellites in use = ");
      Serial.println(gps.satellites.value());

      // Horizontal Dim. of Precision (100ths-i32)
      Serial.print("HDOP = ");
      Serial.println(gps.hdop.value());
    }
  }
}