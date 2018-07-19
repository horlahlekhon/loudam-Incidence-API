CREATE TABLE public.incidence
(
  id integer NOT NULL,
  message character varying NOT NULL,
  postdate timestamp without time zone,
  username text NOT NULL,
  CONSTRAINT "incidenceBindUsername" FOREIGN KEY (username)
      REFERENCES public.account (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.incidence
  OWNER TO postgres;

-- Index: public."fki_incidenceBindUsername"

-- DROP INDEX public."fki_incidenceBindUsername";

CREATE INDEX "fki_incidenceBindUsername"
  ON public.incidence
  USING btree
  (username COLLATE pg_catalog."default");






CREATE TABLE public.account
(
  username character varying NOT NULL,
  email character varying,
  image text,
  CONSTRAINT account_pkey PRIMARY KEY (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.account
  OWNER TO postgres;
