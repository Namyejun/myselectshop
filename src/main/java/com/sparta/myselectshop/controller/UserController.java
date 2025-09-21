package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.entity.SignupRequestDto;
import com.sparta.myselectshop.entity.UserInfoDto;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.FolderService;
import com.sparta.myselectshop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "User API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final FolderService folderService;

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        bindingResult.getFieldErrors().forEach(fieldError -> {
            log.error(fieldError.getDefaultMessage());
        });
        if (bindingResult.hasErrors()) {
            return "redirect:/api/user/signup";
        } else {
            userService.signup(requestDto);
            return "redirect:/api/user/login-page";
        }
    }

    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = role == UserRoleEnum.ADMIN;

        return new UserInfoDto(username, isAdmin);
    }

    @GetMapping("/user-folder")
    public String getUserInfo(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("folders", folderService.getFolders(userDetails.getUser()));
        return "index :: #fragment";
    }

}
