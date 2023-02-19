import serial
import threading
import time

ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1.0)
time.sleep(3)
ser.reset_input_buffer()
print("Serial OK")

def thread_callback():
    try:
        while True:
            time.sleep(1)
            print("Send data to Arduino")
            ser.write("f\n".encode('utf-8'))
    except KeyboardInterrupt:
        print("Close Serial Communication")
        ser.close
        return True


thr = threading.Thread(target=thread_callback)

thr.start()
thr.join()