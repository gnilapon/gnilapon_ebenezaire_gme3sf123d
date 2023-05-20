package com.gnilapon.anywr.group;

import com.gnilapon.anywr.group.models.entities.Role;
import com.gnilapon.anywr.group.models.enums.ERole;
import com.gnilapon.anywr.group.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.gnilapon.anywr.group")
public class AnywrGroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnywrGroupApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(RoleRepository repository) {
		return args -> {
			repository.save(new Role(null, ERole.ROLE_STUDENT));
			repository.save(new Role(null, ERole.ROLE_TEACHER));
			repository.save(new Role(null, ERole.ROLE_ADMIN));
		};
	}
}
