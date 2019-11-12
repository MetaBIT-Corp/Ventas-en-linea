package com.metabit.ventasenlinea.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.ventasenlinea.entity.PasswordResetToken;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.controller.dto.PasswordResetDto;
import com.metabit.ventasenlinea.repository.PasswordResetTokenRepository;

import com.metabit.ventasenlinea.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	@Autowired
	private PasswordResetTokenRepository tokenRepository;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@ModelAttribute("passwordResetForm")
    public PasswordResetDto passwordReset() {
        return new PasswordResetDto();
    }
	@GetMapping
    public String displayResetPasswordPage(@RequestParam(required = false) String token,
                                           Model model) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null){
            model.addAttribute("error", "\r\n" + "No se pudo encontrar el token de restablecimiento de contraseña");
        } else if (resetToken.isExpired()){
            model.addAttribute("error", "\r\n" + "El token ha expirado, solicite un nuevo restablecimiento de contraseña.");
        } else {
            model.addAttribute("token", resetToken.getToken());
        }
        return "reset-password";
    }
	@PostMapping
    @Transactional
    public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return "redirect:/reset-password?token=" + form.getToken();
        }

        PasswordResetToken token = tokenRepository.findByToken(form.getToken());
        User user = token.getUser();
        String updatedPassword = passwordEncoder.encode(form.getPassword());
        userServiceImpl.updatePassword(updatedPassword, user.getIdUser());
        tokenRepository.delete(token);

        return "redirect:/ventas-online/login";
    }
	
}
