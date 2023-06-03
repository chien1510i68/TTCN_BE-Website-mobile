package com.example.mobilemanager.Repository.User;

import com.example.mobilemanager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> , JpaSpecificationExecutor<User> {
    @Override
    List<User> findAll();

    Optional<User> findByUserName(String userName);

    Optional<User> findAllById(Long id);

    boolean existsAllByUserName(String userName);

    boolean existsAllByEmail(String email);

    boolean existsAllByUserNameAndIdNot(String userName , Long id );

}
