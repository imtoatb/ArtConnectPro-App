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

INSERT INTO has_a_style VALUES
-- Painting (1)
(1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),
(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),
(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),
(46,1),(47,1),(48,1),(49,1),(50,1),

-- Sculpture (2)
(21,2),(22,2),(23,2),(24,2),(25,2),

-- Photography (4)
(26,4),(27,4),(28,4),(29,4),(30,4),

-- Decorative / ancient (5)
(31,5),(32,5),(33,3),(34,5),(35,5);

INSERT INTO Gallery (name, address, ownerName, rating)
VALUES ('Metropolitan Museum', 'New York', 'Met', 4.90);

INSERT INTO Exhibition (title, startDate, endDate, gallery_id)
VALUES ('Met Selected Works', '2000-01-01', '2100-01-01', 1);

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
(title,w_date,durationMinutes,maxParticipants,price,artist_id)
VALUES
('Impressionist Painting','2026-06-01 10:00:00',120,10,50,2),
('Modern Sculpture','2026-06-03 14:00:00',150,8,70,21),
('Photography Basics','2026-06-05 09:00:00',120,12,40,26);

INSERT INTO Booking (booking_date,paymentStatus,workshop_id,member_id) VALUES
(NOW(),'PAID',1,1),
(NOW(),'PENDING',2,2),
(NOW(),'PAID',3,3);

INSERT INTO Review (rating,reviewDate,comment,member_id,artwork_id) VALUES
(4.5,'2026-01-01','Excellent piece',1,1),
(4.0,'2026-01-02','Very interesting',2,10),
(5.0,'2026-01-03','Outstanding',3,20);