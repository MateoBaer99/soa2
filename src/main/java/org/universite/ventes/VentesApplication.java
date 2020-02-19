package org.universite.ventes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VentesApplication {

    //TODO : Mise en place des logs des requetes/réponses
    public VentesApplication() {

        // Register my custom provider - not needed if it's in my.package.
        // register(SecurityRequestFilter.class);
        // TODO :Activation des logs (traces des requêtes et réponses
        //register(new LoggingFeature(LOGGING_INTERCEPTOR, Level.INFO,LoggingFeature.Verbosity.PAYLOAD_TEXT,MAX_ENTITY_SIZE));
        //TODO : Permet d'activer les traces : voir en-têtes HTTP de la réponse
        //property(ServerProperties.TRACING, "ALL");
    }

    public static void main(String[] args) {
        SpringApplication.run(VentesApplication.class, args);
    }
    
    


}
