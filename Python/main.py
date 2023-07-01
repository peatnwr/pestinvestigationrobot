import serial
import threading
import time
import math
import py_qmc5883l
from geopy.distance import geodesic
from datetime import datetime

ser = serial.Serial('/dev/ttyACM0', 115200, timeout=1.0)
nowBearing = 0
presentIndexCoord = 1
turnSelect = ""
listOfDegree = []
locationToGo = ()
ultrasonicDetect = False

currentLocation = (16.47538604598333, 102.82465232727797)

coordDict = {1: (16.47544812240036, 102.82464880980919), 2: (16.475589983754638, 102.8246478615051),
             3: (16.47572275134042, 102.82464691320104), 4: (16.475827328482644, 102.82466113776252),
             5: (16.47571729514015, 102.82467820723629), 6: (16.475589983756816, 102.82468294875779),
             7: (16.47544357556254, 102.82468768027729)}

class crange:
    def __init__(self, start, stop, step=None, modulo=None):
        if(step == 0):
            raise ValueError("crange() arg 3 must not be zero")
        
        if step is None and modulo is None:
            self.start = 0
            self.stop = start
            self.step = 1
            self.modulo = stop
        else:
            self.start = start
            self.stop = stop
            if modulo is None:
                self.step = 1
                self.modulo = step
            else:
                self.step = step
                self.modulo = modulo

    def __iter__(self):
        n = self.start
        if n > self.stop:
            while n < self.modulo:
                yield n
                n += 1
            n = 0
        while n < self.stop:
            yield n
            n += 1

    def __contains__(self, n):
        if(self.start >= self.stop):
            return self.start <= n < self.modulo or 0 <= n < self.stop
        else:
            return self.start <= n < self.stop
        
def mapDegree(degree):
    return degree % 360

def degreeCal(degree):
    approximate = 5
    degreeSize = 360
    global listOfDegree
    listOfDegree = []

    listOfDegree += list(crange(start=mapDegree(degree - approximate),
                                stop=degree,
                                modulo=degreeSize))

    listOfDegree += list(crange(start=degree,
                                stop=mapDegree(degree + approximate),
                                modulo=degreeSize))
    
    return listOfDegree

def convert_to_degrees(raw_value):
    decimal_value = raw_value/100.00
    degrees = int(decimal_value)
    mm_mmmm = (decimal_value - int(decimal_value))/0.6
    positoin = degrees + mm_mmmm
    positoin = "%.8f"%(positoin)
    return positoin

def getGps():
    ser = serial.Serial('/dev/ttyAMA0')
    gpgga_info = "$GPGGA,"
    GPGGA_buffer = 0
    NMEA_buff = 0
    received_data = (str)(ser.readline())
    GPGGA_data_available = received_data.find(gpgga_info)
    if(GPGGA_data_available > 0):
        GPGGA_buffer = received_data.split("$GPGGA,", 1)[1]
        NMEA_buff = (GPGGA_buffer.split(','))
        nmea_time = []
        nmea_latitude = []
        nmea_longitude = []
        nmea_time = NMEA_buff[0]
        nmea_latitude = NMEA_buff[1]
        nmea_longitude = NMEA_buff[3]
        print("NMEA Time : ", nmea_time, '\n')

        try:
            lat = float(nmea_latitude)
            longi = float(nmea_longitude)
        except:
            return getGps();

        lat = convert_to_degrees(lat)
        longi = convert_to_degrees(longi)
        print("NMEA Latitude:", lat, "NMEA Longitude:", longi, '\n')
        ser.close()
        return (lat, longi)
    
def calDistance(coordA, coordB):
    return int(geodesic(coordA, coordB).m)

def getBearing():
    global nowBearing
    try:
        sensor = py_qmc5883l.QMC5883L()
        sensor.calibration = [[1.244893555537046, 0.02503667714209977, -848.2575283021728],
                              [0.02503667714209977, 1.0025596231021396, 1956.936978321106],
                              [0.0, 0.0, 1.0]]
        m = sensor.get_bearing()

        nowBearing = int(m)

        return int(m)
    except:
        getBearing()

def gpsInfo():
    global currentLocation
    currentLocation = getGps()
    if(currentLocation is None):
        currentLocation = (16.47538604598333, 102.82465232727797)

def calCompassBearing(coordA, coordB):
    if(type(coordA) != tuple) or (type(coordB) != tuple):
        raise TypeError("Only tuples are supported as args")
    
    lat1 = math.radians(coordA[0])
    lat2 = math.radians(coordB[0])

    distanceLong = math.radians(coordB[1] - coordA[1])

    x = math.sin(distanceLong) * math.cos(lat2)
    y = math.cos(lat1) * math.sin(lat2) - (math.sin(lat1) * math.cos(lat2) * math.cos(distanceLong))

    initBearing = math.atan2(x, y)

    '''Now we have the initial bearing but math.atan2 return values
    from -180 to +180 which is not what we want for a compass bearing
    The solution is to normalize the initial bearing as shown below'''
    initBearing = math.degrees(initBearing)
    compassBearing = (initBearing + 360) % 360
    
    return int(compassBearing)

def turnLeftOrRight(degreeNow, degreeToGo):
    try:
        left = 0
        right = 0
        degreeSize = 360

        right = len(list(crange(start=degreeNow,
                                stop=degreeToGo,
                                modulo=degreeSize)))
        
        left = len(list(crange(start=degreeToGo,
                               stop=degreeNow,
                               modulo=degreeSize)))
        
        select = ""

        if degreeNow in degreeCal(degreeToGo):
            select = "f"
        elif right < left:
            select = "r"
        elif left < right:
            select = "l"
        else:
            select = "f"
        
        diff = 0
        if select == "r": diff = right
        elif select == "l": diff = left

        global turnSelect
        turnSelect = select

        return select
    except:
        turnLeftOrRight(getBearing(), calCompassBearing(currentLocation, locationToGo))

def putLocation(text):
    global presentIndexCoord
    global locationToGo
    global currentLocation
    if(text == ""):
        locationToGo = coordDict[presentIndexCoord]
    elif(text == "nextCoord"):
        currentLocation = locationToGo
        presentIndexCoord += 1
        locationToGo = coordDict[presentIndexCoord]
    else:
        locationToGo = coordDict[presentIndexCoord]

def thread_CallBack():
    try:
        global ultrasonicDetect
        while True:
            time.sleep(0.01)
            if ser.in_waiting > 0:
                res = ser.readline().decode('utf-8').rstrip()
                print(res)
                if(res == "alreadyInCoordPos"):
                    putLocation("nextCoord")
                elif(res == "objDetect"):
                    ultrasonicDetect = True
    except:
        thread_CallBack()

def forwardCom_Thread():
    while True:
        getBearing()
        putLocation("")
        degreeCal(calCompassBearing(currentLocation, locationToGo))
        turnLeftOrRight(getBearing(), calCompassBearing(currentLocation, locationToGo))
        command = turnSelect+"?"+str(calDistance(currentLocation, locationToGo))+"?"+str(nowBearing)+"?"+str(calCompassBearing(currentLocation, (locationToGo))+"?"+str(listOfDegree)+"\n")
        ser.write(command.encode('utf-8'))

threadForward = threading.Thread(target=forwardCom_Thread)
threadCallback = threading.Thread(target=thread_CallBack)
threadForward.start()
threadCallback.start()
threadForward.join()
threadCallback.join()
ser.close()