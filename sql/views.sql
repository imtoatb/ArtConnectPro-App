USE ArtConnect;

-- display non sensitive information about an artist
CREATE OR REPLACE VIEW ArtistInfo AS
SELECT name, bio, birthYear, city, isActive, website, socialMedia FROM Artist;

SELECT * FROM ArtistInfo;

-- display info about the artworks
CREATE OR REPLACE VIEW ArtworkInfo AS
SELECT title, creationYear, type, medium, dimensions, description, price, status, a.name AS artist
FROM Artwork
JOIN Artist a USING(artist_id);

SELECT * FROM ArtworkInfo;

-- display the future exhibitions major info with the gallery corresponding
CREATE OR REPLACE VIEW ExhibInfo AS
SELECT title, startDate, endDate, description, theme, g.name AS galleryName, g.address AS galleryAddress, g.openingHours 
FROM Exhibition 
JOIN Gallery g USING(gallery_id);

SELECT * FROM ExhibInfo;

-- display best reviews (rating >= 4)
CREATE OR REPLACE VIEW HighRatedReviews AS
SELECT rating, reviewDate, comment, m.name AS member_name, a.title AS artwork_title
FROM Review 
JOIN CommunityMember m USING(member_id)
JOIN Artwork a USING(artwork_id)
WHERE rating >= 4;

SELECT * FROM HighRatedReviews;
-- increase efficiency of searching the ratings
CREATE INDEX idx_review_rating ON Review(rating);

CREATE OR REPLACE VIEW AvailableWorkshops AS
SELECT w.title, w.w_date, w.maxParticipants, COUNT(b.booking_id) AS currentParticipants, 
	(w.maxParticipants - COUNT(b.booking_id)) AS remainingSpots, w.price, w.location, w.level
FROM Workshop w
JOIN Booking b USING(workshop_id) 
WHERE w.w_date > NOW() AND b.paymentStatus != 'CANCELLED'
GROUP BY w.workshop_id
HAVING COUNT(b.booking_id) < w.maxParticipants;

SELECT * FROM AvailableWorkshops;
-- increase speed when searching a date for the search of available worshops
CREATE INDEX idx_workshop_date ON Workshop(w_date);
-- increase speed when searching a status for the search of available worshops
CREATE INDEX idx_booking_status ON Booking(paymentStatus);

