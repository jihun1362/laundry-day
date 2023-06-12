package com.meta.laundry_day.security.util;

import com.meta.laundry_day.user.entity.User;
import com.meta.laundry_day.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.meta.laundry_day.common.message.ErrorCode.USER_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERROR.getMsg()));

        return new UserDetailsImpl(user, user.getEmail());
    }
}