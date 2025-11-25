-- schema.sql
CREATE DATABASE IF NOT EXISTS job_portal;
USE job_portal;

-- users table: role: ADMIN, EMPLOYER, JOBSEEKER
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role ENUM('ADMIN','EMPLOYER','JOBSEEKER') NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE jobs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  employer_id INT NOT NULL,
  title VARCHAR(200) NOT NULL,
  description TEXT,
  requirements TEXT,
  salary VARCHAR(50),
  location VARCHAR(100),
  status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (employer_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE applications (
  id INT AUTO_INCREMENT PRIMARY KEY,
  job_id INT NOT NULL,
  seeker_id INT NOT NULL,
  resume_path VARCHAR(255),
  cover_letter TEXT,
  status ENUM('APPLIED','REVIEWED','REJECTED','HIRED') DEFAULT 'APPLIED',
  applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
  FOREIGN KEY (seeker_id) REFERENCES users(id) ON DELETE CASCADE
);

-- sample admin account (password plain text for example; in production hash it)
INSERT INTO users (name, email, password_hash, role)
VALUES ('Admin User', 'admin@example.com', 'adminpass', 'ADMIN');
