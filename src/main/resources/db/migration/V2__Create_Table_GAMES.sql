
create table GAMES (
    GAME_ID varchar(50) not null,
    PLAYERS varchar(256) not null,
    COLUMNS int not null default 4,
    ROWS int not null default 4,
    STATE varchar(50),
    WINNER varchar(128),
    primary key (GAME_ID)
);
