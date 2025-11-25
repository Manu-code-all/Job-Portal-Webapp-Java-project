🧾 Online Job Portal – Java Project (Semester 3)

A fully functional Java Web Application that connects Employers, Job Seekers, and Admins through an online job portal.
Built using JSP, Servlets, JDBC, MySQL, Maven, and Tomcat, this project strictly follows the academic rubric requirements:
✔ OOP
✔ Collections & Generics
✔ Multithreading
✔ JDBC (CRUD + PreparedStatements)
✔ Transaction Management
✔ MVC Architecture

🚀 Project Overview

This Online Job Portal allows:

👨‍💼 Employers

Post new job listings

View and manage their posted jobs

Delete job postings

Review applications submitted by job seekers

👨‍💻 Job Seekers

View and search approved job listings

Apply to jobs

Track their application statuses

🛠️ Admin

Manage all users (add / delete / assign roles)

Approve or reject job postings

Oversee system activity

Maintain database integrity

🏗️ Features Implemented (Matches Rubric)
✔ 1. OOP Concepts (Polymorphism, Encapsulation, Inheritance, Interfaces)

Model classes: User, Job, Application

DAO pattern for database abstraction

Servlets as controllers (MVC architecture)

✔ 2. Collections & Generics

Uses List<Job>, List<Application>, etc.

Uses ConcurrentLinkedQueue for thread-safe notifications

✔ 3. Multithreading & Synchronization

NotificationWorker background thread

Thread-safe queue for notifications

✔ 4. JDBC + CRUD + PreparedStatement

Secure SQL operations

Insert, Update, Delete, Select implemented in DAOs

✔ 5. Transaction Management

Admin job approval uses manual transaction commit/rollback

✔ 6. MVC Architecture

JSP (View)

Servlets (Controller)

DAO classes (Model/Database Layer)

📁 Project Structure
.
├── sql/
│   └── schema.sql
├── src/main/java/com/example/jobportal/
│   ├── dao/
│   │   ├── JobDAO.java
│   │   ├── UserDAO.java
│   │   └── ApplicationDAO.java
│   ├── model/
│   │   ├── Job.java
│   │   ├── User.java
│   │   └── Application.java
│   ├── servlet/
│   │   ├── AdminUserServlet.java
│   │   ├── EmployerJobServlet.java
│   │   ├── JobSeekerServlet.java
│   │   ├── IndexServlet.java
│   │   └── AppContextListener.java
│   ├── worker/
│   │   └── NotificationWorker.java
│   └── util/
│       └── DBConnectionPool.java
├── src/main/webapp/
│   ├── index.jsp
│   ├── admin/admin-dashboard.jsp
│   ├── employer/employer-dashboard.jsp
│   ├── jobseeker/jobseeker-dashboard.jsp
│   └── WEB-INF/web.xml
├── pom.xml
└── README.md

🛢️ Database Setup

Start MySQL server

Run the DB schema:

mysql -u root -p < sql/schema.sql


This creates:

users

jobs

applications

job_audit

A default admin is also inserted:

email: admin@example.com
password: adminpass

⚙️ Configuration

Edit:

src/main/resources/db.properties


Set:

jdbc.url=jdbc:mysql://localhost:3306/job_portal
jdbc.user=root
jdbc.password=YOUR_PASSWORD
jdbc.maxPoolSize=10

🧩 How to Build & Run
Step 1 — Build using Maven
mvn clean package


Generates:

target/online-job-portal.war

Step 2 — Deploy to Tomcat

Copy WAR to Tomcat:

cp target/online-job-portal.war /path/to/tomcat/webapps/


Start Tomcat:

startup.sh   (Linux/macOS)
startup.bat  (Windows)

Step 3 — Open in Browser
http://localhost:8080/online-job-portal/


Dashboards:

Admin → /admin/admin-dashboard.jsp

Employer → /employer/employer-dashboard.jsp

Job Seeker → /jobseeker/jobseeker-dashboard.jsp

🔍 Testing Tips

Make sure MySQL credentials are correct

Ensure the WAR deploys successfully (check Tomcat logs)

If using servlets that require login, set test userId in session OR implement login servlet

🧠 Future Enhancements (Optional)

Add Login & Role-based Authentication

Add Bootstrap UI for better styling

Add File Upload for resume

Add Email OTP notifications

Add REST API layer (Spring Boot)

✅ Conclusion

This project demonstrates practical knowledge of:

✔ Java
✔ JSP + Servlets
✔ JDBC CRUD
✔ OOP
✔ Multithreading
✔ Maven
✔ Web Deployment using Tomcat

A complete academic + real-world level Java Web Application.
