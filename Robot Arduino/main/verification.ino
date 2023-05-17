float ultrasonic() {
  long duration;

  digitalWrite(trig, LOW);
  delayMicroseconds(2);
  digitalWrite(trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigm LOW);

  duration = pulseIn(echo, HIGH);
  return duration / 29 / 2;
}