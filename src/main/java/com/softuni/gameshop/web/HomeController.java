package com.softuni.gameshop.web;
import com.softuni.gameshop.model.DTO.AddEmailDTO;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
public class HomeController {


    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            String currentUsername = authentication.getName();
            Optional<UserEntity> byUsername = this.userRepository.findByUsername(currentUsername);
            if (byUsername.isPresent() && byUsername.get().getEmail() == null){
                model.addAttribute("confirmed", false);
            }
        }

        return "index";
    }

    @PostMapping("/enter-email")
    public String enterEmail(@ModelAttribute("addEmailDTO") @Valid AddEmailDTO addEmailDTO, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            String currentUsername = authentication.getName();
            Optional<UserEntity> optionalUser = this.userRepository.findByUsername(currentUsername);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                if (user.getEmail() == null) {
                    String enteredEmail = addEmailDTO.getEmail();
                    if (this.userRepository.findByEmail(addEmailDTO.getEmail()).isPresent()){
                        redirectAttributes.addFlashAttribute("emailExists", true);
                        return "redirect:/";
                    }
                    user.setEmail(enteredEmail);
                    userRepository.save(user);
                    redirectAttributes.addAttribute("confirmed", true);
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/return-policy")
    public String returnPolicy(){
        return "return-policy";
    }

}
