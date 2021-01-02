package org.springproject.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.User;
import org.springproject.springproject.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String loginUsername) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(loginUsername);
        if (Objects.isNull(user)) {
            user = userRepository.findByEmail(loginUsername);
            if (Objects.isNull(user)) {
                throw new UsernameNotFoundException("Nie znaleziono uzytkownika o loginie " + loginUsername);
            }
        }
        return new org.springframework.security.core.userdetails.User(loginUsername, user.getPassword(),
                user.getEnabled(), true, true, true,
                AuthorityUtils.createAuthorityList(user.getRole()));
    }

    @Override
    public User createNewUser(User user) {
        log.info("CREATE USER: " + user.toString());
        if (Objects.isNull(userRepository.findByUsername(user.getUsername())) && Objects.isNull(userRepository.findByEmail(user.getEmail()))) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (Objects.isNull(user.getEnabled())) {
                user.setEnabled(true);
            }
            if (Objects.isNull(user.getRole())) {
                user.setRole("ROLE_USER");
            }
            return userRepository.save(user);
        }
        log.info("CREATE USER FAIL ");
        return null;
    }

    @Override
    public List<User> createBatchOfUsers(List<User> users) {
        users.forEach(this::createNewUser);
        return users;
    }


    @Override
    public Page<User> getAllUsers(Integer page, Integer size) {
        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User updateUserById(Long id, User user) {
        log.info("Update user by id service: " + user.toString());
//        if (userRepository.existsById(id) && Objects.isNull(userRepository.findByUsername(user.getUsername())) && Objects.isNull(userRepository.findByEmail(user.getEmail()))) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            if (!getUserById(id).getPassword().equals(user.getPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            return userRepository.save(user);
        }
        log.info("USER NOT UPDATED");
        return null;
    }

    @Override
    public Boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<User> getByKeyword(String keyword, Integer page, Integer size) {
        if (Objects.isNull(page)) {
            page = 1;
        }
        if (Objects.isNull(size)) {
            size = 5;
        }
        if (page < 0) {
            throw new WrongPageException("Page number can't be less than 1");
        }
        Pageable pageable = PageRequest.of(page, size);


        return userRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public List<User> getUsersWithoutCustomerAccount() {
        return userRepository.findAll()
                .stream()
                .filter(user -> Objects.isNull(user.getCustomer()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameAndEmail(usernameOrEmail);
    }

    @Override
    public User getUserByCustomerId(Long customerId) {
        return userRepository.findByCustomer_Id(customerId);
    }
}
