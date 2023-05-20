package com.gnilapon.anywr.group.controllers;

import com.gnilapon.anywr.group.models.dto.UserDTO;
import com.gnilapon.anywr.group.models.dto.payload.request.LoginRequest;
import com.gnilapon.anywr.group.models.dto.payload.request.SignupRequest;
import com.gnilapon.anywr.group.models.dto.payload.response.ErrorResponse;
import com.gnilapon.anywr.group.models.dto.payload.response.JwtResponse;
import com.gnilapon.anywr.group.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Authentication users management")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signin")
  @Operation(summary = "authenticate user , use username and password")
  @GetMapping
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200",description = "authenticate user",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = JwtResponse.class))}),
          @ApiResponse(responseCode = "404",description = "Ressource Not Found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "500",description = "Erreur serveur",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
  })
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      return ResponseEntity.ok(userService.signin(loginRequest));
    }catch (Exception e){
      var errorResponse = new ErrorResponse();
      errorResponse.setMessage(e.getMessage());
      errorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  @PostMapping("/signup")
  @Operation(summary = "signup user , take roles on [student,teacher,admin]")
  @GetMapping
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200",description = "signup user",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))}),
          @ApiResponse(responseCode = "400",description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "404",description = "Ressource Not Found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
          @ApiResponse(responseCode = "500",description = "Erreur serveur",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
  })
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    try {
      return ResponseEntity.ok(userService.signup(signUpRequest));
    }catch (Exception e){
      var errorResponse = new ErrorResponse();
      errorResponse.setMessage(e.getMessage());
      errorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }
}
