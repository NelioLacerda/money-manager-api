package project.moneymanager_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Define a simple REST controller to return a simple message indicating the server is running.
 */
@RestController
@RequestMapping("/status")
public class ServerController {

    /*
     * Returns a simple message indicating that the application is running.
     */
    @GetMapping
    public String status() {
        return "MoneyManager API is running!";
    }
}