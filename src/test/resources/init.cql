CREATE KEYSPACE IF NOT EXISTS spring_boot_cassandra6
WITH replication = {
    'class': 'SimpleStrategy',
    'replication_factor': 1
};

CREATE TABLE spring_boot_cassandra6.users_by_country
(
    country text,
    user_id uuid CHECK NOT_NULL,
    PRIMARY KEY ((country))
) with transactional_mode = 'mixed_reads';
