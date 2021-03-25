from config.config_file import *
from models.Subscription import Subscription
from numpy import random


def generate_subscriptions():
    subscription_list = []
    for i in range(number_of_subscriptions):

        subscription = Subscription()
        for enum_filed in FieldsEnum:

            prob_of_using_filed = frequency_dict[enum_filed]
            coin_toss = random.binomial(1, prob_of_using_filed, size=None)

            if coin_toss:
                subscription.setFiled(enum_filed)

        if subscription.isValid():
            subscription_list.append(subscription)
        else:
            # generate a random Filter
            subscription.setFiled(random.choice(FieldsEnum))
    file = open(PATH_TO_GENERATED_SUBS_FILE, "w")
    for sub in subscription_list:
        # print(sub.__str__())
        file.write(sub.__str__())
    file.close()


def start():
    generate_subscriptions()


if __name__ == "__main__":
    print("ok")
    generate_subscriptions()
