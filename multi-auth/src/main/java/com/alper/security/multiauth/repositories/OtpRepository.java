package com.alper.security.multiauth.repositories;

import com.alper.security.multiauth.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository  extends JpaRepository<Otp,Integer> {
}
