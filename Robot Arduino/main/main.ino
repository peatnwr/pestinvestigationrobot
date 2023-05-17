//LIBRARY
#include <SoftwareSerial.h>

//GPIO PINS [LEFT]
const int leftIn1 = 4, leftIn2 = 5, leftEn = 9, leftHall = 3;

//GPIO PINS [RIGHT]
const int rightIn1 = 8, rightIn2 = 7, rightEn = 6, rightHall = 2;

//ULTRA SONIC
const int echo = 12, trig = 13;

//LED PINS
const int ledPinUl = 18;

//VALUES
int listOfDegree[10];
String val ="", command = "", receiveDist = "", degreeNow = "", degreeToGo = "";
float distance = 0.000;
bool beginSuc = false, lockLH = false, lockRH = false, ttdSuc = false;
float freqLeft = 0.00, freqRight = 0.00, deltaTimeLH = 0.00, deltaTimeRH = 0.00;
int wheelSpeedLeft = 240, wheelSpeedRight = 240;
volatile int hallCountLeft = 0, hallCountRight = 0;

//TIMES RELATED
unsigned long currMillisUS, currMillisLH, currMillisRH, prevMillisUS = 0, prevMillisLH = 0, prevMillisRH = 0, prevTime = 0, prevMillisSpeed = 0, prevMillisStop = 0, prevAutoMode = 0;
const unsigned long interval = 10000;

void setup() {
  Serial.begin(115200);

  while(!Serial) {}

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

  pinMode(ledPinUl, OUTPUT);
}

void loop() {
  if(Serial.available() > 0) {
    val = Serial.readStringUntil('\n');
    splitVal(val);
    goToDegree();
    Serial.print("Command : " + command);
    Serial.print(" Distance : " + receiveDist);
    Serial.print(" Degree Now : " + degreeNow);
    Serial.print(" Degree ToGo : " + degreeToGo);
    Serial.println(" Ultrasonic : " + String(ultrasonic()));
  }
  if(ultrasonic() < 40 && ttdSuc == true) {
    digitalWrite(ledPinUl, HIGH);
    stopCom();
  } else {
    digitalWrite(ledPinUl, LOW);
  }
  if(digitalRead(leftHall) == 0 && lockLH == false) {
    lockLH = true;
    currMillisLH = millis();
    deltaTimeLH = (currMillisLH - prevMillisLH) / 1000.0;
    hallCountLeft += 1;
    freqLeft = 1 / deltaTimeLH;
    prevMillisLH = currMillisLH
  }
  if(digitalRead(leftHall) == 1 && lockLH == true) {
    lockLH = false;
  }
  if(digitalRead(rightHall) == 0 && lockRH == false) {
    lockRH = true;
    currMillisRH = millis();
    deltaTimeRH = (currMillisRH - prevMillisRH) / 1000.0;
    hallCountRight += 1;
    distance += 0.092;
    freqRight = 1 / deltaTimeRH;
    prevMillisRH = currMillisRH;
  }
  if(digitalRead(rightHall) == 1 && lockRH == true) {
    lockRH = false;
  }
  if(freqLeft - freqRight > 0.01) {
    wheelSpeedLeft++;
    wheelSpeedRight--;
  } else if(freqLeft - freqRight < -0.01) {
    wheelSpeedRight+=5;
    wheelSpeedLeft--;
  } else {}
  if(wheelSpeedLeft >= 255) {
    wheelSpeedLeft = 255;
  }
  if(wheelSpeedLeft <= 180) {
    wheelSpeedLeft = 180;
  }
  if(wheelSpeedRight >= 255) {
    wheelSpeedRight = 255;
  }
  if(wheelSpeedRight <= 180) {
    wheelSpeedRight = 180;
  }
}

void splitVal(String val) {
  String parts[5];
  int partIndex = 0;
  for(int i = 0; i < val.length(); i++) {
    char c = val.charAt(i);
    if(c == '?') {
      partIndex++;
    } else {
      parts[partIndex] += c;
    }
  }
  command = parts[0];
  receiveDist = parts[1];
  degreeNow = parts[2];
  degreeToGo = parts[3];
  appendToArr(parts[4]);
}

void appendToArr(String val) {
  int index = 0;
  val.remove(0, 1);
  val.remove(val.length() - 1, 1);

  while(val.length() > 0) {
    int commaIndex = val.indexOf(',');
    if(commaIndex == -1) {
      listOfDegree[index] = val.toInt();
      val = "";
    } else {
      String valueStr = val.substring(0, commaIndex);
      listOfDegree[index] = valueStr.toInt();
      val.remove(0, commaIndex + 1);
    }
    index++;
  }
}

void goToDegree() {
  if(command == "f" && beginSuc == true) {
    forwardCom(wheelSpeedLeft, wheelSpeedRight);
    if(receiveDist.toInt() <= distance) {
      stopCom();
      callBack("goToDistSuc");
    }
  } else if(command == "l" && beginSuc == true) {
    turnCom(command);
    if(receiveDist.toInt() <= distance) {
      stopCom();
      callBack("goToDistSuc");
    }
  } else if(command == "r" && beginSuc == true) {
    turnCom(command);
    if(receiveDist.toInt() <= distance) {
      stopCom();
      callBack("goToDistSuc");
    }
  } else {
    if(degreeNow.toInt() < degreeToGo.toInt()) {
      staticTurnLeftCom();
      for(int i = 0; i < 10; i++) {
        if(degreeNow.toInt() == listOfDegree[i]) {
          stopCom();
          callBack("ttdSuc");
        }
      }
    } else if(degreeNow.toInt() > degreeToGo.toInt()) {
      staticTurnRightCom();
      for (int i = 0; i < 10; i++) {
        if(degreeNow.toInt() == listOfDegree[i]) {
          stopCom();
          callBack("ttdSuc");
        }
      }
    } else {
      stopCom();
    }
  }
}

void callBack(String command) {
  if(command == "ttdSuc") {
    Serial.println("ttdSuc");
    beginSuc = true;
    ttdSuc = true;
    distance = 0.000;
  } else if(command == "goToDistSuc") {
    Serial.println("alreadyInCoordPos");
    beginSuc = false;
    ttdSuc = false;
    distance = 0.000;
  }
}