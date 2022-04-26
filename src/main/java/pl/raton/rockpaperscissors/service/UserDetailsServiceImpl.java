package pl.raton.rockpaperscissors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.raton.rockpaperscissors.entity.AppUser;
import pl.raton.rockpaperscissors.repo.AppUserRepo;

@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AppUserRepo appUserRepo;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        final AppUser appUser = appUserRepo.findAllByUsername(s);

        if (appUser == null) {
            throw new UsernameNotFoundException("Not Found User: " + s);
        }
        return appUserRepo.findAllByUsername(s);
    }
}

