package com.gnilapon.anywr.group.repositories;

import com.gnilapon.anywr.group.models.entities.Role;
import com.gnilapon.anywr.group.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
