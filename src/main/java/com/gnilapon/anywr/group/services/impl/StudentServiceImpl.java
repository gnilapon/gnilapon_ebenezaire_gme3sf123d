package com.gnilapon.anywr.group.services.impl;


import com.gnilapon.anywr.group.models.entities.Student;
import com.gnilapon.anywr.group.repositories.StudentRepository;
import com.gnilapon.anywr.group.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<Student> getStudentsByFilters(String className, String teacherName, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        if ((className==null || className.isEmpty()) && (teacherName==null || teacherName.isEmpty())) {
            // Aucun filtre n'est spécifié, retourne tous les étudiants paginés
            return studentRepository.findAll(pageable);
        } else if (!(className==null || className.isEmpty()) && (teacherName==null || teacherName.isEmpty())) {
            // Filtre par nom de classe uniquement
            return studentRepository.findByClassroomNameContaining(className,pageable);
        } else if (className==null || className.isEmpty()) {
            // Filtre par nom d'enseignant uniquement
            return studentRepository.findByClassroomTeacherFullNameContaining(teacherName,pageable);
        } else {
            // Filtre par nom de classe et nom d'enseignant
            return studentRepository.findByClassroomNameAndClassroomTeacherFullName(className, teacherName, pageable);
        }

    }
}
