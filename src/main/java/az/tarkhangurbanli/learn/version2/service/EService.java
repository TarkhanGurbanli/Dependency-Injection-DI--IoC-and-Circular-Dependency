package az.tarkhangurbanli.learn.version2.service;

import org.springframework.stereotype.Service;

@Service
public class EService {

    private final FService bService;

    public EService(FService bService) {
        this.bService = bService;
    }

    public void doA() {
        System.out.println("AService.doA()");
        bService.doB();
    }

}
