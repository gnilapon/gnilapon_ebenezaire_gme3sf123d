package com.gnilapon.anywr.group.services.impl;


import com.gnilapon.anywr.group.configs.security.jwt.JwtUtils;
import com.gnilapon.anywr.group.exceptions.ResourceNotFoundException;
import com.gnilapon.anywr.group.mappers.UserMapper;
import com.gnilapon.anywr.group.models.dto.UserDTO;
import com.gnilapon.anywr.group.models.dto.payload.request.LoginRequest;
import com.gnilapon.anywr.group.models.dto.payload.request.SignupRequest;
import com.gnilapon.anywr.group.models.dto.payload.response.JwtResponse;
import com.gnilapon.anywr.group.models.entities.Role;
import com.gnilapon.anywr.group.models.entities.User;
import com.gnilapon.anywr.group.models.enums.ERole;
import com.gnilapon.anywr.group.repositories.RoleRepository;
import com.gnilapon.anywr.group.repositories.UserRepository;
import com.gnilapon.anywr.group.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDTO signup(SignupRequest request) {
        logger.info("begin signup user : {}",request.getUsername());
        if (Boolean.TRUE.equals(userRepository.existsByUsername(request.getUsername()))) {
            throw new ResourceNotFoundException("Error: Username is already taken!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            throw new ResourceNotFoundException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(request.getUsername(),request.getEmail(), encoder.encode(request.getPassword()));

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found.."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role admin is not found."));
                        roles.add(adminRole);

                        break;
                    case "teacher":
                        Role modRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role teacher is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role student is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        logger.info("signup pass for user : {}",request.getUsername());
        return UserMapper.INSTANCE.entityToDto(userRepository.save(user));
    }

    @Override
    public JwtResponse signin(LoginRequest request) {
        logger.info("begin signin user : {}",request.getUsername());
        /**if (userRepository.findByUsernameAndPassword(request.getUsername(),encoder.encode(request.getPassword())).isEmpty()) {
            throw new ResourceNotFoundException("Error: User or password do not match!");
        }*/
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        /**UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());*/
        logger.info("signin pass for user : {}",request.getUsername());
        return new JwtResponse(jwt);
    }
}
