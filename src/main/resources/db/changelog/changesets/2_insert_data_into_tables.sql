insert into legal_entity (id, name, balance)
values ('b3ec6a4c-6245-419d-b884-024a69fea3eb', 'OOO Bugorok', 100000);

insert into client (id, name)
values ('f0caf844-5a61-43a7-b1c2-e66971f5e08a', 'John Doe');

insert into legal_entity_clients (legal_entity_id, client_id)
values ('b3ec6a4c-6245-419d-b884-024a69fea3eb', 'f0caf844-5a61-43a7-b1c2-e66971f5e08a')