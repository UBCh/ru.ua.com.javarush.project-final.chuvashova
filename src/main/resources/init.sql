CREATE TEMPORARY TABLE IF NOT EXISTS PROJECT
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
values (1, 'PROJECT-1', 'test project 1', 'task_tracker', 'task_tracker', null, null, 2);
INSERT into PROJECT
values (2, 'PR2', 'PROJECT-2', 'test project 2', 'task_tracker', null, null, 1);



CREATE TEMPORARY TABLE IF NOT EXISTS MAIL_CASE
(
    ID        bigint primary key,
    EMAIL     varchar(255) not null,
    NAME      varchar(255) not null,
    DATE_TIME timestamp    not null,
    RESULT    varchar(255) not null,
    TEMPLATE  varchar(255) not null
);

CREATE TEMPORARY TABLE IF NOT EXISTS sprint
(
    id          number primary key,
    STATUS_CODE varchar(32) not null,
    STARTPOINT  timestamp,
    ENDPOINT    timestamp,
    code        varchar(32),
    PROJECT_ID  bigint      not null
);


insert into sprint
values (1, 'finished', '2023-05-01 08:05:10', '2023-05-07 17:10:02', 'SP-1.001', 1),
       (2, 'active', '2023-05-01 08:06:00', null, 'SP-1.002', 1),
       (3, 'active', '2023-05-01 08:07:00', null, 'SP-1.003', 1),
       (4, 'planning', '2023-05-01 08:08:00', null, 'SP-1.004', 1),
       (5, 'active', '2023-05-10 08:06:00', null, 'SP-2.001', 4),
       (6, 'planning', '2023-05-10 08:07:00', null, 'SP-2.002', 5);

CREATE TEMPORARY TABLE IF NOT EXISTS reference
(
    id         bigint primary key,
    code       varchar(32)   not null,
    ref_type   smallint      not null,
    endpoint   timestamp,
    startpoint timestamp,
    title      varchar(1024) not null,
    aux        varchar

);
insert into reference (id, code, title, ref_type)
-- TASK
values (1, 'task', 'Task', 2),
       (2, 'story', 'Story', 2),
       (3, 'bug', 'Bug', 2),
       (4, 'epic', 'Epic', 2),
-- SPRINT_STATUS
       (5, 'planning', 'Planning', 4),
       (6, 'active', 'Active', 4),
       (7, 'finished', 'Finished', 4),
-- USER_TYPE
       (8, 'author', 'Author', 5),
       (9, 'developer', 'Developer', 5),
       (10, 'reviewer', 'Reviewer', 5),
       (11, 'tester', 'Tester', 5),
-- PROJECT
       (12, 'scrum', 'Scrum', 1),
       (13, 'task_tracker', 'Task tracker', 1),
-- CONTACT
       (14, 'skype', 'Skype', 0),
       (15, 'tg', 'Telegram', 0),
       (16, 'mobile', 'Mobile', 0),
       (17, 'phone', 'Phone', 0),
       (18, 'website', 'Website', 0),
       (19, 'vk', 'VK', 0),
       (20, 'linkedin', 'LinkedIn', 0),
       (21, 'github', 'GitHub', 0),
-- PRIORITY
       (22, 'critical', 'Critical', 7),
       (23, 'high', 'High', 7),
       (24, 'normal', 'Normal', 7),
       (25, 'low', 'Low', 7),
       (26, 'neutral', 'Neutral', 7);

insert into reference (id, code, title, ref_type, aux)
-- MAIL_NOTIFICATION
values (27, 'assigned', 'Assigned', 6, '1'),
       (28, 'three_days_before_deadline', 'Three days before deadline', 6, '2'),
       (29, 'two_days_before_deadline', 'Two days before deadline', 6, '4'),
       (30, 'one_day_before_deadline', 'One day before deadline', 6, '8'),
       (31, 'deadline', 'Deadline', 6, '16'),
       (32, 'overdue', 'Overdue', 6, '32'),
-- TASK_STATUS
       (33, 'todo', 'ToDo', 3, 'in_progress,canceled'),
       (34, 'in_progress', 'In progress', 3, 'ready_for_review,canceled'),
       (35, 'ready_for_review', 'Ready for review', 3, 'review,canceled'),
       (36, 'review', 'Review', 3, 'in_progress,ready_for_test,canceled'),
       (37, 'ready_for_test', 'Ready for test', 3, 'test,canceled'),
       (38, 'test', 'Test', 3, 'done,in_progress,canceled'),
       (39, 'done', 'Done', 3, 'canceled'),
       (40, 'canceled', 'Canceled', 3, null);



