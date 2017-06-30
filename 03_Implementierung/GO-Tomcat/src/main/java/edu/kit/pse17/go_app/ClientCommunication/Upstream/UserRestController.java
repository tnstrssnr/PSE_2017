package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tina on 30.06.17.
 */

@RestController
@RequestMapping("/user")
public class UserRestController {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/login"
    )
    public void login() {

    }

    @RequestMapping(
            method = RequestMethod.GET,
            value= "/{userId}"
    )
    public void getUserInfo(@PathVariable String userId) {

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{userId}"
    )
    public void changeUserData(@PathVariable String userId) {

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{userId}"
    )
    public void deleteUser(@PathVariable String userId) {

    }
}
