'''
This is the file that contains the description of the resulted
'''

from util.constants import *

number_of_subscriptions = 2000
number_of_publications = 4000

frequency_of_equals_op_per_cities_subs = 0.7

frequency_dict = {
    FieldsEnum.STATION_ID: 0.7,
    FieldsEnum.CITY: 0.9,
    FieldsEnum.TEMP: 0.3,
    FieldsEnum.RAIN: 0.4,
    FieldsEnum.WIND: 0.5,
    FieldsEnum.DIRECTION: 0.6,
    FieldsEnum.DATE: 0.7
}

complex_sub_frequency_for_fields_dict = {
    ComplexPublicationEnum.CITY: 1,
    ComplexPublicationEnum.AVG_TEMP: 0.9,
    ComplexPublicationEnum.AVG_RAIN: 0.1,
    ComplexPublicationEnum.AVG_WIND: 0.2
}