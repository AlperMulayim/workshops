package com.alper.security.multiauth.security.providers;

import com.alper.security.multiauth.entities.Otp;
import com.alper.security.multiauth.repositories.OtpRepository;
import com.alper.security.multiauth.security.authentications.OtpAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class OtpAuthProvider  implements AuthenticationProvider {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String otpStr = (String) authentication.getCredentials();

        Optional<Otp> optionalOtp = otpRepository.findOtpByUsername(username);
        Otp otp = optionalOtp.orElseThrow(()-> new BadCredentialsException("OTP PROVIDER NOT VALIDATE PASS"));
        return  new OtpAuth(username,otp, List.of(()->"read"));

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthProvider.class.equals(authentication);
    }
}
