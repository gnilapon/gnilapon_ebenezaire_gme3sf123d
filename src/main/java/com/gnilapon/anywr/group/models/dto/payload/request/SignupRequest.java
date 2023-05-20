package com.gnilapon.anywr.group.models.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  @NotBlank(message = "Invalid username: Empty username")
  @NotNull(message = "Invalid username: username is NULL")
  private String username;

  @NotBlank(message = "Invalid password: Empty password")
  @NotNull(message = "Invalid password: password is NULL")
  private String password;

  @NotBlank(message = "Invalid email: Empty email")
  @NotNull(message = "Invalid email: email is NULL")
  @Email(message = "Invalid email")
  private String email;

  private Set<String> role;
}
