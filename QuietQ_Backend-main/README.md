# Quiet_Q

Quiet_Q is an anonymous chatting application designed to foster communication and interaction in group settings while maintaining user anonymity. The application features community-based posts, scheduled tasks for post management, and tag management for group chats.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
  - [Chats](#chats)
  - [Posts](#posts)
- [Scheduled Tasks](#scheduled-tasks)
- [Entities](#entities)
  - [Chat](#chat)
  - [Community](#community)
  - [Post](#post)
  - [User](#user)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Features

- **Anonymous Group Chats**: Users can participate in group chats anonymously.
- **Community-Based Posts**: Users can create and share posts within specific communities.
- **Scheduled Post Deletion**: Posts are automatically deleted after 24 hours to keep the content fresh and relevant.
- **Tag Management**: Tags can be added to group chats to categorize and organize them.

## Technologies Used

- **Java**: The primary programming language.
- **Spring Boot**: A framework for building production-ready applications quickly.
- **Hibernate / JPA**: ORM for database interactions.
- **H2 Database**: In-memory database used for development and testing.
- **Maven**: Dependency management and build tool.

## Getting Started

### Prerequisites

Ensure you have the following installed on your local machine:

- Java 17 or higher
- Maven 3.6.0 or higher

### Installation

1. **Clone the repository**:

    ```bash
    git clone https://github.com/yourusername/quiet_q.git
    cd quiet_q
    ```

2. **Build the project using Maven**:

    ```bash
    mvn clean install
    ```

3. **Run the application**:

    ```bash
    mvn spring-boot:run
    ```

The application will start and be accessible at `http://localhost:8080`.


