package com.alsab.boozycalc.cocktail.security.service;

import com.alsab.boozycalc.cocktail.dto.UserDto;
import com.alsab.boozycalc.cocktail.mapper.UserMapper;
import com.alsab.boozycalc.cocktail.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.userToDto(
                userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no user find with username " + username))
        );
    }
}
