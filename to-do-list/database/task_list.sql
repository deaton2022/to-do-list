drop database if exists task_list;

create database task_list;
use task_list;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table importance (
importance_id int primary key auto_increment,
importance_type text
);

create table task (
task_id int primary key auto_increment,
importance_id int not null,
task_name text not null,
task_description text,
due_date date not null,
app_user_id int not null,
complete bit not null default(0),
	constraint fk_importance_id
		foreign key (importance_id)
		references importance(importance_id),
    constraint fk_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id)
);

insert into app_role (`name`) values
    ('USER');
    
insert into app_user (username, password_hash, enabled)
    values
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
    values
    (1, 1),
    (2, 1);
    
INSERT INTO importance (importance_type)
	values
    ('Essential'),
    ('Moderate'),
    ('Low');

INSERT INTO task (importance_id, task_name, task_description, due_date,app_user_id,complete)
	values
  (1,"Udemy Course","Finish Angie's Udemy Course", "2023-09-10",1,0),
  (2,"Go Running on Lunch","Take a 5 minute run on lunch", "2023-09-20",1,0);
  