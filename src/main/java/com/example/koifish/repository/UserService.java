package com.example.koifish.repository;

import com.example.koifish.config.SecurityUser;
import com.example.koifish.model.User;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;



	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;


	public boolean validateCredentials(String username, String password) {
		return userRepository.existsByUsernameAndPassword(username, passwordEncoder.encode(password));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userByUsername = userRepository.findByUsernameAndStatus(username, true);
		if (userByUsername.isEmpty()) {
			System.out.println("Could not find user with that email: {}");
			throw new UsernameNotFoundException("Invalid credentials!");
		}
		User user = userByUsername.get();
		System.out.println(user);
		if (!user.getUsername().equals(username)) {
			System.out.println("Could not find user with that username: {}");
			throw new UsernameNotFoundException("Invalid credentials!");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		String role = "CLIENT";
		if(user.getRole() == true) {
			role = "ADMIN";
		}
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));

		System.out.println(grantedAuthorities);
		return new SecurityUser(user.getEmail(), user.getPassword(), true, true, true, true, grantedAuthorities,
				user.getEmail());
	}

}
