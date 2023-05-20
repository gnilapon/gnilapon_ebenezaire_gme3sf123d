package com.gnilapon.anywr.group.services;

import com.gnilapon.anywr.group.models.entities.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    Page<Student> getStudentsByFilters(String className, String teacherName, int page, int pageSize);
}
