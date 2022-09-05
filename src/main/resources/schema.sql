-- Defina os comandos DDL aqui para criação do seu banco de dados.
create table if not exists User(
    id int primary key,
    username varchar(255) unique not null,
    password varchar(255) not null,
    authorities varchar(255) not null,
    isAccountNonExpired bool default true,
    isAccountNonLocked bool default true,
    isCredentialsNonExpired bool default true,
    isEnabled bool default true
);