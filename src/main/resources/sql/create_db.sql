create schema hotel_booking;
comment on schema hotel_booking is 'Schema for hotel booking service';
-- Select schema to run script
SET search_path TO hotel_booking;

-- Create Users Table
CREATE TABLE "user" (
                        user_id BIGSERIAL PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Hotels Table
CREATE TABLE hotel (
                       hotel_id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       city VARCHAR(100) NOT NULL,
                       state VARCHAR(100),
                       zip_code VARCHAR(20),
                       phone_number VARCHAR(20),
                       email VARCHAR(100),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Rooms Table
CREATE TABLE room (
                      room_id BIGSERIAL PRIMARY KEY,
                      hotel_id BIGINT NOT NULL,
                      room_number VARCHAR(10) NOT NULL,
                      room_type VARCHAR(50),
                      price_per_night DECIMAL(10, 2) NOT NULL,
                      is_available BOOLEAN DEFAULT TRUE,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id) ON DELETE CASCADE
);

-- Create Bookings Table
CREATE TABLE booking (
                         booking_id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         room_id BIGINT NOT NULL,
                         check_in_date DATE NOT NULL,
                         check_out_date DATE NOT NULL,
                         booking_status VARCHAR(20) DEFAULT 'CONFIRMED', -- Possible values: CONFIRMED, CANCELED
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE,
                         FOREIGN KEY (room_id) REFERENCES room (room_id) ON DELETE CASCADE
);

-- Indexes for optimization
CREATE INDEX idx_user_id ON booking(user_id);
CREATE INDEX idx_hotel_id ON room (hotel_id);
CREATE INDEX idx_booking_status ON booking(booking_status);
CREATE INDEX idx_check_in_date ON booking(check_in_date);
CREATE INDEX idx_check_out_date ON booking(check_out_date);

-- Sample Data
INSERT INTO "user" (username, email, password)
VALUES ('john_doe', 'john@example.com', 'password123');
INSERT INTO "user" (username, email, password)
VALUES ('jane_smith', 'jane@example.com', 'qwerty456');
INSERT INTO "user" (username, email, password)
VALUES ('alex_wong', 'alex@example.com', 'securepass');
INSERT INTO "user" (username, email, password)
VALUES ('emily_white', 'emily@example.com', 'abc123');

INSERT INTO hotel (name, address, city, state, zip_code, phone_number, email)
VALUES ('Grand Hotel', '123 Main St', 'Metropolis', 'NY', '12345', '123-456-7890', 'info@grandhotel.com');
INSERT INTO hotel (name, address, city, state, zip_code, phone_number, email)
VALUES ('Luxury Resort', '456 High St', 'Cityville', 'CA', '67890', '456-789-0123', 'info@luxuryresort.com');
INSERT INTO hotel (name, address, city, state, zip_code, phone_number, email)
VALUES ('Seaside Inn', '789 Beach Ave', 'Sunnytown', 'FL', '98765', '789-012-3456', 'info@seasideinn.com');
-- Rooms for Grand Hotel (hotel_id = 1)
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (1, '101', 'Single', 100.00);
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (1, '102', 'Double', 150.00);
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (1, '103', 'Suite', 250.00);
-- Rooms for Luxury Resort (hotel_id = 2)
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (2, '201', 'Single', 120.00);
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (2, '202', 'Double', 180.00);
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (2, '203', 'Suite', 300.00);
-- Rooms for Seaside Inn (hotel_id = 3)
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (3, '301', 'Single', 110.00);
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (3, '302', 'Double', 160.00);
INSERT INTO room (hotel_id, room_number, room_type, price_per_night)
VALUES (3, '303', 'Suite', 280.00);

-- Example Booking
INSERT INTO booking (user_id, room_id, check_in_date, check_out_date) VALUES (1, 1, '2024-07-01', '2024-07-05');

-- Create Triggers and Functions to Update `modified_at`
-- For Users Table
CREATE OR REPLACE FUNCTION update_modified_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.modified_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_users_modified_at
    BEFORE UPDATE ON "user"
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at_column();

-- For Hotels Table
CREATE TRIGGER update_hotels_modified_at
    BEFORE UPDATE ON hotel
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at_column();

-- For Rooms Table
CREATE TRIGGER update_rooms_modified_at
    BEFORE UPDATE ON room
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at_column();

-- For Bookings Table
CREATE TRIGGER update_bookings_modified_at
    BEFORE UPDATE ON booking
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at_column();
-- Document
--                     +---------+       +-------+        +----------+
-- Users and Bookings:
--
-- Each user can have multiple bookings. This is a one-to-many relationship.
-- user_id in the bookings table is a foreign key that references user_id in the users table.
-- Hotels and Rooms:
--
-- Each hotel can have multiple rooms. This is a one-to-many relationship.
-- hotel_id in the rooms table is a foreign key that references hotel_id in the hotels table.
-- Rooms and Bookings:
--
-- Each room can be booked multiple times, but each booking is for a single room. This is a one-to-many relationship.
-- room_id in the bookings table is a foreign key that references room_id in the rooms table.
--     Summary
-- Users to Bookings: One user can make many bookings, but each booking is associated with only one user.
-- Hotels to Rooms: One hotel can have many rooms, but each room belongs to only one hotel.
-- Rooms to Bookings: One room can be booked many times, but each booking is for only one room.
