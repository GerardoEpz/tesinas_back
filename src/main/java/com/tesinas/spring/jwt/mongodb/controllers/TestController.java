package com.tesinas.spring.jwt.mongodb.controllers;

import com.tesinas.spring.jwt.mongodb.models.ERole;
import com.tesinas.spring.jwt.mongodb.models.User;
import com.tesinas.spring.jwt.mongodb.repository.RoleRepository;
import com.tesinas.spring.jwt.mongodb.models.Role;
import com.tesinas.spring.jwt.mongodb.repository.UserRepository;
import com.tesinas.spring.jwt.mongodb.services.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SendEmail email;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping("/update-user")
	public ResponseEntity<?> updateUserData(@RequestBody String username) {
		Set<Role> roles = new HashSet<>();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		user.setName("Christian Emmanuel Guerra Hernandez");
		Role alumnoRole = roleRepository.findByName(ERole.ROLE_ALUMNO)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(alumnoRole);
		Role modRole = roleRepository.findByName(ERole.ROLE_PROFESOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(modRole);
		Role directorRole = roleRepository.findByName(ERole.ROLE_DIRECTOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(directorRole);
		Role asesorRole = roleRepository.findByName(ERole.ROLE_ASESOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(asesorRole);
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/email-test")
	public ResponseEntity<?> sendEmailTest() {
		email.SendSimpleMessage("eliu_070@hotmail.com","Another Test", "Haha yes text a lot of text sorry");
		return ResponseEntity.ok("e");
	}

	@GetMapping("/change-password-test")
	public ResponseEntity<?> changePassword(){
		User user  = userRepository.findByUsername("eliu").orElseThrow( () -> new RuntimeException("Error username not found"));

//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("eliu","Hjbj4DRIZA");

//		Authentication authentication = authenticationManager.authenticate(authenticationToken);

//		user.setPassword(encoder.encode("123456"));

//		userRepository.save(user);
//		email.SendSimpleMessage(user.getEmail(),"password changed","Hi "+user.getName()+" your password hsa been changed");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		return ResponseEntity.ok(username);
	}
}
