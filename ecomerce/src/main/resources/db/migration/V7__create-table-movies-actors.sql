create table movies_actors (
    movie_id int not null,
    actor_id int not null,
    movie_date date,
    primary key (movie_id, actor_id),
    foreign key (movie_id) references movie(id),
    foreign key (actor_id) references actors(id)
);