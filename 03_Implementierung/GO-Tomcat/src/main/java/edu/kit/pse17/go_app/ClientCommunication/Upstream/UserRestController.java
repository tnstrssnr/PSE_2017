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
 * RestController Klasse für Rest Ressourcen in Bezug zu Group Funktionalitäten. Alle Rückgabewerte in dieser Klasse
 * sind in einem responseEntity-Objekt umfasst, HTTP-StatusCode und die Rückgabedaten beinhaltend in seinem Aufbau,
 * formattiert als Json-String, Gson benutzend.
 */

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Diese Methode liefert dem Anfragenden eine Liste aller Gruppen, in die der Benutzer mit der User Id {userId}
     * Mitglied ist, bzw. zu denen er eine Anfrage bekommen hat. Sie wird genau dann von einem Client aufgerufen, wenn
     * ein Benutzer sich in der App anmeldet.
     *
     * @param userId Die Id des Benutzers, dessen Daten zurückgegeben werden sollen. Diese wird von Spring aus der URL
     *               extrahiert und als Argument der Methode verwendet.
     * @return eine Liste aller Gruppen von Gruppenobjekten. Der Rückgabewert dieser Methode wird innerhalb der Methode
     * in ein Json-Objekt geparst und in der empfangenden Methode des Clients zu Java Objekten konvertiert. Die
     * Konvertierung nach JSON und zurück ändert nicht den Inhalt der Daten.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{userId}/{email}/{userName}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Group>> getData(@PathVariable("userId") final String userId, @PathVariable("email") final String email, @PathVariable("userName") String userName) {
        List<Group> data = userService.getData(userId, email, userName);
        if (data == null || data.size() == 0) {

            //Dummy-Gruppe, die an den Client gesendet wird, falls der User noch in keiner Gruppe ist
            data = new ArrayList<>();
            Group group = new Group(-1, "dummy", "dummy", 0, null, new ArrayList<>(), new ArrayList<>());
            GroupService.makeJsonable(group);
            data.add(group);
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer sich zum ersten Mal in der App anmeldet. Die Methode veranlasst
     * das userDAO einen neuen eintrag in der Datenbank anzulegen. Dazu überträgt der Client die benötigten Daten im
     * Request Body der HTTP-Anfrage, verpackt als JSON-Objekt.
     *
     * @param user User der vom userService erstellt werden soll.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/{userId}")
    public ResponseEntity<String> createUser(@PathVariable("userId") final String userId, @RequestBody final UserEntity user) {
        if (userService.createUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }
    }


    /**
     * Mit dieser Methode kann der user seinen Benutzernamen ändern. BenutzerId und Emailadresse können nicht geändert
     * werden.
     *
     * @param user User-Objekt, das die geänderten Daten enthält
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{userId}"
    )
    public ResponseEntity updateUser(@RequestBody final UserEntity user) {
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer seinen Benutzeraccount löschen möchte.
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
     * können, um Kommunikationsströme zu initiieren.
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
     * Diese Methode wird aufgerufen um einen Nutzer anhand seiner Login-Email
     * wiederzukriegen.
     *
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
