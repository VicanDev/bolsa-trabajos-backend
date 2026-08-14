CREATE DATABASE bolsa_trabajos;

use bolsa_trabajos;

-- Insertar Usuarios (contra para todos "123456")
INSERT INTO usuarios (nombre, correo, password, rol) VALUES 
('Empresa Tech SAC', 'contacto@techsac.pe', '$2a$10$V3yKxe2vyknsLJ1Xjk2vIeAUaNzrOPzh2GjffcrdqfoHJrFqT21zu', 'EMPLEADOR'),
('Logistica Express', 'rrhh@logistica.pe', '$2a$10$V3yKxe2vyknsLJ1Xjk2vIeAUaNzrOPzh2GjffcrdqfoHJrFqT21zu', 'EMPLEADOR'),
('Cristiano Ronaldo', 'cr7.ronaldo@gmail.com', '$2a$10$V3yKxe2vyknsLJ1Xjk2vIeAUaNzrOPzh2GjffcrdqfoHJrFqT21zu', 'POSTULANTE'),
('Maria Rodriguez', 'maria.rodriguez@hotmail.com', '$2a$10$V3yKxe2vyknsLJ1Xjk2vIeAUaNzrOPzh2GjffcrdqfoHJrFqT21zu', 'POSTULANTE');

-- Insertar Categorias
INSERT INTO categorias (nombre) VALUES 
('Tecnologia y Sistemas'),
('Atencion al Cliente'),
('Logistica y Almacen'),
('Ventas y Marketing');

-- Insertar Empleadores 
INSERT INTO empleadores (id_usuario, razon_social, ruc, descripcion) VALUES 
(1, 'Tech Solutions SAC', '20123456789', 'Empresa lider en desarrollo de software y consultoria IT.'),
(2, 'Logistica Express Peru', '20987654321', 'Empresa de transporte y envios a nivel nacional.');

-- 4. Insertar Postulantes 
INSERT INTO postulantes (id_usuario, cv_url, habilidades, disponibilidad) VALUES 
(3, 'https://linkedin.com/in/cr7', 'Java, Spring Boot, Angular, MySQL', 'Tiempo Completo'),
(4, 'https://linkedin.com/in/mariarodriguez', 'Atencion al cliente, Office, Ingles Avanzado', 'Medio Tiempo');

-- 5. Insertar Ofertas
INSERT INTO ofertas (id_empleador, id_categoria, titulo, descripcion, ubicacion, duracion, requisitos, fecha_publicacion, estado) VALUES 
(1, 1, 'Desarrollador Java Junior', 'Buscamos un programador Java con ganas de aprender y participar en proyectos innovadores.', 'Lima, Peru (Remoto)', 'Plazo Indeterminado', 'Experiencia en Java 17, Spring Boot, bases de datos SQL.', '2026-08-14', 'ACTIVA'),
(1, 1, 'Practicante de Frontend (Angular)', 'Unete a nuestro equipo agil para desarrollar interfaces web modernas.', 'Lima, Peru', '6 meses', 'Estudiante de ultimos ciclos, conocimientos de Angular y TypeScript.', '2026-08-14', 'ACTIVA'),
(2, 3, 'Asistente de Almacen', 'Se requiere asistente para control de inventarios y despacho de mercaderia.', 'Callao, Peru', '1 año', 'Secundaria completa, experiencia de 6 meses en almacenes.', '2026-08-14', 'ACTIVA'),
(2, 2, 'Ejecutivo de Atencion al Cliente', 'Atencion de reclamos y consultas via call center.', 'Miraflores, Lima', 'Tiempo Completo', 'Habilidades de comunicacion, empatia, manejo de quejas.', '2026-08-14', 'ACTIVA');

-- 6. Insertar Postulaciones (Juan y Maria postulan)
INSERT INTO postulaciones (id_oferta, id_postulante, fecha_postulacion, estado) VALUES 
(1, 1, '2026-08-14', 'EN REVISION'),
(2, 1, '2026-08-14', 'PENDIENTE'),
(3, 2, '2026-08-14', 'PENDIENTE'),
(4, 2, '2026-08-14', 'RECHAZADO');
