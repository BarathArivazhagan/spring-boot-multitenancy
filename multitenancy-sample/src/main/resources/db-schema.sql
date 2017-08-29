create schema tenanta;
create schema tenantb;
create table tenanta.user_table (user_id bigint not null auto_increment, user_name varchar(255), primary key (user_id));
create table tenantb.user_table (user_id bigint not null auto_increment, user_name varchar(255), primary key (user_id));