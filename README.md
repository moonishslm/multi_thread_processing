# Multi-Threaded File Processing Application

## Overview

This project is a multi-threaded Spring Boot application designed to process uploaded files containing customer and account data. The application validates the data, saves valid entries to a PostgreSQL database, and logs any errors to a JSON file. Additionally, it generates periodic reports of accounts which account_balance in higher than 1000 in JSON and XML formats based on the stored data.

## Features

- **File Upload**: Upload customer and account data files via a REST API.
- **Data Validation**: Validate incoming data and separate valid and invalid entries.
- **Database Integration**: Store valid customer and account entries in a PostgreSQL database.
- **Error Logging**: Log invalid entries to a JSON file for further analysis.
- **Scheduled Reporting**: Automatically generate reports in JSON and XML formats at specified intervals.

## Technologies Used

- Spring Boot
- Spring MVC
- Spring Data JPA
- PostgreSQL
- Jackson (for JSON and XML processing)
- Lombok
- Java 8 (for date/time handling)

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- PostgreSQL database

### Installation

API Endpoints:  

Upload Files
Endpoint:POST /api/upload  

Description: Upload customer and account files.  

Request Body:
accountFile: Multipart file containing account data.
customerFile: Multipart file containing customer data.  

Error Logs:
Invalid entries are logged in a JSON file named error_log.json located in the root directory of the project.
Scheduled Reports
The application generates reports every hour (or as configured) in both JSON (customer_account_report.json) and XML (customer_account_report.xml) formats, located in the root directory.
