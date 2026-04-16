INSERT INTO Discipline (name) VALUES
('Painting'),
('Sculpture'),
('Printmaking'),
('Photography'),
('Decorative Arts');

INSERT INTO Artist (name, bio, birthYear, isActive, city) VALUES
('Georges Seurat','Pointillist painter',1859,FALSE,'Paris'),
('Camille Pissarro','Impressionist painter',1830,FALSE,'Paris'),
('Edgar Degas','Painter and sculptor',1834,FALSE,'Paris'),
('Gustave Courbet','Realist painter',1819,FALSE,'Ornans'),
('Jean-Baptiste-Camille Corot','Landscape painter',1796,FALSE,'Paris'),
('Mary Cassatt','American painter',1844,FALSE,'Paris'),
('Winslow Homer','American painter',1836,FALSE,'Boston'),
('Thomas Eakins','Realist painter',1844,FALSE,'Philadelphia'),
('John Singer Sargent','Portrait painter',1856,FALSE,'Florence'),
('James McNeill Whistler','Tonalist painter',1834,FALSE,'Lowell'),
('Paul Cézanne','Post-Impressionist painter',1839,FALSE,'Aix'),
('Henri Matisse','Fauvist painter',1869,FALSE,'Le Cateau'),
('Amedeo Modigliani','Modern painter',1884,FALSE,'Livorno'),
('Egon Schiele','Expressionist painter',1890,FALSE,'Vienna'),
('Gustav Klimt','Symbolist painter',1862,FALSE,'Vienna'),
('Edvard Munch','Expressionist painter',1863,FALSE,'Oslo'),
('Piet Mondrian','Abstract painter',1872,FALSE,'Amersfoort'),
('Wassily Kandinsky','Abstract artist',1866,FALSE,'Moscow'),
('Kazimir Malevich','Suprematist artist',1879,FALSE,'Kyiv'),
('Paul Klee','Modern artist',1879,FALSE,'Bern'),
('Constantin Brancusi','Sculptor',1876,FALSE,'Hobita'),
('Alberto Giacometti','Sculptor',1901,FALSE,'Borgonovo'),
('Henry Moore','Sculptor',1898,FALSE,'Castleford'),
('Barbara Hepworth','Sculptor',1903,FALSE,'Wakefield'),
('Louise Bourgeois','Sculptor',1911,FALSE,'Paris'),
('Ansel Adams','Photographer',1902,FALSE,'San Francisco'),
('Walker Evans','Photographer',1903,FALSE,'St. Louis'),
('Dorothea Lange','Photographer',1895,FALSE,'Hoboken'),
('Man Ray','Photographer',1890,FALSE,'Philadelphia'),
('Diane Arbus','Photographer',1923,FALSE,'New York'),
('Unknown Greek Artisan','Ancient artist',NULL,FALSE,NULL),
('Unknown Egyptian Artisan','Ancient artist',NULL,FALSE,NULL),
('Unknown Japanese Printmaker','Ukiyo-e artist',NULL,FALSE,NULL),
('Unknown Persian Artist','Miniature artist',NULL,FALSE,NULL),
('Unknown Medieval Artist','Religious art',NULL,FALSE,NULL),
('Rembrandt van Rijn','Dutch master',1606,FALSE,'Leiden'),
('Johannes Vermeer','Baroque painter',1632,FALSE,'Delft'),
('Peter Paul Rubens','Flemish painter',1577,FALSE,'Siegen'),
('Diego Velázquez','Spanish painter',1599,FALSE,'Seville'),
('Francisco Goya','Spanish painter',1746,FALSE,'Fuendetodos'),
('El Greco','Renaissance painter',1541,FALSE,'Crete'),
('Sandro Botticelli','Italian painter',1445,FALSE,'Florence'),
('Raphael','Renaissance painter',1483,FALSE,'Urbino'),
('Titian','Venetian painter',1488,FALSE,'Pieve'),
('Caravaggio','Baroque painter',1571,FALSE,'Milan'),
('Albrecht Dürer','German artist',1471,FALSE,'Nuremberg'),
('Hans Holbein','Portrait painter',1497,FALSE,'Augsburg'),
('Jean Fouquet','French painter',1420,FALSE,'Tours'),
('Giotto di Bondone','Proto-Renaissance',1267,FALSE,'Florence'),
('Duccio di Buoninsegna','Sienese painter',1255,FALSE,'Siena');

