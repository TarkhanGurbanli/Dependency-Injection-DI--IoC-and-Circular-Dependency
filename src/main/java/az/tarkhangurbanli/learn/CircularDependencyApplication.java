package az.tarkhangurbanli.learn;

import az.tarkhangurbanli.learn.version3.service.GService;
import az.tarkhangurbanli.learn.version3.service.ProcessCoordinator;
import az.tarkhangurbanli.learn.version3.service.ZService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CircularDependencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircularDependencyApplication.class, args);

        ProcessCoordinator coordinator = new ProcessCoordinator(new GService(), new ZService());
        coordinator.execute();
    }

//    @Bean
//    CommandLineRunner run(AService aService) {
//        return args -> {
//            aService.startProcess();
//        };
//    }

}
