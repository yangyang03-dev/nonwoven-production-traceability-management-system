CREATE TABLE IF NOT EXISTS Product(
  id INT NOT NULL,
  m_company varchar(256) NOT NULL,
  m_destination varchar(256) NOT NULL,
    m_quality_level varchar(256) NOT NULL,
    m_time varchar(256) NOT NULL,
    material_id1 INT NOT NULL,
    material_id2 INT NOT NULL,
    ws_name1 varchar(256) NOT NULL,
    ws_start_time1 varchar(256) NOT NULL,
    ws_end_time1 varchar(256) NOT NULL,
    ws_name2 varchar(256) NOT NULL,
    ws_start_time2 varchar(256) NOT NULL,
    ws_end_time2 varchar(256) NOT NULL,
    ws_name3 varchar(256) NOT NULL,
    ws_start_time3 varchar(256) NOT NULL,
    ws_end_time3 varchar(256) NOT NULL,
    inspection_start_time varchar(256) NOT NULL,
    inspection_end_time varchar(256) NOT NULL,
    inspection_result varchar(256) NOT NULL,
    responsible_person varchar(256) NOT NULL,
    version INT,
    PRIMARY KEY(id)
);
CREATE TABLE IF NOT EXISTS Credential(
    id INT NOT NULL,
    name varchar(256) NOT NULL,
    password varchar(256) NOT NULL,
    is_logged_in INT NOT NULL,
    PRIMARY KEY(id)
);
CREATE TABLE IF NOT EXISTS Material(
    id INT NOT NULL,
    title varchar(256) NOT NULL,
    type varchar(256) NOT NULL,
    supplier varchar(256) NOT NULL,
    pass_rate FLOAT NOT NULL,
    good_rate FLOAT NOT NULL,
    quality_level varchar(256) NOT NULL,
    inspector varchar(256) NOT NULL,
    version INT,
    PRIMARY KEY(id)
)
