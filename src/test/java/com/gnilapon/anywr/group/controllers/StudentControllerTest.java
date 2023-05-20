package com.gnilapon.anywr.group.controllers;

import com.gnilapon.anywr.group.models.dto.payload.response.ErrorResponse;
import com.gnilapon.anywr.group.models.entities.Classroom;
import com.gnilapon.anywr.group.models.entities.Student;
import com.gnilapon.anywr.group.models.entities.Teacher;
import com.gnilapon.anywr.group.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudentsWithFilters() {
        // Mocking the service response
        Classroom classroom = new Classroom("12345","SIL",new Teacher("1234","ben","amed"));
        List<Student> students = Arrays.asList(new Student("123","John", "Doe",classroom), new Student("12","Jane", "Smith",classroom));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());
        when(studentService.getStudentsByFilters(anyString(), anyString(), anyInt(), anyInt())).thenReturn(studentPage);

        // Calling the controller method
        ResponseEntity<?> responseEntity = studentController.getStudents("ClassName", "FullName", 0, 10);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetStudentsWithoutFilters() {
        // Mocking the service response
        Classroom classroom = new Classroom("12345","SIL",new Teacher("1234","ben","amed"));
        List<Student> students = Arrays.asList(new Student("123","John", "Doe",classroom), new Student("12","Jane", "Smith",classroom));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());
        when(studentService.getStudentsByFilters(anyString(), anyString(), anyInt(), anyInt())).thenReturn(studentPage);

        // Calling the controller method without filters
        ResponseEntity<?> responseEntity = studentController.getStudents(null, null, 0, 10);

        // Verifying the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testExceptionHandling() {
        // Mocking the service to throw an exception
        when(studentService.getStudentsByFilters(anyString(), anyString(), anyInt(), anyInt())).thenThrow(new RuntimeException("Some error"));

        // Calling the controller method
        ResponseEntity<?> responseEntity = studentController.getStudents("ClassName", "FullName", 0, 10);

        // Verifying the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assert errorResponse != null;
        assertEquals("Some error", errorResponse.getMessage());
        assertEquals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorResponse.getCode());
    }
}