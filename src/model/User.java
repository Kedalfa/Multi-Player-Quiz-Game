package model;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private Date registrationDate;

    public User() {}

    public User(int id, String username, String passwordHash, String email, Date registrationDate) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public User(String username, String password, String email, Date registrationDate) {
        this.username = username;
        this.passwordHash = password;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    public String getPassword() { return passwordHash; }
} 