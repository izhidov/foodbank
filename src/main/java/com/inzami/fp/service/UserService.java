package com.inzami.fp.service;

import com.inzami.fp.domain.Organization;
import com.inzami.fp.exception.UserAlreadyExistException;
import com.inzami.fp.exception.UserNotMatchException;
import com.inzami.fp.repository.UserRepository;
import com.inzami.fp.domain.User;
import com.inzami.fp.exception.EntityNotFoundInServiceException;
import com.inzami.fp.rest.dto.save.UserSaveDTO;
import com.inzami.fp.rest.dto.search.UserSearchForm;
import com.inzami.fp.rest.dto.update.UserUpdateDTO;
import com.inzami.fp.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MapperFacade mapperFacade;

    @Transactional
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundInServiceException(User.class, email, "email")
        );
        return user;
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.truncatedTo(ChronoUnit.MINUTES));
        System.out.println(localDateTime.with(LocalTime.MIN));
        System.out.println(localDateTime.with(LocalTime.MAX));
    }

    public User getCurrentUser() throws EntityNotFoundInServiceException {
        String currentUserEmail = AuthUtils.getAuthUser().getUsername();
        User user = findByEmail(currentUserEmail);
        if (user == null) {
            throw new EntityNotFoundInServiceException(User.class, currentUserEmail);
        }
        return user;
    }

    public User findOne(Long id) throws EntityNotFoundInServiceException {
        User result = userRepository.findOne(id);
        if (result == null) {
            throw new EntityNotFoundInServiceException(User.class, id);
        }
        return result;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> searchUsers(UserSearchForm userSearchForm) {
        Pageable pageable = new PageRequest(userSearchForm.getPage(), userSearchForm.getLimit());
        List<User> users = userRepository.findByEmailLike(userSearchForm.getUserEmail(), pageable);
        return users;
    }

    public User update(Long userId, UserUpdateDTO userUpdateDTO) {
        User user = findOne(userId);
        if (StringUtils.isNotBlank(userUpdateDTO.getFirstName())) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }
        if (StringUtils.isNotBlank(userUpdateDTO.getLastName())) {
            user.setLastName(userUpdateDTO.getLastName());
        }
        if (StringUtils.isNotBlank(userUpdateDTO.getEmail())) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (StringUtils.isNotBlank(userUpdateDTO.getPhone())) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getActive() != null) {
            user.setActive(userUpdateDTO.getActive());
        }
        if (userUpdateDTO.getRole() != null) {
            user.setRole(userUpdateDTO.getRole());
        }
        if (userUpdateDTO.getOrganizationId() != null) {
            Organization organization = organizationService.findOne(userUpdateDTO.getOrganizationId());
            user.setOrganization(organization);
        }

        user = userRepository.save(user);
        return user;
    }

    public User create(UserSaveDTO userSaveDTO) {
        long count = userRepository.countByEmail(userSaveDTO.getEmail());
        if(count > 0){
            throw new UserAlreadyExistException(userSaveDTO.getEmail());
        }
        User user = mapperFacade.map(userSaveDTO, User.class);
        save(user);
        return user;
    }
}
