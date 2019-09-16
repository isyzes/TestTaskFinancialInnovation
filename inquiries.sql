CREATE DATABASE todo_app;

USE todo_app;
drop table if exists hibernate_sequence;
drop table if exists task;
drop table if exists task_logs;
drop table if exists user;

create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );

create table task (
    id bigint not null,
    name varchar(250),
    state bit,
    user_id bigint,
    primary key (id)
) engine=MyISAM;

create table task_logs (
    id bigint not null,
    comment varchar(1000),
    spent_time double precision,
    task_id bigint,
    primary key (id)
) engine=MyISAM;

create table user (
    id bigint not null,
    full_name varchar(255),
    login varchar(255),
    password varchar(255),
    primary key (id)
) engine=MyISAM;

alter table task add constraint FK2hsytmxysatfvt0p1992cw449 foreign key (user_id) references user (id)
alter table task_logs add constraint FK3mgmyjnsxbkxhyr03v9hac9en foreign key (task_id) references task (id)

-- запрос для учета времени
CREATE EVENT spent_time
    ON SCHEDULE every 1 SECOND
    DO
		UPDATE task_logs SET spent_time = spent_time + 0.00027778 WHERE task_id IN (SELECT id FROM task WHERE state = false);

-- запрос №1 по заданию
use todo_app;
SELECT USER .*, COUNT(task.user_id) total_tasks
FROM USER
JOIN task ON USER .id = task.user_id
GROUP BY USER.id;

-- запрос №2 по заданию
use todo_app;
SELECT USER .*
FROM USER
JOIN task ON USER .id = task.user_id
JOIN task_logs ON task.id = task_logs.task_id
GROUP BY USER.id HAVING SUM(task_logs.spent_time) > 100;