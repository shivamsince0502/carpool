use carpool;
insert into pooler(pooler_id, pooler_name, pooler_email, pooler_mob, pooler_username, pooler_password)
values(1, "Shivam", "shivam@gmail.com", "8789595982", "shivamsince0502", "shivam123"	),
	  (2, "Keshav", "Keshav@gmail.com", "4354678765", "keshavDeo", "keshav123"	),
	  (3, "Sachin", "sachin@gmail.com", "9875645342", "sachinkeral", "sachin123"	);


insert owner(owner_id, owner_name, owner_email, owner_mob, owner_username, owner_password)
values(1, "Aftab", "Af@gmail.com", "53423245", "aftabo", "123"),
	  (2, "Karan", "Ka@gmail.com", "43456764", "karan", "123");
	 
insert into car(car_id, car_name, car_color, car_number)
values(1, "BMW-SF", "red", "TS09LADF"),
	  (2, "LAMBO-NSF", "black", "TS04JADF"),
	  (3, "MG-HECTRE", "grey", "TS09LTFF");

insert into owner_cars(owner_cars_id, owner_id, car_id)
values(1, 1, 1),
	  (2, 2, 2),
	   (3, 2, 3);
	  
insert into ride(ride_id, owner_id, car_id, no_of_seats, is_active, ride_datetime)
values(1, 1, 1, 2, true, now()),
	  (2, 2, 2, 2, true, now());
	
insert into city(city_id, city_name)
values(1, "Patna"),
	  (2, "Kankarbagh"),
	  (3, "Gandhi Maindan"),
	  (4, "Boring Road"),
	  (5, "Phulwari Sarif"),
	  (6, "NIT Ghat");
	 
insert into ride_cities(ride_cities_id, ride_id, city_id)
values(1, 1, 1),
	  (2, 1, 2),
	  (3, 1, 3),
	  (4, 1, 4),
	  (5, 1, 5),
	  (6, 1, 6),
	  (8, 2, 1),
	  (9, 2, 2),
	  (10, 2, 4),
	  (11, 2, 3),
	  (12, 2, 5);
	 
insert into ride_pooler(ride_pooler_id, ride_id, pooler_id, seat_no, is_active)
values(1, 1,  1, 2, true),
	  (2, 1,  2, 3, true),
	  (3, 2,  3, 4, true);
	 
	 
insert into city(city_id, city_name)
	values(7, "Bermo"),
		  (8, "Sector4"),
		  (9, "Sector5"),
		  (10, "Chass"),
		  (11, "Banaras"),
		  (12, "Darbhanga");
		 
insert into city(city_id, city_name)
	values(13, "Hyderabad"),
		  (14, "Secunderabad"),
		  (15, "Saroornagar"),
		  (16, "Sindhi Colony"),
		  (17, "Miyapur"),
		  (18, "Ameerpet"),
		  (19, "Lalaguda"),
		  (20, "Uppuguda"),
		  (21, "Yousufguda"),
		  (22, "Toli Chowki"),
		  (23, "Balangar"),
		  (24, "Malakjgiri"),
		  (25, "Hyderabad"),
		  (26, "Chilkalguda"),
		  (27, "Basheer BAgh"),
		  (28, "Bagh Lingampally"),
		  (29, "Laad Bazar"),
		  (30, "Edi Bazaar");
	  
