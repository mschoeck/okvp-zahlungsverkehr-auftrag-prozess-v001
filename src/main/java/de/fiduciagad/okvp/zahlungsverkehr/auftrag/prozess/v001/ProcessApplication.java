package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.v001;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class ProcessApplication {
	private final static Logger LOGGER = Logger.getLogger(ProcessApplication.class.getName());
	
	public static void main(String... args) {
		SpringApplication.run(ProcessApplication.class, args);
		LOGGER.info("Application " + ProcessApplication.class.getName() + " läuft");
	}
	
/*	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Folgende Komponenten sind registirert:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }*/

}
