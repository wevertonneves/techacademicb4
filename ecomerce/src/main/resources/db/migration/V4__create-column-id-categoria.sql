alter table produtos
add categoria_id INT;
alter table produtos
add constraint fk_categoria FOREIGN Key (categoria_id) references categoria(id);