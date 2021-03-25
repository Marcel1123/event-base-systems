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
