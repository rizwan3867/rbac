package com.company.rbac.server;

import java.util.Set;

class UserRole {

    private String roleName;

    // role to permission mapping.
    private Set<ActionType> actions;

    public String getRoleName() {
        return roleName;
    }

    public Set<ActionType> getActions() {
        return actions;
    }

    public UserRole(String roleName, Set<ActionType> actions) {
        this.roleName = roleName;
        this.actions = actions;
    }
}
