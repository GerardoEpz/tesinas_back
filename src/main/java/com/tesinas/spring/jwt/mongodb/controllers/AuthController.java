package com.tesinas.spring.jwt.mongodb.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.tesinas.spring.jwt.mongodb.models.ERole;
import com.tesinas.spring.jwt.mongodb.models.Role;
import com.tesinas.spring.jwt.mongodb.models.User;
import com.tesinas.spring.jwt.mongodb.payload.request.SignUpRequestList;
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
	public List<MessageResponse> registerUser(SignUpRequestList signUpRequests){
		List<MessageResponse> messages = new ArrayList<>();
		for (SignupRequest signUpRequest : signUpRequests.getSignupRequests()) {
			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				messages.add(new MessageResponse("El Usuario '" + signUpRequest.getUsername() + "' ya existe",false));
				continue;
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				messages.add(new MessageResponse("El email del Usuario '" + signUpRequest.getUsername() + "' ya está registrado",false));
				continue;
			}

			// Create new user's account
			User user = new User(signUpRequest.getUsername(),
					signUpRequest.getEmail(),
					signUpRequest.getName(),
					signUpRequest.getGrupo(),
					encoder.encode(signUpRequest.getPassword()));

			Set<String> strRoles = signUpRequest.getRoles();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				Role alumnoRole = roleRepository.findByName(ERole.ROLE_ALUMNO)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(alumnoRole);
			} else {
				strRoles.forEach(role -> {
					Role roleChosed;
					switch (role) {

						case "Director":
							roleChosed = roleRepository.findByName(ERole.ROLE_DIRECTOR)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(roleChosed);

							break;
						case "Profesor":
							roleChosed = roleRepository.findByName(ERole.ROLE_PROFESOR)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(roleChosed);

							break;
						case "Asesor":
							roleChosed = roleRepository.findByName(ERole.ROLE_ASESOR)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(roleChosed);

							break;

						default:
							roleChosed = roleRepository.findByName(ERole.ROLE_ALUMNO)
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(roleChosed);
					}
				});
			}

			user.setRoles(roles);
			userRepository.save(user);
			messages.add(new MessageResponse("Usuario '" + signUpRequest.getUsername() + "' agregado exitosamente", true));
		}
		return messages;
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUserWithoutPrivileges(@Valid @RequestBody SignUpRequestList signUpRequests) {
		signUpRequests.getSignupRequests().get(0).setRole(null);
		try{
			List<MessageResponse> messages = registerUser(signUpRequests);
			for ( MessageResponse message : messages){
				if (message.getAdded() == false) return ResponseEntity.badRequest().body(messages);
			}
			return ResponseEntity.ok(messages);
		} catch (RuntimeException error){
			throw new RuntimeException(error);
		}
	}

	@PreAuthorize("hasRole('DIRECTOR') or hasRole('PROFESOR') or hasRole('ASESOR') ")
	@PostMapping("/signup-user-with-privileges")
	public ResponseEntity<?> registerUserWithPrivileges(@Valid @RequestBody SignUpRequestList signUpRequests) {
		try{
			List<MessageResponse> messages = registerUser(signUpRequests);
			for ( MessageResponse message : messages){
				if (message.getAdded() == false) return ResponseEntity.badRequest().body(messages);
			}
			return ResponseEntity.ok(messages);
		} catch (RuntimeException error){
			throw new RuntimeException(error);
		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/expires")
	public ResponseEntity<?> isJWTValid(){
		return ResponseEntity.ok("JWT is Valid");
	}
}
