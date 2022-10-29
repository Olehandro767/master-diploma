package ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/admin")
public class AdminRestController {

    @GetMapping("")
    public void signIn() {}
}