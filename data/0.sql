CREATE TABLE station (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  create_time datetime NOT NULL,
  update_time datetime NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE nurse (
  id bigint NOT NULL,
  name varchar(45) NOT NULL,
  create_time timestamp NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE nurse_station_mapping (
  nurse_id bigint NOT NULL,
  station_id bigint NOT NULL,
  create_time timestamp NOT NULL,
  PRIMARY KEY (nurse_id,station_id)
);
