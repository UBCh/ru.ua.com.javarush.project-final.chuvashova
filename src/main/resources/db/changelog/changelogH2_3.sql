--changeset CREATE TABLE task

create table task
(
    id          number primary key,
    title       varchar(1024),
    TYPE_CODE   varchar(32),
    STATUS_CODE varchar(32),
    PROJECT_ID  number,
    SPRINT_ID   number,
    PARENT_ID   number,
    STARTPOINT  timestamp,
    ENDPOINT    timestamp
);

insert into task
values (1, 'Data', 'epic', 'in_progress', 1, 1, 1, '2023-05-15 09:05:10', null),
       (2, 'Trees', 'epic', 'in_progress', 1, 1, 1, '2023-05-15 12:05:10', null),
       (3, 'task-3', 'task', 'ready_for_test', 2, 5, 1, '2023-06-14 09:28:10', null),
       (4, 'task-4', 'task', 'ready_for_review', 2, 5, 1, '2023-06-14 09:28:10', null),
       (5, 'task-5', 'task', 'todo', 2, 5, 1, '2023-06-14 09:28:10', null),
       (6, 'task-6', 'task', 'done', 2, 5, 1, '2023-06-14 09:28:10', null),
       (7, 'task-7', 'task', 'canceled', 2, 5, 1, '2023-06-14 09:28:10', null);

CREATE TABLE contact
(
    id    bigint primary key,
    code  varchar(32),
    walue varchar(4096)
);


INSERT into contact
values (1, 'skype', 'userSkype'),
       (2, 'mobile', '+01234567890'),
       (3, 'website', 'user.com'),
       (4, 'github', 'adminGitHub'),
       (5, 'tg', 'adminTg'),
       (6, 'vk', 'adminVk');

CREATE TABLE USERS
(
    id           number primary key,
    EMAIL        varchar(128),
    PASSWORD     varchar(128),
    FIRST_NAME   varchar(32),
    LAST_NAME    varchar(32),
    DISPLAY_NAME varchar(32),
    role         varchar(128)
);



insert into USERS
values (1, 'user@gmail.com', '{noop}password', 'userFirstName', 'userLastName', 'userDisplayName', 'user'),
       (2, 'admin@gmail.com', '{noop}admin', 'adminFirstName', 'adminLastName', 'adminDisplayName', 'admin'),
       (3, 'guest@gmail.com', '{noop}guest', 'guestFirstName', 'guestLastName', 'guestDisplayName', 'guest'),
       (4, 'manager@gmail.com', '{noop}manager', 'managerFirstName', 'managerLastName', 'managerDisplayName',
        'manager');

create table USER_ROLE
(
    USER_ID bigint   not null,
    ROLE    smallint not null,
    constraint UK_USER_ROLE unique (USER_ID, ROLE),
    constraint FK_USER_ROLE foreign key (USER_ID) references USERS (ID) on delete cascade
);
insert into USER_ROLE
values (1, 0),
       (2, 0),
       (2, 1),
       (4, 2);

create table PROFILE
(
    id                 bigint primary key,
    LAST_LOGIN         timestamp,
    LAST_FAILED_LOGIN  timestamp,
    MAIL_NOTIFICATIONS bigint
);
insert into PROFILE
values (1, null, null, 49),
       (2, null, null, 14);

create table ACTIVITY
(
    ID            bigint primary key,
    AUTHOR_ID     bigint not null,
    TASK_ID       bigint not null,
    UPDATED       timestamp,
    COMMENT       varchar(4096),

    TITLE         varchar(1024),
    DESCRIPTION   varchar(4096),
    ESTIMATE      integer,
    TYPE_CODE     varchar(32),
    STATUS_CODE   varchar(32),
    PRIORITY_CODE varchar(32),
    constraint FK_ACTIVITY_USERS foreign key (AUTHOR_ID) references USERS (ID),
    constraint FK_ACTIVITY_TASK foreign key (TASK_ID) references TASK (ID) on delete cascade
);
insert into ACTIVITY
values (1, 1, 1, '2023-05-15 09:05:10', null, 'Data', null, 3, 'epic', 'in_progress', 'low'),
       (2, 2, 1, '2023-05-15 12:25:10', null, 'Data', null, null, null, null, 'normal'),
       (3, 1, 1, '2023-05-15 14:05:10', null, 'Data', null, 4, null, null, null),
       (4, 1, 2, '2023-05-15 12:05:10', null, 'Trees', 'Trees desc', 4, 'epic', 'in_progress', 'normal');



create table TASK_TAG
(
    ID      bigint      not null,
    TASK_ID bigint      not null,
    TAG     varchar(32) not null,
    constraint UK_TASK_TAG unique (TASK_ID, TAG),
    constraint FK_TASK_TAG foreign key (TASK_ID) references TASK (ID) on delete cascade
);

create table USER_BELONG
(
    ID             bigint primary key,
    OBJECT_ID      bigint      not null,
    OBJECT_TYPE    smallint    not null,
    USER_ID        bigint      not null,
    USER_TYPE_CODE varchar(32) not null,
    STARTPOINT     timestamp,
    ENDPOINT       timestamp,
    constraint FK_USER_BELONG foreign key (USER_ID) references USERS (ID)
);


create table ATTACHMENT
(
    ID          bigint primary key,
    NAME        varchar(128)  not null,
    FILE_LINK   varchar(2048) not null,
    OBJECT_ID   bigint        not null,
    OBJECT_TYPE smallint      not null,
    USER_ID     bigint        not null,
    DATE_TIME   timestamp,
    constraint FK_ATTACHMENT foreign key (USER_ID) references USERS (ID)
);





