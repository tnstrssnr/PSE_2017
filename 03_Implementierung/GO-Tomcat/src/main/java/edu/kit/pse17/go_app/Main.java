package edu.kit.pse17.go_app;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Upstream.GoRestController;
import edu.kit.pse17.go_app.ClientCommunication.Upstream.GroupRestController;
import edu.kit.pse17.go_app.ClientCommunication.Upstream.UserRestController;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import edu.kit.pse17.go_app.ServiceLayer.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
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
public class Main extends SpringBootServletInitializer {

    private static SessionFactory sf;

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
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return
                application.sources(Main.class);
    }


    @Bean
    public SessionFactory sessionFactory() {
        if (sf == null) {
            final Configuration config = new Configuration();
            sf = config.configure().buildSessionFactory();
        }
        return sf;
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImp(sessionFactory());
    }

    @Bean
    @ConditionalOnMissingBean
    public GsonHttpMessageConverter gsonHttpMessageConverter(final Gson gson) {
        final GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        return converter;
    }

    @Bean
    public GoRestController goRestController() {
        return new GoRestController(goService());
    }

    @Bean
    UserRestController userRestController() {
        return new UserRestController(userService());
    }

    @Bean
    GroupRestController groupRestController() {
        return new GroupRestController(groupService());
    }

    @Bean
    public GoService goService() {
        return new GoService(new GoDaoImp(sessionFactory()));
    }

    @Bean
    public GroupService groupService() {
        return new GroupService(new GroupDaoImp(sessionFactory()));
    }

    @Bean
    public UserService userService() {
        return new UserService(new UserDaoImp(sessionFactory()));
    }
}
