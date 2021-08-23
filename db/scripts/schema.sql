create table if not exists account (
	id serial primary key,
	username varchar not null,
	email varchar not null unique,
	phone varchar not null unique
);

create table if not exists ticket (
	id serial primary key,
	session_id int not null,
	row int not null,
	cell int not null,
	account_id int not null references account(id),
	constraint place_taken unique(session_id, row, cell)
);

create table if not exists film (
    id serial primary key,
    filmName text
);