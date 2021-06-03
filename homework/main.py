import paho.mqtt.client as mqtt
import time

from config.config_file import *
from models.Subscription import Subscription
from numpy import random

def on_message(client, userdata, message):
    print("message received ", str(message.payload.decode("utf-8")))
    print("message topic=", message.topic)
    print("message qos=", message.qos)
    print("message retain flag=", message.retain)

#broker_address="192.168.1.184"
broker_address = "localhost"
print("creating new instance")
client = mqtt.Client("MqttBrokerClient12")  # create new instance
client.on_message = on_message  # attach function to callback
print("connecting to broker")
client.connect(broker_address, port=8883)  # connect to broker
client.loop_start()  # start the loop
#print("Subscribing to topic", "/BrokerAverage/ALL")
client.subscribe("publication/client2")
subscription = Subscription()
for enum_filed in FieldsEnum:

	prob_of_using_filed = frequency_dict[enum_filed]
	coin_toss = random.binomial(1, prob_of_using_filed, size=None)

	if coin_toss:
		subscription.setFiled(enum_filed)

if not subscription.isValid():
   	# generate a random Filter
	subscription.setFiled(random.choice(FieldsEnum))


#while(True):
#	print("Send message: " + subscription.__str__())




''' A client shold publish a message with the following format to the two borkers (/reg_broker/listen, /avg_broker/listen) :
		pattern = client_id: <client_id>, subscription: <subscription>
    and should start subscribing to publication/<client_id> in order to receive all the messages from the brokers.
'''

#client.publish("/reg_broker/listen", "client_id: client2, subscription:{(city,=,\"Iasi\");(avg_wind,>=,13)}")
client.publish("/avg_broker/listen", "client_id: client2, subscription:"+subscription.__str__())
#	time.sleep(1)
time.sleep(2000)  # wait
client.loop_stop()  # stop the loop
