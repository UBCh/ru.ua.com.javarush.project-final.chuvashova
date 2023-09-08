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

create table reference
(
    ID         bigint primary key,
    CODE       varchar(32)   not null,
    REF_TYPE   smallint      not null,
    ENDPOINT   timestamp,
    STARTPOINT timestamp,
    title      varchar(1024) not null,
    AUX        varchar

);
insert into reference (ID, CODE, TITLE, REF_TYPE)
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

insert into reference (ID, CODE, TITLE, REF_TYPE, AUX)
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


