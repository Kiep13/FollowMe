CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    password VARCHAR(255) NOT NULL
);

CREATE TYPE country_enum AS ENUM (
    -- Europe
    'sk', 'hr', 'cz', 'at', 'hu', 'de', 'it', 'pl', 'si', 'fr',
    'es', 'gr', 'uk', 'nl', 'be', 'ch', 'pt', 'se', 'no', 'fi',
    'dk', 'ie', 'ru', 'ua', 'bg', 'ro', 'rs', 'tr', 'cy', 'mt',
    'is', 'lu', 'ee', 'lv', 'lt', 'by', 'al', 'me', 'mk', 'xk',
    'md', 'ad', 'li', 'va', 'sm', 'mc', 'gb',

    -- Asia
    'af', 'am', 'az', 'bh', 'bd', 'bt', 'bn', 'kh', 'cn', 'ge',
    'in', 'id', 'ir', 'iq', 'il', 'jp', 'jo', 'kz', 'kw', 'kg',
    'la', 'lb', 'my', 'mv', 'mn', 'mm', 'np', 'kp', 'om', 'pk',
    'ph', 'qa', 'sa', 'sg', 'kr', 'lk', 'sy', 'tw', 'tj', 'th',
    'tl', 'tm', 'ae', 'uz', 'vn', 'ye',

    -- Africa
    'dz', 'ao', 'bj', 'bw', 'bf', 'bi', 'cv', 'cm', 'cf', 'td',
    'km', 'cd', 'cg', 'ci', 'dj', 'eg', 'gq', 'er', 'sz', 'et',
    'ga', 'gm', 'gh', 'gn', 'gw', 'ke', 'ls', 'lr', 'ly', 'mg',
    'mw', 'ml', 'mr', 'mu', 'ma', 'mz', 'na', 'ne', 'ng', 'rw',
    'st', 'sn', 'sc', 'sl', 'so', 'za', 'ss', 'sd', 'tz', 'tg',
    'tn', 'ug', 'zm', 'zw',

    -- Americas
    'ag', 'ar', 'aw', 'bs', 'bb', 'bz', 'bm', 'bo', 'br', 'ca',
    'ky', 'cl', 'co', 'cr', 'cu', 'cw', 'dm', 'do', 'ec', 'sv',
    'fk', 'gd', 'gp', 'gt', 'gy', 'ht', 'hn', 'jm', 'mq', 'mx',
    'ms', 'ni', 'pa', 'py', 'pe', 'pr', 'bl', 'kn', 'lc', 'mf',
    'pm', 'vc', 'sx', 'sr', 'tt', 'tc', 'us', 'uy', 've', 'vg',
    'vi',

    -- Oceania
    'as', 'au', 'ck', 'fj', 'pf', 'gu', 'ki', 'mh', 'fm', 'nr',
    'nc', 'nz', 'nu', 'mp', 'pw', 'pg', 'ws', 'sb', 'tk', 'to',
    'tv', 'vu', 'wf'
);

CREATE TABLE IF NOT EXISTS excursion (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    date DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    amount_of_places INT NOT NULL,
    country country_enum NOT NULL
);


-- Table creation for Participation
CREATE TABLE IF NOT EXISTS participation (
    id UUID PRIMARY KEY,
    excursion_id UUID NOT NULL,
    user_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (excursion_id) REFERENCES excursion (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Table creation for Participant
CREATE TABLE IF NOT EXISTS participant (
    id UUID PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    citizenship country_enum NOT NULL,
    passport_number VARCHAR(50) NOT NULL,
    participation_id UUID NOT NULL,
    FOREIGN KEY (participation_id) REFERENCES participation (id)
);