package com.tesinas.spring.jwt.mongodb.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.tesinas.spring.jwt.mongodb.models.ERole;
import com.tesinas.spring.jwt.mongodb.models.Role;
import com.tesinas.spring.jwt.mongodb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tesinas.spring.jwt.mongodb.payload.request.LoginRequest;
import com.tesinas.spring.jwt.mongodb.payload.request.SignupRequest;
import com.tesinas.spring.jwt.mongodb.payload.response.JwtResponse;
import com.tesinas.spring.jwt.mongodb.payload.response.MessageResponse;
import com.tesinas.spring.jwt.mongodb.repository.RoleRepository;
import com.tesinas.spring.jwt.mongodb.repository.UserRepository;
import com.tesinas.spring.jwt.mongodb.security.jwt.JwtUtils;
import com.tesinas.spring.jwt.mongodb.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getUsername(),
												 userDetails.getEmail(),
												 userDetails.getName(),
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role alumnoRole = roleRepository.findByName(ERole.ROLE_ALUMNO)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(alumnoRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "Director":
					Role directorRole = roleRepository.findByName(ERole.ROLE_DIRECTOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(directorRole);

					break;
				case "Profesor":
					Role modRole = roleRepository.findByName(ERole.ROLE_PROFESOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				case "Asesor":
					Role asesorRole = roleRepository.findByName(ERole.ROLE_ASESOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(asesorRole);

					break;
				default:
					Role alumnoRole = roleRepository.findByName(ERole.ROLE_ALUMNO)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(alumnoRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/expires")
	public ResponseEntity<?> isJWTValid(){
		return ResponseEntity.ok(new MessageResponse("JWT is Valid"));
	}
}
