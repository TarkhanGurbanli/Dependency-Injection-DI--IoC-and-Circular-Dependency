package az.tarkhangurbanli.learn.problem.service;

import org.springframework.stereotype.Service;

@Service
public class CService {

    private final DService bService;

    public CService(DService bService) {
        this.bService = bService;
    }

    public void doA() {
        System.out.println("AService.doA()");
        bService.doB();
    }

}
