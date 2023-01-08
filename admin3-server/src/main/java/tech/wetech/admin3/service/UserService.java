package tech.wetech.admin3.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.exception.BusinessException;
import tech.wetech.admin3.model.User;
import tech.wetech.admin3.repository.UserRepository;
import tech.wetech.admin3.service.dto.PageDTO;

import java.time.LocalDateTime;

import static tech.wetech.admin3.exception.CommonResultStatus.RECORD_NOT_EXIST;

/**
 * @author cjbi
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String username, String fullName, String avatar, User.Gender gender, User.State state) {
        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setAvatar(avatar);
        user.setGender(gender);
        user.setState(state);
        user.setCreatedTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(RECORD_NOT_EXIST));

    }

    @Transactional
    public User updateUser(Long userId, String fullName, String avatar, User.Gender gender, User.State state) {
        User user = findUserById(userId);
        user.setFullName(fullName);
        user.setAvatar(avatar);
        user.setGender(gender);
        user.setState(state);
        return userRepository.save(user);
    }

    @Transactional
    public User lockUser(Long userId) {
        User user = findUserById(userId);
        user.setState(User.State.LOCKED);
        return userRepository.save(user);
    }

    @Transactional
    public User unlockUser(Long userId) {
        User user = findUserById(userId);
        user.setState(User.State.NORMAL);
        return userRepository.save(user);
    }

    public PageDTO<User> findUsers(Pageable pageable, User user) {
        Page<User> page = userRepository.findAll(Example.of(user), pageable);
        return new PageDTO<>(page.getContent(), page.getTotalElements());
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
