USE ArtConnect;

-- To verify that the workshop date is not in the past
DROP TRIGGER IF EXISTS check_workshop_date;
DELIMITER //
CREATE TRIGGER check_workshop_date
BEFORE Insert On Workshop FOR EACH ROW
BEGIN
	IF NEW.w_date < NOW() THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The date must be superior than today date';
	END IF;
END //
DELIMITER ;


-- Max capacity for the workshop
DROP TRIGGER IF EXISTS check_workshop_capacity;
DELIMITER // 
CREATE TRIGGER check_workshop_capacity
BEFORE INSERT ON Booking FOR EACH ROW
BEGIN
	DECLARE current_bookings INT;
    DECLARE max_capacity INT;
    
    SELECT COUNT(*) INTO current_bookings FROM Booking
    WHERE workshop_id = NEW.workshop_id;
    
    SELECT maxParticipants INTO max_capacity FROM Workshop
    WHERE workshop_id = NEW.workshop_id;
    
    IF current_bookings >= max_capacity THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Worshop is fully booked';
    END IF;
END //
DELIMITER ;


CREATE TABLE IF NOT EXISTS ExhibitionAudit (
audit_id INT PRIMARY KEY,
exhib_id INT,
action ENUM('INSERT', 'UPDATE', 'DELETE'),
action_date DATETIME DEFAULT CURRENT_TIMESTAMP,
old_title VARCHAR(50),
new_title VARCHAR(50),
old_startDate DATE,
new_startDate DATE,
old_endDate DATE,
new_endDate Date
);

DROP TRIGGER IF EXISTS audit_exhibition_changes;
DELIMITER //
CREATE TRIGGER audit_exhibition_changes
AFTER UPDATE ON Exhibition
FOR EACH ROW
BEGIN
    INSERT INTO ExhibitionAudit (
        exhib_id,
        action,
        old_title,
        new_title,
        old_startDate,
        new_startDate,
        old_endDate,
        new_endDate
    )
    VALUES (
        NEW.exhib_id,
        'UPDATE',
        OLD.title,
        NEW.title,
        OLD.startDate,
        NEW.startDate,
        OLD.endDate,
        NEW.endDate
    );
END //
DELIMITER ;  
    
    
    
    
    
-- Tells us the nbof participants for a workshop
DROP PROCEDURE IF EXISTS GetWorkshopParticipantsCount;
DELIMITER //
CREATE PROCEDURE GetWorkshopParticipantsCount(IN p_workshop_id INT)
BEGIN
	SELECT COUNT(*) AS participants_count
    FROM Booking
    WHERE workshop_id = p_workshop_id;
END //
DELIMITER ;


-- Handles booking a workshop for a member
DROP PROCEDURE IF EXISTS BookWorkshop;
DELIMITER //
CREATE PROCEDURE BookWorkshop(
    IN p_workshop_id INT,
    IN p_member_id INT,
    IN p_paymentStatus ENUM("PENDING", "PAID", "CANCELLED")
)
BEGIN
    INSERT INTO Booking (booking_date, paymentStatus, workshop_id, member_id)
    VALUES (NOW(), p_paymentStatus, p_workshop_id, p_member_id);

    SELECT 'Workshop booked' AS message;
END //
DELIMITER ;

-- With a discipline, returns all the artist with that discipline
DROP PROCEDURE IF EXISTS GetArtistByDiscipline;
DELIMITER //
CREATE PROCEDURE GetArtistByDiscipline(IN p_discipline_id INT)
BEGIN
	SELECT a.* FROM Artist a
    JOIN has_a_style h ON a.artist_id = h.artist_id
    WHERE h.discipline_id = p_discipline_id;
END //
DELIMITER ;

-- With a member id, shows all the booking and the review 
DROP PROCEDURE IF EXISTS GetBookingsByMember;
DELIMITER //
CREATE PROCEDURE GetBookingsByMember(IN p_member_id INT)
BEGIN
    SELECT b.booking_id, b.booking_date, b.paymentStatus,
           w.workshop_id, w.title AS workshop_title, w.w_date, w.durationMinutes
    FROM Booking b
    JOIN Workshop w ON b.workshop_id = w.workshop_id
    WHERE b.member_id = p_member_id;
END //
DELIMITER ;



-- Calculates the average rating of an artwork
DROP FUNCTION IF EXISTS GetAverageArtworkRating;
DELIMITER //
CREATE FUNCTION GetAverageArtworkRating(p_artwork_id INT)
RETURNS DECIMAL(4, 2) DETERMINISTIC
BEGIN
	DECLARE avg_rating DECIMAL(4, 2);
    
    SELECT AVG(rating) INTO avg_rating FROM Review
    WHERE artwork_id = p_artwork_id;
    
    RETURN avg_rating;
END //
DELIMITER ;

-- Tells if an artist is active
DROP PROCEDURE IF EXISTS GetAllActiveArtists;
DELIMITER //
CREATE PROCEDURE GetAllActiveArtists()
BEGIN
    SELECT artist_id, name, contactEmail, phone, city, bio, isActive,
        CASE WHEN isActive = 1 THEN 'Active' ELSE 'Inactive' END AS status
    FROM Artist
    WHERE isActive = TRUE
    ORDER BY name;
END //
DELIMITER ;



-- Tells, for an choosen hour, which gallery are open
DROP FUNCTION IF EXISTS CountOpenGalleriesForHour;
DELIMITER //
CREATE FUNCTION CountOpenGalleriesForHour(p_hour TIME)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE open_galleries INT DEFAULT 0;
    DECLARE open_time TIME;
    DECLARE close_time TIME;

    SELECT COUNT(*)
    INTO open_galleries
    FROM Gallery
    WHERE STR_TO_DATE(SUBSTRING_INDEX(openingHours, '-', 1), '%H:%i') <= p_hour
    AND STR_TO_DATE(SUBSTRING_INDEX(SUBSTRING_INDEX(openingHours, '-', -1), ' ', 1), '%H:%i') >= p_hour;

    RETURN open_galleries;
END //
DELIMITER ;


CALL GetWorkshopParticipantsCount(1);
CALL BookWorkshop(1, 3, 'PAID');
CALL GetArtistByDiscipline(1);
CALL GetBookingsByMember(1);
SELECT GetAverageArtworkRating(1) AS avg_rating;
SELECT GetArtistActiveStatus(1) AS active_status;
SELECT CountOpenGalleriesForHour('10:00:00') AS open_galleries;

SELECT * FROM Artist;
