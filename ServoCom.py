import socket
import sys
#from adafruit_servokit import ServoKit

#kit = ServoKit(channels=16)

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

    servo1 = data[0]
    servo2 = data[1]
    servo3 = data[2]

    print(servo1)
    print(servo2)
    print(servo3)
    print("\n")

    #kit.servo[0].angle = servo1
    #kit.servo[1].angle = servo2
    #kit.servo[2].angle = servo3