package com.company.rbac.server;

import java.util.HashSet;
import java.util.Set;

class Resource {

    private String resourceName;

    private Set<String> userRoles;

    public Resource(String name, Set<String> userRoles) {
        this.resourceName = name;
        this.userRoles = userRoles;
    }

    public Resource(String name) {
        this.resourceName = name;
        userRoles = new HashSet<>();
    }

    public boolean hasRoles() {
        return !userRoles.isEmpty();
    }

    public String getResourceName() {
        return resourceName;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }
}
