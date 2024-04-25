insert into legal_entity (id, name, balance, owner, status)
values ('b3ec6a4c-6245-419d-b884-024a69fea3eb', 'OOO Bugorok', 100000, 'johndoe@gmail.com', 'EXISTS');

insert into legal_entity (id, name, balance, owner,  status)
values ('b3ec6a4c-6245-419d-b884-024a69fea3ec', 'OOO UgaBuga', 10000, 'johndoe@gmail.com', 'EXISTS');

insert into client (id, name, username, password,  status)
values ('f0caf844-5a61-43a7-b1c2-e66971f5e08a', 'John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W', 'EXISTS');

insert into legal_entity_clients (legal_entity_id, client_id)
values ('b3ec6a4c-6245-419d-b884-024a69fea3eb', 'f0caf844-5a61-43a7-b1c2-e66971f5e08a');

insert into legal_entity_clients (legal_entity_id, client_id)
values ('b3ec6a4c-6245-419d-b884-024a69fea3ec', 'f0caf844-5a61-43a7-b1c2-e66971f5e08a');