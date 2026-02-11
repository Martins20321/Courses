create table patient(
    id bigint not null auto_increment,
        name varchar(100) not null,
        email varchar(100) not null unique,
        phone varchar(20) not null,
        cpf char(11) not null unique,
        street_Adress varchar(100) not null,
        neighborhood varchar(100) not null,
        postal_Code varchar(9) not null,
        additional_Information varchar(100),
        number varchar(20),
        state char(2) not null,
        city varchar(100) not null,

        primary key(id)
);