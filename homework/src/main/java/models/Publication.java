package models;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

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
                "(stationId," + stationId +
                "),(city,\"" + city + '\"' +
                "),(temp," + temp +
                "),(rain," + rain +
                "),(wind," + wind +
                "),(direction,\"" + direction + '\"' +
                "),(data," + data +
                ")}";
    }
}
