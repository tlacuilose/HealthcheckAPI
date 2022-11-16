package com.tlacuilose.HealthcheckAPI;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tlacuilose.HealthcheckAPI.exceptions.InvalidRegistrationException;
import com.tlacuilose.HealthcheckAPI.exceptions.UserNotFoundException;
import com.tlacuilose.HealthcheckAPI.util.Hasher;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/user/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        if (!validateEmail(email) || !validatePassword(password)) {
            throw new InvalidRegistrationException();
        }
        try {
            String hash = Hasher.hashPassword(password);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(hash);
            userRepository.save(newUser);

            user.setPassword("");

            return new ResponseEntity<User>(user, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new InvalidRegistrationException();
        }

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Integer id) {
        Optional<User> savedUser = userRepository.findById(id);
        if (!savedUser.isPresent()) {
            throw new UserNotFoundException();
        }

        user.setId(id);
        userRepository.save(user);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public @ResponseBody String deleteUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
        return "Deleted";
    }

    private boolean validateEmail(String email) {
        return email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    private boolean validatePassword(String password) {
        return password.matches("^.{8,20}$");
    }

}
