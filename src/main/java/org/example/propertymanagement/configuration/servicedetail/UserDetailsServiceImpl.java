package org.example.propertymanagement.configuration.servicedetail;


import lombok.RequiredArgsConstructor;
import org.example.propertymanagement.customexception.PlatformException;
import org.example.propertymanagement.repo.MemberRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepo memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var member =
                memberRepo
                        .findByEmail(username)
                        .orElseThrow(() -> new PlatformException("", HttpStatus.NOT_FOUND));
        return new CustomizeUserDetails(member);
    }
}
