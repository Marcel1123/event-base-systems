from util.SubFilters import *


class ComplexSubscription:
    def __init__(self):
        self.city = None
        self.avg_temperature = None
        self.avg_rain = None
        self.avg_wind = None

    def __str__(self) -> str:
        ret_val = "{"
        if self.city:
            ret_val += self.city.__str__()
        if self.avg_temperature:
            ret_val += self.avg_temperature.__str__()
        if self.avg_rain:
            ret_val += self.avg_rain.__str__()
        if self.avg_wind:
            ret_val += self.avg_wind.__str__()

        if ret_val != "{":
            ret_val = ret_val[:-1]
            ret_val += "}\n"
        else:
            ret_val = None
        return ret_val


    def setFiled(self, enum_val):

        if enum_val == ComplexPublicationEnum.CITY:
            self.city = CityFilter()
        elif enum_val == ComplexPublicationEnum.AVG_TEMP:
            self.avg_temperature = AvgTemperatureFilter()
        elif enum_val == ComplexPublicationEnum.AVG_RAIN:
            self.avg_rain = AvgRainFilter()
        elif enum_val == ComplexPublicationEnum.AVG_WIND:
            self.avg_wind = AvgRainFilter()


    def isValid(self) -> bool:
        return self.city is not None or \
               self.avg_temperature is not None or \
               self.avg_rain is not None or \
               self.avg_wind is not None

