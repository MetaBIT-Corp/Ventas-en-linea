INSERT INTO productos (id_articulo, descripcion_articulo, imagen, marca, margen_ganancia, porcentaje_descuento, titulo, habilitado) VALUES 
(1, "Consola de videojuego que puede jugarse en modo portatil y modo Dock", "/img_products/nintendo.jpg", "Nintento", 20, 0.0, "Nintendo Switch", 1),
(2, "Consola de video juego con potencia para correr juegos en 4K", "/img_products/play.jpg","Sony", 20, 0.0, "Play Station 5", 1),
(3, "Consola con potencia para 4K, microprocesador Scorpio con 100teraflops", "/img_products/xbox.jpg", "Microsoft", 20, 0.0, "Xbox One S", 0);

INSERT INTO users (id_user, codigo_verificacion, email, password, verifyed) VALUES
(1, "111111", "ventas@gmail.com", "$2a$10$eKUggP4YYTN6sy1ftSa4K.7UshfN1gEqDuHvV1i45WUKfaaojMSW.", 1),
(2, "111111", "admin@gmail.com", "$2a$10$eKUggP4YYTN6sy1ftSa4K.7UshfN1gEqDuHvV1i45WUKfaaojMSW.", 1);

INSERT INTO clientes (id_cliente, apellido_cliente, direccion, nombre_cliente, user_id_user) VALUES
(1, "Amaya Palacios", "en su casa", "Edwin Joel", 1);
(2, "Ocho García", "en su casa", "Diego de Jesús", 2),

INSERT INTO user_roles(user_role_id, role, id_user) VALUES
(1, "ROLE_VENTAS", 1)
(2, "ROLE_ADMIN", 2)


INSERT INTO `cargos`(`id`, `titulo_cargo`) VALUES (1,"Ventas"),(2,"Bodega");
