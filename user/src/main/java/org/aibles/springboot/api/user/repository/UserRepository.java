package org.aibles.springboot.api.user.repository;

import org.aibles.springboot.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
