create table account (
	id serial primary key,
	username varchar not null,
	email varchar not null unique,
	phone varchar not null unique
);

create table ticket (
	id serial primary key,
	session_id int not null,
	row int not null,
	cell int not null,
	account_id int not null references account(id),
	constraint place_taken unique(session_id, row, cell)
);

