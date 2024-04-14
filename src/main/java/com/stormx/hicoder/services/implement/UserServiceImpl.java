package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.controllers.helpers.UpdateUserProfile;
import com.stormx.hicoder.entities.FileDB;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.AppException;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.UserRepository;
import com.stormx.hicoder.services.FileStorageService;
import com.stormx.hicoder.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;
    private  final FileStorageService fileStorageService;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "User not found"));
    }


    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    @Override
    public void updateUser(User user, UpdateUserProfile updateUserProfile) {
        if (updateUserProfile.getBirthday() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthday = LocalDate.parse(updateUserProfile.getBirthday(), formatter);
            user.setBirthday(birthday);
        }
        BeanUtils.copyProperties(updateUserProfile, user, getNullPropertyNames(updateUserProfile));
        userRepository.save(user);
    }

    @Override
    public void updateAvatar(User user, String avatarUrl) {
        String[] splited = avatarUrl.split("/");
        String idFile = splited[splited.length - 1];
        FileDB fileDB = fileStorageService.getFile(idFile);
        String fileType = fileDB.getType();
        if (!fileType.startsWith("image")) {
            throw new BadRequestException("File is not an image");
        }
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
    }

    @Override
    public void removeUser(User user) {
      userRepository.delete(user);
    }

    @Override
    public void changeRole(User user, Role role) {
        user.setRole(role);
        userRepository.save(user);
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
