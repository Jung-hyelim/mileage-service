-- drop table if exists
drop table if exists first_event_check;
drop table if exists mileage;
drop table if exists mileage_detail;
drop table if exists mileage_history;

-- create table & index
create table first_event_check (
    id bigint not null auto_increment,
    event_type varchar(12) not null,
    event_key varchar(36) not null,
    primary key (id)
);
create unique index index_first_event_check_unique on first_event_check (event_type, event_key);

create table mileage (
    id bigint not null auto_increment,
    user_id varchar(36) not null,
    event_type varchar(12) not null,
    event_key varchar(36) not null,
    is_deleted bit not null,
    created_time datetime default CURRENT_TIMESTAMP,
    updated_time datetime,
    primary key (id)
);
create index index_mileage_event_type_event_key on mileage (event_type, event_key);
create index index_mileage_user_id_event_type_event_key on mileage (user_id, event_type, event_key);

create table mileage_detail (
    id bigint not null auto_increment,
    mileage_id bigint not null,
    point_type varchar(12) not null,
    point integer not null,
    created_time datetime default CURRENT_TIMESTAMP,
    updated_time datetime,
    primary key (id)
);
create index index_mileage_detail_mileage_id on mileage_detail (mileage_id);

create table mileage_history (
    id bigint not null auto_increment,
    mileage_id bigint not null,
    action varchar(12) not null,
    changed_point integer not null,
    created_time datetime default CURRENT_TIMESTAMP,
    updated_time datetime,
    primary key (id)
);
create index index_mileage_history_mileage_id on mileage_history (mileage_id);
