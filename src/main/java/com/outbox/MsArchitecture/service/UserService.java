package com.outbox.MsArchitecture.service;

import com.outbox.MsArchitecture.dto.UserRequestDto;
import com.outbox.MsArchitecture.entity.Outbox;
import com.outbox.MsArchitecture.entity.User;
import com.outbox.MsArchitecture.mapper.UserMapper;
import com.outbox.MsArchitecture.mapper.UserRequestDtoMapper;
import com.outbox.MsArchitecture.repository.OutboxRepository;
import com.outbox.MsArchitecture.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;
    private final UserRequestDtoMapper userRequestDtoMapper;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, OutboxRepository outboxRepository,
                       UserRequestDtoMapper userRequestDtoMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.outboxRepository = outboxRepository;
        this.userRequestDtoMapper = userRequestDtoMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    public User createUser(UserRequestDto userRequestDto) {
        try {
            User user = userRequestDtoMapper.mapToUserEntity(userRequestDto);

            user = userRepository.save(user);

            Outbox outbox = userMapper.mapToOutBoxEntity(user);

            outboxRepository.save(outbox); // This time saving message in a table instead of directly publishing

            return user;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}