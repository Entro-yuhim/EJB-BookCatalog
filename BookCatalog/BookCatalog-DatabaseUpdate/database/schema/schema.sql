DROP TABLE AUTHOR CASCADE CONSTRAINTS;
DROP TABLE BOOK CASCADE CONSTRAINTS;
DROP TABLE BOOK_AUTHOR CASCADE CONSTRAINTS;
DROP TABLE REVIEW CASCADE CONSTRAINTS;
DROP SEQUENCE author_id_seq;
DROP SEQUENCE book_id_seq;
DROP SEQUENCE review_id_seq;


CREATE TABLE author (
  id         INTEGER PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR2(255),

  CONSTRAINT unique_author UNIQUE (last_name, first_name)
);

CREATE SEQUENCE author_id_seq;

CREATE TABLE book (
  id             INTEGER PRIMARY KEY,
  create_date    DATE DEFAULT CURRENT_DATE,
  isbn           VARCHAR(14) UNIQUE NOT NULL,
  publisher      VARCHAR2(255),
  title          VARCHAR(255)       NOT NULL,
  year_published DATE

);

CREATE SEQUENCE book_id_seq;

CREATE TABLE book_author (
  author_id INTEGER NOT NULL,
  book_id   INTEGER NOT NULL,

  CONSTRAINT author_to_book_fk FOREIGN KEY (author_id) REFERENCES author (id),
  CONSTRAINT book_to_author_fk FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE
);

CREATE TABLE review (
  id          INTEGER PRIMARY KEY,
  username    VARCHAR2(255) DEFAULT 'anonymous',
  review_text CLOB      NOT NULL,
  rating      NUMBER(1) NOT NULL,
  book_id     INTEGER   NOT NULL,

  CONSTRAINT review_fk FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE,
  CONSTRAINT rating_values CHECK (rating BETWEEN 1 AND 5)
);

CREATE SEQUENCE review_id_seq;