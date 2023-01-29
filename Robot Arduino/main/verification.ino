float ultrasonic() {
  float duration;

  digitalWrite(trig, LOW);
  delayMicroseconds(2);
  digitalWrite(trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(trig, LOW);

  duration = pulseIn(echo, HIGH);
  return duration / 29 / 2;
}

float hallLeft() {
  float val = analogRead(leftHall);
  return val;
}

float hallRight() {
  float val = analogRead(rightHall);
  return val;
}

float hallLeftDegree() {
  float val = hallLeft();
  float degree = map(val, 0, 1023, 0, 360);
  return degree;
}

float hallRightDegree() {
  float val = hallRight();
  float degree = map(val, 0, 1023, 0, 360);
  return degree;
}

int hallLeftSpeed() {
  int rpm = (hallCounterLeft * 60) / 7;
  hallCounterLeft = 0;
  return rpm;
}

int hallRightSpeed() {
  int rpm = (hallCounterRight * 60) / 7;
  hallCounterRight = 0;
  return rpm;
}

void hallInterruptLeft() {
  hallCounterLeft++;
}

void hallInterruptRight() {
  hallCounterRight++;
}