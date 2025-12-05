package com.security.TryingToMakeCorrectRealizationOfJWT.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserDetailsInfo {
    private UserDetailsInfoHeader userDetailsInfoHeader;
    private UserDetailsInfoPayload userDetailsInfoPayload;
    private String token;

    public UserDetailsInfo(UserDetailsInfoHeader userDetailsInfoHeader,
                           UserDetailsInfoPayload userDetailsInfoPayload,
                           String token) {
        this.userDetailsInfoHeader = userDetailsInfoHeader;
        this.userDetailsInfoPayload = userDetailsInfoPayload;
        this.token = token;
    }

    public void setUserDetailsInfoHeader(UserDetailsInfoHeader userDetailsInfoHeader) {
        this.userDetailsInfoHeader = userDetailsInfoHeader;
    }

    public void setUserDetailsInfoPayload(UserDetailsInfoPayload userDetailsInfoPayload) {
        this.userDetailsInfoPayload = userDetailsInfoPayload;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public UserDetailsInfoPayload getUserDetailsInfoPayload() {
        return userDetailsInfoPayload;
    }

    public UserDetailsInfoHeader getUserDetailsInfoHeader() {
        return userDetailsInfoHeader;
    }
}
