/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ana.teixeira
 */
public class Usuario {

    private int id;
    private int user_type;
    private int recep_validated;
    private boolean online;
    private String username;
    private String name;
    private String city;
    private String state;
    private String password;

    public Usuario() {
    }

    public Usuario(int id, String name, int user_type, String city, String state, int recep_validated, String password) {
        this.id = id;
        this.name = name;
        this.user_type = user_type;
        this.city = city;
        this.state = state;
        this.recep_validated = recep_validated;
        this.password = password;
    }

    public Usuario(String username, String name, String city, String state, String password) {
        this.username = username;
        this.name = name;
        this.city = city;
        this.state = state;
        this.password = password;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRecep_validated() {
        return recep_validated;
    }

    public void setRecep_validated(int recep_validated) {
        this.recep_validated = recep_validated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
