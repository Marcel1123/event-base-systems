package models;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

import utils.Constant;

@Getter
@Builder
public class Publication {
    private Integer stationId;
    private String city;
    private Integer temp;
    private Double rain;
    private Integer wind;
    private String direction;
    private Date data;

    @Override
    public String toString() {
        return "{" +
                "("+ Constant.StationId +"," + stationId +
                "),("+ Constant.City +",\"" + city + '\"' +
                "),("+ Constant.Temp + "," + temp +
                "),(" + Constant.Rain+ "," + rain +
                "),("+Constant.Wind+"," + wind +
                "),("+Constant.Direction+",\"" + direction + '\"' +
                "),("+Constant.Data +"," + data +
                ")}";
    }
}
