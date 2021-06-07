import enum
import operator
from datetime import datetime

# Path to the output file containing the generated subscriptions
PATH_TO_GENERATED_SUBS_FILE = "./output/subsription_file.txt"
PATH_TO_GENERATED_PUBS_FILE = "./output/publication_file.txt"


class FieldsEnum(enum.Enum):
    STATION_ID = 1
    CITY = 2
    TEMP = 3
    RAIN = 4
    WIND = 5
    DIRECTION = 6
    DATE = 7


class ComplexPublicationEnum(enum.Enum):
    CITY = 8
    AVG_TEMP = 9
    AVG_RAIN = 10
    AVG_WIND = 11


class FilterValueTypeEnum(enum.Enum):
    NUMERIC = 1
    STRING = 2


# Some list of know values that will pe used in publications and subscriptions
c_list_of_station_ids           = [1, 2, 3, 4, 5, 6, 7, 8]
c_list_of_cities                = ["Iasi", "Botosani", "Suceava", "Vaslui", "Braila"]
c_list_of_wind_directions       = ["N", "S", "W", "E", "NW", "NE", "SW", "SE"]
c_list_of_numeric_operators     = [">", "<=", "<", "<=", "="]
c_list_of_string_operators      = ["=", "!="]

# temperature interval
c_temp_max = 40
c_temp_min = -30

# probability interval
c_probability_max = 1
c_probability_min = 0

# wind speed interval
c_wind_km_h_max = 120
c_wind_km_h_min = 1

# declaration of the publication date interval
c_start_date_time = datetime(2020, 5, 17)
c_end_date_time   = datetime.now()

# rain
c_rain_min = 0.0
c_rain_max = 100.0
