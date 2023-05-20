package com.gnilapon.anywr.group.controllers;

import com.gnilapon.anywr.group.models.dto.UserDTO;
import com.gnilapon.anywr.group.models.dto.payload.request.LoginRequest;
import com.gnilapon.anywr.group.models.dto.payload.request.SignupRequest;
import com.gnilapon.anywr.group.models.dto.payload.response.JwtResponse;
import com.gnilapon.anywr.group.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testAuthenticateUserSuccess() {
        // Préparation des données de test
        LoginRequest loginRequest = new LoginRequest("username", "password");
        JwtResponse jwtResponse = new JwtResponse("token");

        // Mock du comportement du UserService
        when(userService.signin(loginRequest)).thenReturn(jwtResponse);

        // Appel de la méthode à tester
        ResponseEntity<?> responseEntity = authController.authenticateUser(loginRequest);

        // Vérification des résultats
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(jwtResponse, responseEntity.getBody());
        verify(userService, times(1)).signin(loginRequest);
    }

    @Test
    void testRegisterUserSuccess() {
        // Création des objets de test
        SignupRequest signUpRequest = new SignupRequest();
        UserDTO userDTO = new UserDTO();
        ResponseEntity<UserDTO> expectedResponse = ResponseEntity.ok(userDTO);

        // Configurer le comportement du mock
        when(userService.signup(signUpRequest)).thenReturn(userDTO);

        // Appeler la méthode à tester
        ResponseEntity<?> actualResponse = authController.registerUser(signUpRequest);

        // Vérifier les résultats
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        // Vérifier que la méthode userService.signup() a été appelée une fois
        verify(userService, times(1)).signup(signUpRequest);
    }
}