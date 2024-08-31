-- Create the table to store recommendations
CREATE TABLE recommendations (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 name VARCHAR(255) NOT NULL,
                                 type VARCHAR(50) NOT NULL, -- Enum type stored as string
                                 city VARCHAR(255) NOT NULL,
                                 address VARCHAR(255),
                                 description TEXT,
                                 rating DOUBLE DEFAULT 0,
                                 website VARCHAR(255),
                                 contact_number VARCHAR(50),
                                 email VARCHAR(255),
                                 opening_hours VARCHAR(255),
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                 created_by VARCHAR(255),
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 updated_by VARCHAR(255)
);