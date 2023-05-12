package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
