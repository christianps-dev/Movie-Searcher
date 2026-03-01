CREATE TABLE IF NOT EXISTS movies (
    id        SERIAL PRIMARY KEY,
    film_title   varchar(255),
    film_rating  varchar(255),
    film_genre   varchar(255),
    film_year    varchar(255),
    poster_url   varchar(255),
    film_country varchar(255),
    film_type    varchar(255),
    film_plot    TEXT
);

CREATE TABLE IF NOT EXISTS  users(
    id        SERIAL PRIMARY KEY,
    username   varchar(255) UNIQUE,
    email      varchar(255) UNIQUE,
    password   varchar(255)
);

CREATE TABLE IF NOT EXISTS favorites(
    id        SERIAL PRIMARY KEY,
    movie_id   INTEGER REFERENCES movies(id),
    user_id    INTEGER REFERENCES users(id)

);
