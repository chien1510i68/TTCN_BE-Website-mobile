package com.example.mobilemanager.Service.AuthService;

import com.example.mobilemanager.Entity.Role;
import com.example.mobilemanager.Entity.User;
import com.example.mobilemanager.Model.UserDetailsImpl;
import com.example.mobilemanager.Repository.Role.RoleRepository;
import com.example.mobilemanager.Repository.User.UserRepository;
import com.example.mobilemanager.Request.AuthRequest.RegisterRequest;
import com.example.mobilemanager.Response.ResponseSuccess.LogginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenServiceImpl implements AuthenService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtUtils;

    @Autowired
    public AuthenServiceImpl(AuthenticationManager authenticationManager,
                             UserRepository userRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder encoder,
                             JwtTokenProvider jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LogginResponse authenticateUser(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateToken(authentication);

        //other option generate jwt token with role
        String jwt = jwtUtils.generateTokenWithAuthorities(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return new LogginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public void registerUser(RegisterRequest request) {
        if (userRepository.existsAllByUserName(request.getUserName())) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }

        if (userRepository.existsAllByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống!");
        }
        // Create new user's account
        Role role = new Role();
        role.setId(3);
        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .isSuperAdmin(false)
                .role(role)
                .phoneNumber(request.getPhoneNumber())
                .addr(request.getAddress())
                .password(encoder.encode(request.getPassword()))
                .build();

//        Set<Role> roles = new HashSet<>();
//        if (role == null) {
//            Role userRole = roleRepository.findByName(RoleEnum.USER)
//                    .orElseThrow(() -> new RuntimeException("Vai trò không tìm thấy trong hệ thống."));
//            roles.add(userRole);
//        } else {
//            role.forEach(roleItem -> {
//                try {
//                    RoleEnum value = RoleEnum.valueOf(roleItem);
//                    Role roleValue = roleRepository.findByName(value).orElseThrow(RuntimeException::new);
//                    roles.add(roleValue);
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
////                switch (roleItem) {
////                    case RoleEnum.ADMIN.va:
////                        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////                        roles.add(adminRole);
////
////                        break;
////                    case "mod":
////                        Role modRole = roleRepository.findByName(RoleEnum.MOD)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////                        roles.add(modRole);
////
////                        break;
////                    default:
////                        Role userRole = roleRepository.findByName(RoleEnum.USER)
////                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////                        roles.add(userRole);
////                }
////            });
//            });
//            user.setRoles(roles);
        userRepository.save(user);
    }
}
