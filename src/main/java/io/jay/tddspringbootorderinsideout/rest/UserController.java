package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.service.UserService;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return userService.get(id);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.add(user);
    }

    @PatchMapping("/users/{id}")
    public User updateUser(@PathVariable String id, @RequestBody NameValueList nameValueList) {
        return userService.update(id, nameValueList);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
