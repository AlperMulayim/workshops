package com.alper.security.multiauth.repositories;

import com.alper.security.multiauth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
}
