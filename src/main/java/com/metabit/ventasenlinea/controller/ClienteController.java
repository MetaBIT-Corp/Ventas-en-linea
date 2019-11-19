package com.metabit.ventasenlinea.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.metabit.ventasenlinea.entity.Cuenta;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.entity.UserRole;
import com.metabit.ventasenlinea.repository.UserJpaRepository;
import com.metabit.ventasenlinea.service.impl.ClienteServiceImpl;
import com.metabit.ventasenlinea.service.impl.CuentaServiceImpl;
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
	@Qualifier("cuentaServiceImpl")
	private CuentaServiceImpl cuentaServiceImpl;
	
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
			String codigo = codigo(6);
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
			
			//Creación de cuenta bancaria
			Cuenta cuenta = new Cuenta();
			
			int codigo_cuenta = Integer.parseInt(codigo(3));
			cuenta.setCodigo(codigo_cuenta);
			
			cuenta.setNumeroTarjeta(codigo(16));
			
			Random r = new Random();
		    double saldo = 1000 + (10000 - 1000) * r.nextDouble();
		    saldo = round(saldo, 2);
		    cuenta.setSaldo(saldo);
			
		    cuenta.setCliente(cliente);
		    
		   Date d1 = null;
			try {
				d1 = new SimpleDateFormat("dd/MM/yyyy").parse("1/01/2020");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Date d2 = null;
			try {
				d2 = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2020");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			Date randomDate = null;
			try {
				randomDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2020");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(d1 != null && d2 != null)
				randomDate = new Date(ThreadLocalRandom.current().nextLong(d1.getTime(), d2.getTime()));
		    
			cuenta.setFechaDeVencimiento(randomDate);
			
		    System.out.println("FECHA: " + cuenta.getFechaDeVencimiento().toString());
		    
		    cuentaServiceImpl.createCuenta(cuenta);
		    
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
	@GetMapping("/updateCliente")
	public String updateCliente(Model model) {		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		User user2=userServiceImpl.findByEmail(userDetail.getUsername());
		Cliente cliente=clienteServiceImpl.BuscarUsuario(user2);
		model.addAttribute("cliente", cliente);
		model.addAttribute("user2", user2);
		model.addAttribute("user", userDetail.getUsername());
		model.addAttribute("role",userDetail.getAuthorities().toArray()[0].toString());
		
		return "cliente/updateCliente";
	}
	@PostMapping("/updateCuenta")
	public String updateCuenta(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingClient,
								@Valid @ModelAttribute("user2") User user2, BindingResult bindingUser,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();		
		User exisUser = userServiceImpl.findByEmail(userDetail.getUsername());
		System.out.print("Esta pasando aqui"+cliente.toString());
		if( bindingClient.hasErrors()) {
			System.out.print("\nEsta pasando aqui Errores");
			return "cliente/updateCliente";
		}
		else {			
			
			Cliente clienteA=clienteServiceImpl.BuscarUsuario(exisUser);
			clienteA.setNombreCliente(cliente.getNombreCliente());
			clienteA.setApellidoCliente(cliente.getApellidoCliente());
			clienteA.setDireccion(cliente.getDireccion());
			clienteServiceImpl.createCliente(clienteA);
			return "redirect:/ventas-online/base";			
			
		}
		
	}
	public String codigo(int required) {
		String codigo = "";
		char[] numeros = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		
		int j = 0;
		int cant = 0;
		
		for (int i = 0; i < required; i++) {
			
			j++;
			
			if(required == 16 && j == 4 && cant < 3) {
				codigo += numeros[(int)(Math.random() * (10))]+"-";
				cant++;
				j = 0;
			}	
			else
				codigo += numeros[(int)(Math.random() * (10))];
			
		}
		System.out.println("AQUIIIIIII: "+codigo);
		//primero de 6
		//luego de 3
		//luego de 12 con formato de num de tarj
		
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
        email.setFrom("Empresa de Venta <ventasonline19@gmail.com>");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);       
        mailSender.send(email);
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
