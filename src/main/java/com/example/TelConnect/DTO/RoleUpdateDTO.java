package com.example.TelConnect.DTO;

public class RoleUpdateDTO {

    String RequestorRole;

    String RequestorCidn;

    String UserRole;

    public String getUserCidn() {
        return UserCidn;
    }

    public void setUserCidn(String userCidn) {
        UserCidn = userCidn;
    }

    String UserCidn;

    public String getRequestorCidn() {
        return RequestorCidn;
    }

    public void setRequestorCidn(String requestorCidn) {
        RequestorCidn = requestorCidn;
    }

    public String getRequestorRole() {
        return RequestorRole;
    }

    public void setRequestorRole(String requestorRole) {
        RequestorRole = requestorRole;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }
}
