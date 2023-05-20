package com.gnilapon.anywr.group.controllers;

import com.gnilapon.anywr.group.mappers.StudentMapper;
import com.gnilapon.anywr.group.models.dto.StudentDto;
import com.gnilapon.anywr.group.models.dto.payload.response.ErrorResponse;
import com.gnilapon.anywr.group.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Get the list of students with the following:\n" +
            "Filters: Class Name and/or Teacher Full Name\n" +
            "All Students list will be returned in case of no filters value\n" +
            "Paginated")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "List of students",content = {@Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = StudentDto.class)))}),
            @ApiResponse(responseCode = "404",description = "Ressource Not Found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Erreur serveur",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "Not Authenticated",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))})
    })
    /**@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")*/
    public ResponseEntity<?> getStudents(@RequestParam(name = "classname",required = false) String name,
                                         @RequestParam(name = "fullname" ,required = false) String lName,
                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                         @RequestParam(name = "size",defaultValue = "10") int pageSize) {
        try {
                return ResponseEntity.ok(studentService.getStudentsByFilters(name,lName,page,pageSize).map(StudentMapper.INSTANCE::entityToDto));

        } catch (Exception e) {
            var errorResponse = new ErrorResponse();
            errorResponse.setMessage(e.getMessage());
            errorResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

