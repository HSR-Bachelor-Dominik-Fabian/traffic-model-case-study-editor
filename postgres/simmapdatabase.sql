--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-04-04 15:23:11

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
-- TOC entry 2131 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 181 (class 1259 OID 16552)
-- Name: Link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Link" (
    "Id" character varying(100) NOT NULL,
    "NetworkId" integer NOT NULL,
    "QuadKey" character varying(18),
    "Length" numeric,
    "Freespeed" numeric,
    "Capacity" numeric,
    "Permlanes" numeric,
    "Oneway" boolean,
    "Modes" character varying(100),
    "From" character varying(100),
    "To" character varying(100),
    "MinLevel" integer,
    "LastModified" date,
    "Long1" numeric,
    "Lat1" numeric,
    "Long2" numeric,
    "Lat2" numeric
);


ALTER TABLE "Link" OWNER TO postgres;

--
-- TOC entry 2132 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN "Link"."Oneway"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Link"."Oneway" IS '
';


--
-- TOC entry 182 (class 1259 OID 16558)
-- Name: Network; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Network" (
    "Name" character varying(20),
    "Id" integer NOT NULL
);


ALTER TABLE "Network" OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16561)
-- Name: Network_Options; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Network_Options" (
    "OptionName" character varying(100) NOT NULL,
    "NetworkId" integer NOT NULL,
    "Value" character varying(100)
);


ALTER TABLE "Network_Options" OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 16564)
-- Name: Node; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Node" (
    "Id" character varying(100) NOT NULL,
    "NetworkId" integer NOT NULL,
    "QuadKey" character varying(18),
    "X" numeric,
    "Y" numeric,
    "Lat" numeric,
    "Long" numeric
);


ALTER TABLE "Node" OWNER TO postgres;

--
-- TOC entry 2133 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN "Node"."Id"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Node"."Id" IS '
';


--
-- TOC entry 2134 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN "Node"."QuadKey"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Node"."QuadKey" IS '
';


--
-- TOC entry 1999 (class 2606 OID 16571)
-- Name: Id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network"
    ADD CONSTRAINT "Id" PRIMARY KEY ("Id");


--
-- TOC entry 1994 (class 2606 OID 16573)
-- Name: LinkId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "LinkId" PRIMARY KEY ("Id", "NetworkId");


--
-- TOC entry 2001 (class 2606 OID 16575)
-- Name: Network_OptionsId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network_Options"
    ADD CONSTRAINT "Network_OptionsId" PRIMARY KEY ("NetworkId", "OptionName");


--
-- TOC entry 2003 (class 2606 OID 16577)
-- Name: NodeId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node"
    ADD CONSTRAINT "NodeId" PRIMARY KEY ("Id", "NetworkId");


--
-- TOC entry 1995 (class 1259 OID 16756)
-- Name: Link_DataAccessIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "Link_DataAccessIndex" ON "Link" USING btree ("QuadKey" varchar_pattern_ops, "NetworkId", "MinLevel");

ALTER TABLE "Link" CLUSTER ON "Link_DataAccessIndex";


--
-- TOC entry 2004 (class 1259 OID 16578)
-- Name: NodeQuadKeyIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "NodeQuadKeyIndex" ON "Node" USING btree ("QuadKey");


--
-- TOC entry 1996 (class 1259 OID 16580)
-- Name: fki_Link_Node_From; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_Link_Node_From" ON "Link" USING btree ("From", "NetworkId");


--
-- TOC entry 1997 (class 1259 OID 16581)
-- Name: fki_Link_Node_To; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_Link_Node_To" ON "Link" USING btree ("To", "NetworkId");


--
-- TOC entry 2005 (class 2606 OID 16582)
-- Name: Link_Network; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_Network" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2006 (class 2606 OID 16587)
-- Name: Link_Node_From; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_Node_From" FOREIGN KEY ("From", "NetworkId") REFERENCES "Node"("Id", "NetworkId");


--
-- TOC entry 2007 (class 2606 OID 16592)
-- Name: Link_Node_To; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_Node_To" FOREIGN KEY ("To", "NetworkId") REFERENCES "Node"("Id", "NetworkId");


--
-- TOC entry 2009 (class 2606 OID 16597)
-- Name: NetworkId; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node"
    ADD CONSTRAINT "NetworkId" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2008 (class 2606 OID 16602)
-- Name: Network_Network_Options; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network_Options"
    ADD CONSTRAINT "Network_Network_Options" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2130 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-04-04 15:23:11

--
-- PostgreSQL database dump complete
--

