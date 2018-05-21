
--
-- appliance
-- table for storing info and state of registered appliances
--

create table appliance (
    id      uuid primary key,
    type    text not null,
    status  text not null
);

--
-- command
-- user insert commands to appliances in this tables
-- entries are fetched by appliance
--

create table command (
    id            long primary key auto_increment,
    appliance_id  uuid unique references appliance(id),
    command       text not null
);

