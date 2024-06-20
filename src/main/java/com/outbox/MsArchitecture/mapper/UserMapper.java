package com.outbox.MsArchitecture.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outbox.MsArchitecture.entity.Outbox;
import com.outbox.MsArchitecture.entity.User;
import com.outbox.MsArchitecture.model.Aggregate;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ObjectMapper objectMapper;

    public UserMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Outbox mapToOutBoxEntity(User user) throws JsonProcessingException {
        return new Outbox(
                Aggregate.USER,
                objectMapper.writeValueAsString(user),
                false
        );
    }
}

