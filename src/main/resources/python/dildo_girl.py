import sys
from lovesense.comm import LovesenseSerialSync
from time import sleep

# NAME: dildo_girl.py
# AUTHOR: PornToken.io
#
# USAGE: 
# python dildo_girl.py command level duration_secs
# 
# command: 1-255 depending on how much ETH is sent
# level: 1-255 depending on how much ETH is sent 
# duration in seconds: 1-n depending on how much ETH is sent
#
# NOTE: The caller, in this case the Tomcat Spring Boot Application
# is responsible for synchronization. 
# Do not call this script asynchronously from Java.
#
# Java: Runtime.getRuntime().exec("python dildo_girl.py command level duration_secs");
#
# Depends on: https://github.com/metafetish/lovesense-py

def main():
    command = sys.argv[1];
    level = sys.argv[2];
    duration_in_seconds = sys.argv[3];
    
    print("Lovesense Dildo Command: {}".format(command))
    print("Lovesense Dildo Level: {}".format(level))
    print("Duration in Seconds: {}".format(duration_in_seconds))
    
    s = LovesenseSerialSync("/dev/rfcomm0")
    
    if command == 'vibrate':
        print('Vibrating Dildo at level: {}'.format(level))
        s.setVibrate(level)
    elif command == 'rotate':
        print('Rotate Dildo at level: {}'.format(level))
        s.setRotate(level)
    elif command == 'blitz':
        print('Rotate and Vibrate Dildo at level: {}'.format(level))
        s.setVibrate(level)
        s.setRotate(level)
        
    sleep(duration_in_seconds)
    
    s.setVibrate(0)
    s.setRotate(0)
    
    return 0

if __name__ == "__main__":
    main()