INSERT INTO Artist 
(name, bio, birthYear, contactEmail, city, isActive, phone, website, socialMedia)
VALUES
('Cecily Brown','Contemporary British painter',1969,'contact@cecilybrownstudio.com','London',TRUE,'+44 20 7946 0958','www.cecilybrown.com','instagram.com/cecilybrown'),
('Julie Mehretu','Ethiopian-American painter',1970,'info@mehretustudio.com','New York',TRUE,'+1 212 555 0191','www.juliemehretu.com','instagram.com/juliemehretu'),
('Mark Bradford','American contemporary artist',1961,'studio@markbradford.com','Los Angeles',TRUE,'+1 310 555 0112','www.markbradfordstudio.com','instagram.com/markbradford'),
('Njideka Akunyili Crosby','Nigerian visual artist',1983,'contact@njideka.com','Los Angeles',TRUE,'+1 323 555 0147','www.njideka.com','instagram.com/njideka'),
('Jordan Casteel','American painter',1989,'info@jordan-casteel.com','New York',TRUE,'+1 917 555 0183','www.jordancasteel.com','instagram.com/jordancasteel');

UPDATE Artist
SET 
contactEmail = CONCAT(LOWER(REPLACE(name,' ','_')), '@artlegacy.org'),
phone = CONCAT('+33 1 55 ', FLOOR(RAND()*9000)+1000),
website = CONCAT('www.', LOWER(REPLACE(name,' ','-')), '.org'),
socialMedia = CONCAT('instagram.com/', LOWER(REPLACE(name,' ','_')))
WHERE artist_id IN (
    SELECT artist_id FROM (
        SELECT artist_id 
        FROM Artist
        WHERE isActive = FALSE
        ORDER BY RAND()
        LIMIT 10
    ) AS tmp
);

INSERT INTO has_a_style VALUES
-- Painting (1)
(1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),
(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),
(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),
(46,1),(47,1),(48,1),(49,1),(50,1),

-- ➕ NEW alive painters (Painting)
(51,1),(52,1),(53,1),(54,1),(55,1),

-- Sculpture (2)
(21,2),(22,2),(23,2),(24,2),(25,2),

-- Photography (4)
(26,4),(27,4),(28,4),(29,4),(30,4),

-- Decorative / ancient (5)
(31,5),(32,5),(33,3),(34,5),(35,5);

INSERT INTO Gallery (name, address, ownerName, rating, website) VALUES
('Metropolitan Museum','New York','Met',4.90,'www.metmuseum.org'),
('Louvre Contemporary','Paris','French State',4.85,'www.louvre.fr'),
('Tate Modern','London','Tate Group',4.75,'www.tate.org.uk'),
('Uffizi Gallery','Florence','Italian State',4.80,'www.uffizi.it'),
('MoMA','New York','MoMA Board',4.88,'www.moma.org'),
('National Gallery','London','UK Government',4.70,'www.nationalgallery.org.uk'),
('Berlin Art Space','Berlin','Berlin City',4.60,'www.berlinart.de'),
('Tokyo Art Center','Tokyo','Tokyo Arts Council',4.65,'www.tokyoart.jp'),
('Madrid Fine Arts','Madrid','Spanish Ministry',4.72,'www.madridfinearts.es'),
('Amsterdam Rijks Studio','Amsterdam','Rijksmuseum',4.78,'www.rijksmuseum.nl');

INSERT INTO Exhibition (title, startDate, endDate, gallery_id) VALUES
('Impressionist Highlights','2024-01-01','2026-12-31',2),
('Modern Abstract Forms','2025-03-01','2026-10-01',5),
('Renaissance Masters','2023-05-01','2026-08-01',4),
('Baroque and Beyond','2024-06-01','2026-09-01',6),
('Photography and Society','2025-01-15','2026-07-15',8),
('Ancient Civilizations','2023-02-01','2026-05-01',10),
('Sculpture in Motion','2024-04-01','2026-11-01',7),
('Women in Art','2025-02-01','2026-12-01',1),
('Urban and Street Art','2025-06-01','2026-12-31',3),
('Asian Art Traditions','2024-03-01','2026-09-30',9);

