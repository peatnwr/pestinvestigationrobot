import serial
import threading
import time

ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1.0)
time.sleep(3)
ser.reset_input_buffer()
print("Serial OK")

class myThread(threading.Thread):
    def __init__(self, val):
        threading.Thread.__init__(self)
        self.val = val

    def run(self):
        global command
        command = self.val
        ser.write(command+"\n".encode('utf-8'))
        ser.close()

thr1 = myThread("f")

thr1.start()