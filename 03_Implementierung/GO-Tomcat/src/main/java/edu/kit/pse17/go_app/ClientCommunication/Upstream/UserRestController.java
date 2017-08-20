package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.User;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import edu.kit.pse17.go_app.ServiceLayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse UserRestController gehört zum Upstream ClientCommunication Modul und bildet einen Teil der REST API, die
 * der Tomcat Server den Clients zur Kommunikation anbietet. Die Aufgabe dieser Klasse ist die Abwicklung von
 * REST-Requests, die User-spezifische Anfragen beinhalten. Dazu gehört: - das Empfangen und Senden von HTTP-Requests -
 * das Parsen der empfangenen / zu sendenden Daten von bzw. nach JSON - das Weiterleiten der Anfragen zur Bearbeitung an
 * die richtige Stelle im Programm (das UserDAO)
 * <p>
 * Das REST API wird umgesetzt von dem Java Framework Spring, anhand der Annotationen der Methoden in dieser Klasse. Die
 * Klasse selbst ist annotiert mit "@RestController", um zu signalisieren, dass es sich um eine Klasse handelt, deren
 * Methoden Rest Ressourcen beschreiben. Die Methoden dieser Klasse sind auf die URL {Base_URL}/user gemappt.
 * <p>
 * Die Methoden der Klasse werden aufgerufen, von den Methoden des Interfaces "TomcatRestApi", das von den Clients des
 * Systems verwendet wird.
 * <p>
 * Bei einem Methodenaufruf in dieser Klasse, wird die Anfrage an die DAOs der MySQL Datenbank der Anwendung
 * weitergeleitet. Von dort werden die richtigen Daten geholt (falls der Client bestimmte Daten in der Antwort
 * erwartet). Danach werden die Daten von dieser Klasse in JSON-Objekte umgewandelt (mithilfe der Gson Library) und dem
 * Client in der Antwort zugesendet.
 * <p>
 * Nähere Erläuterungen zum JSON-Schema und der Konvertierung finden sich im Entwurfsdokument.
 * <p>
 * Die Klasse verfügt nur über den Standard-Konstruktor (der implizit gegeben ist). Es muss nirgends im Programm eine
 * Instanz dieser Klasse erzeugt werden. Um die Instanziierung und Objektverwaltung dieser Klasse kümmert sich das
 * Spring-Framework.
 */

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/test"
    )

    /**
     * Testmethode, die einzig dem Testen dient.
     * @return Hello World
     */

    public String test() {
        return "Hello World";
    }

    /**
     * Diese Methode liefert dem Anfragenden eine Liste aller Gruppen, in die der Benutzer mit der User Id {userId}
     * Mitglied ist, bzw. zu denen er eine Anfrage bekommen hat. Sie wird genau dann von einem Client aufgerufen, wenn
     * ein Benutzer sich in der App anmeldet.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP GET-Request an den Server an die URL {Base_URL}/user/{userId},
     * die {userId}
     * <p>
     * Da in den Gruppen die einzelnen Gos dieser Gruppe gespeichert sind, erhält der Anfragende mit dem Aufruf dieser
     * Methode sämtliche Daten, die den Benutzer mit der User Id {userID} betreffen und für das Navigieren und Benutzen
     * der App benötigt werden (abgesehen von Änderungen der Daten, die zu einem späteren Zeitpunkt stattfinden).
     *
     * @param userId Die Id des Benutzers, dessen Daten zurückgegeben werden sollen. Diese wird von Spring aus der URL
     *               extrahiert und als Argument der Methode verwendet.
     * @return eine Liste aller Gruppen von Gruppenobjekten. Der Rückgabewert dieser Methode wird innerhalb der Methode
     * in ein Json-Objekt geparst und in der empfangenden Methode des Clients zu Java Objekten konvertiert. Die
     * Konvertierung nach JSON und zurück ändert nicht den Inhalt der Daten.
     * <p>
     * Die Liste kann leer sein, für den Fall dass ein Benutzer nicht Mitglied in irgendeiner Gruppe ist. In diesem Fall
     * wird in dem JSON-Objekt ein leerer data-Block übertragen. Die Länge der Liste ist auf 300 Gruppen beschränkt
     * (dies ist die Gesamtanzahl an Gruppen, die von dem System unterstützt werden)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{userId}/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Group>> getData(@PathVariable("userId") final String userId, @PathVariable("email") final String email) {
        List<Group> data = userService.getData(userId, email);
        if (data == null || data.size() == 0) {
            data = new ArrayList<>();
            Group group = new Group(-1, "dummy", "dummy", 0, null, new ArrayList<>(), new ArrayList<>());
            GroupService.makeJsonable(group);
            data.add(group);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer sich zum ersten Mal in der App anmeldet. Die Methode veranlasst
     * das userDAO einen neuen eintrag in der Datenbank anzulegen. Dazu überträgt der Client die benötigten Daten im
     * Request Body der HTTP-Anfrage, verpackt als JSON-Objekt.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an die URL {base_URL}/user/{userId}.
     * <p>
     * Die Methode besitzt keinen Rückgabewert, lediglich einen Statuscode in der HTTP-Antwort, die an den Anfragenden
     * gesendet wird. Der Statuscode gibt an, ob die Transaktion erfolgreich war.
     *
     * @param user User der vom userService erstellt werden soll.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/{userId}")
    public ResponseEntity<String>
    createUser(@RequestBody final UserEntity user) {
        if (userService.createUser(user)) {
            return
                    ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }
    }


    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{userId}"
    )
    public ResponseEntity<String> updateUser(@RequestBody final UserEntity user) {
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer seinen Benutzeraccount löschen möchte.
     * <p>
     * In der Methode wird das UserDAO dazu aufgerufen, das Tupel aus der User-Relation zu entfernen. Durch
     * Fremdschlüssel-Constraints in der Datenbank, werden alle dem User gehörenden Gruppen (in denen er Admin war) ,
     * Gos (in denen er Go-Verantwortlicher war), Gruppenmitgliedschaften sowie Gruppenanfragen an den User automatisch
     * gelöscht.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP DELETE-Request an die URL {base_URL}/user/{userId}.
     * <p>
     * Die Methode besitzt keinen Rückgabewert, lediglich einen Statuscode in der HTTP-Antwort, die an den Anfragenden
     * gesendet wird. Der Statuscode gibt an, ob die Transaktion erfolgreich war.
     *
     * @param userId Die Id des Benutzers, dessen Konto entfernt werden soll. Diese wird von Spring aus der URL
     *               extrahiert und als Argument der Methode verwendet.
     */

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{userId}"
    )
    public ResponseEntity<String> deleteUser(@PathVariable("userId") final String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methoe wird aufgerufen, um das Gerät, dass ein Benutzer aktuell benutzt auf dem Server mit seiner
     * InstanceId zu registrieren. Die InstanceId wird vom Server benötigt, um das Gerät des Benutzers identifizieren zu
     * können, um Kommunikaiotnsströme zu initiieren. Da diese InstanceId sich von Gerät zu Gerät unterscheidet bzw.
     * sich durch Konfiguationsänderungen ändern kann, sollte diese Methode zusätzlich zu getData() bei jeder Anmeldung
     * von dem Client aufgerufen werden.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an die URL {base_URL}/user/device/{instanceId}.
     *
     * @param instanceId Die InstanceID des Geräts, an dem sich der User angemeldet hat. Diese wird von Spring aus der
     *                   URL extrahiert und als Argument der Methode verwendet. Generiert wird die InstanceId vom
     *                   Service Firebase Cloud Messaging, das auch benutzt wird, um Downstream-Kommunikation zu
     *                   realisieren.
     */

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{userId}/device/{instanceId}"
    )
    public ResponseEntity<String> registerDevice(@PathVariable("userId") final String uid, @PathVariable("instanceId") final String instanceId) {
        userService.registerDevice(uid, instanceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode wird aufgerufen um einen Nutzer anhand seiner Login-Email/registrierten Emailadresse wiederzukriegen
     * bei Aktivitäten wie zB. dem Login.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP GET-Request.
     * @param mail Die Emailadresse des gesuchten Nutzers
     * @return Gibt das gesuchte User-Objekt wieder.
     */

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/search/{mail}"
    )
    public ResponseEntity<User> getUserbyMail(@PathVariable("mail") String mail) {

        //Gmail adresses typically end w/ .com -- TLD is ignored in URI, has to be re-added to email address string
        mail = mail + ".com";
        User user = userService.getUserbyMail(mail);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
