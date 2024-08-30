-- Create the 'accommodations' table




CREATE TABLE  IF NOT EXISTS accommodations (
                                accommodation_id BIGINT AUTO_INCREMENT PRIMARY KEY,    -- Primary key
                                name VARCHAR(20) NOT NULL,
                                price DOUBLE NOT NULL,
                                type VARCHAR(50) NOT NULL,
                                rating DOUBLE NOT NULL CHECK (rating >= 0 AND rating <= 10),
                                available BOOLEAN NOT NULL,
                                street VARCHAR(20),
                                city VARCHAR(20),
                                state VARCHAR(20),
                                country VARCHAR(20),
                                postal_code VARCHAR(10)
);

-- Create the 'reservations' table

CREATE TABLE IF NOT EXISTS reservations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,                  -- Primary key
                              accommodation_id BIGINT NOT NULL,                      -- Foreign key referencing accommodations table
                              start_date DATE NOT NULL,
                              end_date DATE NOT NULL,
                              guest_name VARCHAR(20) NOT NULL,
                              guest_email VARCHAR(50) NOT NULL,
                              FOREIGN KEY (accommodation_id) REFERENCES accommodations(accommodation_id) -- Foreign key constraint
);
