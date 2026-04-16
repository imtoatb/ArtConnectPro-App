START TRANSACTION;
SET SQL_SAFE_UPDATES = 0;

-- check that workshop exists
SELECT @workshop_exists := COUNT(*)
FROM Workshop
WHERE workshop_id = 1;

-- check that there are members
SELECT @member_exists := COUNT(*)
FROM CommunityMember
WHERE member_id = 1;

-- Check current booking and capacity
SELECT @current_bookings := COUNT(*)
FROM Booking
WHERE workshop_id = 1;

SELECT @max_capacity := maxParticipants
FROM Workshop
WHERE workshop_id = 1;

-- Execute insert
INSERT INTO Booking (booking_date, paymentStatus, workshop_id, member_id)
SELECT NOW(), 'PENDING', 1, 1
FROM Workshop w, CommunityMember m
WHERE w.workshop_id = 1
AND m.member_id = 1;

SET SQL_SAFE_UPDATES = 1;

COMMIT;
