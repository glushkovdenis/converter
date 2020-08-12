CREATE DATABASE converter;

\c converter;

CREATE TABLE valutes (
	id varchar(255),
	numcode varchar(255),
	charcode varchar(255),
	nominal int,
	name varchar(255),
	value numeric,
	date date
);

CREATE TABLE history (
	source_valute varchar(255),
	target_valute varchar(255),
	original_amount varchar(255),
	received_amount varchar(255),
	date varchar(255)
);