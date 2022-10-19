package ru.kata.spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring_boot.model.User;

import java.util.Arrays;

@RestController
@RequestMapping("api")
public class RestTemplateController {
    private final RestTemplate restTemplate;
    private static final String KATA_API = "http://94.198.50.185:7081/api/users";

    private String cookie = "";
    private HttpHeaders headers = null;

    private User user = null;

    @Autowired
    public RestTemplateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping()
    public String getUserList() {
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        HttpEntity<String> response = restTemplate.exchange(
                KATA_API, HttpMethod.GET, entity, String.class);

        HttpHeaders respHeaders = response.getHeaders();
        cookie = respHeaders.getFirst(respHeaders.SET_COOKIE);
        cookie = cookie.substring(0, cookie.indexOf(';')+1);
        headers.add("Cookie", cookie);

        String result = response.getBody();
        System.out.println(result);
        System.out.println(cookie);
        return result;
    }

    @PostMapping("")
    public String createUser() {

        user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 15);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        HttpEntity<String> response = restTemplate.exchange(
                KATA_API, HttpMethod.POST, entity, String.class);
        /*
        headers = response.getHeaders();
        cookie = headers.getFirst(headers.SET_COOKIE);
        cookie = cookie.substring(0, cookie.indexOf(';'));
        */
        String result = response.getBody();
        System.out.println(result);
        return result;
    }

    @PutMapping("/update")
    public String updateUser() {

        user.setName("Thomas");
        user.setLastName("Shelby");

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        HttpEntity<String> response = restTemplate.exchange(
                KATA_API, HttpMethod.PUT, entity, String.class);
        /*
        headers = response.getHeaders();
        cookie = headers.getFirst(headers.SET_COOKIE);
        cookie = cookie.substring(0, cookie.indexOf(';'));
        */
        String result = response.getBody();
        System.out.println(result);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        HttpEntity<String> response = restTemplate.exchange(
                KATA_API+"/"+id, HttpMethod.DELETE, entity, String.class);
        String result = response.getBody();
        System.out.println(result);
        return result;
    }
}
