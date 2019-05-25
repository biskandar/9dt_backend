
create table MOVES (
    MOVE_ID varchar(50) not null,
    GAME_ID varchar(50),
    TYPE varchar(50) not null,
    PLAYER varchar(128) not null,
    COLUMN int default 0 not null,
    primary key (MOVE_ID),
    foreign key (GAME_ID) references GAMES(GAME_ID)
);
