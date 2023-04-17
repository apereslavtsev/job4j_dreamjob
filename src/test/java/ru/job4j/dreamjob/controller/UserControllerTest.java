package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;

    private UserController userController;
    
    private Model model;

    @BeforeEach
    public void register() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        model = new ConcurrentModel();
    }

    @Test
    void whenRegisterUserThenRedirectToIndex() {
        var user = new User(1, "testUsr", "Example@mail.com", "123");
        when(userService.save(any(User.class))).thenReturn(Optional.of(user));
        
        var view = userController.register(user, model);
        
        assertThat(view).isEqualTo("redirect:/");
    }
    
    @Test
    void whenRegisterDuplicateUserThenGetErrorPageWithMessage() {
        var user = new User(1, "testUsr", "Example@mail.com", "123");
        when(userService.save(any(User.class))).thenReturn(Optional.empty());
        
        var view = userController.register(user, model);
        var message = model.getAttribute("message");
        
        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("Пользователь с такой почтой уже существует");
    }
    
    @Test
    void whenLoginUserThenRedirectToIndex() {
        var user = new User(1, "testUsr", "Example@mail.com", "123");
        when(userService.findByEmailAndPassword(any(String.class), any(String.class)))
            .thenReturn(Optional.of(user));
        HttpSession session = mock(HttpSession.class);
        
        var view = userController.loginUser(user, model, session);
        
        verify(session).setAttribute("user", user);
        assertThat(view).isEqualTo("redirect:/");
    }

    
    @Test
    void whenLoginInvalidUserThenRedirectToLoginPageWithMessage() {
        var user = new User(1, "testUsr", "Example@mail.com", "123");
        when(userService.findByEmailAndPassword(any(String.class), any(String.class)))
            .thenReturn(Optional.empty());
        HttpSession session = mock(HttpSession.class);
        
        var view = userController.loginUser(user, model, session);
        var message = model.getAttribute("error");
        
        assertThat(view).isEqualTo("users/login");
        assertThat(message).isEqualTo("Почта или пароль введены неверно");
    }
    
    @Test
    void whenLogoutUserThenLogout() {
        HttpSession session = mock(HttpSession.class);
        
        var view = userController.logout(session);
        
        verify(session).invalidate();
        assertThat(view).isEqualTo("redirect:/users/login");
    }
    
}
