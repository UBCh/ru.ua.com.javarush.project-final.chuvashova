--changeset CREATE TABLE PROJECT,MAIL_CASE, SPRINT,REFERENCE,USERS,PROFILE

CREATE TABLE PROJECT
(
    id          number primary key,
    code        varchar(32),
    title       varchar(1024),
    description varchar(4096),
    TYPE_CODE   varchar(32),
    STARTPOINT  timestamp,
    ENDPOINT    timestamp,
    PARENT_ID   number
);

INSERT into PROJECT
values (1, 'PROJECT-1', 'test project 1', 'task_tracker', null, null, null, null);
INSERT into PROJECT
values (2, 'PR2', 'PROJECT-2', 'test project 2', 'task_tracker', null, null, 1);



create table MAIL_CASE
(
    ID        bigint primary key,
    EMAIL     varchar(255) not null,
    NAME      varchar(255) not null,
    DATE_TIME timestamp    not null,
    RESULT    varchar(255) not null,
    TEMPLATE  varchar(255) not null
);

create table SPRINT
(
    ID          bigint primary key,
    STATUS_CODE varchar(32)   not null,
    STARTPOINT  timestamp,
    ENDPOINT    timestamp,
    TITLE       varchar(1024) not null,
    PROJECT_ID  bigint        not null,
    constraint FK_SPRINT_PROJECT foreign key (PROJECT_ID) references PROJECT (ID) on delete cascade
);
insert into SPRINT
values (1, 'finished', '2023-05-01 08:05:10', '2023-05-07 17:10:01', 'SP-1.001', 1),
       (2, 'active', '2023-05-01 08:06:00', null, 'SP-1.002', 1),
       (3, 'active', '2023-05-01 08:07:00', null, 'SP-1.003', 1),
       (4, 'planning', '2023-05-01 08:08:00', null, 'SP-1.004', 1),
       (5, 'active', '2023-05-10 08:06:00', null, 'SP-2.001', 2),
       (6, 'planning', '2023-05-10 08:07:00', null, 'SP-2.002', 2),
       (7, 'planning', '2023-05-10 08:08:00', null, 'SP-2.003', 2);
create table REFERENCE
(
    ID         bigint primary key,
    CODE       varchar(32)   not null,
    REF_TYPE   smallint      not null,
    ENDPOINT   timestamp,
    STARTPOINT timestamp,
    TITLE      varchar(1024) not null,
    AUX        varchar,
    constraint UK_REFERENCE_REF_TYPE_CODE unique (REF_TYPE, CODE)
);


