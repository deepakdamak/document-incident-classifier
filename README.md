# Document-to-Incident Classification System

## Overview

A Spring Boot application that accepts a list of incident topics, processes a document (PDF or text), splits it into sentences, and classifies each sentence into the most relevant topic using keyword matching. The system stores results and provides REST APIs for topic management, document upload, result retrieval, and a dashboard.

---

## Technologies Used

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA / Hibernate
- PostgreSQL 9.5+
- Apache PDFBox (for PDF text extraction)
- SpringDoc OpenAPI (Swagger UI)

---

## Prerequisites

- JDK 17
- Maven 3.6+
- PostgreSQL 9.5+ installed and running
- (Optional) Docker / Docker Compose

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/deepakdamak/document-incident-classifier.git
cd document-incident-classifier

2. Create Database
Create a PostgreSQL database named incident_classifier:
CREATE DATABASE incident_classifier;

3. Configure Database Credentials
Edit src/main/resources/application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/incident_classifier
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update


4. Build and Run
bash
mvn clean install
mvn spring-boot:run


5. Access Swagger UI (Optional)
Open http://localhost:8080/swagger-ui.html to explore and test the APIs interactively.

API Usage
This section explains how to use the API endpoints. You can test them using Postman or curl.

1. Add a Topic
Endpoint: POST /api/topics

Headers: Content-Type: application/json

Body (JSON): Provide title and keywords.

Postman:

Method: POST

URL: http://localhost:8080/api/topics

Body → raw → JSON:

json
{
  "title": "Delhi Bomb Blast",
  "keywords": ["Delhi", "blast", "explosion", "bomb"]
}
curl:

bash
curl -X POST http://localhost:8080/api/topics \
  -H "Content-Type: application/json" \
  -d '{"title":"Delhi Bomb Blast","keywords":["Delhi","blast","explosion","bomb"]}'
Response (201 Created):

json
{
  "success": true,
  "message": "Topic created",
  "data": {
    "id": 1,
    "title": "Delhi Bomb Blast",
    "keywords": ["Delhi", "blast", "explosion", "bomb"]
  },
  "timestamp": "2026-03-24T10:00:00"
}
2. Get All Topics
Endpoint: GET /api/topics

Postman: GET http://localhost:8080/api/topics

curl:

bash
curl http://localhost:8080/api/topics
Response (200 OK):

json
{
  "success": true,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "title": "Delhi Bomb Blast",
      "keywords": ["Delhi", "blast", "explosion", "bomb"]
    },
    {
      "id": 2,
      "title": "Kanpur Bomb Blast",
      "keywords": ["Kanpur", "blast", "explosion", "bomb"]
    }
  ],
  "timestamp": "2026-03-24T10:00:00"
}
3. Get Topic by ID
Endpoint: GET /api/topics/{id}

Postman: GET http://localhost:8080/api/topics/1

curl:

bash
curl http://localhost:8080/api/topics/1
Response (200 OK):

json
{
  "success": true,
  "message": "Success",
  "data": {
    "id": 1,
    "title": "Delhi Bomb Blast",
    "keywords": ["Delhi", "blast", "explosion", "bomb"]
  },
  "timestamp": "2026-03-24T10:00:00"
}
4. Upload a Document
Endpoint: POST /api/documents

Content-Type: multipart/form-data

Form fields:

text – for raw text input (type: Text)

file – for PDF file upload (type: File)

Postman (text):

Method: POST

URL: http://localhost:8080/api/documents

Body → form-data

Key: text (type Text)

Value: A massive explosion occurred in Delhi near Connaught Place causing panic. Meanwhile in Kanpur, a similar blast was reported in an industrial area.

Postman (PDF):

Key: file (type File)

Value: select a PDF file

curl (text):

bash
curl -X POST http://localhost:8080/api/documents \
  -F "text=A massive explosion occurred in Delhi near Connaught Place causing panic. Meanwhile in Kanpur, a similar blast was reported in an industrial area."
curl (PDF):

bash
curl -X POST http://localhost:8080/api/documents \
  -F "file=@/path/to/your/document.pdf"
Response (201 Created):

json
{
  "success": true,
  "message": "Document uploaded and processed",
  "data": {
    "id": 1,
    "originalText": "A massive explosion occurred in Delhi near Connaught Place causing panic. Meanwhile in Kanpur, a similar blast was reported in an industrial area.",
    "createdAt": "2026-03-24T10:00:00"
  },
  "timestamp": "2026-03-24T10:00:00"
}
5. Get Classification Results
Endpoint: GET /api/documents/{id}/results

Postman: GET http://localhost:8080/api/documents/1/results

curl:

bash
curl http://localhost:8080/api/documents/1/results
Response (200 OK):

json
{
  "success": true,
  "message": "Success",
  "data": {
    "documentId": 1,
    "results": [
      {
        "text": "A massive explosion occurred in Delhi near Connaught Place causing panic.",
        "assignedTopic": "Delhi Bomb Blast",
        "confidence": 0.75
      },
      {
        "text": "Meanwhile in Kanpur, a similar blast was reported in an industrial area.",
        "assignedTopic": "Kanpur Bomb Blast",
        "confidence": 0.75
      }
    ]
  },
  "timestamp": "2026-03-24T10:00:00"
}
6. Dashboard
Endpoint: GET /api/dashboard

Postman: GET http://localhost:8080/api/dashboard

curl:

bash
curl http://localhost:8080/api/dashboard
Response (200 OK):

json
{
  "success": true,
  "message": "Success",
  "data": {
    "totalDocuments": 10,
    "totalChunks": 200,
    "topicDistribution": {
      "Delhi Bomb Blast": 90,
      "Kanpur Bomb Blast": 70,
      "UNCLASSIFIED": 40
    }
  },
  "timestamp": "2026-03-24T10:00:00"
}
Database Schema
Table: topics
Column	Type	Description
id	SERIAL (PK)	Auto‑increment primary key
title	VARCHAR(255)	Topic name (unique)
keywords	TEXT	Comma‑separated keywords
Table: documents
Column	Type	Description
id	SERIAL (PK)	Auto‑increment primary key
original_text	TEXT	Full extracted text
created_at	TIMESTAMP	Upload timestamp
Table: classified_chunks
Column	Type	Description
id	SERIAL (PK)	Auto‑increment primary key
document_id	INTEGER (FK)	References documents(id)
text_chunk	TEXT	Sentence / paragraph
assigned_topic_id	INTEGER (FK)	References topics(id) (nullable)
confidence_score	NUMERIC(5,4)	Score between 0 and 1
created_at	TIMESTAMP	Processing timestamp
Classification Logic
Each text chunk is lower‑cased.

For each topic, the number of keyword matches (substring) is counted.

The topic with the highest count is assigned.

Confidence = matches / totalKeywordsInTopic (rounded to 4 decimal places).

If no matches → topic is UNCLASSIFIED (assigned_topic_id = null), confidence = 0.

Bonus Features (Optional)
Swagger UI – available at /swagger-ui.html.

Pagination – can be added to list endpoints (e.g., /api/topics?page=0&size=10).

Docker – a Dockerfile and docker-compose.yml can be provided.

Troubleshooting
PostgreSQL syntax error with pagination:
If you see ERROR: syntax error at or near "$2", use dialect org.hibernate.dialect.PostgreSQL95Dialect in application.properties.

File upload limit:
Max file size is 10MB. Adjust in application.properties if needed.



