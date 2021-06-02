package brokers;

import models.Publication;
import models.PublicationAvg;
import models.Subscription;

import java.io.Serializable;

public interface IBroker extends Serializable {
    void startBroker() throws Exception;
    void startPublisher() throws Exception;
    void startListener() throws Exception;
    void process(final Publication pub);
    void process(final PublicationAvg pub_avg);
    void forward(final Publication pub);
    void forward(final PublicationAvg pub_avg);
    void addSubscriptionFromPayload(final String payload);
    void setNB(final IBroker nb);
    void addSubscriptionFromNB(final Subscription subscription);
}
