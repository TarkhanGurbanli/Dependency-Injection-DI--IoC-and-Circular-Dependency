package az.tarkhangurbanli.learn.version3.service;

import org.springframework.stereotype.Service;

@Service
public class ProcessCoordinator {

    private final GService gService;
    private final ZService zService;

    public ProcessCoordinator(GService gService, ZService zService) {
        this.gService = gService;
        this.zService = zService;
    }

    public void execute() {
        gService.doG();
        zService.doZ();
        gService.doG();
    }

}

