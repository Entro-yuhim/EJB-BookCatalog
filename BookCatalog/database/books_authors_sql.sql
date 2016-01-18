INSERT INTO author VALUES (1, 'Brian', 'Biggs');
INSERT INTO author VALUES (2, 'Dan', 'James');
INSERT INTO author VALUES (3, 'Craig', 'Thompson');
INSERT INTO author VALUES (4, 'Various', '');
INSERT INTO author VALUES (5, 'Christian', 'Slade');
INSERT INTO author VALUES (6, 'Scott', 'Morse');
INSERT INTO author VALUES (7, 'Alex', 'Robinson');
INSERT INTO author VALUES (8, 'Tim', 'Sievert');
INSERT INTO author VALUES (9, 'Alan', 'Moore');
INSERT INTO author VALUES (10, 'Matt', 'Kindt');
INSERT INTO author VALUES (11, 'Rich', 'Koslowski');
INSERT INTO author VALUES (12, 'Tom', 'Hart');
INSERT INTO author VALUES (13, 'Liz', 'Prince');
INSERT INTO author VALUES (14, 'Renee', 'French');
INSERT INTO author VALUES (15, 'Andy', 'Hartzell');
INSERT INTO author VALUES (16, 'Nicolas', 'Mahler');
INSERT INTO author VALUES (17, 'Pete', 'Sickman-Garner');
INSERT INTO author VALUES (18, 'Ulf', 'K.');
INSERT INTO author VALUES (19, 'Mahler', '');
INSERT INTO author VALUES (20, 'David', 'Yurkovich');
INSERT INTO author VALUES (21, 'Jeffrey', 'Brown');
INSERT INTO author VALUES (22, 'Mawil', 'Witzel');
INSERT INTO author VALUES (23, 'Kuper,', 'Peter');
INSERT INTO author VALUES (24, 'Nate', 'Powell');
INSERT INTO author VALUES (25, 'Robert', 'Venditti');
INSERT INTO author VALUES (26, 'Jeremy', 'Tinder');
INSERT INTO author VALUES (27, 'James', 'Kochalka');
INSERT INTO author VALUES (28, 'Brett', 'Warnock');
INSERT INTO author VALUES (29, 'Kelter,', 'Bill/');
INSERT INTO author VALUES (30, 'Tony', 'Consiglio');
ALTER SEQUENCE AUTHOR_ID_SEQ INCREMENT BY 30;

select AUTHOR_ID_SEQ.nextval from dual;

ALTER SEQUENCE AUTHOR_ID_SEQ INCREMENT BY 1;
-- TODO: alter sequence next value after restoring author
--SELECT pg_catalog.setval('author_id_seq', 30, true);


