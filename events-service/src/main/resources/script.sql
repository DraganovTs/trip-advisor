-- Create the table to store events
CREATE TABLE events (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        city VARCHAR(255) NOT NULL,
                        event_type VARCHAR(50) NOT NULL, -- Enum type stored as string
                        date DATE NOT NULL,
                        ticket_price DOUBLE DEFAULT 0,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        created_by VARCHAR(255),
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        updated_by VARCHAR(255)
);