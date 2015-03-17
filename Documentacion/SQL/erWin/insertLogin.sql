CREATE TABLE Users(username VARCHAR(255) PRIMARY KEY, passwd VARCHAR(255));
CREATE TABLE UserRoles(username VARCHAR(255), role VARCHAR(32)) ;


select *
from UserRoles


INSERT INTO Users (username, passwd) VALUES ('admin', '1r/ZLPNZEuFijF+yS65G2TXdGOsjlZFHsU8O8WPj3Ow=');

INSERT INTO UserRoles (username, role) VALUES ('admin', 'admin');

INSERT INTO Users (username, passwd) VALUES ('cajero', '/DWM0BA3wq84xJCinlxATVsMeBPIjli39yD58AvOj7U=');

INSERT INTO UserRoles (username, role) VALUES ('cajero', 'cajero');