package top.anyel.gateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.anyel.gateway.model.Users;
import top.anyel.gateway.repository.UsersRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UsersRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users usuario = usuarioRepository.findByUsernameAndEstadoRegistroTrue(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    return UsuarioPrincipal.build(usuario);
  }

}
