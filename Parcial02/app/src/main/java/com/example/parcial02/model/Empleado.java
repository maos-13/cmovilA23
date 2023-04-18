package com.example.parcial02.model;

public class Empleado {

    private long id;
    private String user;
    private String password;
    private String email;

    public Empleado() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
