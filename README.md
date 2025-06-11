# Java Socket-Based Chat Room Application with GUI Swing and PostgreSQL Integration

## Overview

This is a Java-based chat room application that allows multiple users to communicate in real-time using socket programming. The application features a Swing-based GUI for the client interface and integrates with a PostgreSQL database to store chat history. The server handles multiple client connections, broadcasts messages, and logs all messages to the database.  Users can enter to a specific chatroom with assigned password.

## Features

- **Real-Time Chat**: Multiple users can join a chat room and exchange messages in real-time.
- **Swing GUI**: A user-friendly interface for clients to send and view messages.
- **PostgreSQL Integration**: Stores chat history (user, message, timestamp) in a database.
- **Multi-Client Support**: The server handles multiple clients concurrently using threads.

## Tech Stack

- **Language**: Java (JDK 24)
- **GUI Framework**: Swing, Flatlaf
- **Networking**: Java Socket Programming (ServerSocket, Socket)
- **Database**: PostgreSQL

## Prerequisites

1. **Java Development Kit (JDK)**: JDK 17 or later installed.
2. **PostgreSQL**: PostgreSQL server installed and running (version 15 or later recommended).
3. **PostgreSQL JDBC Driver**: Download the driver (`postgresql-42.7.3.jar`) from Maven Repository and add it to your project’s classpath.
4. **IDE**: IntelliJ IDEA, Eclipse, or any Java IDE (optional, for development).

## Screenshots
![Creating Room](/img/ss2.png)
![Created Room](/img/ss3.png)
![Joining into the room](/img/ss4.png)
![Messaging each other in the room](/img/ss1.png)