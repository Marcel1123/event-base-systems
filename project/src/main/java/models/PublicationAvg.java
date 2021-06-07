package models;

import utils.Constant;
import java.lang.String;

import lombok.Getter;

@Getter
public class PublicationAvg {
    private String city;
    private Double temp_avg;
    private Double rain_avg;
    private Double wind_avg;
    private Integer count;

    public PublicationAvg(final String city)
    {
        this.city = city;
        this.temp_avg = 0.0;
        this.rain_avg = 0.0;
        this.wind_avg = 0.0;
        this.count = 0;
    }

    public PublicationAvg(TPublicationAVG tPublicationAVG)
    {
        this.city = tPublicationAVG.getCity();
        this.temp_avg = tPublicationAVG.getTemp_avg();
        this.rain_avg = tPublicationAVG.getRain_avg();
        this.wind_avg = tPublicationAVG.getWind_avg();
        this.count = tPublicationAVG.getCount();
    }

    public void addPublication(@org.jetbrains.annotations.NotNull Publication publication)
    {
        if (publication.getCity().equals(this.city))
        {
            count +=1;
            this.temp_avg = (this.temp_avg + publication.getTemp())/count;
            this.rain_avg = (this.rain_avg + publication.getRain())/count;
            this.wind_avg = (this.wind_avg + publication.getWind())/count;
        }
    }

    @Override
    public String toString() {
        return "{(" + Constant.City + "," + this.city +"),"+
                "(" + Constant.AVGTemp + "," + this.temp_avg +"),"+
                "(" + Constant.AVGRain + "," + this.rain_avg +"),"+
                "(" + Constant.AVGWind + "," + this.wind_avg +")}";
    }
}
