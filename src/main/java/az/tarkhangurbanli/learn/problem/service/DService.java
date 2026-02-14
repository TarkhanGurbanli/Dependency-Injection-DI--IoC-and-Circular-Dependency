package az.tarkhangurbanli.learn.problem.service;

import org.springframework.stereotype.Service;

@Service
public class DService {

    private final CService aService;

    public DService(CService aService) {
        this.aService = aService;
    }

    public void doB() {
        System.out.println("BService.doB()");
        aService.doA();
    }

}
