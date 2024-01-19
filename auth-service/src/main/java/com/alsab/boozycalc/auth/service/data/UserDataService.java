package com.alsab.boozycalc.auth.service.data;

import com.alsab.boozycalc.auth.dto.UserDto;
import com.alsab.boozycalc.auth.exception.ItemNotFoundException;
import com.alsab.boozycalc.auth.mapper.UserMapper;
import com.alsab.boozycalc.auth.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserRepo repo;
    private final UserMapper mapper;

    public Flux<UserDto> findAll() {
        return repo.findAll().map(mapper::userToDto);
    }

    public Mono<UserDto> findById(Long id) throws ItemNotFoundException {
        return repo.findById(id).map(mapper::userToDto);
    }

    public Mono<UserDto> findByUserName(String username) throws UsernameNotFoundException {
        return repo.existsByUsername(username).flatMap(
                exists -> {
                    if (!exists) throw new UsernameNotFoundException(username);
                    return repo.findByUsername(username).map(mapper::userToDto);
                }
        );
    }

    public Mono<UserDto> saveUser(UserDto userDto) {
        return repo.save(mapper.dtoToUser(userDto)).map(mapper::userToDto);
    }

    public Mono<Boolean> userExists(String username) {
        return repo.existsByUsername(username);
    }
}
