from util.constants import *
from numpy import random
from config.config_file import frequency_of_equals_op_per_cities_subs


class FieldFilter:
    def __init__(self, name):
        self.name  = name
        self.value = None
        self.ValueType  = None
        self.operator = None

    def __str__(self) -> str:
        ret_val = ""
        if self.operator is None or self.value is None or self.ValueType is None:
            # print(self.operator)
            # print(self.value)
            # print(self.ValueType)
            raise Exception(" Error, bad inputs received!")
            exit(1)

        if self.ValueType is FilterValueTypeEnum.NUMERIC:
            ret_val = "({},{},{});".format(self.name, self.operator, str(self.value))
        else:
            ret_val = "({},{},\"{}\");".format(self.name, self.operator, str(self.value))
        return ret_val


class NumericFilter(FieldFilter):
    def __init__(self, name):
        super(NumericFilter, self).__init__(name)
        self.operator = c_list_of_numeric_operators[random.randint(0, len(c_list_of_numeric_operators))]
        self.ValueType = FilterValueTypeEnum.NUMERIC


class StringFilter(FieldFilter):
    def __init__(self, name):
        super(StringFilter, self).__init__(name)
        self.operator = c_list_of_string_operators[random.randint(0, len(c_list_of_string_operators))]
        self.ValueType = FilterValueTypeEnum.STRING


class StationIdFilter(NumericFilter):
    def __init__(self):
        super(StationIdFilter, self).__init__("stationId")
        self.value = c_list_of_station_ids[random.randint(0, len(c_list_of_station_ids))]


class CityFilter(StringFilter):
    def __init__(self):
        super(CityFilter, self).__init__("city")
        coin_toss = random.binomial(1, frequency_of_equals_op_per_cities_subs, size=None)
        if coin_toss:
            self.operator = "="
        self.value = c_list_of_cities[random.randint(0, len(c_list_of_cities))]


class TemperatureFilter(NumericFilter):
    def __init__(self):
        super(TemperatureFilter, self).__init__("temp")
        self.value = random.randint(c_temp_min, c_temp_max)


class AvgTemperatureFilter(NumericFilter):
    def __init__(self):
        super(AvgTemperatureFilter, self).__init__("avg_temp")
        self.value = random.randint(c_temp_min, c_temp_max)


class RainFilter(NumericFilter):
    def __init__(self):
        super(RainFilter, self).__init__("rain")
        self.value = float(random.randint(c_temp_min, c_rain_max))/100
        self.operator = "="


class AvgRainFilter(NumericFilter):
    def __init__(self):
        super(AvgRainFilter, self).__init__("avg_rain")
        self.value = float(random.randint(c_temp_min, c_rain_max))/100


class WindFilter(NumericFilter):
    def __init__(self):
        super(WindFilter, self).__init__("wind")
        self.value = random.randint(c_wind_km_h_min, c_wind_km_h_max)
        self.operator = "="


class AvgWindFilter(NumericFilter):
    def __init__(self):
        super(AvgWindFilter, self).__init__("avg_wind")
        self.value = random.randint(c_wind_km_h_min, c_wind_km_h_max)
        self.operator = "="


class DirectionFilter(StringFilter):
    def __init__(self):
        super(DirectionFilter, self).__init__("direction")
        self.value = c_list_of_wind_directions[random.randint(0, len(c_list_of_wind_directions))]


class DateFilter(NumericFilter):
    def __init__(self):
        super(DateFilter, self).__init__("date")
        time_between_dates = c_end_date_time - c_start_date_time
        days_between_dates = time_between_dates.days
        random_number_of_days = random.randint(0, days_between_dates)
        random_date = c_start_date_time + datetime
        self.value = random_date
