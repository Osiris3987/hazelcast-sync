create table legal_entity
(
    id varchar(36) primary key,
    name varchar not null,
    balance decimal not null
);

create table client
(
    id varchar(36) primary key,
    name varchar not null
);

create table transaction
(
    id varchar(36) primary key,
    type varchar(10) not null,
    amount decimal not null,
    client_id varchar(36) not null,
    legal_entity_id varchar(36) not null,
    constraint transaction_client foreign key (client_id) references client(id),
    constraint transaction_legal_entity foreign key (legal_entity_id) references legal_entity(id)
);

create table legal_entity_clients
(
    legal_entity_id      varchar(36) not null,
    client_id varchar(36) not null,
    constraint legal_entity_clients_unique unique (legal_entity_id, client_id),
    constraint legal_entity_clients_legal_entity foreign key (legal_entity_id) references legal_entity (id),
    constraint legal_entity_clients_client foreign key (client_id) references client (id)
);
