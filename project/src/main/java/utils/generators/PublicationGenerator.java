package utils.generators;

import models.Publication;
import utils.StaticData;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class PublicationGenerator {
    private static int publicationCreated = 0;

    public static Publication createNewPublication(){
        publicationCreated++;
        return Publication.builder()
                .stationId(publicationCreated)
                .city(getRandomCity())
                .direction(getRandomDirection())
                .temp(getRandomInteger(-10, 90))
                .rain(getRandomDouble(0.5, 10.0))
                .wind(getRandomInteger(0, 80))
                .data(getRandomDate())
                .build();
    }

    private static String getRandomCity(){
        return StaticData.cities[new Random().nextInt(StaticData.cities.length)];
    }

    private static String getRandomDirection(){
        return StaticData.directions[new Random().nextInt(StaticData.directions.length)];
    }

    private static Integer getRandomInteger(int min, int max){
        return new Random().nextInt((max - min) + 1) + min;
    }

    private static Double getRandomDouble(double min, double max){
        return min + (max - min) * new Random().nextDouble();
    }

    // get random date between (today - 20days) and (today + 20days)
    private static Date getRandomDate(){
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        long leftLimit = timeStampMillis - (1000 * 60 * 60 * 24 * 20);
        long rightLimit = timeStampMillis + (1000 * 60 * 60 * 24 * 20);
        return new Date(
                leftLimit + (long) (Math.random() * (rightLimit - leftLimit))
        );
    }
}
