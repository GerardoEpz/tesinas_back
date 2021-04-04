package com.tesinas.spring.jwt.mongodb.payload.request;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class SignUpRequestList {

    @Valid
    public List<SignupRequest> signupRequests = new ArrayList<>();

    public List<SignupRequest> getSignupRequests() {
        return signupRequests;
    }

    public void setSignupRequests(List<SignupRequest> signupRequests) {
        this.signupRequests = signupRequests;
    }
}
