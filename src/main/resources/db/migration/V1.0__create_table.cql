CREATE TABLE users_by_country
(
    country text,
    user_id uuid CHECK NOT_NULL,
    PRIMARY KEY ((country))
) with transactional_mode = 'mixed_reads';
