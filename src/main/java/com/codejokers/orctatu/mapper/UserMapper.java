package com.codejokers.orctatu.mapper;

import com.codejokers.orctatu.dto.UserDTO;
import com.codejokers.orctatu.dto.UserResponseDTO;
import com.codejokers.orctatu.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "googleId", source = "userDTO.sub")
    @Mapping(target = "emailVerified", source = "userDTO.email_verified")
    public abstract User toUser(final UserDTO userDTO);

    public abstract UserResponseDTO toUserResponseDTO(final UserDTO userDTO);
}