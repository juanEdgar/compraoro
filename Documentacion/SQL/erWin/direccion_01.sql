--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;





SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: Calle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "calle" (
    "fiidcalle" integer NOT NULL,
    "fcnombre" character varying(255) NOT NULL,
    "fkcolonia" integer NOT NULL
);


--
-- Name: CodigoPostal; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "codigopostal" (
    "fiidcodigopostal" integer NOT NULL,
    "fccodigopostal" character(8) NOT NULL,
    "fkmunicipio" integer NOT NULL
);


--
-- Name: Colonia; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "colonia" (
    "fiidcolonia" integer NOT NULL,
    "fcnombre" character varying(255) NOT NULL,
    "fkmunicipio" integer NOT NULL
);




CREATE TABLE "direccion" (
    "fiiddireccion" integer NOT NULL,
    "fcnumerointerior" character(8),
    "fcnumeroexterior" character(8) NOT NULL,
    "fcusuariomodifico" character varying(20),
    "fdfechamodificacion" timestamp without time zone,
    "fkcodigopostal" integer NOT NULL,
    "fkcalle" integer NOT NULL,
    "fkcolonia" integer NOT NULL
);



CREATE TABLE "estado" (
    "fiidestado" integer NOT NULL,
    "fcnombre" character varying(128) NOT NULL
);



CREATE TABLE "municipio" (
    "fiidmunicipio" integer NOT NULL,
    "fcnombre" character varying(255) NOT NULL,
    "fkestado" integer
);


ALTER TABLE ONLY "calle"
    ADD CONSTRAINT "calle_pkey" PRIMARY KEY ("fiidcalle");




ALTER TABLE ONLY "codigopostal"
    ADD CONSTRAINT "codigopostal_pkey" PRIMARY KEY ("fiidcodigopostal");



ALTER TABLE ONLY "colonia"
    ADD CONSTRAINT "colonia_pkey" PRIMARY KEY ("fiidcolonia");


ALTER TABLE ONLY "direccion"
    ADD CONSTRAINT "direccion_pkey" PRIMARY KEY ("fiiddireccion");



ALTER TABLE ONLY "estado"
    ADD CONSTRAINT "estado_pkey" PRIMARY KEY ("fiidestado");

ALTER TABLE ONLY "municipio"
    ADD CONSTRAINT "municipio_pkey" PRIMARY KEY ("fiidmunicipio");

ALTER TABLE ONLY "codigopostal"
    ADD CONSTRAINT "fk_cpmunicipio" FOREIGN KEY ("fkmunicipio") REFERENCES "municipio"("fiidmunicipio");


ALTER TABLE ONLY "direccion"
    ADD CONSTRAINT "fk_Calle" FOREIGN KEY ("fkcalle") REFERENCES "calle"("fiidcalle");



ALTER TABLE ONLY "direccion"
    ADD CONSTRAINT "fk_codigopostal" FOREIGN KEY ("fkcodigopostal") REFERENCES "codigopostal"("fiidcodigopostal");


ALTER TABLE ONLY "calle"
    ADD CONSTRAINT "fk_colonia" FOREIGN KEY ("fkcolonia") REFERENCES "colonia"("fiidcolonia");


ALTER TABLE ONLY "municipio"
    ADD CONSTRAINT "fk_estado" FOREIGN KEY ("fkestado") REFERENCES "estado"("fiidestado");


ALTER TABLE ONLY "colonia"
    ADD CONSTRAINT "fk_municipio" FOREIGN KEY ("fkmunicipio") REFERENCES "municipio"("fiidmunicipio");




CREATE  SEQUENCE seq_estado INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 1;

CREATE  SEQUENCE seq_municipio INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 1;

CREATE  SEQUENCE seq_colonia INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 1;

CREATE  SEQUENCE seq_calle INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 1;

CREATE  SEQUENCE seq_codigopostal INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 1;

CREATE  SEQUENCE seq_direccion INCREMENT  BY 1
     MINVALUE 1  
     START  WITH 1;

