package com.metabit.ventasenlinea.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.Empleado;
import com.metabit.ventasenlinea.entity.Mail;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.entity.PasswordResetToken;
import com.metabit.ventasenlinea.controller.dto.PasswordForgotDto;
import com.metabit.ventasenlinea.repository.PasswordResetTokenRepository;
import com.metabit.ventasenlinea.service.ClienteService;
import com.metabit.ventasenlinea.service.EmpleadoService;
import com.metabit.ventasenlinea.service.impl.ClienteServiceImpl;
import com.metabit.ventasenlinea.service.impl.EmailServiceImpl;
import com.metabit.ventasenlinea.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {
	private static final Log LOG= LogFactory.getLog(PasswordForgotController.class);
	@Autowired
	@Qualifier("clienteServiceImpl")
	private ClienteService clienteService;
	
	@Autowired
	@Qualifier("empleadoServiceImpl")
	private EmpleadoService empleadoService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	@Autowired
	private PasswordResetTokenRepository tokenRepository;
	@Autowired
	@Qualifier("emailServiceImpl")
	private EmailServiceImpl emailServiceImpl;
	
	@ModelAttribute("forgotPasswordForm")
    public PasswordForgotDto forgotPasswordDto() {
        return new PasswordForgotDto();
    }
	@GetMapping
    public String displayForgotPasswordPage() {
       
		return "forgot-password";
    }
	@PostMapping
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
                                            BindingResult result,
                                            HttpServletRequest request) {

        
		if (result.hasErrors()){			
            return "forgot-password";
        }
        

        User user = userServiceImpl.findByEmail(form.getEmail());
        if (user == null){
            result.rejectValue("email", null, "No pudimos encontrar una cuenta para esa dirección de correo electrónico.");
            return "forgot-password";
        }
        else {
        	PasswordResetToken token = new PasswordResetToken();
            //UUID Genera una cadena de 128bits(16bytes)
            token.setToken(UUID.randomUUID().toString());
            token.setUser(user);
            //30 minutos de Expiracion
            token.setExpiryDate(30);
            tokenRepository.save(token);
            Mail mail = new Mail();
            Map<String, Object> model = new HashMap<>();
        	Empleado empleado=empleadoService.buscarPorUser(user);
        	if(empleado!=null) {
        		empleado.setUser(user);
        		model.put("isEmpleado", 1);
        		model.put("empleado", empleado);
        		
        	}
        	Cliente cliente=clienteService.BuscarUsuario(user);
        	if(cliente!=null) {
        		cliente.setUser(user);
        		model.put("isCliente", 1);
        		 model.put("cliente", cliente);
        		 
        	}
        	mail.setFrom("Empresa de Venta <ddjochoa.20@gmail.com>");
            mail.setTo(user.getEmail());
            mail.setSubject("Recuperacion de Contraseña");            
            model.put("token", token);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
            mail.setModel(model);
            emailServiceImpl.sendEmail(mail);
            return "redirect:/forgot-password?success";
        }

    }
}
