package com.outbox.MsArchitecture.mapper;

import com.outbox.MsArchitecture.dto.UserRequestDto;
import com.outbox.MsArchitecture.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDtoMapper {
    public User mapToUserEntity(UserRequestDto userRequestDto) {
        return new User(
                userRequestDto.getFirstName(),
                userRequestDto.getLastName(),
                userRequestDto.getDob(),
                userRequestDto.getAddress(),
                userRequestDto.getEmail()
        );
    }
}
