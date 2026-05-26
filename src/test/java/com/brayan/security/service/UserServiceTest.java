package com.brayan.security.service;

import com.brayan.security.dto.UpdateUserRequest;
import com.brayan.security.dto.UserResponse;
import com.brayan.security.entity.Role;
import com.brayan.security.entity.RoleName;
import com.brayan.security.entity.User;
import com.brayan.security.exception.BadRequestException;
import com.brayan.security.exception.ResourceNotFoundException;
import com.brayan.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAllShouldReturnUsers() {
        User user = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .enabled(true)
                .roles(Set.of(Role.builder().id(1L).name(RoleName.USER).build()))
                .build();
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> responses = userService.findAll();

        assertEquals(1, responses.size());
        assertEquals("jane@example.com", responses.get(0).getEmail());
    }

    @Test
    void findByIdShouldReturnUser() {
        User user = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .enabled(true)
                .roles(Set.of(Role.builder().id(1L).name(RoleName.USER).build()))
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.findById(1L);

        assertEquals(1L, response.getId());
        assertEquals("jane@example.com", response.getEmail());
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void updateShouldModifyUser() {
        User user = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .enabled(true)
                .roles(Set.of(Role.builder().id(1L).name(RoleName.USER).build()))
                .build();
        UpdateUserRequest request = new UpdateUserRequest("Janet", "Doe", "janet@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.update(1L, request);

        assertEquals("Janet", response.getFirstName());
        assertEquals("janet@example.com", response.getEmail());
    }

    @Test
    void updateShouldThrowWhenEmailExistsInAnotherUser() {
        User user = User.builder().id(1L).firstName("Jane").lastName("Doe").email("jane@example.com").enabled(true).roles(Set.of()).build();
        User another = User.builder().id(2L).firstName("Tom").lastName("Smith").email("tom@example.com").enabled(true).roles(Set.of()).build();
        UpdateUserRequest request = new UpdateUserRequest("Jane", "Doe", "tom@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(another));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.update(1L, request));
        assertEquals("El email ya se encuentra registrado", exception.getMessage());
    }

    @Test
    void disableShouldSetEnabledFalse() {
        User user = User.builder().id(1L).enabled(true).roles(Set.of()).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.disable(1L);

        assertFalse(response.getEnabled());
    }

    @Test
    void enableShouldSetEnabledTrue() {
        User user = User.builder().id(1L).enabled(false).roles(Set.of()).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.enable(1L);

        assertTrue(response.getEnabled());
    }
}
