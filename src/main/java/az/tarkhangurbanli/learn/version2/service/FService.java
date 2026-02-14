package az.tarkhangurbanli.learn.version2.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class FService {

    private final EService aService;

    public FService(@Lazy EService aService) {
        this.aService = aService;
    }

    public void doB() {
        System.out.println("BService.doB()");
        aService.doA();
    }

}
