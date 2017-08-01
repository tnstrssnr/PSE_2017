package edu.kit.pse17.go_app;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.toplink.LocalSessionFactory;
import org.springframework.orm.toplink.LocalSessionFactoryBean;

import javax.security.auth.login.Configuration;

/**
 * Die Main-Klasse der Server-Anwendung. In ihr wird keine Anwendungslogik ausgeführt.
 * <p>
 * Sie enthält die main-methode, wo die Ausführung des Programms gestartet wird und danach die Kontrolle
 * über die Ausführung an andere Klassen abgegeben wird.
 */

@SpringBootApplication
public class Main {

    /**
     * Die Main-Methode, wo die Ausführung des Programms gestartet wird.
     * <p>
     * In ihr werden alle wichtigen Objekte des Programms instanziiert, die zur Audführung benötigt werden (Starten der
     * Spring-Applikation, aufbauen einer Verbindung zur Datenbank,...)
     * <p>
     * Danach wird an diese objekte die Kontrolle über den Programmablauf übergeben.
     *
     * @param args Es werden der Main-Methode keine Argumente übergeben bzw. übergebene Argumente werden ignoriert.
     */
    public static void main(final String[] args) {

        SpringApplication.run(Main.class, args);

        while(true) {

        }

    }

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){
        return hemf.getSessionFactory();
    }

}