--
-- TOC entry 1948 (class 0 OID 42759046)
-- Dependencies: 169
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (1, '9781603090155', 'Diamond Comic Distributors', 'Johnny Boo 2', to_date('2015', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (2, '9781891830433', 'Top Shelf', 'Blankets', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (3, '9780971359765', 'Top Shelf Productions', 'Clumsy', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (4, '9781891830730', 'Top Shelf', 'Tricked', to_date('2005', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (5, '9781603090520', 'Top Shelf Productions', 'Superf*ckers', to_date('2010', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (6, '9781891830686', 'Top Shelf Productions', 'Mosquito', to_date('2005', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (7, '9781891830600', 'Top Shelf Productions', 'Carnet De Voyage (Travel Journal)', to_date('2004', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (8, '9781891830143', 'Top Shelf Productions', 'Speechless', to_date('2001', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (9, '9781603090124', 'Top Shelf Productions', 'Delayed replays', to_date('2008', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (10, '9781603090087', 'Edition 52', 'Hieronymus B., 1997-2007', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (11, '9781891830402', 'Top Shelf Productions', 'Beach Safari', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (12, '9781891830815', 'Top Shelf Productions', 'Cry Yourself To Sleep', to_date('2006', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (13, '9781891830921', 'Top Shelf Productions', 'Death By Chocolate', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (14, '9781891830129', 'Top Shelf Productions', 'Dear Julia,', to_date('2000', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (15, '9781891830334', 'Top Shelf Productions', 'James Kochalka''s Magic Boy '||chr(38) ||' Robot Elf', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (16, '9781891830969', 'Top Shelf Productions', 'Super Spy', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (17, '9781891830419', 'Top Shelf Productions', 'Unlikely', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (18, '9781603090131', 'Top Shelf', 'Johnny Boo', to_date('2008', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (19, '9781891830259', 'Top Shelf Productions', 'Hey, Mister', to_date('2002', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (20, '9781891830655', 'Top Shelf Productions', 'The King', to_date('2005', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (21, '9781891830860', 'Top Shelf Productions', 'I Am Going To Be Small', to_date('2006', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (22, '9781891830716', 'Top Shelf Productions', 'Any easy intimacy', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (23, '9781603090001', 'Top Shelf Productions', 'League of Extraordinary Gentlemen', to_date('2009', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (24, '9781891830327', 'Top Shelf Productions', 'Top Shelf Asks the Big Questions', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (25, '9781891830389', 'Top Shelf Productions', 'Van Helsing''s night off', to_date('2004', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (26, '9781891830174', 'Top Shelf Productions', 'The Collected Hutch Owen', to_date('2000', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (27, '9781891830877', 'Top Shelf', 'The surrogates', to_date('2006', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (28, '9781603090339', 'Diamond Comic Distributors', 'Swallow Me Whole', to_date('2015', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (29, '9781891830976', 'Top Shelf Productions', 'Fox Bunny Funny', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (30, '9781891830310', 'Top Shelf Productions', 'Three Fingers', to_date('2002', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (31, '9781891830518', 'Top Shelf Productions', 'Less Than Heroes', to_date('2004', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (32, '9781891830556', 'Top Shelf Productions', 'Hutch Owen', to_date('2004', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (33, '9781891830372', 'Top Shelf Productions', 'The Barefoot Serpent', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (34, '9781891830778', 'Top Shelf', 'Every girl is the end of the world for me', to_date('2005', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (35, '9781891830693', 'Top Shelf Productions', 'Lone Racer', to_date('2006', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (36, '9781603090186', 'Top Shelf Productions', 'The surrogates', to_date('2009', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (37, '9781891830709', 'Top Shelf Productions', 'The Ticking', to_date('2006', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (38, '9781603090032', 'Diamond Comic Distributors', 'Veeps', to_date('2015', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (39, '9781603090100', 'Top Shelf Productions', 'Korgi', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (40, '9781891830365', 'Top Shelf Productions', 'Monkey vs. Robot '||chr(38) ||' the Crystal of Power', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (41, '9781603090162', 'Diamond Comic Distributors', 'American Elf 3', to_date('2015', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (42, '9781891830853', 'Top Shelf Productions', 'American elf', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (43, '9781891830914', 'Top Shelf Productions', 'Incredible Change-Bots', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (44, '9781891830020', 'Top Shelf Productions', 'Hey, Mister', to_date('2001', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (45, '9781891830723', 'Top Shelf Productions', 'Will You Still Love Me If I Wet The Bed?', to_date('2005', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (46, '9781603090056', 'Top Shelf Productions', 'That Salty Air', to_date('2007', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (47, '9781891830754', 'Top Shelf Production', '110 perc[ent]', to_date('2006', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (48, '9781891830457', 'Top Shelf Productions', 'The mirror of love', to_date('2003', 'YYYY'));
INSERT INTO book(id, isbn, publisher, title, year_published) VALUES (49, '9781891830297', 'Top Shelf Productions', 'Pinky '||chr(38) ||' Stinky', to_date('2002', 'YYYY'));

ALTER SEQUENCE BOOK_ID_SEQ INCREMENT BY 30;

select BOOK_ID_SEQ.nextval from dual;

ALTER SEQUENCE BOOK_ID_SEQ INCREMENT BY 1;

INSERT INTO book_author(book_id, author_id) VALUES (1, 27);
INSERT INTO book_author(book_id, author_id) VALUES (2, 3);
INSERT INTO book_author(book_id, author_id) VALUES (3, 21);
INSERT INTO book_author(book_id, author_id) VALUES (4, 7);
INSERT INTO book_author(book_id, author_id) VALUES (5, 27);
INSERT INTO book_author(book_id, author_id) VALUES (6, 2);
INSERT INTO book_author(book_id, author_id) VALUES (7, 3);
INSERT INTO book_author(book_id, author_id) VALUES (8, 23);
INSERT INTO book_author(book_id, author_id) VALUES (9, 13);
INSERT INTO book_author(book_id, author_id) VALUES (10, 18);
INSERT INTO book_author(book_id, author_id) VALUES (11, 22);
INSERT INTO book_author(book_id, author_id) VALUES (12, 26);
INSERT INTO book_author(book_id, author_id) VALUES (13, 20);
INSERT INTO book_author(book_id, author_id) VALUES (14, 1);
INSERT INTO book_author(book_id, author_id) VALUES (15, 27);
INSERT INTO book_author(book_id, author_id) VALUES (16, 10);
INSERT INTO book_author(book_id, author_id) VALUES (17, 21);
INSERT INTO book_author(book_id, author_id) VALUES (18, 27);
INSERT INTO book_author(book_id, author_id) VALUES (19, 17);
INSERT INTO book_author(book_id, author_id) VALUES (20, 11);
INSERT INTO book_author(book_id, author_id) VALUES (21, 21);
INSERT INTO book_author(book_id, author_id) VALUES (22, 21);
INSERT INTO book_author(book_id, author_id) VALUES (23, 9);
INSERT INTO book_author(book_id, author_id) VALUES (24, 4);
INSERT INTO book_author(book_id, author_id) VALUES (25, 19);
INSERT INTO book_author(book_id, author_id) VALUES (26, 12);
INSERT INTO book_author(book_id, author_id) VALUES (27, 25);
INSERT INTO book_author(book_id, author_id) VALUES (28, 24);
INSERT INTO book_author(book_id, author_id) VALUES (29, 15);
INSERT INTO book_author(book_id, author_id) VALUES (30, 11);
INSERT INTO book_author(book_id, author_id) VALUES (31, 20);
INSERT INTO book_author(book_id, author_id) VALUES (32, 12);
INSERT INTO book_author(book_id, author_id) VALUES (33, 6);
INSERT INTO book_author(book_id, author_id) VALUES (34, 21);
INSERT INTO book_author(book_id, author_id) VALUES (35, 16);
INSERT INTO book_author(book_id, author_id) VALUES (36, 25);
INSERT INTO book_author(book_id, author_id) VALUES (37, 14);
INSERT INTO book_author(book_id, author_id) VALUES (38, 29);
INSERT INTO book_author(book_id, author_id) VALUES (39, 5);
INSERT INTO book_author(book_id, author_id) VALUES (40, 27);
INSERT INTO book_author(book_id, author_id) VALUES (41, 27);
INSERT INTO book_author(book_id, author_id) VALUES (42, 27);
INSERT INTO book_author(book_id, author_id) VALUES (43, 21);
INSERT INTO book_author(book_id, author_id) VALUES (44, 17);
INSERT INTO book_author(book_id, author_id) VALUES (45, 13);
INSERT INTO book_author(book_id, author_id) VALUES (46, 8);
INSERT INTO book_author(book_id, author_id) VALUES (47, 30);
INSERT INTO book_author(book_id, author_id) VALUES (48, 9);
INSERT INTO book_author(book_id, author_id) VALUES (49, 27);

--TODO: alter sequence of book
--SELECT pg_catalog.setval('book_id_seq', 49, true);


--TOOD: alter sequence of reviews
--SELECT pg_catalog.setval('review_id_seq', 1, false);


-- Completed on 2016-01-13 15:43:25