INSERT INTO Artwork 
(title, creationYear, type, medium, dimensions, status, artist_id, exhib_id)
VALUES
('Study for A Sunday Afternoon',1884,'Painting','Oil on canvas',NULL,'EXHIBITED',1,1),
('Hay Harvest',1890,'Painting','Oil on canvas',NULL,'EXHIBITED',2,1),
('Dancer Adjusting Slipper',1873,'Painting','Pastel',NULL,'EXHIBITED',3,1),
('The Stone Breakers Study',1849,'Painting','Oil on canvas',NULL,'EXHIBITED',4,1),
('Italian Landscape Sketch',1825,'Painting','Oil on paper',NULL,'EXHIBITED',5,1),
('Mother and Child',1890,'Painting','Oil on canvas',NULL,'EXHIBITED',6,1),
('The Gulf Stream Study',1899,'Painting','Oil on canvas',NULL,'EXHIBITED',7,1),
('The Gross Clinic Sketch',1875,'Painting','Oil on canvas',NULL,'EXHIBITED',8,1),
('Portrait of a Lady',1885,'Painting','Oil on canvas',NULL,'EXHIBITED',9,1),
('Nocturne in Blue and Gold',1872,'Painting','Oil on panel',NULL,'EXHIBITED',10,1),

('Mont Sainte-Victoire Study',1904,'Painting','Oil on canvas',NULL,'EXHIBITED',11,1),
('Red Studio Interior',1911,'Painting','Oil on canvas',NULL,'EXHIBITED',12,1),
('Seated Nude Study',1917,'Painting','Oil on canvas',NULL,'EXHIBITED',13,1),
('Self Portrait with Stripes',1910,'Painting','Oil on canvas',NULL,'EXHIBITED',14,1),
('Golden Portrait Study',1907,'Painting','Oil on canvas',NULL,'EXHIBITED',15,1),
('Melancholy Figure',1895,'Painting','Oil on canvas',NULL,'EXHIBITED',16,1),
('Composition with Lines',1920,'Painting','Oil on canvas',NULL,'EXHIBITED',17,1),
('Improvisation Study',1913,'Painting','Oil on canvas',NULL,'EXHIBITED',18,1),
('Black Square Study',1915,'Painting','Oil on canvas',NULL,'EXHIBITED',19,1),
('Abstract Garden',1925,'Painting','Mixed media',NULL,'EXHIBITED',20,1),

('Bird in Space Variant',1923,'Sculpture','Bronze',NULL,'EXHIBITED',21,1),
('Standing Figure',1947,'Sculpture','Bronze',NULL,'EXHIBITED',22,1),
('Reclining Figure Study',1951,'Sculpture','Stone',NULL,'EXHIBITED',23,1),
('Oval Form',1936,'Sculpture','Wood',NULL,'EXHIBITED',24,1),
('Spider Study',1995,'Sculpture','Steel',NULL,'EXHIBITED',25,1),

('Yosemite Valley',1940,'Photograph','Gelatin silver print',NULL,'EXHIBITED',26,1),
('Alabama Tenant Farmer',1936,'Photograph','Gelatin silver print',NULL,'EXHIBITED',27,1),
('Migrant Mother Study',1936,'Photograph','Gelatin silver print',NULL,'EXHIBITED',28,1),
('Rayograph Composition',1922,'Photograph','Photogram',NULL,'EXHIBITED',29,1),
('Portrait of a Young Man',1960,'Photograph','Film',NULL,'EXHIBITED',30,1),

('Greek Vase Fragment',-450,'Object','Ceramic',NULL,'EXHIBITED',31,1),
('Egyptian Sarcophagus Detail',-1200,'Object','Wood and paint',NULL,'EXHIBITED',32,1),
('Japanese Woodblock Print',1850,'Print','Ink on paper',NULL,'EXHIBITED',33,1),
('Persian Miniature Scene',1600,'Painting','Ink and gold',NULL,'EXHIBITED',34,1),
('Medieval Icon Panel',1300,'Painting','Tempera on wood',NULL,'EXHIBITED',35,1),

