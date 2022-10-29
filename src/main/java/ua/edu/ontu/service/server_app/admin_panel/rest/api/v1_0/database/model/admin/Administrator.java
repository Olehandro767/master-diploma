package ua.edu.ontu.service.server_app.admin_panel.rest.api.v1_0.database.model.admin;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admin_table")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
}