insert into cocktail_types(name)
values ('Ancestrals'),
('Sours'),
('Spirit-Forward Cocktails'),
('Duos and Trios'),
('Champagne Cocktails'),
('Highballs, Collinses, and Fizzes'),
('Juleps and Smashes'),
('Hot Drinks'),
('Flips and Nogs'),
('Pousse Family'),
('Tiki'),
('Punch'),
('Old (and Odd) Birds');

insert into ingredient_types (name)
values ('Spirits'),
('Liquor & Bitters'),
('Wines'),
('Beer & Ciders'),
('Aromatic Bitters'),
('Syrups'),
('Juices'),
('Non-alcoholic Drinks'),
('Dairy products'),
('Fruits & Berries'),
('Veggies'),
('Garnishes'),
('Ice'),
('Glasses');

insert into ingredients(name, type)
values ('White Rum', 1),
('Tequila', 1),
('Gin', 1) ,
('Vodka', 1),
('Triple Sec', 2),
('Coffee liqour', 2),
('Champagne', 3),
('Simple syrup', 6),
('Lime syrup', 6),
('Vanilla  syrup', 6),
('Blue Curacao', 6),
('Coconut syrup', 6),
('Lemon juice', 7),
('Lime juice', 7),
('Orange juice', 7),
('Grapefruit juice', 7),
('Pineapple juice', 7),
('Сranberry juice', 7),
('Soda', 8),
('Lemon Soda', 8),
('Cola', 8),
('Tonic', 8),
('Ginger Ale', 8),
('Bitter Lemon', 8),
('Cream', 9),
('Egg white', 9),
('Egg yolk', 9),
('Milk', 9),
('Ice cream', 9),
('Orange', 10),
('Lime', 10),
('Orange peel', 12),
('Lime peel', 12),
('Orange wheel', 12),
('Lime wheel', 12),
('Ice cubes', 13),
('Crashed ice', 13),
('Ice spheres', 13),
('Rocks', 14),
('Highball', 14),
('Old-fashioned', 14),
('Martini', 14),
('Coupe', 14),
('Nick & Nora', 14),
('Collins', 14),
('Margarita', 14),
('Balloon', 14);

insert into cocktails(name, type)
values('Daiquiri', 2),
('Pina Colada', 11),
('Negroni',3),
('Manhattan',3),
('Cuba Libre',6),
('Ramos Fizz',6),
('Tom Collins',6),
('Screwdriver',6),
('Old Fashioned',1),
('Whiskey Sour', 2),
('Mojito',7),
('White Russian',4),
('Black Russian',4),
('Clover Club', 2),
('Tequila Sunrise',4),
('Paloma',6),
('Long Island iced tea',6),
('Gin Tonic',6),
('Margarita', 2),
('Ancient Elixir', 1),
('Citrus Sour', 2),
('Bourbon Bliss', 3),
('Trio of Tropics', 4),
('Champagne Sparkle', 5),
('Gin Fizz Fandango', 6),
('Mint Julep Magic', 7),
('Spiced Toddy', 8),
('Eggnog Extravaganza', 9),
('Heritage Pousse', 10),
('Tiki Tangerine', 11),
('Punchy Pineapple', 12),
('Vintage Vesper', 1),
('Cranberry Collins', 2),
('Scotch Symphony', 3),
('Duo of Delights', 4),
('Prosecco Paradise', 5),
('Vodka Voltage', 6),
('Smashed Strawberry', 7),
('Cinnamon Cocoa', 8),
('Maple Flip', 9),
('Generations Punch', 10),
('Exotic Escape', 11),
('Mystery Bird', 12),
('Retro Rye', 1),
('Lemon Twist Sour', 2),
('Tequila Trailblazer', 3),
('Triple Threat', 4),
('Elegant Effervescence', 5),
('Highland Highball', 6),
('Minted Raspberry Smash', 7),
('Warm Apple Toddy', 8),
('Eggcellent Flip', 9),
('Velvet Pousse', 10),
('Tropical Tiki', 11),
('Fruitful Punch', 12),
('Majestic Manhattan', 1),
('Sour Cherry Bomb', 2),
('Cognac Cascade', 3),
('Whiskey Waltz', 4);


insert into recipes(cocktail_id, ingredient_id, quantity)
values
/* Daiquiri */
(1, 1, 60),
(1, 14, 30),
(1, 8, 15),

/* Pina Colada */
(2, 1, 40),
(2, 12, 20),
(2, 17, 100),
(2, 25, 10),

/* Cuba Libre */
(5, 1, 60),
(5, 21, 150),

/* Ramos Fizz */
(6, 3, 50),
(6, 13, 15),
(6, 14, 15),
(6, 25, 50),
(6, 8, 30),
(6, 19, 15),

/* Tom Collins */
(7, 3, 60),
(7, 14, 22.5),
(7, 8, 15),
(7, 19, 60),
(7, 35, 1),

/* Screwdriver */
(8,4,60),
(8,15,150),

/* White Russian */
(12, 4, 50),
(12, 6, 20),
(12, 25, 30),

/* Black Russian */
(13, 4, 50),
(13, 6, 20),

/* Paloma */
(16, 2, 50),
(16, 13, 5),
(16, 16, 80),
(16, 19, 20),

/* Long Island iced tea */
(17, 1, 22.5),
(17, 2, 22.5),
(17, 3, 22.5),
(17, 4, 22.5),
(17, 5, 22.5),
(17, 8, 22.5),
(17, 14, 22.5),
(17, 21, 10),

/* Gin Tonic */
(18, 3, 60),
(18, 22, 150),

/* Margarita */
(19, 2, 50),
(19, 5, 20),
(19, 14,15);

insert into parties(name, event_date, location)
values('Repka Xmas Holiday', '2023-12-14 20:00:00+03', 'Repka');

insert into menus(party_id, cocktail_id)
values(1,8),
(1,17),
(1,13);

insert into products(ingredient_id, name, price)
values(1, 'Barcelo blanco', 1.279),
(4, 'Orthodox', 0.849),
(2, 'Sierra', 2.141),
(13, 'Homemade', 0.224),
(8, 'Barinoff', 0.381),
(21, 'Coca-cola', 0.111),
(15, 'Сады Придония', 0.15),
(25, 'Простоквашино', 0.397),
(12, 'Richeza', 1.058),
(5, 'Monin Triple Sec', 2.413),
(6, 'Monin Coffee', 2.407);

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
