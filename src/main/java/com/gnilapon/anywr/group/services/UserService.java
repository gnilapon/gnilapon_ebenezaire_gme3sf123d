package com.gnilapon.anywr.group.services;

import com.gnilapon.anywr.group.models.dto.UserDTO;
import com.gnilapon.anywr.group.models.dto.payload.request.LoginRequest;
import com.gnilapon.anywr.group.models.dto.payload.request.SignupRequest;
import com.gnilapon.anywr.group.models.dto.payload.response.JwtResponse;

public interface UserService {
    UserDTO signup(SignupRequest request);
    JwtResponse signin(LoginRequest request);
}
