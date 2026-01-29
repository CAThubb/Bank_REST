package com.dimochic.Bank.user.service.impl;

import com.dimochic.Bank.user.model.entity.User;
import com.dimochic.Bank.user.repository.UserRepository;
import com.dimochic.Bank.user.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserPrincipalServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email + " + email));

        return UserPrincipal.create(user);
    }
}
