alter table Products add category_id int;

alter table Products add constraint FK_movie_category
foreign key (category_id) references category(id);