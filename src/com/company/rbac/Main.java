package com.company.rbac;

import com.company.rbac.client.UserConstants;
import com.company.rbac.client.User;
import com.company.rbac.client.UserService;

public class Main {

    public static void main(String[] args) {

        // lets create some users
        User rizwan = new User("Md","Rizwan","mrizwan");
        User salman = new User("Salman", "F", "salmanf");
        User darshan = new User("Darshan", "Shetty", "dshetty");
        User shenoy = new User("Srinivas", "Shenoy", "sshenoy");

        UserService.getInstance().assignRoleForUser(rizwan, UserConstants.SUPER_USER);
        UserService.getInstance().assignRoleForUser(salman, UserConstants.USER);
        UserService.getInstance().assignRoleForUser(darshan, UserConstants.IT);
        UserService.getInstance().assignRoleForUser(shenoy, UserConstants.ADMIN);

        // assiging another role to user darshan
        UserService.getInstance().assignRoleForUser(darshan, UserConstants.ADMIN);

        // Try removing a role
        UserService.getInstance().removeRoleForUser(darshan, UserConstants.ADMIN);

        // Check whether permission exists for a resource
        boolean permissions = UserService.getInstance().checkAccessPermissions(darshan, "configurations.txt");
        System.out.println(permissions);

    }
}
