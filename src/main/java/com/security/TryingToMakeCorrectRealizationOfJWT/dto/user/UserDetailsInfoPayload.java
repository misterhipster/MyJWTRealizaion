package com.security.TryingToMakeCorrectRealizationOfJWT.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsInfoPayload {
    private String sub;
    private String iss;
    private String exp;
    private String iat;
    private String email;

//    public UserDetailsInfoPayload(String email, long l) {
//        this.email = email;
//    }
}
