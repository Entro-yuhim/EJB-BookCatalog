CREATE TABLE author(
 id NUMBER(19) PRIMARY KEY,
 firstname varchar2(15) NOT NULL,
 lastname varchar(20)
);

CREATE SEQUENCE author_id_seq;

CREATE TABLE book(
 id NUMBER(19) PRIMARY KEY,
 createdate DATE DEFAULT CURRENT_DATE,
 isbn VARCHAR2(14) UNIQUE NOT NULL,
 publisher VARCHAR2(30),
 title varchar(50) NOT NULL,
 yearpublished DATE

);

CREATE SEQUENCE book_id_seq;

CREATE TABLE book_author(
 author_id number(10) NOT NULL,
 book_id number(10) NOT NULL,

 CONSTRAINT author_to_book_fk FOREIGN KEY (author_id) REFERENCES author(id),
 CONSTRAINT book_to_author_fk FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE review(
 id NUMBER(10) PRIMARY KEY,
 username VARCHAR2(15) default 'anonymous',
 review_text VARCHAR2(255) NOT NULL,
 rating NUMBER(1) NOT NULL,
 book_id NUMBER(10) NOT NULL,

 CONSTRAINT review_fk FOREIGN KEY (id) REFERENCES book(id),
 CONSTRAINT rating_values CHECK(rating BETWEEN 1 AND 5)
);

CREATE SEQUENCE review_id_seq;