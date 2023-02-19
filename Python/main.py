import serial
import threading
import time

ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1.0)
time.sleep(3)
ser.reset_input_buffer()
print("Serial OK")

def thread_callback():
    while True:
        time.sleep(1)
        print("Send data to Arduino from Thread-1")
        ser.write("f\n".encode('utf-8'))
        while ser.in_waiting <= 0:
            time.sleep(0.01)
        res = ser.readline().decode('utf-8').rstrip()
        if(res == "f is complete"):
            print(res)
            break
    
def thread_sec():
    while True:
        time.sleep(1)
        print("Send data to Arduino from Thread-2")
        while ser.in_waiting <= 0:
            time.sleep(0.01)
        res = ser.readline().decode('utf-8').rstrip()
        print(res)


thr = threading.Thread(target=thread_callback)
thr1 = threading.Thread(target=thread_sec)

thr.start()
thr1.start()

thr.join()
thr1.join()

ser.close()