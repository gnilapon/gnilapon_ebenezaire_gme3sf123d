package com.gnilapon.anywr.group.mappers;

import com.gnilapon.anywr.group.models.dto.StudentDto;
import com.gnilapon.anywr.group.models.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper extends EntityMapper<Student, StudentDto> {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
}
