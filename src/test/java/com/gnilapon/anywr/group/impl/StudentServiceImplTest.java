package com.gnilapon.anywr.group.impl;

import com.gnilapon.anywr.group.models.entities.Student;
import com.gnilapon.anywr.group.repositories.StudentRepository;
import com.gnilapon.anywr.group.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudentsByFiltersNoFilters() {
        String className = null;
        String teacherName = null;
        int page = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Student> expectedPage = new PageImpl<>(List.of(new Student(), new Student()));
        when(studentRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Student> result = studentService.getStudentsByFilters(className, teacherName, page, pageSize);

        assertEquals(expectedPage, result);
        verify(studentRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void testGetStudentsByFiltersClassNameFilterOnly() {
        String className = "Math";
        String teacherName = null;
        int page = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Student> expectedPage = new PageImpl<>(List.of(new Student(), new Student()));
        when(studentRepository.findByClassroomNameContaining(className, pageable)).thenReturn(expectedPage);

        Page<Student> result = studentService.getStudentsByFilters(className, teacherName, page, pageSize);

        assertEquals(expectedPage, result);
        verify(studentRepository, times(1)).findByClassroomNameContaining(className, pageable);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void testGetStudentsByFiltersTeacherNameFilterOnly() {
        String className = null;
        String teacherName = "John Doe";
        int page = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Student> expectedPage = new PageImpl<>(List.of(new Student(), new Student()));
        when(studentRepository.findByClassroomTeacherFullNameContaining(teacherName, pageable)).thenReturn(expectedPage);

        Page<Student> result = studentService.getStudentsByFilters(className, teacherName, page, pageSize);

        assertEquals(expectedPage, result);
        verify(studentRepository, times(1)).findByClassroomTeacherFullNameContaining(teacherName, pageable);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void testGetStudentsByFiltersClassNameAndTeacherNameFilters() {
        String className = "Math";
        String teacherName = "John Doe";
        int page = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Student> expectedPage = new PageImpl<>(List.of(new Student(), new Student()));
        when(studentRepository.findByClassroomNameAndClassroomTeacherFullName(className, teacherName, pageable)).thenReturn(expectedPage);

        Page<Student> result = studentService.getStudentsByFilters(className, teacherName, page, pageSize);

        assertEquals(expectedPage, result);
        verify(studentRepository, times(1)).findByClassroomNameAndClassroomTeacherFullName(className, teacherName, pageable);
        verifyNoMoreInteractions(studentRepository);
    }

}