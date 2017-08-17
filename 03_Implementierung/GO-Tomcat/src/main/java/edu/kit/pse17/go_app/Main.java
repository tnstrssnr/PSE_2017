package edu.kit.pse17.go_app;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Die Main-Klasse der Server-Anwendung. In ihr wird keine Anwendungslogik ausgeführt.
 * <p>
 * Sie enthält die main-methode, wo die Ausführung des Programms gestartet wird und danach die Kontrolle
 * über die Ausführung an andere Klassen abgegeben wird.
 */


@SpringBootApplication
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
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
        final Gson gson = new Gson();
        String newStatus = "testid_1 BESTÄTIGT";
        System.out.println(gson.toJson(newStatus));
        SpringApplication.run(Main.class, args);

    }

    @Bean
    public SessionFactory sessionFactory() {
        final Configuration config = new Configuration();
        return config.configure().buildSessionFactory();
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImp();
    }

    @Bean
    @ConditionalOnMissingBean
    public GsonHttpMessageConverter gsonHttpMessageConverter(final Gson gson) {
        final GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        return converter;
    }
}
