--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-03-23 15:48:10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2126 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 184 (class 1259 OID 16422)
-- Name: Link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Link" (
    "Id" character varying(20) NOT NULL,
    "NetworkId" integer NOT NULL,
    "QuadKey" character varying(18),
    length numeric,
    freespeed numeric,
    capacity numeric,
    permlanes numeric,
    oneway boolean,
    modes character varying(20)
);


ALTER TABLE "Link" OWNER TO postgres;

--
-- TOC entry 2127 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN "Link".oneway; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Link".oneway IS '
';


--
-- TOC entry 181 (class 1259 OID 16394)
-- Name: Network; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Network" (
    "Name" character varying(20),
    "Id" integer NOT NULL
);


ALTER TABLE "Network" OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16412)
-- Name: Network_Options; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Network_Options" (
    "OptionName" character varying(10) NOT NULL,
    "NetworkId" integer NOT NULL,
    "Value" character varying(10)
);


ALTER TABLE "Network_Options" OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 16402)
-- Name: Node; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Node" (
    "Id" character varying(20) NOT NULL,
    "NetworkId" integer NOT NULL,
    "QuadKey" character varying(18),
    x character varying(10),
    y character varying(10)
);


ALTER TABLE "Node" OWNER TO postgres;

--
-- TOC entry 2128 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "Node"."Id"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Node"."Id" IS '
';


--
-- TOC entry 2129 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "Node"."QuadKey"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Node"."QuadKey" IS '
';


--
-- TOC entry 1993 (class 2606 OID 16398)
-- Name: Id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network"
    ADD CONSTRAINT "Id" PRIMARY KEY ("Id");


--
-- TOC entry 2000 (class 2606 OID 16429)
-- Name: LinkId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "LinkId" PRIMARY KEY ("Id", "NetworkId");


--
-- TOC entry 1998 (class 2606 OID 16416)
-- Name: Network_OptionsId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network_Options"
    ADD CONSTRAINT "Network_OptionsId" PRIMARY KEY ("NetworkId", "OptionName");


--
-- TOC entry 1995 (class 2606 OID 16436)
-- Name: NodeId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node"
    ADD CONSTRAINT "NodeId" PRIMARY KEY ("Id", "NetworkId");


--
-- TOC entry 1996 (class 1259 OID 16438)
-- Name: NodeQuadKeyIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "NodeQuadKeyIndex" ON "Node" USING btree ("QuadKey");


--
-- TOC entry 2001 (class 1259 OID 16437)
-- Name: QuadKeyIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "QuadKeyIndex" ON "Link" USING btree ("QuadKey");


--
-- TOC entry 2004 (class 2606 OID 16430)
-- Name: Link_Network; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_Network" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2002 (class 2606 OID 16407)
-- Name: NetworkId; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node"
    ADD CONSTRAINT "NetworkId" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2003 (class 2606 OID 16417)
-- Name: Network_Network_Options; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network_Options"
    ADD CONSTRAINT "Network_Network_Options" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2125 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-03-23 15:48:10

--
-- PostgreSQL database dump complete
--

