import socket
import sys
from adafruit_servokit import ServoKit
import time
from threading import Thread

class ServoCom(Thread):
    def __init__(self):
        Thread.__init__(self)

    def run(self):
        kit = ServoKit(channels=16)

        #UDP communication
        UDP_IP = '127.0.0.1'
        UDP_PORT = 5556
        BUFFER_SIZE = 20 
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.bind((UDP_IP,UDP_PORT))

        while True:
            data, addr = s.recvfrom(1024)
            data = data.decode()
            data = data.split('\n')[0].split(',')

            if(len(data) == 3):
                print(data[0])
                print(data[1])
                print(data[2])

                angle1 = float(data[0])
                angle2 = float(data[1])
                angle3 = float(data[2])

                kit.servo[0].angle = angle1
                kit.servo[1].angle = angle2
                kit.servo[2].angle = angle3
