void forwardCom(int wheelSpeedLeft, int wheelSpeedRight) {
  analogWrite(leftEn, wheelSpeedLeft);
  analogWrite(rightEn, wheelSpeedRight);
  digitalWrite(leftIn1, HIGH);
  digitalWrite(leftIn2, LOW);
  digitalWrite(rightIn1, HIGH);
  digitalWrite(rightIn2, LOW);
}

void turnCom(String command) {
  if(command == "l"){
    analogWrite(leftEn, 180);
    analogWrite(rightEn, 255);
    digitalWrite(leftIn1, HIGH);
    digitalWrite(leftIn2, LOW);
    digitalWrite(rightIn1, HIGH);
    digitalWrite(rightIn2, LOW);
  } else if(command == "r"){
    analogWrite(leftEn, 255);
    analogWrite(rightEn, 180);
    digitalWrite(leftIn1, HIGH);
    digitalWrite(leftIn2, LOW);
    digitalWrite(rightIn1, HIGH);
    digitalWrite(rightIn2, LOW);
  }
}

void staticTurnLeftCom() {
  analogWrite(leftEn, 255);
  analogWrite(rightEn, 255);
  digitalWrite(leftIn1, HIGH);
  digitalWrite(leftIn2, LOW);
  digitalWrite(rightIn1, LOW);
  digitalWrite(rightIn2, HIGH);
}

void staticTurnRightCom() {
  analogWrite(leftEn, 255);
  analogWrite(rightEn, 255);
  digitalWrite(leftIn1, LOW);
  digitalWrite(leftIn2, HIGH);
  digitalWrite(rightIn1, HIGH);
  digitalWrite(rightIn2, LOW);
}

void backwardCom() {
  analogWrite(leftEn, 255);
  analogWrite(rightEn, 255);
  digitalWrite(leftIn1, LOW);
  digitalWrite(leftIn2, HIGH);
  digitalWrite(rightIn1, LOW);
  digitalWrite(rightIn2, HIGH);
}

void stopCom() {
  analogWrite(leftEn, 0);
  analogWrite(rightEn, 0);
  digitalWrite(leftIn1, LOW);
  digitalWrite(leftIn2, LOW);
  digitalWrite(rightIn1, LOW);
  digitalWrite(rightIn2, LOW);
  }