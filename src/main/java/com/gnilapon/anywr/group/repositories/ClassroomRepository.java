package com.gnilapon.anywr.group.repositories;

import com.gnilapon.anywr.group.models.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom,String> {
}
