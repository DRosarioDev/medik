create table if not exists users(
	id serial primary key,
	nome varchar(255) not null,
	cognome varchar(255) not null,
	email varchar(255) unique not null,
	password varchar(255) not null,
	cf varchar(255) unique not null,
	data_nascita timestamp not null,
	medico_id int references users(id),
	role varchar(10) not null default 'PAZIENTE',
	constraint chk_role check (role in ('PAZIENTE', 'MEDICO'))
);

create table if not exists hibernate_sequences (
	sequence_name varchar(255) not null,
	next_val int8,
	primary key (sequence_name)
);

create table if not exists appuntamenti(
	id serial primary key,
	titolo varchar(255) not null,
	data_inizio timestamp not null,
	data_fine timestamp not null,
	paziente_id int not null,
	medico_id int not null,
	foreign key (paziente_id) references users(id),
	foreign key (medico_id) references users(id)
);

create table if not exists ricette(
	id serial primary key,
	descrizione varchar(255) not null,
	data timestamp not null,
	urgenza varchar(10) not null default ('BASSA'),
	medico_id int not null,
	paziente_id int not null,
	foreign key (medico_id) references users(id),
	foreign key (paziente_id) references users(id),
	constraint chk_urgenza check (urgenza in ('BASSA', 'MEDIA', 'ALTA'))
	
);