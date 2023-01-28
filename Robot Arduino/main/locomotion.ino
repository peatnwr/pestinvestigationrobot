void forwardCom() {
  analogWrite(leftEn, 210);
  analogWrite(rightEn, 130);
  digitalWrite(leftIn1, HIGH);
  digitalWrite(leftIn2, LOW);
  digitalWrite(rightIn1, HIGH);
  digitalWrite(rightIn2, LOW);
}

void turnLeftCom() {
  analogWrite(leftEn, 210);
  analogWrite(rightEn, 130);
  digitalWrite(leftIn1, HIGH);
  digitalWrite(leftIn2, LOW);
  digitalWrite(rightIn1, LOW);
  digitalWrite(rightIn2, HIGH);

}

void turnRightCom() {
  analogWrite(leftEn, 210);
  analogWrite(rightEn, 130);
  digitalWrite(leftIn1, LOW);
  digitalWrite(leftIn2, HIGH);
  digitalWrite(rightIn1, HIGH);
  digitalWrite(rightIn2, LOW);
}

void backwardCom() {
  analogWrite(leftEn, 210);
  analogWrite(rightEn, 130);
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