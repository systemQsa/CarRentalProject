package com.myproject.dao.entity;

public enum UserRole {
    ADMIN(1,"admin"),USER(2,"user"),MANAGER(3,"manager");

    private final int id;
    private final String role;

    UserRole(int id,String role){
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public String getRole(){return role;}

    public static String getRoleId(int userId){
        String role = null;
        for (UserRole userRole: UserRole.values()) {
            if (userRole.getId() == userId){
                role = userRole.getRole();
            }
        }
        return role;
    }
}

