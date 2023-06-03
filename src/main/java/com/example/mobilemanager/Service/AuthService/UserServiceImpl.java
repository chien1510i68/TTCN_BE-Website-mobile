package com.example.mobilemanager.Service.AuthService;

import com.example.mobilemanager.Entity.Role;
import com.example.mobilemanager.Entity.User;
import com.example.mobilemanager.Model.DTO.UserDTO;
import com.example.mobilemanager.Repository.Role.RoleRepository;
import com.example.mobilemanager.Repository.User.CustomUserRepository;
import com.example.mobilemanager.Repository.User.UserRepository;
import com.example.mobilemanager.Request.UserRequest.CreateUserRequest;
import com.example.mobilemanager.Request.UserRequest.GetListUserRequest;
import com.example.mobilemanager.Request.UserRequest.UpdateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        checkUserIsExistByName(request.getUserName(), null);
        checkUserIsExistByEmail(request.getEmail(), null);
        checkRoleIsValid(request.getRoleId());
        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .isSuperAdmin(false)
                .addr(request.getAddr())
                .password(encoder.encode(request.getPassword()))
                .build();
        user.setRole(buildRole(request.getRoleId()));

        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UpdateUserRequest request) {
        checkUserIsExistByName(request.getUserName(), request.getUserID());
        checkRoleIsValid(request.getRoleId());
        validateUserExist(request.getUserID());
        Optional<User> user = userRepository.findById(request.getUserID());
        if (user.isPresent()) {
            User userUpdatePojo = user.get();
            userUpdatePojo.setUserName(request.getUserName());
            userUpdatePojo.setPhoneNumber(request.getPhoneNumber());
            userUpdatePojo.setPassword(encoder.encode(request.getPassword()));
            userUpdatePojo.setRole(buildRole(request.getRoleId()));
            return modelMapper.map(userRepository.save(userUpdatePojo), UserDTO.class);
        }
        throw new RuntimeException("Có lỗi xảy ra trong quá trình update vai trò");
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getAllUsers(GetListUserRequest request) {
        Specification<User> specification = CustomUserRepository.buildFilterSpecification(request.getKeyword() , request.getId());
        return userRepository.findAll(specification, PageRequest.of(request.getStart(), request.getLimit()));
    }

    @Override
    public UserDTO findUserById(Long id) {

        User user  = userRepository.findAllById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy id"));
        return modelMapper.map(user , UserDTO.class);
    }

    private void checkUserIsExistByName(String name, Long id) {
        if (id == null && userRepository.existsAllByUserName(name))
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        if (id != null && userRepository.existsAllByUserNameAndIdNot(name, id))
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
    }
    private void checkUserIsExistByEmail(String email, Long id) {
        if (id == null && userRepository.existsAllByEmail(email))
            throw new RuntimeException("Email đã tồn tại");
        if (id != null && userRepository.existsAllByUserNameAndIdNot(email, id))
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
    }

    private void validateUserExist(Long id) {
        boolean isExist = userRepository.existsById(id);
        if (!isExist)
            throw new RuntimeException("Người dùng không tồn tại trên hệ thống");
    }

    private void checkRoleIsValid(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds))
            return;
        List<Role> roles = buildRole(roleIds);
        if (CollectionUtils.isEmpty(roles)) {
            throw new RuntimeException("Roles không tồn tại");
        }
        List<Integer> listIdExists = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Integer> idNotExists = roleIds.stream().filter(s -> !listIdExists.contains(s)
        ).collect(Collectors.toList());
        if (!idNotExists.isEmpty())
            throw new RuntimeException(String.format("Trong danh sách role ids có mã không tồn tại trên hệ thống: %s", idNotExists));
    }

    private List<Role> buildRole(List<Integer> roleIds) {
        return CollectionUtils.isEmpty(roleIds) ? new ArrayList<>() : roleRepository.findAllById(roleIds);
    }

    private void checkRoleIsValid(Integer roleId) {
        if (roleId == null)
            return;
        Role role = buildRole(roleId);
        if (role == null) {
            throw new RuntimeException("Role không tồn tại");
        }
    }

    private Role buildRole(Integer roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role không tồn tại"));
    }
}
