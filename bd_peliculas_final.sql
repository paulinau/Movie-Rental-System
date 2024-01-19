CREATE DATABASE pelicula;
USE pelicula;

CREATE TABLE `peliculas` (
  `nombre` varchar(20) PRIMARY KEY NOT NULL,
  `genero` varchar(10),
  `anio` int(4),
  `costo` double,
  `duracion` int(5)
);

CREATE TABLE `tarjetas` (
  `numerotarjeta` varchar(16) PRIMARY KEY NOT NULL,
  `saldo` double,
  `mes` varchar(5),
  `cvv` varchar(3),
  `titular` varchar(20)
);

CREATE TABLE `rentas` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `numerotarjeta` varchar(16),
  `nombrePelicula` varchar(20),
  `horaInicio` varchar(8),
  `tiempoRenta` int(5)
);

ALTER TABLE `rentas` ADD FOREIGN KEY (`numerotarjeta`) REFERENCES `tarjetas` (`numerotarjeta`);

ALTER TABLE `rentas` ADD FOREIGN KEY (`nombrePelicula`) REFERENCES `peliculas` (`nombre`);

INSERT INTO peliculas (nombre, genero, anio, costo, duracion)
VALUES ('avatar', 'accion', '2022', '100', '1000');
INSERT INTO peliculas (nombre, genero, anio, costo, duracion)
VALUES ('exorcista', 'terror', '2023', '50.50', '500');
INSERT INTO peliculas (nombre, genero, anio, costo, duracion)
VALUES ('titanic', 'romance', '1999', '80', '1200');

INSERT INTO tarjetas (numerotarjeta, saldo, mes, cvv, titular)
VALUES ('1234567890123456', '100', '12/23', '123', 'Diego');
INSERT INTO tarjetas (numerotarjeta, saldo, mes, cvv, titular)
VALUES ('1111111111111111', '900', '11/24', '124', 'Daniel');
INSERT INTO tarjetas (numerotarjeta, saldo, mes, cvv, titular)
VALUES ('2222222222222222', '60', '09/25', '125', 'Paulina');