package com.gnilapon.anywr.group.impl;

import com.gnilapon.anywr.group.configs.security.jwt.JwtUtils;
import com.gnilapon.anywr.group.models.dto.UserDTO;
import com.gnilapon.anywr.group.models.dto.payload.request.LoginRequest;
import com.gnilapon.anywr.group.models.dto.payload.request.SignupRequest;
import com.gnilapon.anywr.group.models.dto.payload.response.JwtResponse;
import com.gnilapon.anywr.group.models.entities.Role;
import com.gnilapon.anywr.group.models.entities.User;
import com.gnilapon.anywr.group.models.enums.ERole;
import com.gnilapon.anywr.group.repositories.RoleRepository;
import com.gnilapon.anywr.group.repositories.UserRepository;
import com.gnilapon.anywr.group.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup() {
        // Mock input data
        SignupRequest request = new SignupRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setRole(Collections.singleton("student"));

        // Mock UserRepository.existsByUsername()
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);

        // Mock UserRepository.existsByEmail()
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        // Mock RoleRepository.findByName()
        Role studentRole = new Role();
        studentRole.setName(ERole.ROLE_STUDENT);
        when(roleRepository.findByName(ERole.ROLE_STUDENT)).thenReturn(Optional.of(studentRole));

        // Mock PasswordEncoder.encode()
        when(encoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // Mock UserRepository.save()
        User savedUser = new User();
        savedUser.setUsername(request.getUsername());
        savedUser.setEmail(request.getEmail());
        savedUser.setPassword("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Perform the signup
        UserDTO result = userService.signup(request);

        // Verify the result
        assertNotNull(result);
        assertEquals(request.getUsername(), result.getUsername());
        assertEquals(request.getEmail(), result.getEmail());

        // Verify that UserRepository.existsByUsername() was called with the correct argument
        verify(userRepository).existsByUsername(request.getUsername());

        // Verify that UserRepository.existsByEmail() was called with the correct argument
        verify(userRepository).existsByEmail(request.getEmail());

        // Verify that RoleRepository.findByName() was called with the correct argument
        verify(roleRepository).findByName(ERole.ROLE_STUDENT);

        // Verify that UserRepository.save() was called with the correct argument
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSignin() {
        // Mock des dépendances
        LoginRequest loginRequest = new LoginRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        String jwtToken = "jwtToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwtToken);

        // Appel de la méthode à tester
        JwtResponse jwtResponse = userService.signin(loginRequest);

        // Vérification des appels aux dépendances
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(authentication);

        // Vérification du résultat
        assertNotNull(jwtResponse);
        assertEquals(jwtToken, jwtResponse.getToken());
    }
}