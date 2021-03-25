from datetime import date, timedelta
import random
import os

from config.config_file import number_of_publications
from models.Publication import Publication
from util.constants import *


def generate_publication(counter):
    return Publication(
        counter,
        random.sample(c_list_of_cities, 1)[0],
        random.randint(c_temp_min, c_temp_max),
        random.uniform(c_rain_min, c_rain_max),
        random.randint(c_wind_km_h_min, c_wind_km_h_max),
        random.sample(c_list_of_wind_directions, 1)[0],
        get_random_data()
    )


def get_random_data():
    start_date = date(2021, 1, 1)
    end_date = date(2021, 6, 1)

    time_between_dates = end_date - start_date
    days_between_dates = time_between_dates.days
    random_number_of_days = random.randrange(days_between_dates)
    random_date = start_date + timedelta(days=random_number_of_days)

    return random_date


def start():
    with open(PATH_TO_GENERATED_PUBS_FILE, "w+") as publication_file:
        for i in range(0, number_of_publications):
            publication_file.write(generate_publication(i).__str__() + "\n")


if __name__ == '__main__':
    for i in range(0, number_of_publications):
        print(generate_publication(i))
