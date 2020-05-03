package com.company.rbac.client;

import com.company.rbac.server.AccessService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for creating users and assigning roles to it.
 * It talks to AccessService and assigns roles.
 */
public class UserService {

    private static UserService userService;

    List<User> users = new ArrayList<User>();

    private UserService() {

    }

    public static UserService getInstance() {
        if(userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    // Assign a role
    public void assignRoleForUser(User user, String role) {
        AccessService accessService = AccessService.getInstance();
        accessService.assignRole(user.getUserId(), role);
    }


    // remove a role
    public void removeRoleForUser(User user, String role) {
        AccessService accessService = AccessService.getInstance();
        accessService.removeRole(user.getUserId(), role);
    }


    public boolean checkAccessPermissions(User user, String resourceName) {
        return AccessService.getInstance().checkAccessPermission(user.getUserId(), resourceName);
    }
}