('Self Portrait Study',1650,'Painting','Oil on canvas',NULL,'EXHIBITED',36,1),
('Interior with Woman Reading',1660,'Painting','Oil on canvas',NULL,'EXHIBITED',37,1),
('Baroque Allegory Sketch',1625,'Painting','Oil on panel',NULL,'EXHIBITED',38,1),
('Royal Portrait Study',1635,'Painting','Oil on canvas',NULL,'EXHIBITED',39,1),
('Capricho Study',1800,'Print','Etching',NULL,'EXHIBITED',40,1),

('Religious Vision',1580,'Painting','Oil on canvas',NULL,'EXHIBITED',41,1),
('Madonna Study',1480,'Painting','Tempera',NULL,'EXHIBITED',42,1),
('High Renaissance Portrait',1510,'Painting','Oil on panel',NULL,'EXHIBITED',43,1),
('Venetian Color Study',1550,'Painting','Oil on canvas',NULL,'EXHIBITED',44,1),
('Dramatic Light Scene',1605,'Painting','Oil on canvas',NULL,'EXHIBITED',45,1),

('Engraving Study',1500,'Print','Engraving',NULL,'EXHIBITED',46,1),
('Court Portrait',1530,'Painting','Oil on panel',NULL,'EXHIBITED',47,1),
('Illuminated Manuscript Leaf',1450,'Painting','Ink on parchment',NULL,'EXHIBITED',48,1),
('Fresco Fragment',1305,'Painting','Fresco',NULL,'EXHIBITED',49,1),
('Altarpiece Panel',1310,'Painting','Tempera',NULL,'EXHIBITED',50,1);

UPDATE Artwork
SET
dimensions = COALESCE(dimensions,
    CASE type
        WHEN 'Painting' THEN '100x80 cm'
        WHEN 'Sculpture' THEN '150x60x60 cm'
        WHEN 'Photograph' THEN '80x60 cm'
        WHEN 'Print' THEN '60x45 cm'
        ELSE '90x70 cm'
    END
),

description = COALESCE(description,
    CONCAT(
        'Artwork from the Met-inspired collection, created in ',
        creationYear,
        ', representing ',
        LOWER(type),
        ' in a classical museum context.'
    )
),

price = COALESCE(price,
    CASE
        WHEN type = 'Painting' THEN 50000 + (creationYear % 100) * 2000
        WHEN type = 'Sculpture' THEN 80000 + (creationYear % 100) * 3000
        WHEN type = 'Photograph' THEN 20000 + (creationYear % 100) * 1000
        WHEN type = 'Print' THEN 15000 + (creationYear % 100) * 800
        ELSE 30000
    END);

INSERT INTO Artwork 
(title, creationYear, type, medium, dimensions, description, price, status, artist_id, exhib_id)
VALUES

-- Cecily Brown (51)
('Chaotic Figure Field',2019,'Painting','Oil on canvas','210x170 cm',
'Expressive abstraction exploring fragmented human forms',
950000,'EXHIBITED',51,2),

-- Julie Mehretu (52)
('Urban Palimpsest',2020,'Painting','Ink and acrylic on canvas','260x200 cm',
'Layered architectural abstraction inspired by global cities',
1300000,'EXHIBITED',52,2),

-- Mark Bradford (53)
('City Fragments',2018,'Mixed Media','Paper, acrylic, collage','240x180 cm',
'Textural urban landscape built from recycled materials',
1100000,'EXHIBITED',53,2),

-- Njideka Akunyili Crosby (54)
('Domestic Memory Scene',2021,'Painting','Acrylic and transfers','200x150 cm',
'Intimate domestic scene blending Nigerian and American culture',
1200000,'EXHIBITED',54,2),

-- Jordan Casteel (55)
('Portrait of a Quiet Moment',2022,'Painting','Oil on canvas','180x140 cm',
'Contemporary portrait capturing everyday emotional presence',
800000,'EXHIBITED',55,2);

INSERT INTO ArtworkTag (name) VALUES
('Impressionism'),
('Modern Art'),
('Renaissance'),
('Baroque'),
('Abstract'),
('Photography'),
('Ancient Art');

INSERT INTO has_a_tag VALUES
(1,1),(2,1),(3,1),(4,1),
(11,2),(12,2),(13,2),(14,2),
(36,4),(37,4),(38,4),
(41,3),(42,3),(43,3),
(21,5),(22,5),
(26,6),(27,6),(28,6),
(31,7),(32,7);

