package com.gnilapon.anywr.group.models.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@NotBlank(message = "Invalid username: Empty username")
	@NotNull(message = "Invalid username: username is NULL")
    private String username;

	@NotBlank(message = "Invalid password: Empty password")
	@NotNull(message = "Invalid password: password is NULL")
	private String password;

}
