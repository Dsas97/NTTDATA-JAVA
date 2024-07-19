
CREATE DATABASE manage_cuentas;


CREATE TABLE manage_cuentas.dbo.estado_cuenta (
	id int IDENTITY(1,1) NOT NULL,
	nombre varchar(100) NULL,
	CONSTRAINT estado_cuenta_pk PRIMARY KEY (id)
);


INSERT INTO manage_cuentas.dbo.estado_cuenta (nombre)
	VALUES (N'Activa'),
	(N'Inactiva'),
	(N'Bloqueada'),
	(N'Cerrada'),
	(N'En revisión'),
	(N'Pendiente de aprobación'),
	(N'Suspendida temporalmente');


CREATE TABLE manage_cuentas.dbo.tipo_movimiento (
	id int IDENTITY(1,1) NOT NULL,
	nombre varchar(100) NULL,
	es_ingreso bit,
	CONSTRAINT tipo_movimiento_pk PRIMARY KEY (id)
);

CREATE TABLE manage_cuentas.dbo.tipo_cuenta (
	id int IDENTITY(1,1) NOT NULL,
	nombre varchar(100) NULL,
	CONSTRAINT tipo_cuenta_pk PRIMARY KEY (id)
);


INSERT INTO manage_cuentas.dbo.tipo_cuenta (nombre)
	VALUES (N'ahorros');
INSERT INTO manage_cuentas.dbo.tipo_cuenta (nombre)
	VALUES (N'crédito');
INSERT INTO manage_cuentas.dbo.tipo_cuenta (nombre)
	VALUES (N'corriente');


INSERT INTO manage_cuentas.dbo.tipo_movimiento (nombre, es_ingreso)
	VALUES (N'Depósito',1),
	(N'Retiro',0),
	(N'Transferencia recepcionada', 1),
	(N'Transferencia enviada',0);

CREATE TABLE manage_cuentas.dbo.estado_cliente (
	id int IDENTITY(1,1) NOT NULL,
	nombre varchar(100) NULL,
	CONSTRAINT estado_cliente_pk PRIMARY KEY (id)
);

INSERT INTO manage_cuentas.dbo.estado_cliente (nombre)
	VALUES (N'activo'),
	(N'inactivo'),
	(N'bloqueado'),
	(N'pendiente aprobación'),
	(N'cancelado');

CREATE TABLE manage_cuentas.dbo.persona (
	id bigint IDENTITY(1,1) NOT NULL,
	nombre varchar(255) NULL,
	genero varchar(100) NULL,
	edad int NULL,
	identificacion varchar(20) NULL,
	direccion varchar(255) NULL,
	telefono varchar(10) NULL,
	CONSTRAINT persona_pk PRIMARY KEY (id)
);

CREATE TABLE manage_cuentas.dbo.cliente (
	id bigint IDENTITY(1,1) NOT NULL,
	password varchar(200) NULL,
	id_persona bigint NOT NULL,
	id_estado_cliente int not null
	CONSTRAINT cliente_pk PRIMARY KEY (id),
	CONSTRAINT cliente_persona_FK FOREIGN KEY (id_persona) REFERENCES manage_cuentas.dbo.persona(id),
	CONSTRAINT cliente_estado_cliente_FK FOREIGN KEY (id_estado_cliente) REFERENCES manage_cuentas.dbo.estado_cliente(id)
);


CREATE TABLE manage_cuentas.dbo.cuenta (
	id bigint IDENTITY(1,1) NOT NULL,
	id_cliente bigint NOT NULL,
	numero_cuenta varchar(100) NULL,
	id_tipo_cuenta int NOT NULL,
	saldo_inicial numeric(17,2) NULL,
	id_estado_cuenta int NOT NULL,
	CONSTRAINT cuenta_pk PRIMARY KEY (id),
	CONSTRAINT cuenta_unique UNIQUE (id_cliente),
	CONSTRAINT cuenta_cliente_FK FOREIGN KEY (id_cliente) REFERENCES manage_cuentas.dbo.cliente(id),
	CONSTRAINT cuenta_tipo_cuenta_FK FOREIGN KEY (id_tipo_cuenta) REFERENCES manage_cuentas.dbo.tipo_cuenta(id),
	CONSTRAINT cuenta_estado_cuenta_FK FOREIGN KEY (id_estado_cuenta) REFERENCES manage_cuentas.dbo.estado_cuenta(id)
);


CREATE TABLE manage_cuentas.dbo.movimiento (
	id bigint IDENTITY(1,1) NOT NULL,
	id_cuenta bigint NOT NULL,
	fecha datetime2(0) NULL,
	id_tipo_movimiento int NOT NULL,
	valor numeric(17,2) NULL,
	saldo numeric(17,2) NULL,
	estado bit DEFAULT 1,
	CONSTRAINT movimiento_pk PRIMARY KEY (id)
);

