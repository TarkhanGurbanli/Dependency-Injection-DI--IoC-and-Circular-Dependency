package az.tarkhangurbanli.learn.version1.service;

import az.tarkhangurbanli.learn.version1.event.CallAEvent;
import az.tarkhangurbanli.learn.version1.event.CallBEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AService {

    private final ApplicationEventPublisher publisher;

    public AService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void startProcess() {
        System.out.println("AService: startProcess()");
        publisher.publishEvent(new CallBEvent("hello from A"));
    }

    // BService A-nı çağırmaq istəyəndə
    @EventListener
    public void onCallA(CallAEvent event) {
        System.out.println("AService: onCallA(), value = " + event.value());
    }

}
