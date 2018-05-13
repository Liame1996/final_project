from grovepi import *
from firebase import firebase
import paho.mqtt.client as mqtt
import time
import base64
import json
import time
import requests
import random
import threading
import sys
from picamera import PiCamera
from time import sleep

encoded_string = ''

firebase_url = 'https://software-project-4ae0c.firebaseio.com/'

ultrasonic_ranger = 4
Relay_pin = 2

pinMode(Relay_pin, "OUTPUT")

camera = PiCamera()

port = 1883
    
mqttc = mqtt.Client('client1',clean_session=False)
mqttc.username_pw_set('uuovadvt','P366rclQpQTe')
mqttc.connect('m23.cloudmqtt.com', 18203, 60)
camera.rotation = 180
camera.resolution = (1280, 720)

fixed_interval = 10
while 1:
    y = 0
    try:
        distant = ultrasonicRead(ultrasonic_ranger)
        print (distant, 'cm')
                
        if(distant != y or distant != (y+1) or distant != (y-1) or distant != (y+2) or distant != (y-2) or distant != (y+3) or distant != (y-3) or distant != (y+4) or distant != (y-4)):
            camera.start_preview()
            sleep(5)
            camera.resolution = (800, 600)
            camera.capture('/home/pi/Desktop/'+ str(distant)+'.jpg' )
            with open("/home/pi/Desktop/" + str(distant)+ ".jpg", "rb") as image_file:
                encoded_string = base64.b64encode(image_file.read())
                
            print("publish")
                        
            camera.stop_preview()

        if distant <= 10:
            digitalWrite(Relay_pin,1)
        else:
            digitalWrite(Relay_pin,0)
            
        print (distant, 'cm')

        data = {'value': distant}
        result = requests.post(firebase_url + '/distant.json', data=json.dumps(data))

        print ('Record inserted. Result Code = '+ str(result.status_code) + ',' + result.text)
        time.sleep(fixed_interval) 

        def pub():
            mqttc.publish("image", payload=encoded_string, qos=0)
            threading.Timer(1, pub).start()

        pub()
        print("Sent to CloudMQTT")

    except IOError:
        print("Error! Something went wrong")
        time.sleep(fixed_interval)
    
