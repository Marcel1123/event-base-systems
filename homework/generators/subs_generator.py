from config.config_file import *
from models.Subscription import Subscription
from models.ComplexSubscription import ComplexSubscription
from numpy import random


def generate_complex_subscription():
    complex_subscription = None
    is_subscription_valid = False
    while not is_subscription_valid:
        complex_subscription = ComplexSubscription()
        for enum_filed in ComplexPublicationEnum:

            prob_of_using_filed = complex_sub_frequency_for_fields_dict[enum_filed]
            coin_toss = random.binomial(1, prob_of_using_filed, size=None)

            if coin_toss:
                complex_subscription.setFiled(enum_filed)
        is_subscription_valid = complex_subscription.isValid()

    return complex_subscription


def generate_subscription():
    subscription = None
    is_subscription_valid = False
    while not is_subscription_valid:
        subscription = Subscription()
        for enum_filed in FieldsEnum:
            prob_of_using_filed = frequency_dict[enum_filed]
            coin_toss = random.binomial(1, prob_of_using_filed, size=None)

            if coin_toss:
                subscription.setFiled(enum_filed)
        is_subscription_valid = subscription.isValid()
    return subscription


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
