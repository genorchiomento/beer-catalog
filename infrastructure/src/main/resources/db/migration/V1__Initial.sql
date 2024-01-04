
CREATE TABLE beer (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    style VARCHAR(255),
    origin VARCHAR(255),
    ibu DOUBLE,
    abv DOUBLE,
    color VARCHAR(255),
    ingredients VARCHAR(4000),
    flavor_description VARCHAR(4000),
    aroma_description VARCHAR(4000),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6)
);