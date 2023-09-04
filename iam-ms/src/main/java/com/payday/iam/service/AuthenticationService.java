package com.payday.iam.service;

import com.payday.iam.auth.dto.request.SignUpRequest;
import com.payday.iam.auth.dto.request.SigninRequest;
import com.payday.iam.auth.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    String signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