INSERT INTO CommunityMember (name,email,birthYear,membershipType,city) VALUES
('Alice Martin','alice@mail.com',1995,'PREMIUM','Paris'),
('Bob Dupont','bob@mail.com',1988,'FREE','Lyon'),
('Claire Moreau','claire@mail.com',1992,'PREMIUM','Paris'),
('David Leroy','david@mail.com',1985,'FREE','Marseille');

INSERT INTO Workshop 
(title, w_date, durationMinutes, maxParticipants, price, description, location, level, artist_id)
VALUES

('Impressionist Painting','2026-06-01 10:00:00',120,10,50,
 'Learn impressionist techniques and color blending',
 'Paris Studio','BEGINNER',2),

('Modern Sculpture','2026-06-03 14:00:00',150,8,70,
 'Hands-on introduction to modern sculpting techniques',
 'New York Workshop Space','INTERMEDIATE',21),

('Photography Basics','2026-06-05 09:00:00',120,12,40,
 'Introduction to composition, lighting and framing in photography',
 'Berlin Photo Lab','BEGINNER',26);

INSERT INTO Booking (booking_date, paymentStatus, workshop_id, member_id)
VALUES

-- Early bookings (2026-01 to 2026-02)
('2026-01-05 10:15:00','PAID',1,1),
('2026-01-08 14:30:00','PAID',2,2),
('2026-01-10 09:45:00','PENDING',3,3),
('2026-01-12 11:20:00','PAID',1,2),

-- Mid bookings (2026-02 to 2026-03)
('2026-02-03 16:10:00','PAID',2,1),
('2026-02-07 13:00:00','CANCELLED',3,2),
('2026-02-10 10:00:00','PAID',1,3),
('2026-02-15 18:40:00','PAID',2,3),

-- Late bookings (2026-03 to 2026-04)
('2026-03-01 09:30:00','PAID',3,1),
('2026-03-05 15:25:00','PENDING',1,2),
('2026-03-10 12:00:00','PAID',2,3),
('2026-03-15 14:45:00','PAID',3,2),

-- Recent bookings (2026-04)
('2026-04-01 10:10:00','PAID',1,1),
('2026-04-05 11:55:00','PAID',2,2),
('2026-04-10 17:20:00','PENDING',3,3),
('2026-04-12 09:00:00','PAID',1,3);

INSERT INTO Review (rating,reviewDate,comment,member_id,artwork_id) VALUES
(4.5,'2026-01-01','Excellent piece',1,1),
(4.0,'2026-01-02','Very interesting',2,10),
(5.0,'2026-01-03','Outstanding',3,20);

INSERT INTO Gallery 
(name, address, ownerName, openingHours, contactPhone, rating, website)
VALUES

('Louvre Modern Wing','Paris','French State','09:00-18:00','+33 1 40 20 50 50',4.90,'www.louvre.fr'),

('MoMA Contemporary','New York','MoMA','10:30-17:30','+1 212 708 9400',4.80,'www.moma.org'),

('Tate Modern','London','Tate','10:00-18:00','+44 20 7887 8888',4.70,'www.tate.org.uk'),

('Ueno Art Space','Tokyo','Tokyo Arts','09:30-17:00','+81 3 3823 6921',4.60,'www.ueno-art.jp'),

('Berlin Art Hub','Berlin','City of Berlin','10:00-19:00','+49 30 266 424242',4.50,'www.berlinart.de');

INSERT INTO Exhibition 
(title, startDate, endDate, description, curatorName, theme, gallery_id)
VALUES

('Impressionist Masters','2025-01-01','2026-12-31',
 'Key impressionist works highlighting light and movement',
 'Jean Dupont','Impressionism',1),

('Modern Abstractions','2025-03-01','2026-11-30',
 'Exploration of abstract expression in modern art',
 'Sarah Collins','Abstract Art',2),

('Sculpture Forms','2025-05-01','2026-10-01',
 'Study of volume, shape and material in sculpture',
 'Marco Bianchi','Sculpture',3),

('Photography and Reality','2025-06-01','2026-09-01',
 'Photographic works exploring perception and reality',
 'Emily Carter','Photography',4),

('Ancient to Medieval','2025-02-01','2026-08-01',
 'Artworks spanning ancient civilizations to medieval period',
 'Hiroshi Tanaka','Historical Art',5);