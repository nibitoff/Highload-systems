insert into parties(name, event_date, location)
values('Repka Xmas Holiday', '2023-12-14 20:00:00+03', 'Repka');

insert into menus(party_id, cocktail_id)
values(1,8),
(1,17),
(1,13);

insert into purchases(party_id, product_id, quantity)
values(1, 1, 1000),
(1, 2, 1000),
(1, 3, 750),
(1, 4, 500),
(1, 5, 1000),
(1, 6, 500),
(1, 7, 1000),
(1, 8, 100),
(1, 10, 1000),
(1, 11, 1000);
