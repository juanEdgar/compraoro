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


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


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


--
-- Name: Direccion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

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


--
-- Name: Estado; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "estado" (
    "fiidestado" integer NOT NULL,
    "fcnombre" character varying(128) NOT NULL
);


--
-- Name: Municipio; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "municipio" (
    "fiidmunicipio" integer NOT NULL,
    "fcnombre" character varying(255) NOT NULL,
    "fkestado" integer
);


--
-- Data for Name: Calle; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: CodigoPostal; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: Colonia; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: Direccion; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: Estado; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: Municipio; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Name: Calle_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "calle"
    ADD CONSTRAINT "calle_pkey" PRIMARY KEY ("fiidcalle");


--
-- Name: CodigoPostal_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "codigopostal"
    ADD CONSTRAINT "codigopostal_pkey" PRIMARY KEY ("fiidcodigopostal");


--
-- Name: Colonia_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "colonia"
    ADD CONSTRAINT "colonia_pkey" PRIMARY KEY ("fiidcolonia");


--
-- Name: Direccion_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "direccion"
    ADD CONSTRAINT "direccion_pkey" PRIMARY KEY ("fiiddireccion");


--
-- Name: Estado_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "estado"
    ADD CONSTRAINT "estado_pkey" PRIMARY KEY ("fiidestado");


--
-- Name: Municipio_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "municipio"
    ADD CONSTRAINT "municipio_pkey" PRIMARY KEY ("fiidmunicipio");


--
-- Name: fk_CPMunicipio; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "codigopostal"
    ADD CONSTRAINT "fk_cpmunicipio" FOREIGN KEY ("fkmunicipio") REFERENCES "municipio"("fiidmunicipio");


--
-- Name: fk_Calle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "direccion"
    ADD CONSTRAINT "fk_Calle" FOREIGN KEY ("fkcalle") REFERENCES "calle"("fiidcalle");


--
-- Name: fk_CodigoPostal; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "direccion"
    ADD CONSTRAINT "fk_codigopostal" FOREIGN KEY ("fkcodigopostal") REFERENCES "codigopostal"("fiidcodigopostal");


--
-- Name: fk_Colonia; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "calle"
    ADD CONSTRAINT "fk_colonia" FOREIGN KEY ("fkcolonia") REFERENCES "colonia"("fiidcolonia");


--
-- Name: fk_Estado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "municipio"
    ADD CONSTRAINT "fk_estado" FOREIGN KEY ("fkestado") REFERENCES "estado"("fiidestado");


--
-- Name: fk_Municipio; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "colonia"
    ADD CONSTRAINT "fk_municipio" FOREIGN KEY ("fkmunicipio") REFERENCES "municipio"("fiidmunicipio");


--
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM "Nayely";
GRANT ALL ON SCHEMA public TO "Nayely";
GRANT ALL ON SCHEMA public TO PUBLIC;


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

--
-- PostgreSQL database dump complete
--

