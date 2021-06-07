import sys
import threading
import time
import uuid

import paho.mqtt.client as mqtt
from numpy import random

from config.config_file import *
from models.Subscription import Subscription

number_of_subscriptions_generated = 0
number_of_publications_readed = 0
lock = threading.Lock()

avg_latency = [0.0, 0.0, 0.0]
msg_readed = [0, 0, 0]

start_time = round(time.time() * 1000)



class Subscriber:
	def __init__(self, id):
		self.id = id
		self.thread_id = str(uuid.uuid4())
		self.broker_address = "localhost"
		self.client = mqtt.Client("MqttBrokerClient12")  # create new instance
		self.subscription = None
		self.setup()
		global start_time
		start_time = round(time.time() * 1000)

	def setup(self):
		print("creating new instance")
		self.client.on_message = on_message
		self.client.connect(self.broker_address, port=8883)  # connect to broker
		print("connecting to broker")
		self.client.loop_start()  # start the loop
		self.client.subscribe(f"publication/client{self.id}")
		if random.uniform(0, 1) > 0.5:
			self.broker_id = f"/avg_broker/listen"
		else:
			self.broker_id = f"/reg_broker/listen"

	def generate_subscription(self):
		subscription = Subscription()
		for enum_filed in FieldsEnum:
			prob_of_using_filed = frequency_dict[enum_filed]
			coin_toss = random.binomial(1, prob_of_using_filed, size=None)

			if coin_toss:
				subscription.setFiled(enum_filed)

		if not subscription.isValid():
			subscription.setFiled(random.choice(FieldsEnum))
		self.subscription = subscription

	def publish(self):
		subs = "{(city,!=,\"Suceava\")}"
		self.client.publish(self.broker_id, f"client_id: client{self.id}, subscription:{self.subscription.__str__()}")
		time.sleep(0.01)
		# self.client.publish(self.broker_id, f"client_id: client{self.id}, subscription:{subs}")
		# time.sleep(2)

	def stop(self):
		self.client.loop_stop()


def on_message(cient, userdata, message):
	timp = round(time.time() * 1000)
	global number_of_publications_readed
	number_of_publications_readed = number_of_publications_readed + 1
	content = str(message.payload.decode("utf-8")).split("<>")
	# print("MSG: " + message.topic, sep="  --->")
	# print("TMP: ", timp - int(content[1]))
	# print("message received ", content)
	avg_latency[int(message.topic[-1])] = timp - int(content[1])
	msg_readed[int(message.topic[-1])] = msg_readed[int(message.topic[-1])] + 1


class Mythread(threading.Thread):
	def __init__(self, subs_id):
		threading.Thread.__init__(self)
		self.subscriber = Subscriber(subs_id)

	def run(self):
		global start_time
		global number_of_subscriptions_generated
		while number_of_subscriptions_generated < 10000:
			with lock:
				number_of_subscriptions_generated = number_of_subscriptions_generated + 1
			print(f"###################### THREAD ID: {self.subscriber.thread_id} ######################################################")
			self.subscriber.generate_subscription()
			print(f"Subscription {number_of_subscriptions_generated} generated....")
			print(f"Subscription: {self.subscriber.subscription.__str__()}")
			self.subscriber.publish()
			print(f"Subscription {number_of_subscriptions_generated} published....")
			print("######################################################################################################")

		while ((round(time.time() * 1000)) - start_time) < 1 * 60 * 1000:
			i = 0
		else:
			self.subscriber.stop()


if __name__ == '__main__':
	# sys.argv.append("0")
	# sys.argv.append("4000")
	if len(sys.argv) != 3:
		raise Exception
	subscriber = Subscriber(sys.argv[1])
	while number_of_subscriptions_generated < int(sys.argv[2]):
		# with lock:
		number_of_subscriptions_generated = number_of_subscriptions_generated + 1
		# print(
		# 	f"###################### THREAD ID: {subscriber.thread_id} ######################################################")
		subscriber.generate_subscription()
		print(f"Subscription {number_of_subscriptions_generated} generated....")
		print(f"Subscription: {subscriber.subscription.__str__()}")
		subscriber.publish()
		print(f"Subscription {number_of_subscriptions_generated} published....")
		print("######################################################################################################")

	while ((round(time.time() * 1000)) - start_time) < 3 * 60 * 1000:
		i = 0
	else:
		subscriber.stop()

	if msg_readed[int(sys.argv[1])] == 0:
		print(f"Thread-{int(sys.argv[1])}: No publication readed")
	else:
		print(f"Latenta medie thread-{int(sys.argv[1])}: {round((avg_latency[int(sys.argv[1])] / msg_readed[int(sys.argv[1])]) * 1000)}")
	# threads = []
	# for d in range(0, 3):
	# 	threads.append(Mythread(d))
	# threads[0].start()
	# threads[1].start()
	# threads[2].start()
	#
	# threads[0].join()
	# threads[1].join()
	# threads[2].join()
	# for d in range(0, 3):
	# 	if msg_readed[d] == 0:
	# 		print(f"Thread-{d}: No publication readed")
	# 	else:
	# 		print(f"Latenta medie thread-{d}: {avg_latency[d] / msg_readed[d]}")


''' A client shold publish a message with the following format to the two borkers (/reg_broker/listen, /avg_broker/listen) :
		pattern = client_id: <client_id>, subscription: <subscription>
    and should start subscribing to publication/<client_id> in order to receive all the messages from the brokers.
'''
