package org.example.client;

public class UserCredential {
    private String email;
    private String password;

    public UserCredential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredential() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
