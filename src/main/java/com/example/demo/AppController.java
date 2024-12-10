package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin // Allows cross-origin requests
public class AppController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String fun1() {
    	return "welcoe";
    }

    @PostMapping("/use")
    public Map<String, Object> registerUser(@RequestBody Userss user) {
        Map<String, Object> response = new HashMap<>();
        
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            response.put("success", false);
            response.put("message", "Username already exists");
        } else {
            // Save the user to the database
            userRepository.save(user);
            response.put("success", true);
            response.put("message", "User registered successfully with Spring Boot: " + user.getUsername());
        }
        
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        // Response map
        Map<String, Object> response = new HashMap<>();
        
        // Check for hardcoded admin credentials
        if ("admin@artgallery".equals(username) && "12345678".equals(password)) {
            response.put("success", true);
            response.put("message", "Admin login successful");
            response.put("role", "ADMIN");
        }
        else  if ("artist@gmail.com".equals(username) && "12345678".equals(password)) {
            response.put("success", true);
            response.put("message", "Artist login successful");
            response.put("role", "ARTIST");
        }
        
        else {
            // Find user by username
            Userss user = userRepository.findByUsername(username);
            
            // Check if user exists and passwords match
            if (user != null && user.getPassword().equals(password)) {
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("role", "USER");
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password");
            }
        }
        return response;
    }
}