CREATE TEMPORARY TABLE IF NOT EXISTS task
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
values (1, 'Data', 'epic', 'ready_for_review', 1, 1, null, '2023-05-15 09:05:10', null),
       (2, 'Trees', 'epic', 'in_progress', 1, 2, null, '2023-05-15 12:05:10', null),
       (3, 'task-3', 'task', 'ready_for_test', 2, 5, 1, '2023-06-14 09:28:10', null),
       (4, 'task-4', 'task', 'ready_for_review', 2, 5, 1, '2023-06-14 09:28:10', null),
       (5, 'task-5', 'task', 'todo', 2, 5, 1, '2023-06-14 09:28:10', null),
       (6, 'task-6', 'task', 'done', 2, 5, 1, '2023-06-14 09:28:10', null),
       (7, 'task-7', 'task', 'canceled', 2, 5, 1, '2023-06-14 09:28:10', null);

CREATE TEMPORARY TABLE IF NOT EXISTS contact
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

CREATE TEMPORARY TABLE IF NOT EXISTS users
(
    id           number primary key,
    email        varchar(128),
    password     varchar(128),
    first_name   varchar(32),
    last_name    varchar(32),
    display_name varchar(32),
    role         varchar(128),
    endpoint     varchar(128),
    startpoint   varchar(128)
);



insert into users
values (1, 'user@gmail.com', '{noop}password', 'userFirstName', 'userLastName', 'userDisplayName', 'user',
        '2023-05-15 12:05:10',
        null),
       (2, 'admin@gmail.com', '{noop}admin', 'adminFirstName', 'adminLastName', 'adminDisplayName', 'admin',
        '2023-05-15 12:05:10',
        null),
       (3, 'guest@gmail.com', '{noop}guest', 'guestFirstName', 'guestLastName', 'guestDisplayName', 'guest',
        '2023-05-15 12:05:10',
        null),
       (4, 'manager@gmail.com', '{noop}manager', 'managerFirstName', 'managerLastName', 'managerDisplayName', 'manager',
        '2023-05-15 12:05:10', null);


CREATE TEMPORARY TABLE IF NOT EXISTS USER_ROLE
(
    USER_ID bigint   not null,
    ROLE    smallint not null
);
insert into USER_ROLE
values (1, 0),
       (2, 0),
       (2, 1),
       (4, 2);

CREATE TEMPORARY TABLE IF NOT EXISTS PROFILE
(
    id                 bigint primary key,
    LAST_LOGIN         timestamp,
    LAST_FAILED_LOGIN  timestamp,
    MAIL_NOTIFICATIONS bigint
);
insert into PROFILE
values (1, null, null, 49),
       (2, null, null, 14);

CREATE TEMPORARY TABLE IF NOT EXISTS ACTIVITY
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
    PRIORITY_CODE varchar(32)
);
insert into ACTIVITY
values (1, 1, 1, '2023-05-15 09:05:10', null, 'Data', null, 3, 'epic', 'in_progress', 'low'),
       (2, 2, 1, '2023-05-15 12:25:10', null, 'Data', null, null, null, null, 'normal'),
       (3, 1, 1, '2023-05-15 14:05:10', null, 'Data', null, 4, null, null, null),
       (4, 1, 2, '2023-05-15 12:05:10', null, 'Trees UPD', 'task UPD', 4, 'epic', 'ready_for_review', 'high'),
       (5, 1, 2, null, null, 'Trees', 'Trees desc', 4, 'epic', 'in_progress', 'normal');



CREATE TEMPORARY TABLE IF NOT EXISTS TASK_TAG
(
    ID      bigint      not null,
    TASK_ID bigint      not null,
    TAG     varchar(32) not null
);

CREATE TEMPORARY TABLE IF NOT EXISTS USER_BELONG
(
    ID             bigint primary key,
    object_id      bigint      not null,
    OBJECT_TYPE    smallint    not null,
    user_id        bigint      not null,
    USER_TYPE_CODE varchar(32) not null,
    STARTPOINT     timestamp,
    ENDPOINT       timestamp

);
insert into USER_BELONG
values (1, 1, 2, 2, 'task_developer', '2023-06-14 08:35:10', '2023-06-14 08:55:00'),
       (2, 1, 2, 2, 'task_reviewer', '2023-06-14 09:35:10', null),
       (3, 1, 2, 1, 'task_developer', '2023-06-12 11:40:00', '2023-06-12 12:35:00'),
       (4, 1, 2, 1, 'task_developer', '2023-06-13 12:35:00', null),
       (5, 1, 2, 1, 'task_tester', '2023-06-14 15:20:00', null),
       (6, 2, 2, 2, 'task_developer', '2023-06-08 07:10:00', null),
       (7, 2, 2, 1, 'task_developer', '2023-06-09 14:48:00', null),
       (8, 2, 2, 1, 'task_tester', '2023-06-10 16:37:00', null);


CREATE TEMPORARY TABLE IF NOT EXISTS ATTACHMENT
(
    ID          bigint primary key,
    NAME        varchar(128)  not null,
    FILE_LINK   varchar(2048) not null,
    OBJECT_ID   bigint        not null,
    OBJECT_TYPE smallint      not null,
    USER_ID     bigint        not null,
    DATE_TIME   timestamp
);




