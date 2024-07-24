package ba.sum.fsre.ednevnik1.services;

import ba.sum.fsre.ednevnik1.models.user;
import ba.sum.fsre.ednevnik1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public ba.sum.fsre.ednevnik1.models.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user u = repository.findByEmail(username);
        return new ba.sum.fsre.ednevnik1.models.UserDetails(u);
    }

}