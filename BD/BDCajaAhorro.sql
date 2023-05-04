DROP DATABASE IF EXISTS caja_ahorro;

CREATE DATABASE caja_ahorro;

USE caja_ahorro;

CREATE TABLE cliente(
	idCliente				INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre					VARCHAR(50),
    fechaNacimiento			VARCHAR(50),
    genero					CHAR, -- M, F, O
    numeroHijos				INT,
    ingresoMensual			DOUBLE
);

CREATE TABLE presupuestoCredito(
	idPresupuestoCredito	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    montoCredito			DOUBLE,
    resultadoAnalisis		VARCHAR(40),
    idCliente				INT NOT NULL,
    CONSTRAINT fk_presupuestoCredito_idCliente FOREIGN KEY (idCliente) 
                REFERENCES cliente(idCliente)
);

CREATE TABLE creditoOtorgado(
	idCreditoOtorgado		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idPresupuestoCredito	INT,
    CONSTRAINT fk_creditoOtorgado_idPresupuestoCredito FOREIGN KEY (idPresupuestoCredito)
				REFERENCES presupuestoCredito(idPresupuestoCredito)
);

DROP PROCEDURE IF EXISTS insertarpresupuestoCredito;
DELIMITER $$
CREATE PROCEDURE insertarpresupuestoCredito(	IN	var_nombre					VARCHAR(50),	-- 1
												IN	var_fechaNacimiento			VARCHAR(50),	-- 2
												IN	var_genero					CHAR,			-- 3
												IN	var_numeroHijos				INT,			-- 4
												IN	var_ingresoMensual			DOUBLE,			-- 5

                                                IN	var_montoCredito			DOUBLE,			-- 6
												IN	var_resultadoAnalisis		VARCHAR(40),	-- 7

												OUT	var_idCliente				INT,			-- 8
												OUT	var_idPresupuestoCredito	INT				-- 9
											)
    BEGIN        
        INSERT INTO cliente(nombre, fechaNacimiento, genero, numeroHijos, ingresoMensual)
                    VALUES(var_nombre, var_fechaNacimiento, var_genero, var_numeroHijos, var_ingresoMensual);
        SET var_idCliente=LAST_INSERT_ID();
        
        INSERT INTO presupuestoCredito(montoCredito, resultadoAnalisis, idCliente)
					VALUES(var_montoCredito, var_resultadoAnalisis, var_idCliente);
		SET var_idPresupuestoCredito=LAST_INSERT_ID();
    END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarpresupuestoCredito;
DELIMITER $$
CREATE PROCEDURE actualizarpresupuestoCredito(	IN	var_nombre					VARCHAR(50),	-- 1
												IN	var_fechaNacimiento			VARCHAR(50),	-- 2
												IN	var_genero					CHAR,			-- 3
												IN	var_numeroHijos				INT,			-- 4
												IN	var_ingresoMensual			DOUBLE,			-- 5

                                                IN	var_montoCredito			DOUBLE,			-- 6
												IN	var_resultadoAnalisis		VARCHAR(40),	-- 7

												IN	var_idCliente				INT,			-- 8
												IN	var_idPresupuestoCredito	INT				-- 9
											)
    BEGIN        
        UPDATE cliente
		SET 	nombre=var_nombre, fechaNacimiento=var_fechaNacimiento, genero=var_genero,
				numeroHijos=var_numeroHijos, ingresoMensual=var_ingresoMensual
        WHERE	idCliente=var_idCliente;
        
        UPDATE presupuestoCredito
		SET 	montoCredito=var_montoCredito, resultadoAnalisis=var_resultadoAnalisis
		WHERE	idPresupuestoCredito=var_idPresupuestoCredito;
    END
$$
DELIMITER ;

CALL insertarpresupuestoCredito("Francisco Rocha", "02/10/2000", "M", 0, 3000.00, 100.00, "Aprobado", @out1, @out2);

CALL actualizarpresupuestoCredito("Alda Rocha", "02/10/2006", "F", 1, 5000.00, 50.00, "Rechazado", 1, 1);

INSERT INTO creditoOtorgado(idPresupuestoCredito) VALUES(1);

SELECT * FROM cliente WHERE nombre LIKE "%Francisco%";
SELECT * FROM presupuestoCredito pc INNER JOIN cliente c ON pc.idCliente=c.idCliente WHERE c.nombre LIKE "%%" ORDER BY pc.idPresupuestoCredito DESC;
SELECT * FROM creditoOtorgado co INNER JOIN presupuestoCredito pc INNER JOIN cliente c WHERE co.idPresupuestoCredito=pc.idPresupuestoCredito AND pc.idCliente=c.idCliente AND c.nombre LIKE "%%";