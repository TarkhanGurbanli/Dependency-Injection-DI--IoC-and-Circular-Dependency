package az.tarkhangurbanli.learn.version1.service;

import az.tarkhangurbanli.learn.version1.event.CallAEvent;
import az.tarkhangurbanli.learn.version1.event.CallBEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class BService {

    private final ApplicationEventPublisher publisher;

    public BService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void onCallB(CallBEvent event) {
        System.out.println("BService: onCallB(), message = " + event.message());

        // BService A-nın metodunu "çağırmaq" istəyir
        publisher.publishEvent(new CallAEvent(100));
    }

}
