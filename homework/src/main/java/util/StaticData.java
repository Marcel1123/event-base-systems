package util;

import lombok.Getter;

import java.util.Date;

public class StaticData {
    public static final String[] directions = {"N", "S", "W", "E", "NW", "NE", "SW", "SE"};
    public static final String[] cities = {"Iasi", "Botosani", "Suceava", "Vaslui", "Braila"};
    public static final String[] operators = {">", ">=", "<", "<=", "="};
    public static final Date[] dates = {
            new Date()
    };
}