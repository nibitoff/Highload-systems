package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.UserDto;
import com.alsab.boozycalc.entity.UserEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.UserMapper;
import com.alsab.boozycalc.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public Iterable<UserEntity> findAll() {
        return userRepo.findAll();
    }

    public UserDto findById(Long id) throws ItemNotFoundException {
        return userMapper.userToDto(
                userRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(UserDto.class, id))
        );
    }

    public UserDto findByUserName(String username) throws UsernameNotFoundException {
        return userMapper.userToDto(
                userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no user find with username " + username))
        );
    }

    public UserDto saveUser(UserDto userDto) {
        return userMapper.userToDto(userRepo.save(userMapper.dtoToUser(userDto)));
    }

    public boolean userExists(String username) {
        try {
            findByUserName(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }
}
