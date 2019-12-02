INSERT INTO departamentos (id_departamento, descripcion_departamento, habilitado, nombre_departamento) VALUES
(1, 'Descripción del departamento', 1, 'Juegos');

INSERT INTO categorias (id_categoria, descripcion_categoria, habilitado, nombre_categoria, id_departamento) VALUES
(1, 'Descripción de la categoría', 1, 'Juegos', 1);

INSERT INTO subcategorias (id_subcategoria, descripcion_subcategoria, habilitado, nombre_subcategoria, categoria_id_categoria) VALUES
(1, 'Descripción de la subcategoría', 1, 'Juegos', 1);

INSERT INTO productos (id_articulo, descripcion_articulo, imagen, marca, margen_ganancia, porcentaje_descuento, titulo, habilitado, id_subcategoria) VALUES 
(1, 'Consola de videojuego que puede jugarse en modo portatil y modo Dock', '/img_products/nintendo.jpg', 'Nintento', 20, 0.0, 'Nintendo Switch', 1, 1);
INSERT INTO productos (id_articulo, descripcion_articulo, imagen, marca, margen_ganancia, porcentaje_descuento, titulo, habilitado, id_subcategoria) VALUES
(2, 'Consola de video juego con potencia para correr juegos en 4K', '/img_products/play.jpg','Sony', 20, 0.0, 'Play Station 5', 1, 1);
INSERT INTO productos (id_articulo, descripcion_articulo, imagen, marca, margen_ganancia, porcentaje_descuento, titulo, habilitado, id_subcategoria) VALUES
(3, 'Consola con potencia para 4K, microprocesador Scorpio con 100teraflops', '/img_products/xbox.jpg', 'Microsoft', 20, 0.0, 'Xbox One S', 0, 1);

INSERT INTO kardex (id_kardex, costo_unitario, stock_maximo, stock_minimo, unidades_disponibles, producto_id_articulo) VALUES
(1, 50, 20, 1, 20, 1);
INSERT INTO kardex (id_kardex, costo_unitario, stock_maximo, stock_minimo, unidades_disponibles, producto_id_articulo) VALUES
(2, 60, 30, 1, 25, 2);
INSERT INTO kardex (id_kardex, costo_unitario, stock_maximo, stock_minimo, unidades_disponibles, producto_id_articulo) VALUES
(3, 70, 40, 1, 30, 3);

INSERT INTO cargos (id, titulo_cargo) VALUES 
(1,'Ventas');
INSERT INTO cargos (id, titulo_cargo) VALUES 
(2,'Bodega');

INSERT INTO users (id_user, codigo_verificacion, email, password, verifyed) VALUES
(1, '111111', 'ventas@gmail.com', '$2a$10$eKUggP4YYTN6sy1ftSa4K.7UshfN1gEqDuHvV1i45WUKfaaojMSW.', 1);
INSERT INTO users (id_user, codigo_verificacion, email, password, verifyed) VALUES
(2, '111111', 'admin@gmail.com', '$2a$10$eKUggP4YYTN6sy1ftSa4K.7UshfN1gEqDuHvV1i45WUKfaaojMSW.', 1);
INSERT INTO users (id_user, codigo_verificacion, email, password, verifyed) VALUES
(3, '111111', 'bodega@gmail.com', '$2a$10$eKUggP4YYTN6sy1ftSa4K.7UshfN1gEqDuHvV1i45WUKfaaojMSW.', 1);

INSERT INTO empleados(id_empleado, apellido_empleado, direccion, nombre_empleado, id_cargo, user_id_user) VALUES 
(1,'Ochoa','SS','Diego de jesus',1,1);
INSERT INTO empleados(id_empleado, apellido_empleado, direccion, nombre_empleado, id_cargo, user_id_user) VALUES 
(2,'Colato','SS','Juan Pablo',2,3);

INSERT INTO user_roles(user_role_id, role, id_user) VALUES
(1, 'ROLE_VENTAS', 1);
INSERT INTO user_roles(user_role_id, role, id_user) VALUES
(2, 'ROLE_ADMIN', 2);
INSERT INTO user_roles(user_role_id, role, id_user) VALUES
(3, 'ROLE_BODEGA', 3);



INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(1, 0.0846251, 0.0784816, 'Antigua Y Barbuda');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(2, 0.21605, 0.194563, 'Argentina');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(3, 0.405416, 0.501079, 'Bahamas');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(4, 0.493686, 0.558421, 'Barbados');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(5, 0.628763, 0.165888, 'Belice');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(6, 0.616998, 0.170969, 'Bolivia');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(7, 0.836926, 0.258227, 'Brasil');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(8, 0.141796, 0.938358, 'Cánada');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(9, 0.064444, 0.886919, 'Chile');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(10, 0.2322, 0.112085, 'Colombia');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(11, 0.998573, 0.436029, 'Costa Rica');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(12, 0.215951, 0.870128, 'Cuba');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(13, 0.888554, 0.455852, 'Dominicana');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(14, 0.252129, 0.477267, 'Ecuador');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(15, 0.986744, 0.964274, 'El Salvador');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(16, 0.657456, 0.575889, 'Estados Unidos');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(17, 0.73624, 0.147808, 'Granada');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(18, 0.344591, 0.917374, 'Guatemala');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(19, 0.0924722, 0.100991, 'Guyana');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(20, 0.51428, 0.634176, 'Haití');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(21, 0.820358, 0.423528, 'Honduras');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(22, 0.212968, 0.974034, 'Jamaica');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(23, 0.343943, 0.206438, 'México');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(24, 0.618464, 0.270254, 'Nicaragua');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(25, 0.742073, 0.181672, 'Pánama');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(26, 0.94845, 0.80582, 'Paraguay');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(27, 0.781479, 0.584046, 'Perú');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(28, 0.700116, 0.144103, 'República Dominicana');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(29, 0.45777, 0.580013, 'San Cristóbal y Nieves');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(30, 0.523949, 0.44526, 'San Vicente y las Granadinas');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(31, 0.375169, 0.954722, 'Santa Lucia');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(32, 0.410637, 0.264921, 'Surinam');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(33, 0.437439, 0.116949, 'Trinidad y Tobago');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(34, 0.027319, 0.34057, 'Uruguay');
INSERT INTO paises (id_pais, costo_envio, impuesto, nombre_pais) VALUES
(35, 0.745289, 0.380926, 'Venezuela');

INSERT INTO estados(idEstado, titulo_estado) VALUES 
(1,'Enviado');
INSERT INTO estados(idEstado, titulo_estado) VALUES 
(2,'Pendiente');
INSERT INTO estados(idEstado, titulo_estado) VALUES 
(3,'Autorizado');
INSERT INTO estados(idEstado, titulo_estado) VALUES 
(4,'Solicitado');
