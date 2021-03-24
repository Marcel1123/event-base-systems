import datetime
import random


#### PARAMETERS
directions = ["N", "S", "W", "E", "NW", "NE", "SW", "SE"]
cities = ["Iasi", "Botosani", "Suceava", "Vaslui", "Braila"]
operators = [">", ">=", "<", "<=", "="]
NUMBER_OF_MESSAGES = 50
temperature_range = [-20, 45]
rain_range = [0.0, 20.0]
wind_range = [0, 120]


class Publication:
    def __init__(self, stationId, city, temp, rain, wind, direction, data):
        self.stationId = stationId
        self.city = city
        self.temp = temp
        self.rain = rain
        self.wind = wind
        self.direction = direction
        self.data = data

    def __str__(self):
        return "{" + \
                "(stationId," + str(self.stationId) + \
                "),(city,\"" + self.city + '\"' + \
                "),(temp," + str(self.temp) + \
                "),(rain," + str(self.rain) + \
                "),(wind," + str(self.wind) + \
                "),(direction,\"" + str(self.direction) + '\"' + \
                "),(data," + str(self.data) + \
                ")}"


def generate_publication(counter):
    return Publication(
        counter,
        random.sample(cities, 1)[0],
        random.randint(temperature_range[0], temperature_range[1]),
        random.uniform(rain_range[0], rain_range[1]),
        random.randint(wind_range[0], wind_range[1]),
        random.sample(directions, 1)[0],
        get_random_data()
    )


def get_random_data():
    start_date = datetime.date(2021, 1, 1)
    end_date = datetime.date(2021, 6, 1)

    time_between_dates = end_date - start_date
    days_between_dates = time_between_dates.days
    random_number_of_days = random.randrange(days_between_dates)
    random_date = start_date + datetime.timedelta(days=random_number_of_days)

    return random_date


if __name__ == '__main__':
    for i in range(0, NUMBER_OF_MESSAGES):
        print(generate_publication(i))
