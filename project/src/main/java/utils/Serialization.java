package utils;

import lombok.SneakyThrows;
import models.TPublication;
import models.TPublicationAVG;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;

public class Serialization {
    @SneakyThrows
    public static byte[] serialize(TPublication publication){
        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
        byte[] serialized = serializer.serialize(publication);

        return serialized;
    }
    @SneakyThrows
    public static byte[] serialize(TPublicationAVG publication){
        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
        byte[] serialized = serializer.serialize(publication);

        return serialized;
    }

    @SneakyThrows
    public static TPublication deserialize(byte[] bytes){
        TPublication publication = new TPublication();
        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
        deserializer.deserialize(publication, bytes);

        return publication;
    }

    @SneakyThrows
    public static TPublicationAVG deserializeAVG(byte[] bytes){
        TPublicationAVG publication = new TPublicationAVG();
        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
        deserializer.deserialize(publication, bytes);

        return publication;
    }
}
