package com.company.rbac.server;

import java.util.*;

/**
 * This class will be orchestrator which does all the operation to its data classes.
 * UserService will be talking only to AccessService.
 */
public class AccessService {

    private static AccessService accessService;

    private HashMap<Integer, List<String>> userRoleMapping = new HashMap<>();

    private List<UserRole> userRolesList = new ArrayList<>();

    private List<Resource> resources = new ArrayList<>();

    private AccessService() {
        initialize();
    }
    public static AccessService getInstance() {

        if(accessService == null) {
            accessService = new AccessService();
        }
        return accessService;
    }

    public boolean isValidUserRole(String roleName) {
        return userRolesList.stream().anyMatch(userRole -> userRole.getRoleName().equals(roleName));
    }

    private void initialize() {
        // Create different roles with its permissions.
        intializeRoles();
        // Create resources
        initializeResources();
    }

    private void initializeResources() {
        Resource resource = new Resource("index1.txt",
                new HashSet<>(Arrays.asList(RoleConstants.ADMIN, RoleConstants.IT, RoleConstants.SUPER_USER, RoleConstants.USER)));
        resources.add(resource);

        resource = new Resource("configurations.txt",
                new HashSet<>(Arrays.asList(RoleConstants.ADMIN, RoleConstants.IT, RoleConstants.SUPER_USER)));
        resources.add(resource);

        resource = new Resource("salaries.txt",
                new HashSet<>(Arrays.asList(RoleConstants.SUPER_USER)));
        resources.add(resource);
    }

    private void intializeRoles() {
        UserRole userRole = new UserRole(RoleConstants.SUPER_USER,
                new HashSet<>(Arrays.asList(ActionType.DELETE, ActionType.READ, ActionType.WRITE)));
        userRolesList.add(userRole);
        userRole = new UserRole(RoleConstants.ADMIN,
                new HashSet<>(Arrays.asList(ActionType.READ, ActionType.WRITE)));
        userRolesList.add(userRole);
        userRole = new UserRole(RoleConstants.IT,
                new HashSet<>(Arrays.asList(ActionType.READ, ActionType.WRITE)));
        userRolesList.add(userRole);
        userRole = new UserRole(RoleConstants.USER,
                new HashSet<>(Arrays.asList(ActionType.READ)));
        userRolesList.add(userRole);
    }


    public void assignRole(int userId, String role) {

        List<String> roles = this.userRoleMapping.get(userId);
        boolean doesExist = false;
        // check whether the role already exists
        if(roles != null) {
            doesExist = roles.stream().anyMatch(userRole -> userRole.contains(role));
        }
        if(doesExist) {
            System.out.println("The role is already assigned to the user");
        } else {
            List<String> userRoles = this.userRoleMapping.get(userId);
            if(userRoles == null) {
                userRoles = new ArrayList<>();
            }
            boolean validUserRole = isValidUserRole(role);
            if(validUserRole) {
                userRoles.add(role);
                this.userRoleMapping.put(userId, userRoles);
            }
        }

    }

    public void removeRole(int userId, String role) {
        if(userRoleMapping.containsKey(userId)) {
            List<String> roles = this.userRoleMapping.get(userId);
            boolean doesExist = false;
            // check whether the role already exists
            if(roles != null) {
                doesExist = roles.stream().anyMatch(userRole -> userRole.contains(role));
            }
            if(doesExist) {
                List<String> userRoles = this.userRoleMapping.get(userId);
                userRoles.remove(role);
                this.userRoleMapping.put(userId, userRoles);
            } else {
                System.out.println("The role "+role+ "does not exist for user");
            }
        } else {
            System.out.println("The user does not exist in directory");
        }
    }

    public boolean checkAccessPermission(int userId, String resourceName) {

        // fetch roles for the particular user
        if(userRoleMapping.containsKey(userId)) {
            List<String> roles = this.userRoleMapping.get(userId);
            // check whether the role already exists
            if(roles != null) {
                List<String> userRoles = this.userRoleMapping.get(userId);
                for (Resource resource : resources) {
                    if(resource.getResourceName().equals(resourceName)) {
                        if(resource.hasRoles()) {
                            Set<String> resourceRoles = resource.getUserRoles();
                            // we need to check whether resourceRoles contains any of the user roles
                            for (String userRoleName : roles) {
                                if(resourceRoles.contains(userRoleName)) {
                                   return true;
                                }
                            }
                        }
                    }
                }
            }

        } else {
            System.out.println("The user does not exist");
        }
        return false;
    }
}
