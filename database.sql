use facebook_api;
create table user
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    name varchar(255) null,
    password text null,
    phone_number varchar(10) null,
    link_avatar text null,
    uuid text null,
    verification_code varchar(6) null,
    token nvarchar(255) null
);
create table post
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    content text null,
    user_id int null
);
create table comment
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    content text null,
    post_id int null,
    user_id int null
);

create table blocks
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    id_blocks int null,
    id_blocked int null
);
create table friend
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    idA int null,
    idB int null
);
create table likes
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    post_id int null,
    user_id int null
);
create table file
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    content longtext null,
    post_id int null
);
create table report
(
    id int not null primary key auto_increment,
    deleted bool null,
    created_date bigint null,
    created_by nvarchar(255) null,
    modified_date bigint null,
    modified_by nvarchar(255) null,
    user_id int null,
    post_id int null
);