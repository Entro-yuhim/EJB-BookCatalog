--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.13
-- Dumped by pg_dump version 9.4.0
-- Started on 2016-01-11 14:44:03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 1950 (class 0 OID 42758836)
-- Dependencies: 168
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY author (id, firstname, lastname) FROM stdin;
1	Brian	Biggs
2	Dan	James
3	Craig	Thompson
4	Various	
5	Christian	Slade
6	Scott	Morse
7	Alex	Robinson
8	Tim	Sievert
9	Alan	Moore
10	Matt	Kindt
11	Rich	Koslowski
12	Tom	Hart
13	Liz	Prince
14	Renee	French
15	Andy	Hartzell
16	Nicolas	Mahler
17	Pete	Sickman-Garner
18	Ulf	K.
19	Mahler	
20	David	Yurkovich
21	Jeffrey	Brown
22	Mawil	Witzel
23	Kuper,	Peter
24	Nate	Powell
25	Robert	Venditti
26	Jeremy	Tinder
27	James	Kochalka
28	Brett	Warnock
29	Kelter,	Bill/
30	Tony	Consiglio
\.


--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 172
-- Name: author_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('author_id_seq', 30, true);


--
-- TOC entry 1951 (class 0 OID 42758844)
-- Dependencies: 169
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY book (id, createdate, isbn, publisher, title, yearpublished) FROM stdin;
1	2016-01-11 13:07:42.889	9781603090155	Diamond Comic Distributors	Johnny Boo 2	2015
2	2016-01-11 13:07:47.42	9781891830433	Top Shelf	Blankets	2003
3	2016-01-11 13:07:50.842	9780971359765	Top Shelf Productions	Clumsy	2003
4	2016-01-11 13:07:51.978	9781891830730	Top Shelf	Tricked	2005
5	2016-01-11 13:07:54.26	9781603090520	Top Shelf Productions	Superf*ckers	2010
6	2016-01-11 13:07:56.514	9781891830686	Top Shelf Productions	Mosquito	2005
7	2016-01-11 13:07:58.784	9781891830600	Top Shelf Productions	Carnet De Voyage (Travel Journal)	2004
8	2016-01-11 13:07:59.918	9781891830143	Top Shelf Productions	Speechless	2001
9	2016-01-11 13:08:07.828	9781603090124	Top Shelf Productions	Delayed replays	2008
10	2016-01-11 13:08:08.968	9781603090087	Edition 52	Hieronymus B., 1997-2007	2007
11	2016-01-11 13:08:10.084	9781891830402	Top Shelf Productions	Beach Safari	2003
12	2016-01-11 13:08:13.488	9781891830815	Top Shelf Productions	Cry Yourself To Sleep	2006
13	2016-01-11 13:08:18.035	9781891830921	Top Shelf Productions	Death By Chocolate	2007
14	2016-01-11 13:08:22.612	9781891830129	Top Shelf Productions	Dear Julia,	2000
15	2016-01-11 13:08:27.153	9781891830334	Top Shelf Productions	James Kochalka's Magic Boy & Robot Elf	2003
16	2016-01-11 13:08:28.284	9781891830969	Top Shelf Productions	Super Spy	2007
17	2016-01-11 13:08:29.429	9781891830419	Top Shelf Productions	Unlikely	2003
18	2016-01-11 13:08:30.562	9781603090131	Top Shelf	Johnny Boo	2008
19	2016-01-11 13:08:32.869	9781891830259	Top Shelf Productions	Hey, Mister	2002
20	2016-01-11 13:08:35.164	9781891830655	Top Shelf Productions	The King	2005
21	2016-01-11 13:08:36.315	9781891830860	Top Shelf Productions	I Am Going To Be Small	2006
22	2016-01-11 13:08:37.475	9781891830716	Top Shelf Productions	Any easy intimacy	2007
23	2016-01-11 13:08:43.161	9781603090001	Top Shelf Productions	League of Extraordinary Gentlemen	2009
24	2016-01-11 13:08:45.458	9781891830327	Top Shelf Productions	Top Shelf Asks the Big Questions	2003
25	2016-01-11 13:08:47.94	9781891830389	Top Shelf Productions	Van Helsing's night off	2004
26	2016-01-11 13:08:51.388	9781891830174	Top Shelf Productions	The Collected Hutch Owen	2000
27	2016-01-11 13:08:55.96	9781891830877	Top Shelf	The surrogates	2006
28	2016-01-11 13:08:57.114	9781603090339	Diamond Comic Distributors	Swallow Me Whole	2015
29	2016-01-11 13:09:00.566	9781891830976	Top Shelf Productions	Fox Bunny Funny	2007
30	2016-01-11 13:09:08.617	9781891830310	Top Shelf Productions	Three Fingers	2002
31	2016-01-11 13:09:09.763	9781891830518	Top Shelf Productions	Less Than Heroes	2004
32	2016-01-11 13:09:13.232	9781891830556	Top Shelf Productions	Hutch Owen	2004
33	2016-01-11 13:09:15.495	9781891830372	Top Shelf Productions	The Barefoot Serpent	2003
34	2016-01-11 13:09:18.927	9781891830778	Top Shelf	Every girl is the end of the world for me	2005
35	2016-01-11 13:09:21.209	9781891830693	Top Shelf Productions	Lone Racer	2006
36	2016-01-11 13:09:22.354	9781603090186	Top Shelf Productions	The surrogates	2009
37	2016-01-11 13:09:26.911	9781891830709	Top Shelf Productions	The Ticking	2006
38	2016-01-11 13:09:30.345	9781603090032	Diamond Comic Distributors	Veeps	2015
39	2016-01-11 13:09:35.253	9781603090100	Top Shelf Productions	Korgi	2007
40	2016-01-11 13:09:36.407	9781891830365	Top Shelf Productions	Monkey vs. Robot & the Crystal of Power	2003
41	2016-01-11 13:09:40.936	9781603090162	Diamond Comic Distributors	American Elf 3	2015
42	2016-01-11 13:09:42.079	9781891830853	Top Shelf Productions	American elf	2007
43	2016-01-11 13:09:45.515	9781891830914	Top Shelf Productions	Incredible Change-Bots	2007
44	2016-01-11 13:09:51.196	9781891830020	Top Shelf Productions	Hey, Mister	2001
45	2016-01-11 13:09:53.486	9781891830723	Top Shelf Productions	Will You Still Love Me If I Wet The Bed?	2005
46	2016-01-11 13:09:54.621	9781603090056	Top Shelf Productions	That Salty Air	2007
47	2016-01-11 13:10:06.706	9781891830754	Top Shelf Production	110 perc[ent]	2006
48	2016-01-11 13:10:10.127	9781891830457	Top Shelf Productions	The mirror of love	2003
49	2016-01-11 13:10:13.294	9781891830297	Top Shelf Productions	Pinky & Stinky	2002
\.


--
-- TOC entry 1952 (class 0 OID 42758853)
-- Dependencies: 170
-- Data for Name: book_author; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY book_author (books_id, authors_id) FROM stdin;
1	27
2	3
3	21
4	7
5	27
6	2
7	3
8	23
9	13
10	18
11	22
12	26
13	20
14	1
15	27
16	10
17	21
18	27
19	17
20	11
21	21
22	21
23	9
24	4
25	19
26	12
27	25
28	24
29	15
30	11
31	20
32	12
33	6
34	21
35	16
36	25
37	14
38	29
39	5
40	27
41	27
42	27
43	21
44	17
45	13
46	8
47	30
48	9
49	27
\.


--
-- TOC entry 1962 (class 0 OID 0)
-- Dependencies: 173
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('book_id_seq', 49, true);


--
-- TOC entry 1953 (class 0 OID 42758856)
-- Dependencies: 171
-- Data for Name: review; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY review (id, message, rating, username, book_id) FROM stdin;
\.


--
-- TOC entry 1963 (class 0 OID 0)
-- Dependencies: 174
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comment_id_seq', 1, false);


-- Completed on 2016-01-11 14:44:03

--
-- PostgreSQL database dump complete
--

