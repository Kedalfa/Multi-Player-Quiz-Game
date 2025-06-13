# Multiplayer Online Quiz Game

## Project Overview
A modern, user-friendly Multiplayer Online Quiz Game built for university advanced programming coursework. This project demonstrates four core advanced programming concepts: **database**, **file handling**, **threading**, and **socket programming**. Multiple users can log in, join a real-time multiplayer lobby, chat, and compete in quiz matches. The game uses sockets for real-time communication and supports running multiple app instances on the same computer.

---

## Features
- **User Registration and Login System**
- **Single Player Quiz Mode**
  - Play quizzes solo and track your results
  - Choose from 4 categories: Science, General, Sports, and History
  - Select the number of questions (5 or 10)
  - Choose difficulty level: Easy, Medium, or Hard
- **Multiplayer Quiz Mode**
  - Join a real-time multiplayer lobby
  - See a live list of online users (with friendly message if none)
  - Chat with other users in the lobby
  - Compete in quiz matches with other users
- **Quiz Customization**
  - Select quiz category, number of questions, and difficulty
- **Question and Answer System**
  - Multiple-choice questions with instant feedback
  - Scoring system and result summary
- **Real-Time Multiplayer Lobby**
  - See who is online in real time
  - User list updates automatically as users join/leave
- **Automatic or Manual Server Startup**
- **Real-Time Socket Communication**
- **Threading**
  - Each client and server connection runs in its own thread for smooth, responsive UI and networking
- **Database Integration**
  - MySQL database stores users, questions, and results
- **File Handling**
  - Download your quiz results as a TXT file
- **User-Friendly Graphical Interface (GUI)**
  - Modern, intuitive design using Java Swing
- **Error Handling and Connection Feedback**
  - Friendly messages for connection issues or empty lobbies
- **Testable on a Single PC**
  - Open multiple app windows and log in as different users to simulate multiplayer

---

## Technologies Used
- **Programming Language:** Java
- **Socket Programming:** Java Sockets (TCP)
- **Threading:** Java Threads
- **Database:** MySQL (with JDBC for connectivity)
- **File Handling:** Java File I/O (for saving results)
- **GUI Framework:** Java Swing

---

## Getting Started

### 1. Clone the Repository
```
git clone https://github.com/Kedalfa/Multi-Player-Quiz-Game.git
```

### 2. Set Up the Database
- Import the provided SQL file (`quizgame.sql`) into your MySQL server.
- Update the database connection settings in `src/db/DBConnection.java` if needed (username, password).


### 3. Run the Server

### 4. Run the Application (Client)

### 5. Log In as Different Users
- Register or log in with different usernames in each app window.

### 6. Open the Multiplayer Window
- Click the "Multiplayer Quiz" button in the dashboard.
- You will see a list of online users in real time.
- If no users are online, a message will appear.

### 9. Start a Quiz Match
- You can start a quiz match and compete with other users.

---

## How to Test Multiplayer
- Open two (or more) app windows by running the client command multiple times.
- Log in as different users in each window.
- Open the multiplayer window in each app.
- You should see all logged-in users in the online users list.
- This works even if all app windows are on the same computer (using `localhost`).

---

## Folder Structure
```
Multi-Player-Quiz-Game/
        ├── src/                # Java source code
        │   ├── db/             # Database access classes
        │   ├── model/          # Data models (User, Question, etc.)
        │   ├── network/        # Server and client socket code
        │   ├── ui/             # User interface (Swing pages)
        │   └── main/           # Main entry points
        ├── db_schema.sql       # MySQL database schema and sample data
        ├── README.md           
```

---

## Screenshots


## 📸 App Screenshots

###  Home Page  
![Home Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/home.png?raw=true)

###  Register Page  
![Register Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/register.png?raw=true)

###  Login Page  
![Login Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/login.png?raw=true)

###  Dashboard Page  
![Dashboard Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/dashboard.png?raw=true)

###  Single Player Page  
![Single Player Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/singlePlayer.png?raw=true)

###  Quiz Page  
![Quiz Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/Quiz.png?raw=true)

###  Result Page  
![Result Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/result.png?raw=true)

###  History Page  
![History Page](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/history.png?raw=true)

###  Multiplayer Lobby  
![Multiplayer Lobby](https://github.com/Kedalfa/Multi-Player-Quiz-Game/blob/main/Screenshots/multiPlayerLobby.png?raw=true)



---

## Author
Developed by: Kaleb Dagnachew
