CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    password VARCHAR(255) NOT NULL
);

-- Hashed password actual value 1234 with bcrypt cosr factor 10
INSERT INTO users (id, first_name, last_name, email, password, role) VALUES
('8db44b94-b254-42bb-9867-3756be46c450', 'John', 'Doe', 'admin@gmail.com', '$2a$10$Vjzdo/JS5s/4Cy8.ZU7yB.eUh1nrXp7w6lEVvILJy3zjfQgr0wS36', 'ADMIN'),
('c7b3f611-4ff2-4456-b545-4834e1accda6', 'Georgia', 'Blackwell', 'user@gmail.com', '$2a$10$Vjzdo/JS5s/4Cy8.ZU7yB.eUh1nrXp7w6lEVvILJy3zjfQgr0wS36', 'USER');

CREATE TABLE IF NOT EXISTS excursion (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    date DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    amount_of_places INT NOT NULL,
    country VARCHAR(50) NOT NULL
);

INSERT INTO excursion (title, image_url, description, date, price, amount_of_places, country) VALUES
('High Tatras Hike', 'https://breaking-the-borders.com/wp-content/uploads/2023/10/tatras-slovakia-hikes-beginners.webp', 'Spend a day hiking scenic trails, spotting waterfalls, and enjoying panoramic views from alpine peaks in the High Tatras.', '2025-06-05', 50, 20, 'Slovakia'),
('Plitvice Lakes National Park', 'https://www.akvillas.com/-/media/akvillas/experiences/croatia/explore-the-stunning-plitvice-lakes/main_plitvice-lakes-national-park--4.jpg?la=en&hash=F83198B9EC159C2B34122648744CFA698321D201', 'Discover the beauty of Plitvice Lakes, with its cascading waterfalls and vibrant turquoise lakes, on a guided tour.', '2025-06-10', 60, 15, 'Croatia'),
('Vienna City Tour', 'https://cdn.getyourguide.com/img/location/5ffebf9a37c53.jpeg/88.jpg', 'Discover the imperial charm of Vienna: Schönbrunn Palace, St. Stephen’s Cathedral, and a taste of classic Viennese coffee culture.', '2025-06-15', 70, 25, 'Austria'),
('Kraków Old Town & Wawel Castle', 'https://cdn-imgix.headout.com/media/images/02c29dbbd22083c5b54d1b127bdbb1a9-26296-krakow-krakow-wawel-castle--cathedral-and-old-town-with-st.-mary-s-basilica-visiting-03.jpg?auto=format&w=1051.2&h=540&q=90&fit=fit', 'Explore cobbled streets, medieval churches, and the legendary Wawel Castle in one of Europe’s most beautiful historic cities.', '2025-06-18', 55, 30, 'Poland'),
('Tuscany Flavors Day Trip', 'https://media-cdn.tripadvisor.com/media/attractions-splice-spp-674x446/09/c8/9f/58.jpg', 'Indulge in a full-day escape to the Tuscan countryside with wine tastings, local cheeses, and a rustic Italian lunch.', '2025-06-20', 80, 10, 'Italy'),
('Paris City Walk & Seine Cruise', 'https://cdn.tourcms.com/a/11676/2130/1/default.jpg', 'Discover Parisian charm on foot, visiting iconic landmarks like the Eiffel Tower and Notre-Dame, finishing with a relaxing Seine river cruise.', '2025-06-22', 75, 12, 'France'),
('Black Forest Adventure', 'https://d1hirb55zrpywb.cloudfront.net/macs-adventure-tours/tours/WSBOSCHW/tourimagegallery/ss-1500-typical-black-forest-scene-rsz-15032023132529449.jpg', 'Hike through lush forests, see traditional villages, and taste authentic Black Forest cake in Germany’s famous Schwarzwald region.', '2025-06-25', 65, 18, 'Germany'),
('Lake Bled & Spa Relaxation', 'https://cdn.relaxos.sk/image/1/737x493.78/43753.jpeg', 'Visit the fairy-tale Lake Bled, explore its charming island church, and unwind with a thermal spa experience.', '2025-06-28', 90, 20, 'Slovenia'),
('Danube River Kayak Tour', 'https://media.tacdn.com/media/attractions-splice-spp-674x446/10/6f/35/30.jpg', 'Enjoy a scenic kayak trip along the Danube River, passing castles, cliffs, and nature reserves on a fun outdoor adventure.', '2025-07-01', 85, 15, 'Slovakia'),
('Caving Adventure in Aggtelek National Park', 'https://media-cdn.tripadvisor.com/media/attractions-splice-spp-674x446/06/70/57/90.jpg', 'Explore the mystical Baradla Cave system, discovering stunning underground formations and fascinating local legends.', '2025-07-03', 95, 8, 'Hungary');

-- Table creation for Participation
CREATE TABLE IF NOT EXISTS participation (
    id UUID PRIMARY KEY,
    excursion_id SERIAL NOT NULL,
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
    citizenship VARCHAR(50) NOT NULL,
    passport_number VARCHAR(50) NOT NULL,
    participation_id UUID NOT NULL,
    FOREIGN KEY (participation_id) REFERENCES participation (id)
);
