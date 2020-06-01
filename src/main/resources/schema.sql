-- Drop DB
DROP TABLE IF EXISTS client_account;
DROP TABLE IF EXISTS client;

-- Create DB
create table client
(
    client_id bigint auto_increment primary key,
    name      varchar(250) not null
);

create table client_account
(
    client_account_id bigint auto_increment primary key,
    client_id         bigint  not null,
    balance           decimal not null default 0.00
);

alter table client_account
    add constraint fk_client_account_reference_client foreign key (client_id)
        references client (client_id);