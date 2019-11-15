package com.metabit.ventasenlinea.controller;

import javax.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

import com.metabit.ventasenlinea.entity.Cargo;
import com.metabit.ventasenlinea.entity.Empleado;
import com.metabit.ventasenlinea.entity.User;
import com.metabit.ventasenlinea.entity.UserRole;
import com.metabit.ventasenlinea.service.CargoService;
import com.metabit.ventasenlinea.service.EmpleadoService;
import com.metabit.ventasenlinea.service.UserRoleService;
import com.metabit.ventasenlinea.service.UserService;



@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
	private static final Log LOG= LogFactory.getLog(EmpleadoController.class);
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	@Autowired
	@Qualifier("cargoServiceImpl")
	private CargoService cargoService;
	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;
	
	@Autowired
	@Qualifier("empleadoServiceImpl")
	private EmpleadoService empleadoService;
	//Bloque de codigo para crear uno de ventas
	@GetMapping("/crear-empleado-venta")
	public ModelAndView createEmpleadoVenta()
	{
		LOG.info("Si esta pasando por este get al cargar la vista "+codigo(6));
		ModelAndView mav = new ModelAndView("empleado/crearEmpleadoV");		
		mav.addObject("user", new User());
		mav.addObject("empleado", new Empleado());
		//mav.addObject("cargos",cargoService.getCargos());
		
		return mav;
	}
	
	@PostMapping("/crear-empleado-venta")
	public String saveEmpleadoVenta(@Valid @ModelAttribute("empleado") Empleado empleado,BindingResult bindingEmpleado,
			 @ModelAttribute("user") User user,
			 RedirectAttributes redirAttrs) {
		LOG.info("Si esta pasando por este POST al cargar la vista "+user.toString()+" fdf: "+empleado.toString()+" Cargo: "+cargoService.buscarPorId(1));
		User exisUser = userService.findByEmail(user.getEmail());
		user.setCodigoVerificacion(codigo(6));
		user.setPassword(user.getCodigoVerificacion());
		user.setPasswordConfirm(user.getCodigoVerificacion());
		
		if(bindingEmpleado.hasErrors()) {
			
			return "empleado/crearEmpleadoV";
		}
		else {
			
			if(exisUser != null) {
				LOG.info("Entra aqui a email existente");
				redirAttrs.addFlashAttribute("message", "Ya existe una cuenta con este correo");
				return "redirect:/empleado/crear-empleado-venta";
			}			
			Cargo cargo=new Cargo();
			cargo=cargoService.buscarPorId(1);
			//Guardando Cargo
			empleado.setCargo(cargo);
			//Usuario ya verificado
			user.setVerifyed(1);
			//Coloca las 2 contraseñas
			user.setPassword(user.getCodigoVerificacion());
			user.setPasswordConfirm(user.getCodigoVerificacion());
			BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			user.setPassword(pe.encode(user.getPassword()));
			//Asigna el usuario al empleado
			empleado.setUser(user);
			//guardar el rol del nuevo empleado			
			LOG.info("Si esta pasando por este POST al cargar la vista "+empleado.toString()+" USer:"+user.toString()+" Cargo: "+cargo.toString());
			//Guardado de Forma Logica xD
			userService.createUser(user);
			empleadoService.crearEmpleado(empleado);
			userRoleService.createUserRole(new UserRole(user, "ROLE_VENTAS"));
			//Envio del correo electronico
			String asunto="Empleado<Ventas>";
			String content="Hola "+empleado.getNombreEmpleado()+" "+empleado.getApellidoEmpleado()+
							"\nPor este medio te damos la Bienvenida a nuestra empresa de ventas online"+
							"\nSu Cuenta de usuario es la siguiente: "+
							"\nUsuario: "+user.getEmail()+
							"\nContraseña: "+user.getCodigoVerificacion();
			sendEmail(user.getEmail(),asunto,content);
			return "redirect:/usuario/listar";
		}
		
		
	}
	
	//Bloque de codigo para crear uno de bodega
		@GetMapping("/crear-empleado-bodega")
		public ModelAndView createEmpleado()
		{
			LOG.info("Si esta pasando por este get al cargar la vista "+codigo(6));
			ModelAndView mav = new ModelAndView("empleado/crearEmpleadoB");		
			mav.addObject("user", new User());
			mav.addObject("empleado", new Empleado());
			//mav.addObject("cargos",cargoService.getCargos());
			
			return mav;
		}
		
		@PostMapping("/crear-empleado-bodega")
		public String saveEmpleado(@Valid @ModelAttribute("empleado") Empleado empleado,BindingResult bindingEmpleado,
				 @ModelAttribute("user") User user,
				 RedirectAttributes redirAttrs) {
			LOG.info("Si esta pasando por este POST al cargar la vista "+user.toString()+" fdf: "+empleado.toString()+" Cargo: "+cargoService.buscarPorId(1));
			User exisUser = userService.findByEmail(user.getEmail());
			user.setCodigoVerificacion(codigo(6));
			user.setPassword(user.getCodigoVerificacion());
			user.setPasswordConfirm(user.getCodigoVerificacion());
			
			if(bindingEmpleado.hasErrors()) {
				
				return "empleado/crearEmpleadoB";
			}
			else {
				
				if(exisUser != null) {
					LOG.info("Entra aqui a email existente");
					redirAttrs.addFlashAttribute("message", "Ya existe una cuenta con este correo");
					return "redirect:/empleado/crear-empleado-venta";
				}			
				Cargo cargo=new Cargo();
				cargo=cargoService.buscarPorId(2);
				//Guardando Cargo
				empleado.setCargo(cargo);
				//Usuario ya verificado
				user.setVerifyed(1);
				//Coloca las 2 contraseñas
				user.setPassword(user.getCodigoVerificacion());
				user.setPasswordConfirm(user.getCodigoVerificacion());
				BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
				user.setPassword(pe.encode(user.getPassword()));
				//Asigna el usuario al empleado
				empleado.setUser(user);
				//guardar el rol del nuevo empleado			
				LOG.info("Si esta pasando por este POST al cargar la vista "+empleado.toString()+" USer:"+user.toString()+" Cargo: "+cargo.toString());
				//Guardado de Forma Logica xD
				userService.createUser(user);
				empleadoService.crearEmpleado(empleado);
				userRoleService.createUserRole(new UserRole(user, "ROLE_BODEGA"));
				
				//Envio del correo electronico
				String asunto="Empleado<Bodega>";
				String content="Hola "+empleado.getNombreEmpleado()+" "+empleado.getApellidoEmpleado()+
								"\nPor este medio te damos la Bienvenida a nuestra empresa de ventas online"+
								"\nSu Cuenta de usuario es la siguiente: "+
								"\nUsuario: "+user.getEmail()+
								"\nContraseña: "+user.getCodigoVerificacion();
				sendEmail(user.getEmail(),asunto,content);		
				return "redirect:/usuario/listar";
			}
			
			
		}
	@GetMapping("/edit-empleado2")
	public String editEmpleado(@RequestParam(name="id",required=false,defaultValue="0")int id,Model model) {
		Empleado empleado=new Empleado();
		empleado=empleadoService.buscarPorID(id);
		LOG.info("Este es el objeto de tipo empleado: "+empleado);
		model.addAttribute("empleado", empleado);
		return "empleado/edit-empleado";
	}
	@GetMapping("/edit-empleado/{id}")
	public ModelAndView verficarCodigoGET(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView("empleado/edit-empleado");
		Empleado empleado=new Empleado();
		empleado=empleadoService.buscarPorID(id);
		//LOG.info("Este es el objeto de tipo empleado: "+empleado.toString());
		mav.addObject("empleado", empleado);		
		return mav;
	}
	@PostMapping("/editempleado")
	public String addEmpleado(@Valid @ModelAttribute("empleado") Empleado empleado,BindingResult bindingEmpleado,RedirectAttributes redirAttrs) {
		if(bindingEmpleado.hasErrors()) {
			LOG.info("Entra aqui a email existente");
			return "empleado/edit-empleado";
		}
		else {
			//empleadoService.crearEmpleado(empleado);
			Empleado updateEmpleado=new Empleado();
			updateEmpleado=empleadoService.buscarPorID(empleado.getIdEmpleado());		
			updateEmpleado.setNombreEmpleado(empleado.getNombreEmpleado());
			updateEmpleado.setApellidoEmpleado(empleado.getApellidoEmpleado());
			updateEmpleado.setDireccion(empleado.getDireccion());
			empleadoService.crearEmpleado(updateEmpleado);
			return "redirect:/usuario/listar";
		}
		
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
		
		
		return codigo;
	}
}
