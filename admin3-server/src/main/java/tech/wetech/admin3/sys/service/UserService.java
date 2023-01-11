package tech.wetech.admin3.sys.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.common.BusinessException;
import tech.wetech.admin3.common.DomainEventPublisher;
import tech.wetech.admin3.sys.event.UserCreated;
import tech.wetech.admin3.sys.event.UserDeleted;
import tech.wetech.admin3.sys.event.UserUpdated;
import tech.wetech.admin3.sys.model.Organization;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.repository.UserRepository;
import tech.wetech.admin3.sys.service.dto.PageDTO;

import java.time.LocalDateTime;

import static tech.wetech.admin3.common.CommonResultStatus.RECORD_NOT_EXIST;

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
    public User createUser(String username, String fullName, String avatar, User.Gender gender, User.State state, Organization organization) {
        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setAvatar(avatar);
        user.setGender(gender);
        user.setState(state);
        user.setCreatedTime(LocalDateTime.now());
        user.setOrganization(organization);
        user = userRepository.save(user);
        DomainEventPublisher.instance().publish(new UserCreated(user));
        return user;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(RECORD_NOT_EXIST));
    }

    public PageDTO<User> findOrgUsers(Pageable pageable, String username, User.State state, Organization organization) {
        Page<User> page = userRepository.findOrgUsers(pageable, username, state, organization, organization.makeSelfAsParentIds());
        return new PageDTO<>(page.getContent(), page.getTotalElements());
    }

    public boolean existsUsers(Organization organization) {
        String orgParentIds = organization.makeSelfAsParentIds();
        return userRepository.countOrgUsers(organization, orgParentIds) > 0;
    }


    @Transactional
    public User updateUser(Long userId, String fullName, String avatar, User.Gender gender, User.State state, Organization organization) {
        User user = findUserById(userId);
        user.setFullName(fullName);
        user.setAvatar(avatar);
        user.setGender(gender);
        user.setState(state);
        user.setOrganization(organization);
        user = userRepository.save(user);
        DomainEventPublisher.instance().publish(new UserUpdated(user));
        return user;
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
        User user = findUserById(userId);
        userRepository.delete(user);
        DomainEventPublisher.instance().publish(new UserDeleted(user));
    }
}
