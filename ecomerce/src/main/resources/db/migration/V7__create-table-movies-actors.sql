create table movies_actors (
    Products_id int not null,
    actor_id int not null,
    Products_date date,
    primary key (Products_id, actor_id),
    foreign key (Products_id) references Products(id),
    foreign key (actor_id) references actors(id)
);