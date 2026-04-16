DROP DATABASE IF EXISTS ArtConnect;
CREATE DATABASE ArtConnect;

USE ArtConnect;

CREATE TABLE ArtworkTag (
    artworktag_id INT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(50)
);

CREATE TABLE Artist (
    artist_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    bio VARCHAR(50),
    birthYear INT,
    contactEmail VARCHAR(50),
    city VARCHAR(50),
    isActive BOOLEAN,
    phone VARCHAR(50),
    website VARCHAR(50),
    socialMedia VARCHAR(50)
);

CREATE TABLE Discipline (
    discipline_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE CommunityMember (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    birthYear INT,
    phone VARCHAR(50),
    city VARCHAR(50),
    membershipType ENUM("FREE", "PREMIUM")
);

CREATE TABLE Gallery (
    gallery_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    address VARCHAR(50),
    ownerName VARCHAR(50),
    openingHours VARCHAR(50),
    contactPhone VARCHAR(50),
    rating DECIMAL(4,2),
    website VARCHAR(50)
);

CREATE TABLE Workshop (
    workshop_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
    w_date DATETIME,
    durationMinutes INT,
    maxParticipants INT,
    price DECIMAL(15,2),
    description VARCHAR(50),
    location VARCHAR(50),
    level VARCHAR(50),
    artist_id INT,
    FOREIGN KEY (artist_id) REFERENCES Artist(artist_id)
);

CREATE TABLE Booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_date DATETIME,
    paymentStatus ENUM("PENDING", "PAID", "CANCELLED"),
    workshop_id INT,
    member_id INT,
    FOREIGN KEY (workshop_id) REFERENCES Workshop(workshop_id),
    FOREIGN KEY (member_id) REFERENCES CommunityMember(member_id)
);

CREATE TABLE Exhibition (
    exhib_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
    startDate DATE,
    endDate DATE,
    description VARCHAR(50),
    curatorName VARCHAR(50),
    theme VARCHAR(50),
    gallery_id INT,
    FOREIGN KEY (gallery_id) REFERENCES Gallery(gallery_id)
);

CREATE TABLE Artwork (
    artwork_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
    creationYear INT,
    type VARCHAR(50),
    medium VARCHAR(50),
    dimensions VARCHAR(50),
    description VARCHAR(50),
    price DECIMAL(15,2),
    status ENUM("FOR SALE", "SOLD","EXHIBITED" ),
    artist_id INT,
    exhib_id INT,
    FOREIGN KEY (artist_id) REFERENCES Artist(artist_id),
    FOREIGN KEY (exhib_id) REFERENCES Exhibition(exhib_id)
);

CREATE TABLE Review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    rating DECIMAL(4,2),
    reviewDate DATE,
    comment VARCHAR(50),
    member_id INT,
    artwork_id INT,
    FOREIGN KEY (member_id) REFERENCES CommunityMember(member_id),
    FOREIGN KEY (artwork_id) REFERENCES Artwork(artwork_id)
);

CREATE TABLE has_a_tag (
    artwork_id INT,
    artworktag_id INT,
    PRIMARY KEY (artwork_id, artworktag_id),
    FOREIGN KEY (artwork_id) REFERENCES Artwork(artwork_id),
    FOREIGN KEY (artworktag_id) REFERENCES ArtworkTag(artworktag_id)
);

CREATE TABLE has_a_style (
    artist_id INT,
    discipline_id INT,
    PRIMARY KEY (artist_id, discipline_id),
    FOREIGN KEY (artist_id) REFERENCES Artist(artist_id),
    FOREIGN KEY (discipline_id) REFERENCES Discipline(discipline_id)
);

CREATE TABLE likes_a_style (
    discipline_id INT,
    member_id INT,
    PRIMARY KEY (discipline_id, member_id),
    FOREIGN KEY (discipline_id) REFERENCES Discipline(discipline_id),
    FOREIGN KEY (member_id) REFERENCES CommunityMember(member_id)
);