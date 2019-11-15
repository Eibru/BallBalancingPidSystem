#!/bin/bash
killall python3
killall java
python3 Com.py &
java -jar "/home/pi/project/BallBalancingPidSystem/src/project.jar" &
wait
