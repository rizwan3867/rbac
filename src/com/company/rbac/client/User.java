package com.company.rbac.client;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains user information. for n number of users, n number of objects will be created.
 * But in real world it will be mapped to a db table.
 */
public class User {

    private static final AtomicInteger count = new AtomicInteger(0);

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String userName;

    // this set contains all the roles a user posses.
    // private Set<UserRole> roles;

    public User(String firstName, String lastName, String userName) {

        userId = count.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }
}
