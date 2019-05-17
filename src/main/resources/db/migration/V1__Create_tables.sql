create sequence hibernate_sequence start with 1 increment by 50 ;

create table GAMES (
    GAME_ID varchar(50) not null primary key,
    PLAYERS varchar(256) not null,
    COLUMNS int not null default 4,
    ROWS int not null default 4
);