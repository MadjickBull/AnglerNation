package com.anglernation.models.users;


import com.anglernation.models.lines.Line;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.posts.Post;
import com.anglernation.models.rods.Rod;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Integer phoneNumber;

    private List<Rod> rods = new ArrayList<>();
    private List<Lure> lure = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    public User(String userName, String firstName, String lastName, String password, String email, Integer phoneNumber) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}



