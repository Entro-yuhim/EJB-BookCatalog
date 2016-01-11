CREATE TABLE author
(
  id serial NOT NULL,
  firstname character varying(255),
  lastname character varying(255),
  CONSTRAINT author_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE author
  OWNER TO postgres;


CREATE TABLE book
(
  id serial NOT NULL,
  createdate timestamp without time zone DEFAULT now(),
  isbn character varying(14),
  name character varying(255),
  publisher character varying(255),
  yearpublished integer NOT NULL,
  CONSTRAINT book_pk PRIMARY KEY (id),
  CONSTRAINT isbn_unique UNIQUE (isbn)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE book
  OWNER TO postgres;

CREATE TABLE book_author
(
  booksauthored_id bigint NOT NULL,
  authors_id bigint NOT NULL,
  CONSTRAINT author_book_fk FOREIGN KEY (booksauthored_id)
      REFERENCES book (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT book_author_fk FOREIGN KEY (authors_id)
      REFERENCES author (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE book_author
  OWNER TO postgres;

CREATE TABLE comment
(
  id serial NOT NULL,
  message character varying(255),
  rating integer NOT NULL,
  username character varying(255),
  book_id bigint,
  CONSTRAINT comment_pk PRIMARY KEY (id),
  CONSTRAINT comment_book_fk FOREIGN KEY (book_id)
      REFERENCES book (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE comment
  OWNER TO postgres;
