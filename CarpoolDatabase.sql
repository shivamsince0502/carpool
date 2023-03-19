drop database if exists carpool;
create database carpool;
use carpool;

drop table if exists pooler;
create table pooler(
	pooler_id int  auto_increment, 
	pooler_name varchar(655) not null, 
	pooler_email varchar(655) not null unique, 
	pooler_mob varchar(10) not null unique,
	pooler_username varchar(655) not null unique, 
	pooler_password varchar(655) not null, 
	primary key(pooler_id)
);

drop table if exists owner;
create table owner(
	owner_id int auto_increment, 
	owner_name varchar(655), 
	owner_email varchar(655) not null unique, 
	owner_mob varchar(10) not null unique,
	owner_username varchar(655) not null unique, 
	owner_password varchar(655) not null, 
	primary key(owner_id)
);


drop table if exists admin;
create table admin(
	admin_id int  auto_increment, 
	admin_name varchar(655), 
	admin_email varchar(655) not null unique, 
	admin_mob varchar(10) not null unique,
	admin_username varchar(655) not null unique, 
	admin_password varchar(655) not null, 
	primary key(admin_id)
);


drop table if exists cities;
create table city(
	city_id int auto_increment,
	city_name varchar(655), 
	primary key(city_id)
);


drop table if exists car;
create table car(
	car_id int  auto_increment,
	car_name varchar(655) not null, 
	car_color varchar(655) not null, 
	car_number varchar(655) not null,
	primary key(car_id)
);

drop table if exists owner_cars;
create table owner_cars(
	owner_cars_id int auto_increment, 
	owner_id int not null, 
	car_id int not null,
	primary key (owner_cars_id),
	foreign key (owner_id) references owner(owner_id), 
	foreign key (car_id) references car(car_id)
);

drop table if exists ride;
create table ride(
	ride_id int auto_increment, 
	owner_id int not null, 
	car_id int not null, 
	no_of_seats int, 
	is_active bool, 
	ride_datetime datetime,
	primary key (ride_id),
	foreign key (owner_id) references owner(owner_id), 
	foreign key (car_id) references car(car_id)
);

drop table if exists ride_cities;
create table ride_cities(
	ride_cities_id int auto_increment,
	ride_id int not null, 
	city_id int not null, 
	primary key (ride_cities_id),
	foreign key (ride_id) references ride(ride_id), 
	foreign key (city_id) references city(city_id)
);

drop table if exists ride_pooler;
create table ride_pooler(
	ride_pooler_id int auto_increment, 
	ride_id int not null, 
	pooler_id int not null, 
	start_id int not null,
	end_id int not null,
	seat_no int, 
	is_active bool,
	primary key(ride_pooler_id),
	foreign key (ride_id) references ride(ride_id), 
	foreign key (pooler_id) references pooler(pooler_id),
	foreign key (start_id) references city(city_id),
	foreign key (end_id) references city(city_id)
	
);

drop table if exists deletedride;
create table deletedride(
	deleteride_id int auto_increment primary key,
	ride_id int not null,
	foreign key (ride_id) references ride(ride_id)
);

drop table if exists deletepoolerride;
create table deletepoolerride(
	deletepoolerride_id int auto_increment primary key, 
	ride_pooler_id int not null unique,
	foreign key (ride_pooler_id) references ride_pooler(ride_pooler_id)
);

drop table if exists pool_request;
create table pool_request(
	poolrequest_id int auto_increment primary key,
	ride_pooler_id int not null,
	is_approved bool not null,
	is_seen bool not null default false,
	foreign key (ride_pooler_id) references ride_pooler(ride_pooler_id)
);

drop table if exists poolernotification;
create table poolernotification(
	notification_id int auto_increment primary key, 
	pooler_id int not null,
	message text, 
	is_read bool not null default false,
	foreign key (pooler_id) references pooler(pooler_id)
);


drop table if exists ownernotification;
create table ownernotification(
	notification_id int auto_increment primary key, 
	owner_id int not null,
	message text, 
	is_read bool not null default false,
	foreign key (owner_id) references owner(owner_id)
);



