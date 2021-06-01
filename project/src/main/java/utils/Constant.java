package utils;

public class Constant {
    public static final String SPOUT_ID = "source_spout";
    public static final String AVG_BOLT_ID = "parser_bolt";
    public static final String TEMP_BOLT_ID = "avg_temp_bolt";
    public static final String WIND_BOLT_ID = "avg_wind_bolt";
    public static final String TERMINAL_BOLT_ID = "terminal_bolt";

    /* Publications helper strings*//*
(stationId," + stationId +
                "),(city,\"" + city + '\"' +
                "),(temp," + temp +
                "),(rain," + rain +
                "),(wind," + wind +
                "),(direction,\"" + direction + '\"' +
                "),(data," + data +
                ")}";
* */
    public static final String StationId = "stationId";
    public static final String City = "city";
    public static final String Temp = "temp";
    public static final String AVGTemp = "avg_temp";
    public static final String Rain = "rain";
    public static final String AVGRain = "avg_rain";
    public static final String Wind = "wind";
    public static final String AVGWind = "avg_wind";
    public static final String Direction = "direction";
    public static final String Data = "data";

    public static final String MaxTuplesAvg = "max_tuples_window";
}
