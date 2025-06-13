-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 06, 2025 at 10:22 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quizgame`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'General'),
(3, 'History'),
(2, 'Science'),
(4, 'Sports');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `question_text` text NOT NULL,
  `option1` varchar(255) NOT NULL,
  `option2` varchar(255) NOT NULL,
  `option3` varchar(255) NOT NULL,
  `option4` varchar(255) NOT NULL,
  `correct_answer` varchar(255) NOT NULL,
  `category` varchar(50) NOT NULL,
  `difficulty` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `question_text`, `option1`, `option2`, `option3`, `option4`, `correct_answer`, `category`, `difficulty`) VALUES
(1, 'What is the capital of France?', 'Paris', 'London', 'Berlin', 'Madrid', 'Paris', 'General', 'Easy'),
(2, 'Who discovered gravity?', 'Newton', 'Einstein', 'Galileo', 'Tesla', 'Newton', 'Science', 'Easy'),
(3, 'Which year did World War II end?', '1942', '1945', '1939', '1918', '1945', 'History', 'Medium'),
(4, 'How many players in a football team?', '9', '10', '11', '12', '11', 'Sports', 'Easy'),
(5, 'What is the capital of France?', 'Paris', 'London', 'Berlin', 'Madrid', 'Paris', 'General', 'Easy'),
(6, 'What color is the sky on a clear day?', 'Blue', 'Green', 'Red', 'Yellow', 'Blue', 'General', 'Easy'),
(7, 'How many days are there in a week?', '5', '6', '7', '8', '7', 'General', 'Easy'),
(8, 'Which animal barks?', 'Cat', 'Dog', 'Cow', 'Sheep', 'Dog', 'General', 'Easy'),
(9, 'What is 2 + 2?', '3', '4', '5', '6', '4', 'General', 'Easy'),
(10, 'Which month comes after March?', 'April', 'May', 'June', 'July', 'April', 'General', 'Easy'),
(11, 'What do bees make?', 'Milk', 'Honey', 'Bread', 'Juice', 'Honey', 'General', 'Easy'),
(12, 'What is the opposite of hot?', 'Cold', 'Warm', 'Cool', 'Boiling', 'Cold', 'General', 'Easy'),
(13, 'Which fruit is yellow?', 'Apple', 'Banana', 'Grape', 'Cherry', 'Banana', 'General', 'Easy'),
(14, 'What do you drink from?', 'Plate', 'Cup', 'Fork', 'Knife', 'Cup', 'General', 'Easy'),
(15, 'Who wrote \"Romeo and Juliet\"?', 'Shakespeare', 'Dickens', 'Austen', 'Hemingway', 'Shakespeare', 'General', 'Medium'),
(16, 'What is the largest ocean?', 'Atlantic', 'Indian', 'Arctic', 'Pacific', 'Pacific', 'General', 'Medium'),
(17, 'Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Jupiter', 'Venus', 'Mars', 'General', 'Medium'),
(18, 'What is the boiling point of water?', '90°C', '100°C', '110°C', '120°C', '100°C', 'General', 'Medium'),
(19, 'Who painted the Mona Lisa?', 'Van Gogh', 'Da Vinci', 'Picasso', 'Rembrandt', 'Da Vinci', 'General', 'Medium'),
(20, 'What is the main language in Brazil?', 'Spanish', 'Portuguese', 'French', 'English', 'Portuguese', 'General', 'Medium'),
(21, 'What is the currency of Japan?', 'Yen', 'Dollar', 'Euro', 'Peso', 'Yen', 'General', 'Medium'),
(22, 'Which gas do plants absorb?', 'Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Hydrogen', 'Carbon Dioxide', 'General', 'Medium'),
(23, 'What is the hardest natural substance?', 'Gold', 'Iron', 'Diamond', 'Silver', 'Diamond', 'General', 'Medium'),
(24, 'Which continent is Egypt in?', 'Asia', 'Africa', 'Europe', 'Australia', 'Africa', 'General', 'Medium'),
(25, 'What is the smallest prime number?', '0', '1', '2', '3', '2', 'General', 'Hard'),
(26, 'Who developed the theory of relativity?', 'Newton', 'Einstein', 'Tesla', 'Curie', 'Einstein', 'General', 'Hard'),
(27, 'What is the chemical symbol for gold?', 'Au', 'Ag', 'Gd', 'Go', 'Au', 'General', 'Hard'),
(28, 'Which country hosted the 2016 Olympics?', 'China', 'UK', 'Brazil', 'Russia', 'Brazil', 'General', 'Hard'),
(29, 'What is the square root of 144?', '10', '11', '12', '13', '12', 'General', 'Hard'),
(30, 'Who is the author of \"1984\"?', 'Orwell', 'Huxley', 'Bradbury', 'Atwood', 'Orwell', 'General', 'Hard'),
(31, 'What is the capital of Canada?', 'Toronto', 'Vancouver', 'Ottawa', 'Montreal', 'Ottawa', 'General', 'Hard'),
(32, 'Which element has atomic number 1?', 'Oxygen', 'Hydrogen', 'Helium', 'Carbon', 'Hydrogen', 'General', 'Hard'),
(33, 'What is the largest desert?', 'Sahara', 'Gobi', 'Kalahari', 'Arctic', 'Sahara', 'General', 'Hard'),
(34, 'Who discovered penicillin?', 'Fleming', 'Pasteur', 'Curie', 'Salk', 'Fleming', 'General', 'Hard'),
(35, 'What do plants need for photosynthesis?', 'Sunlight', 'Moonlight', 'Starlight', 'Fire', 'Sunlight', 'Science', 'Easy'),
(36, 'What is H2O?', 'Oxygen', 'Hydrogen', 'Water', 'Salt', 'Water', 'Science', 'Easy'),
(37, 'Which planet is closest to the sun?', 'Venus', 'Earth', 'Mercury', 'Mars', 'Mercury', 'Science', 'Easy'),
(38, 'What do humans breathe in?', 'Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Helium', 'Oxygen', 'Science', 'Easy'),
(39, 'What is the freezing point of water?', '0°C', '10°C', '20°C', '100°C', '0°C', 'Science', 'Easy'),
(40, 'What is the center of an atom called?', 'Electron', 'Proton', 'Nucleus', 'Neutron', 'Nucleus', 'Science', 'Easy'),
(41, 'Which animal lays eggs?', 'Dog', 'Cat', 'Duck', 'Cow', 'Duck', 'Science', 'Easy'),
(42, 'What is the main source of energy for Earth?', 'Wind', 'Sun', 'Water', 'Coal', 'Sun', 'Science', 'Easy'),
(43, 'What is the largest organ in the human body?', 'Heart', 'Skin', 'Liver', 'Brain', 'Skin', 'Science', 'Easy'),
(44, 'What do bees collect from flowers?', 'Water', 'Nectar', 'Leaves', 'Seeds', 'Nectar', 'Science', 'Easy'),
(45, 'What is the process of water turning into vapor?', 'Melting', 'Evaporation', 'Condensation', 'Freezing', 'Evaporation', 'Science', 'Medium'),
(46, 'What is the chemical symbol for sodium?', 'Na', 'So', 'S', 'N', 'Na', 'Science', 'Medium'),
(47, 'Which planet has rings?', 'Earth', 'Mars', 'Saturn', 'Venus', 'Saturn', 'Science', 'Medium'),
(48, 'What is the hardest substance in the human body?', 'Bone', 'Tooth Enamel', 'Nail', 'Hair', 'Tooth Enamel', 'Science', 'Medium'),
(49, 'What is the boiling point of water in Fahrenheit?', '100°F', '180°F', '212°F', '220°F', '212°F', 'Science', 'Medium'),
(50, 'What is the main gas in the air we breathe?', 'Oxygen', 'Nitrogen', 'Carbon Dioxide', 'Hydrogen', 'Nitrogen', 'Science', 'Medium'),
(51, 'What is the study of living things called?', 'Geology', 'Biology', 'Physics', 'Astronomy', 'Biology', 'Science', 'Medium'),
(52, 'What is the largest planet?', 'Earth', 'Jupiter', 'Saturn', 'Mars', 'Jupiter', 'Science', 'Medium'),
(53, 'What is the process by which plants make food?', 'Respiration', 'Photosynthesis', 'Digestion', 'Fermentation', 'Photosynthesis', 'Science', 'Medium'),
(54, 'What is the chemical symbol for iron?', 'Ir', 'Fe', 'In', 'I', 'Fe', 'Science', 'Medium'),
(55, 'What is the powerhouse of the cell?', 'Nucleus', 'Mitochondria', 'Ribosome', 'Chloroplast', 'Mitochondria', 'Science', 'Hard'),
(56, 'What is the speed of light?', '300,000 km/s', '150,000 km/s', '1,000 km/s', '30,000 km/s', '300,000 km/s', 'Science', 'Hard'),
(57, 'Who developed the polio vaccine?', 'Fleming', 'Salk', 'Pasteur', 'Jenner', 'Salk', 'Science', 'Hard'),
(58, 'What is the chemical formula for table salt?', 'NaCl', 'KCl', 'CaCl2', 'Na2SO4', 'NaCl', 'Science', 'Hard'),
(59, 'What is the largest bone in the human body?', 'Femur', 'Tibia', 'Humerus', 'Skull', 'Femur', 'Science', 'Hard'),
(60, 'What is the main function of red blood cells?', 'Fight infection', 'Carry oxygen', 'Digest food', 'Clot blood', 'Carry oxygen', 'Science', 'Hard'),
(61, 'What is the most abundant gas in Earth’s atmosphere?', 'Oxygen', 'Nitrogen', 'Carbon Dioxide', 'Hydrogen', 'Nitrogen', 'Science', 'Hard'),
(62, 'What is the chemical symbol for potassium?', 'K', 'P', 'Pt', 'Po', 'K', 'Science', 'Hard'),
(63, 'Who discovered penicillin?', 'Fleming', 'Pasteur', 'Curie', 'Salk', 'Fleming', 'Science', 'Hard'),
(64, 'What is the study of earthquakes called?', 'Meteorology', 'Seismology', 'Geology', 'Biology', 'Seismology', 'Science', 'Hard'),
(65, 'Who was the first President of the USA?', 'Washington', 'Lincoln', 'Jefferson', 'Adams', 'Washington', 'History', 'Easy'),
(66, 'In which country were the pyramids built?', 'Greece', 'Italy', 'Egypt', 'Mexico', 'Egypt', 'History', 'Easy'),
(67, 'Who discovered America?', 'Columbus', 'Magellan', 'Cook', 'Vespucci', 'Columbus', 'History', 'Easy'),
(68, 'What year did World War II end?', '1942', '1945', '1939', '1918', '1945', 'History', 'Easy'),
(69, 'Who was known as the Maid of Orleans?', 'Cleopatra', 'Joan of Arc', 'Elizabeth I', 'Victoria', 'Joan of Arc', 'History', 'Easy'),
(70, 'Who was the first man on the moon?', 'Armstrong', 'Aldrin', 'Collins', 'Gagarin', 'Armstrong', 'History', 'Easy'),
(71, 'What was the name of the ship on which the Pilgrims traveled to America?', 'Santa Maria', 'Mayflower', 'Endeavour', 'Discovery', 'Mayflower', 'History', 'Easy'),
(72, 'Who was the first emperor of Rome?', 'Augustus', 'Caesar', 'Nero', 'Tiberius', 'Augustus', 'History', 'Easy'),
(73, 'Who invented the telephone?', 'Bell', 'Edison', 'Tesla', 'Marconi', 'Bell', 'History', 'Easy'),
(74, 'Who was the British queen with the longest reign before Elizabeth II?', 'Victoria', 'Mary', 'Anne', 'Elizabeth I', 'Victoria', 'History', 'Easy'),
(75, 'Who was the 16th President of the USA?', 'Washington', 'Lincoln', 'Grant', 'Adams', 'Lincoln', 'History', 'Medium'),
(76, 'What year did the Berlin Wall fall?', '1987', '1989', '1991', '1993', '1989', 'History', 'Medium'),
(77, 'Who was the first female Prime Minister of the UK?', 'Thatcher', 'May', 'Merkel', 'Gandhi', 'Thatcher', 'History', 'Medium'),
(78, 'Who was the leader of the Soviet Union during WWII?', 'Stalin', 'Lenin', 'Trotsky', 'Gorbachev', 'Stalin', 'History', 'Medium'),
(79, 'Who was the first President of India?', 'Gandhi', 'Nehru', 'Prasad', 'Patel', 'Prasad', 'History', 'Medium'),
(80, 'Who discovered penicillin?', 'Fleming', 'Pasteur', 'Curie', 'Salk', 'Fleming', 'History', 'Medium'),
(81, 'Who was the first woman to win a Nobel Prize?', 'Curie', 'Meitner', 'Franklin', 'Goeppert', 'Curie', 'History', 'Medium'),
(82, 'Who was the first black President of South Africa?', 'Mandela', 'Tutu', 'Biko', 'Mbeki', 'Mandela', 'History', 'Medium'),
(83, 'Who was the first President of Turkey?', 'Ataturk', 'Erdogan', 'Demirel', 'Inonu', 'Ataturk', 'History', 'Medium'),
(84, 'Who was the first Chancellor of Germany?', 'Bismarck', 'Adenauer', 'Merkel', 'Schmidt', 'Bismarck', 'History', 'Medium'),
(85, 'Who was the first emperor of China?', 'Qin Shi Huang', 'Wu Zetian', 'Kublai Khan', 'Sun Yat-sen', 'Qin Shi Huang', 'History', 'Hard'),
(86, 'Who was the first President of the Republic of China?', 'Sun Yat-sen', 'Mao Zedong', 'Chiang Kai-shek', 'Deng Xiaoping', 'Sun Yat-sen', 'History', 'Hard'),
(87, 'Who was the first Tsar of Russia?', 'Ivan IV', 'Peter I', 'Nicholas II', 'Alexander I', 'Ivan IV', 'History', 'Hard'),
(88, 'Who was the first king of England?', 'Athelstan', 'Alfred', 'Edward', 'William', 'Athelstan', 'History', 'Hard'),
(89, 'Who was the first President of France?', 'Napoleon', 'De Gaulle', 'Louis-Napoleon', 'Macron', 'Louis-Napoleon', 'History', 'Hard'),
(90, 'Who was the first President of South Korea?', 'Rhee', 'Kim', 'Park', 'Chun', 'Rhee', 'History', 'Hard'),
(91, 'Who was the first President of Israel?', 'Weizmann', 'Ben-Gurion', 'Begin', 'Shamir', 'Weizmann', 'History', 'Hard'),
(92, 'Who was the first President of Pakistan?', 'Jinnah', 'Bhutto', 'Khan', 'Sharif', 'Jinnah', 'History', 'Hard'),
(93, 'Who was the first President of Nigeria?', 'Azikiwe', 'Balewa', 'Obasanjo', 'Babangida', 'Azikiwe', 'History', 'Hard'),
(94, 'Who was the first President of Ghana?', 'Nkrumah', 'Rawlings', 'Akufo-Addo', 'Mills', 'Nkrumah', 'History', 'Hard'),
(95, 'How many players in a football team?', '9', '10', '11', '12', '11', 'Sports', 'Easy'),
(96, 'What sport uses a bat and ball?', 'Football', 'Basketball', 'Cricket', 'Tennis', 'Cricket', 'Sports', 'Easy'),
(97, 'What color is a tennis ball?', 'Red', 'Blue', 'Green', 'Yellow', 'Yellow', 'Sports', 'Easy'),
(98, 'How many holes in a standard golf course?', '9', '12', '15', '18', '18', 'Sports', 'Easy'),
(99, 'What is the national sport of Japan?', 'Sumo', 'Judo', 'Karate', 'Kendo', 'Sumo', 'Sports', 'Easy'),
(100, 'What sport is played at Wimbledon?', 'Tennis', 'Football', 'Cricket', 'Golf', 'Tennis', 'Sports', 'Easy'),
(101, 'How many bases in baseball?', '2', '3', '4', '5', '4', 'Sports', 'Easy'),
(102, 'What is the highest score in a single frame of snooker?', '147', '100', '200', '300', '147', 'Sports', 'Easy'),
(103, 'What is the shape of a soccer ball?', 'Square', 'Round', 'Oval', 'Triangle', 'Round', 'Sports', 'Easy'),
(104, 'What is the Olympic symbol?', 'Stars', 'Rings', 'Stripes', 'Dots', 'Rings', 'Sports', 'Easy'),
(105, 'How many minutes in a rugby match?', '60', '70', '80', '90', '80', 'Sports', 'Medium'),
(106, 'What is the diameter of a basketball hoop in inches?', '16', '18', '20', '22', '18', 'Sports', 'Medium'),
(107, 'What is the maximum break in snooker?', '147', '155', '200', '300', '147', 'Sports', 'Medium'),
(108, 'What is the length of an Olympic swimming pool in meters?', '25', '33', '50', '100', '50', 'Sports', 'Medium'),
(109, 'What is the national sport of Canada?', 'Lacrosse', 'Ice Hockey', 'Basketball', 'Baseball', 'Lacrosse', 'Sports', 'Medium'),
(110, 'What is the only country to play in every World Cup?', 'Brazil', 'Germany', 'Italy', 'Argentina', 'Brazil', 'Sports', 'Medium'),
(111, 'What is the highest score in 10-pin bowling?', '200', '250', '300', '350', '300', 'Sports', 'Medium'),
(112, 'What is the distance of a marathon in miles?', '24.2', '25.2', '26.2', '27.2', '26.2', 'Sports', 'Medium'),
(113, 'What is the name of the football stadium in Barcelona?', 'San Siro', 'Camp Nou', 'Bernabeu', 'Anfield', 'Camp Nou', 'Sports', 'Medium'),
(114, 'What is the term for 3 goals in a game?', 'Double', 'Hat-trick', 'Quad', 'Triple', 'Hat-trick', 'Sports', 'Medium'),
(115, 'What is the maximum score in a single game of ten-pin bowling?', '200', '250', '300', '350', '300', 'Sports', 'Hard'),
(116, 'What is the length of a cricket pitch in yards?', '18', '20', '22', '24', '22', 'Sports', 'Hard'),
(117, 'What is the weight of a shot put in kg (men)?', '5', '6', '7.26', '8', '7.26', 'Sports', 'Hard'),
(118, 'What is the world record for the 100m sprint (men)?', '9.58', '9.68', '9.78', '9.88', '9.58', 'Sports', 'Hard'),
(119, 'What is the height of a basketball hoop in feet?', '8', '9', '10', '11', '10', 'Sports', 'Hard'),
(120, 'What is the length of a football field in meters?', '90', '100', '110', '120', '100', 'Sports', 'Hard'),
(121, 'What is the number of squares on a chessboard?', '32', '48', '64', '100', '64', 'Sports', 'Hard'),
(122, 'What is the name of the trophy for the Wimbledon winner?', 'Cup', 'Plate', 'Shield', 'Bowl', 'Plate', 'Sports', 'Hard'),
(123, 'What is the maximum break in snooker?', '147', '155', '200', '300', '147', 'Sports', 'Hard'),
(124, 'What is the name of the first Olympic Games host city?', 'Athens', 'Paris', 'London', 'Rome', 'Athens', 'Sports', 'Hard');

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `total_questions` int(11) NOT NULL,
  `correct_answers` int(11) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`id`, `user_id`, `score`, `total_questions`, `correct_answers`, `timestamp`) VALUES
(1, 1, 0, 0, 0, '2025-05-21 15:16:48'),
(2, 1, 0, 5, 0, '2025-05-21 15:48:57'),
(3, 1, 30, 5, 3, '2025-05-21 15:53:12'),
(4, 1, 10, 5, 1, '2025-05-21 16:04:45'),
(5, 1, 40, 10, 4, '2025-05-22 00:59:39'),
(6, 1, 50, 5, 5, '2025-05-22 01:28:24'),
(7, 1, 40, 5, 4, '2025-05-22 01:32:33'),
(8, 1, 50, 5, 5, '2025-05-22 01:38:57'),
(9, 1, 20, 5, 2, '2025-05-22 02:53:42'),
(10, 1, 50, 10, 5, '2025-05-23 08:41:35'),
(11, 1, 40, 5, 4, '2025-06-06 00:18:33');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(64) NOT NULL,
  `email` varchar(100) NOT NULL,
  `registration_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password_hash`, `email`, `registration_date`) VALUES
(1, 'test', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'test@gmail.com', '2025-05-21 15:06:28'),
(2, 'abel', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'abel@gmail.com', '2025-05-22 02:14:02');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_category` (`category`),
  ADD KEY `idx_difficulty` (`difficulty`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `results`
--
ALTER TABLE `results`
  ADD CONSTRAINT `results_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
