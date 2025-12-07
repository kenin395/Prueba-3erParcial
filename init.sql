CREATE TABLE IF NOT EXISTS paciente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    edad INT NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    cedula VARCHAR(10) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO paciente (nombre, correo, edad, direccion, cedula, activo) VALUES
('Juan Pérez', 'juan@example.com', 30, 'Av. Siempre Viva 123', '0102030405', TRUE)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);
