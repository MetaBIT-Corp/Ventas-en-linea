package com.metabit.ventasenlinea.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.ventasenlinea.entity.Cliente;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.entity.UserRole;
import com.metabit.ventasenlinea.repository.UserJpaRepository;
import com.metabit.ventasenlinea.service.impl.ClienteServiceImpl;
import com.metabit.ventasenlinea.service.impl.UserRoleServiceImpl;
import com.metabit.ventasenlinea.service.impl.UserServiceImpl;
import com.sun.mail.handlers.image_gif;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	@Qualifier("clienteServiceImpl")
	private ClienteServiceImpl clienteServiceImpl;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleServiceImpl userRoleServiceImpl;
	
	@GetMapping("/crear-cliente")
	public ModelAndView createCliente() {
		ModelAndView mav = new ModelAndView("cliente/crearCliente");
		
		mav.addObject("user", new User());
		mav.addObject("cliente", new Cliente());
		
		return mav;
	}
	
	@PostMapping("/crear-cliente")
	public String storeCliente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingClient ,@Valid @ModelAttribute("user") User user, BindingResult bindingUser, RedirectAttributes redirAttrs) {
		
		User exisUser = userServiceImpl.findByEmail(user.getEmail());
		
		if(bindingUser.hasErrors() || bindingClient.hasErrors()) {
			return "cliente/crearCliente";
		}else {
			String codigo = codigo();
			String asunto = "Ventas en linea";
			String contenido = "Estimado/a "+cliente.getNombreCliente()+
								":\n\nHemos recibido una solicitud para crear una cuenta en nuestro sistema "+
								"a través su dirección de correo electrónico."+
								"\n\n Su código de verificación es: " + codigo;
			
			if(!user.getPassword().equals(user.getPasswordConfirm()) || user.getPassword() == "") {
				redirAttrs.addFlashAttribute("message", "Las contraseñas no coinciden");
				return "redirect:/cliente/crear-cliente";
			}
			
			if(exisUser != null) {
				redirAttrs.addFlashAttribute("message", "Ya existe una cuenta con este correo");
				return "redirect:/cliente/crear-cliente";
			}
			
			user.setCodigoVerificacion(codigo);
			//user.setRole(3);
			user.setVerifyed(0);
			//user.setPassword();
			BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			user.setPassword(pe.encode(user.getPassword())); 
			
			cliente.setUser(user);
			
			userServiceImpl.createUser(user);
			clienteServiceImpl.createCliente(cliente);
			userRoleServiceImpl.createUserRole(new UserRole(user, "ROLE_CLIENTE"));
			
			sendEmail(user.getEmail(), asunto, contenido);
			redirAttrs.addFlashAttribute("success", "succeess");
			
			return "redirect:/cliente/verificar-codigo/"+user.getIdUser();
		}
	}
	
	@GetMapping("/verificar-codigo/{id}")
	public ModelAndView verficarCodigoGET(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView("cliente/verificarCodigo");
		User user = userServiceImpl.findById(id);
		mav.addObject("user", user);
		
		return mav;
	}
	
	@PostMapping("/verificar-codigo")
	public String verificarCodigoPost(@RequestParam("codigo") String codigo, @RequestParam("id_user") int id_user, RedirectAttributes redirAttrs) {
		String asunto = "Ventas en linea";
		String contenido = "Enhorabuena, ya puedes hacer uso de tu cuenta en nuestro sistema de Ventas en línea";
		User user = userServiceImpl.findById(id_user);
		
		if(user.getCodigoVerificacion().equals(codigo)) {
			user.setVerifyed(1);
			userServiceImpl.createUser(user);
			sendEmail(user.getEmail(), asunto, contenido);
			return "redirect:/ventas-online/login";
		}else {
			redirAttrs.addFlashAttribute("message", "El código ingresado es incorrecto");
			return "redirect:/cliente/verificar-codigo/"+id_user;
		}
	}
	
	public String codigo() {
		String codigo = "";
		char[] numeros = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		
		for (int i = 0; i < 6; i++) {
			codigo += numeros[(int)(Math.random() * (10))];
		}
		
		return codigo;
	}
	
	//Inyeccion de la dependencia:
    @Autowired
    private JavaMailSender mailSender;

    //Pasar por parametros: destinatario, asunto y el mensaje
    public void sendEmail(String to, String subject, String content) {

        SimpleMailMessage email = new SimpleMailMessage();
        //Aqui podes Modificarlo por defectp te lo dejo asi
        //NombreEmpresa <ejemplo@ejemplo.com>
        email.setFrom("Empresa de Venta <ddjochoa.20@gmail.com>");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);       
        mailSender.send(email);
    }
}
