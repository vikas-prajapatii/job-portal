package com.noir.job.service.serviceimpl;
import com.noir.job.domain.UserStatus;
import com.noir.job.dto.response.UserResponse;
import com.noir.job.mapper.UserMapper;
import com.noir.job.model.User;
import com.noir.job.payload.UpdateUserRequest;
import com.noir.job.repository.UserRepository;
import com.noir.job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
             throw new Exception("user not found");
        }
        return user;
    }
    @Override
    public User getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                ()->new Exception("user not found")
        );
        return user;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception {
        User user = getUserByEmail(email);
        if(req.getFullName() != null){
            user.setFullName(req.getFullName());
        }
        if(req.getPhone()!= null){
            user.setPhone(req.getPhone());
        }
        if(req.getProfileImage() != null){
            user.setProfileImage(req.getProfileImage());
        }
        return UserMapper.toDTO(userRepository.save(user));

    }
    @Override
    public UserResponse suspendUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }
    @Override
    public UserResponse activateUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);
        return UserMapper.toDTO(userRepository.save(user));
    }
    @Override
    public UserResponse deleteUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }
}
