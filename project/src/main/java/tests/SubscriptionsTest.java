package tests;

import models.*;
import org.apache.storm.shade.org.joda.time.DateTime;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionsTest {

    @org.junit.jupiter.api.Test
    void testMatchSimpleSubscription() {
        SimpleSubscription simpleSubscription = new SimpleSubscription(
                "{(stationId,=,5);(city,=,\"Suceava\");(temp,<=,7);(wind,=,43);(direction,=,\"S\")}");

        assertTrue(simpleSubscription.match(
                Publication.builder()
                        .stationId(5)
                        .city("Suceava")
                        .temp(5)
                        .rain(5.5)
                        .wind(43)
                        .direction("S")
                        .data(DateTime.now().toDate())
                        .build()
        ));
        assertFalse(simpleSubscription.match(
                Publication.builder()
                        .stationId(5)
                        .city("Vaslui")
                        .temp(5)
                        .rain(5.5)
                        .wind(43)
                        .direction("S")
                        .data(DateTime.now().toDate())
                        .build()
        ));
    }

    @org.junit.jupiter.api.Test
    void testUnMatchSimpleSubscription() {
        SimpleSubscription simpleSubscription = new SimpleSubscription(
                "{(stationId,=,5);(city,=,\"Suceava\");(temp,<=,7);(wind,=,43);(direction,=,\"S\")}");


        assertFalse(simpleSubscription.match(
                Publication.builder()
                        .stationId(5)
                        .city("Vaslui")
                        .temp(5)
                        .rain(5.5)
                        .wind(43)
                        .direction("S")
                        .data(DateTime.now().toDate())
                        .build()
        ));
    }


    @org.junit.jupiter.api.Test
    void testUnMatchComplexSubscription() {
        ComplexSubscription complexSubscription = new ComplexSubscription(
                "{(city,=,\"Iasi\");(avg_temp,>,8.5);(avg_wind,<=,13)}");

        PublicationAvg avg_pub= new PublicationAvg("Iasi");
        avg_pub.addPublication(Publication.builder()
                .stationId(5)
                .city("Iasi")
                .temp(9)
                .rain(5.5)
                .wind(10)
                .direction("S")
                .data(DateTime.now().toDate())
                .build()
        );

        assertTrue(complexSubscription.match(avg_pub));
    }
    @org.junit.jupiter.api.Test
    void testPayloadRegex() {
        String patternString1 = "client_id\\s*:\\s*([\\w|_|\\d]+?)\\s*?,\\s*?subscription\\s*:\\s*?(\\{.+\\})";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher("client_id: client_1, subscription:{(stationid,<=,6);(city,!=,\"Suceava\");(direction,=,\"W\")}");

        boolean matched = false;
        if(matcher.find()) {
            System.out.println("found: " + matcher.group(1) +
                    " "       + matcher.group(2));
            matched= true;

        }
        assertTrue(matched);
    }
}