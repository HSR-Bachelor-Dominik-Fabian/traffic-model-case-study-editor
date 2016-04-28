--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-04-28 15:58:30

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
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 188 (class 1259 OID 17078)
-- Name: changeset_pk_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE changeset_pk_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE changeset_pk_id OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 181 (class 1259 OID 16957)
-- Name: Changeset; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Changeset" (
    "Id" bigint DEFAULT nextval('changeset_pk_id'::regclass) NOT NULL,
    "Name" text,
    "UserNr" integer,
    "NetworkId" integer,
    "LastModified" timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE "Changeset" OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 16963)
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
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "Link"."Oneway"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Link"."Oneway" IS '
';


--
-- TOC entry 183 (class 1259 OID 16969)
-- Name: Link_Change; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Link_Change" (
    "Id" character varying(100) NOT NULL,
    "ChangesetNr" bigint NOT NULL,
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


ALTER TABLE "Link_Change" OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 16975)
-- Name: Network; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Network" (
    "Name" character varying(20),
    "Id" integer NOT NULL
);


ALTER TABLE "Network" OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16978)
-- Name: Network_Options; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Network_Options" (
    "OptionName" character varying(100) NOT NULL,
    "NetworkId" integer NOT NULL,
    "Value" character varying(100)
);


ALTER TABLE "Network_Options" OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16981)
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
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN "Node"."Id"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Node"."Id" IS '
';


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN "Node"."QuadKey"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "Node"."QuadKey" IS '
';


--
-- TOC entry 187 (class 1259 OID 16987)
-- Name: Node_Change; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "Node_Change" (
    "Id" character varying(100) NOT NULL,
    "ChangesetNr" bigint NOT NULL,
    "NetworkId" integer NOT NULL,
    "QuadKey" character varying(18),
    "X" numeric,
    "Y" numeric,
    "Lat" numeric,
    "Long" numeric
);


ALTER TABLE "Node_Change" OWNER TO postgres;

--
-- TOC entry 2013 (class 2606 OID 16994)
-- Name: Changeset_PrimaryKey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Changeset"
    ADD CONSTRAINT "Changeset_PrimaryKey" PRIMARY KEY ("Id");


--
-- TOC entry 2025 (class 2606 OID 16996)
-- Name: Id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network"
    ADD CONSTRAINT "Id" PRIMARY KEY ("Id");


--
-- TOC entry 2015 (class 2606 OID 16998)
-- Name: LinkId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "LinkId" PRIMARY KEY ("Id", "NetworkId");


--
-- TOC entry 2021 (class 2606 OID 17000)
-- Name: Link_Change_LinkId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link_Change"
    ADD CONSTRAINT "Link_Change_LinkId" PRIMARY KEY ("Id", "ChangesetNr", "NetworkId");


--
-- TOC entry 2027 (class 2606 OID 17002)
-- Name: Network_OptionsId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network_Options"
    ADD CONSTRAINT "Network_OptionsId" PRIMARY KEY ("NetworkId", "OptionName");


--
-- TOC entry 2029 (class 2606 OID 17004)
-- Name: NodeId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node"
    ADD CONSTRAINT "NodeId" PRIMARY KEY ("Id", "NetworkId");


--
-- TOC entry 2032 (class 2606 OID 17006)
-- Name: Node_ChangeId; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node_Change"
    ADD CONSTRAINT "Node_ChangeId" PRIMARY KEY ("Id", "ChangesetNr", "NetworkId");


--
-- TOC entry 2019 (class 1259 OID 17007)
-- Name: Link_Change_DataAccessIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "Link_Change_DataAccessIndex" ON "Link_Change" USING btree ("QuadKey" varchar_pattern_ops, "NetworkId", "MinLevel");


--
-- TOC entry 2016 (class 1259 OID 17008)
-- Name: Link_DataAccessIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "Link_DataAccessIndex" ON "Link" USING btree ("QuadKey" varchar_pattern_ops, "NetworkId", "MinLevel");

ALTER TABLE "Link" CLUSTER ON "Link_DataAccessIndex";


--
-- TOC entry 2030 (class 1259 OID 17009)
-- Name: NodeQuadKeyIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "NodeQuadKeyIndex" ON "Node" USING btree ("QuadKey");


--
-- TOC entry 2033 (class 1259 OID 17010)
-- Name: Node_ChangeQuadKeyIndex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "Node_ChangeQuadKeyIndex" ON "Node_Change" USING btree ("QuadKey");


--
-- TOC entry 2022 (class 1259 OID 17011)
-- Name: fki_Link_Change_Change_Node_To; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_Link_Change_Change_Node_To" ON "Link_Change" USING btree ("To", "NetworkId");


--
-- TOC entry 2023 (class 1259 OID 17012)
-- Name: fki_Link_Change_Node_From; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_Link_Change_Node_From" ON "Link_Change" USING btree ("From", "NetworkId");


--
-- TOC entry 2017 (class 1259 OID 17013)
-- Name: fki_Link_Node_From; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_Link_Node_From" ON "Link" USING btree ("From", "NetworkId");


--
-- TOC entry 2018 (class 1259 OID 17014)
-- Name: fki_Link_Node_To; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_Link_Node_To" ON "Link" USING btree ("To", "NetworkId");


--
-- TOC entry 2034 (class 2606 OID 17015)
-- Name: Changeset_NetworkID; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Changeset"
    ADD CONSTRAINT "Changeset_NetworkID" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2038 (class 2606 OID 17020)
-- Name: Link_Change_Changeset; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link_Change"
    ADD CONSTRAINT "Link_Change_Changeset" FOREIGN KEY ("ChangesetNr") REFERENCES "Changeset"("Id");


--
-- TOC entry 2039 (class 2606 OID 17025)
-- Name: Link_Change_Link_Network; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link_Change"
    ADD CONSTRAINT "Link_Change_Link_Network" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2035 (class 2606 OID 17030)
-- Name: Link_Network; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_Network" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2036 (class 2606 OID 17035)
-- Name: Link_Node_From; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_Node_From" FOREIGN KEY ("From", "NetworkId") REFERENCES "Node"("Id", "NetworkId");


--
-- TOC entry 2037 (class 2606 OID 17040)
-- Name: Link_Node_To; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_Node_To" FOREIGN KEY ("To", "NetworkId") REFERENCES "Node"("Id", "NetworkId");


--
-- TOC entry 2041 (class 2606 OID 17045)
-- Name: NetworkId; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node"
    ADD CONSTRAINT "NetworkId" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2040 (class 2606 OID 17050)
-- Name: Network_Network_Options; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Network_Options"
    ADD CONSTRAINT "Network_Network_Options" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2042 (class 2606 OID 17055)
-- Name: Node_Change_ChangesetNr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node_Change"
    ADD CONSTRAINT "Node_Change_ChangesetNr" FOREIGN KEY ("ChangesetNr") REFERENCES "Changeset"("Id");


--
-- TOC entry 2043 (class 2606 OID 17060)
-- Name: Node_Change_NetworkId; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "Node_Change"
    ADD CONSTRAINT "Node_Change_NetworkId" FOREIGN KEY ("NetworkId") REFERENCES "Network"("Id");


--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-04-28 15:58:30

--
-- PostgreSQL database dump complete
--

