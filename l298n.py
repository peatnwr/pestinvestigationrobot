import RPi.GPIO as GPIO
from time import sleep

#GPIO Pins
in1 = 23
in2 = 24
en1 = 25
in3 = 17
in4 = 27
en2 = 22

#Status
temp1 = 1

GPIO.setmode(GPIO.BCM)
GPIO.setup(in1, GPIO.output)
GPIO.setup(in2, GPIO.output)
GPIO.setup(en1, GPIO.output)
GPIO.setup(in3, GPIO.output)
GPIO.setup(in4, GPIO.output)
GPIO.setup(en2, GPIO.output)

GPIO.output(in1, GPIO.LOW)
GPIO.output(in2, GPIO.LOW)
GPIO.output(in3, GPIO.LOW)
GPIO.output(in4, GPIO.LOW)

p1 = GPIO.PWN(en1, 1000)
p2 = GPIO.PWM(en2, 1000)

p1.start(100)
p2.start(100)
print("\n")
print("The default speed & direction of motor is LOW & Forward.....")
print("r-run s-stop f-forward b-backward tr-turn right tl-turn left m-medium h-high e-exit")
print("\n")

while(1):

    x = input("")

    if x == 'r':
        print("run")
        if(temp1 == 1):
            GPIO.output(in1, GPIO.HIGH)
            GPIO.output(in3, GPIO.HIGH)
            GPIO.output(in2, GPIO.LOW)
            GPIO.output(in4, GPIO.LOW)
            print("forward")
            x = 'z'
        else:
            GPIO.output(in1, GPIO.LOW)
            GPIO.output(in3, GPIO.LOW)
            GPIO.output(in2, GPIO.HIGH)
            GPIO.output(in4, GPIO.HIGH)
    elif x == 's':
        print('stop')
        GPIO.output(in1, GPIO.LOW)
        GPIO.output(in2, GPIO.LOW)
        GPIO.output(in3, GPIO.LOW)
        GPIO.output(in4, GPIO.LOW)
        x = 'z'
    elif x == 'f':
        print('forward')
        GPIO.output(in1, GPIO.HIGH)
        GPIO.output(in3, GPIO.HIGH)
        GPIO.output(in2, GPIO.LOW)
        GPIO.output(in4, GPIO.LOW)
        temp1 = 1
        x = 'z'
    elif x == 'b':
        print('backward')
        GPIO.output(in1, GPIO.LOW)
        GPIO.output(in3, GPIO.LOW)
        GPIO.output(in2, GPIO.HIGH)
        GPIO.output(in4, GPIO.HIGH)
        temp1 = 0
        x = 'z'
    elif x == 'tr':
        print('turn right')
        GPIO.output(in1, GPIO.LOW)
        GPIO.output(in3, GPIO.HIGH)
        GPIO.output(in2, GPIO.HIGH)
        GPIO.output(in4, GPIO.LOW)
        temp1 = 0
        x = 'z'
    elif x == 'tl':
        print('turn left')
        GPIO.output(in1, GPIO.HIGH)
        GPIO.output(in3, GPIO.LOW)
        GPIO.output(in2, GPIO.LOW)
        GPIO.output(in4, GPIO.HIGH)
        temp1 = 0
        x = 'z'
    elif x == 'm':
        print('medium')
        p1.ChangeDutyCycle(90)
        p2.ChangeDutyCycle(85)
    elif x == 'h':
        print('high')
        p1.ChangeDutyCycle(100)
        p2.ChangeDutyCycle(95)
    elif x == 'e':
        GPIO.cleanup()
        print("GPIO Clean up")
        break
    else:
        print("<<< wrong data >>>")
        print("Please enter the defind data to continue....")