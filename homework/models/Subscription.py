from util.SubFilters import *


class Subscription:
    def __init__(self):
        self.station_id = None
        self.city = None
        self.temperature = None
        self.rain = None
        self.wind = None
        self.direction = None
        self.date = None

    def __str__(self) -> str:
        ret_val = "{"
        if self.station_id:
            ret_val += self.station_id.__str__()
        if self.city:
            ret_val += self.city.__str__()
        if self.temperature:
            ret_val += self.temperature.__str__()
        if self.rain:
            ret_val += self.rain.__str__()
        if self.wind:
            ret_val += self.wind.__str__()
        if self.direction:
            ret_val += self.direction.__str__()
        if self.date:
            ret_val += self.date.__str__()

        if ret_val != "{":
            ret_val = ret_val[:-1]
            ret_val += "}\n"
        else:
            ret_val = None
        return ret_val

    def setFiled(self, enum_val):

        if enum_val == FieldsEnum.STATION_ID:
            self.station_id = StationIdFilter()
        elif enum_val == FieldsEnum.CITY:
            self.city = CityFilter()
        elif enum_val == FieldsEnum.TEMP:
            self.temperature = TemperatureFilter()
        elif enum_val == FieldsEnum.RAIN:
            self.rain = RainFilter()
        elif enum_val == FieldsEnum.WIND:
            self.wind = WindFilter()
        elif enum_val == FieldsEnum.DIRECTION:
            self.direction = DirectionFilter()
        elif enum_val == FieldsEnum.DATE:
            self.direction = DirectionFilter()

    def isValid(self) -> bool:
        return self.station_id is not None or \
                self.city is not None or \
                self.temperature is not None or \
                self.rain is not None or \
                self.wind is not None or \
                self.direction is not None or \
                self.date is not None

