alter table movie add category_id int;

alter table movie add constraint FK_movie_category
foreign key (category_id) references category(id);