package com.gnilapon.anywr.group.mappers;

import com.gnilapon.anywr.group.models.dto.UserDTO;
import com.gnilapon.anywr.group.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends EntityMapper<User, UserDTO> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
