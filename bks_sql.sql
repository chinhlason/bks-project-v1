-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 07, 2023 lúc 06:24 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `bks_sql`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comments`
--

CREATE TABLE `comments` (
  `ID` bigint(20) NOT NULL,
  `userID` bigint(20) NOT NULL,
  `postID` bigint(20) NOT NULL,
  `content` text NOT NULL,
  `create_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `comments`
--

INSERT INTO `comments` (`ID`, `userID`, `postID`, `content`, `create_at`) VALUES
(1, 2, 2, 'hehe', '2023-11-03 03:48:51'),
(2, 2, 2, 'test', '2023-11-03 04:18:38'),
(3, 1, 3, 'test', '2023-11-06 18:07:56'),
(4, 1, 3, 'test', '2023-11-06 18:08:00'),
(5, 1, 3, 'test', '2023-11-06 18:08:01'),
(6, 1, 4, 'test', '2023-11-06 18:08:04'),
(7, 1, 4, 'test', '2023-11-06 18:08:04'),
(8, 1, 4, 'test', '2023-11-06 18:08:05');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `courses`
--

CREATE TABLE `courses` (
  `ID` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `abtrac` text NOT NULL,
  `author` varchar(255) NOT NULL,
  `create_at` datetime NOT NULL,
  `img_url` varchar(255) NOT NULL,
  `price` bigint(20) NOT NULL,
  `serial` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `courses`
--

INSERT INTO `courses` (`ID`, `name`, `abtrac`, `author`, `create_at`, `img_url`, `price`, `serial`) VALUES
(3, 'hoc xlths', 'hoc xlths cung son', 'son ngo', '2023-11-05 06:19:57', '/img/course1', 1000, 'course1'),
(4, 'hoc xlths', 'hoc xlths cung son', 'son ngo', '2023-11-05 06:20:09', '/img/course1', 1000, 'course3');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lessons`
--

CREATE TABLE `lessons` (
  `ID` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL,
  `description` text NOT NULL,
  `title` varchar(255) NOT NULL,
  `course_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `lessons`
--

INSERT INTO `lessons` (`ID`, `create_at`, `description`, `title`, `course_id`) VALUES
(1, '2023-11-05 10:03:35', 'hoc xlths cung son2', 'hoc xlths2', 3),
(2, '2023-11-05 14:30:24', 'hoc xlths cung son', 'hoc xlths', 4),
(3, '2023-11-05 14:30:28', 'hoc xlths cung son', 'hoc xlths', 4),
(4, '2023-11-05 14:30:31', 'hoc xlths cung son', 'hoc xlths', 4),
(5, '2023-11-05 14:30:33', 'hoc xlths cung son', 'hoc xlths', 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `media`
--

CREATE TABLE `media` (
  `ID` bigint(20) NOT NULL,
  `lesson_id` bigint(20) NOT NULL,
  `create_at` datetime(2) NOT NULL,
  `video_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `media`
--

INSERT INTO `media` (`ID`, `lesson_id`, `create_at`, `video_url`) VALUES
(1, 1, '2023-11-05 10:03:35.00', '/img/course2'),
(2, 2, '2023-11-05 14:30:24.00', '/img/course1'),
(3, 3, '2023-11-05 14:30:28.00', '/img/course10'),
(4, 4, '2023-11-05 14:30:31.00', '/img/course11'),
(5, 5, '2023-11-05 14:30:33.00', '/img/course12');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notification`
--

CREATE TABLE `notification` (
  `ID` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL,
  `is_read` bit(2) NOT NULL,
  `message` text NOT NULL,
  `type` varchar(255) NOT NULL,
  `receiver_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `notification`
--

INSERT INTO `notification` (`ID`, `user_id`, `create_at`, `is_read`, `message`, `type`, `receiver_id`) VALUES
(2, 2, '2023-11-03 06:31:54', b'01', 'a', 'PAYMENT_REQUEST', 1),
(3, 1, '2023-11-03 06:39:52', b'00', 'Course confirm successfully', 'PAYMENT_CONFIRMATION', 2),
(4, 2, '2023-11-03 14:27:35', b'00', 'sonnvt', 'PAYMENT_REQUEST', 1),
(5, 1, '2023-11-03 14:30:12', b'00', 'course was confirmed!', 'PAYMENT_CONFIRMATION', 2),
(6, 1, '2023-11-03 14:38:41', b'00', 'Sieu sale 1/1', 'SYSTEM_TO_USER', 1),
(7, 1, '2023-11-03 16:25:00', b'00', 'Sieu sale 1/1', 'SYSTEM_TO_USER', 29),
(8, 1, '2023-11-06 16:58:33', b'00', 'Sieu sale 1/1', 'SYSTEM_TO_USER', 29);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `posts`
--

CREATE TABLE `posts` (
  `ID` bigint(11) NOT NULL,
  `userID` bigint(11) NOT NULL,
  `category` varchar(255) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `create_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `posts`
--

INSERT INTO `posts` (`ID`, `userID`, `category`, `title`, `content`, `create_at`) VALUES
(2, 1, 'sech', 'son dep trai', 'sieu sex', '2023-11-02 15:52:25'),
(3, 1, 'sech2', 'son dep trai2', 'sieu sex2', '2023-11-06 18:07:40'),
(4, 1, 'sech3', 'son dep trai3', 'sieu sex3', '2023-11-06 18:07:48');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `purchase`
--

CREATE TABLE `purchase` (
  `ID` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL,
  `update_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `purchase`
--

INSERT INTO `purchase` (`ID`, `user_id`, `course_id`, `create_at`, `update_at`) VALUES
(1, 1, 3, '2023-11-05 11:20:40', '2023-11-05 17:25:55'),
(2, 1, 3, '2023-11-05 13:25:25', '2023-11-05 13:25:25');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `refreshtoken`
--

CREATE TABLE `refreshtoken` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expiry_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `refreshtoken`
--

INSERT INTO `refreshtoken` (`id`, `user_id`, `token`, `expiry_date`) VALUES
(15, 5, '88130731-76ac-49f5-a1ce-13db60dd849a', '2023-10-30 19:12:57'),
(16, 5, 'f83b2e6a-6b37-4127-8308-ba5977c4e04c', '2023-10-30 19:18:30'),
(144, 1, '3438aafc-7aea-4568-abbb-34ab011d5a85', '2023-11-08 04:27:39'),
(145, 1, '9629602f-8f19-44e4-aad4-8fafcd268284', '2023-11-08 10:32:37');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `register_course`
--

CREATE TABLE `register_course` (
  `ID` bigint(20) NOT NULL,
  `user_ID` bigint(20) NOT NULL,
  `course_ID` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `reset_password_token`
--

CREATE TABLE `reset_password_token` (
  `ID` bigint(20) NOT NULL,
  `user_ID` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expiry_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `reset_password_token`
--

INSERT INTO `reset_password_token` (`ID`, `user_ID`, `token`, `expiry_date`) VALUES
(7, 32, '7dd8f139-1d51-487a-8bea-1d40926dda68', '2023-11-07 16:58:56'),
(8, 33, '6b9d843f-37fe-4d14-be1d-36b2de4cf7ef', '2023-11-07 17:53:06'),
(9, 34, '3698b9af-5cbf-440b-b0bb-e782cbf8bd06', '2023-11-07 17:10:35'),
(10, 35, 'd887c739-967f-450e-be69-7d642f955b0b', '2023-11-07 07:10:49');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `ID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`ID`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `ID` bigint(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`ID`, `username`, `password`, `email`, `fullname`, `phone`) VALUES
(1, 'sonnvt', '$2a$10$p7eLv6R/VUP5EsgM/4Kk6e7cJho/50yDrPO/kujwmHm8iskpZEB26', 'sonnvt2002@gmail.com', 'Son ngo', '0968968032'),
(2, 'sonnvt1', '$2a$10$8xE1stfDubVzdV6QZpJMM.qJqwbcfWTbFvzAPAt5u2OQSN0fFqH0y', 'vuducthinh150@gmail.com', 'Vũ Đức Thịnh', '0968968032'),
(3, 'sonnvt3', '$2a$10$IPjgv9y5xAJUM86jh7KOduCLwxiNTo21ajPbMGx6Hivewu7ygb5N6', 'sonnvt06@gmail.com', 'sonngo', '0923151911'),
(4, 'sonnvt4', '$2a$10$YC1atfNgqkK5GH7B5oQtouh17J8Aa3hMj6NefAJH5DWOXA89VARs6', 'sonnvt064@gmail.com', 'sonngo', '0923151911'),
(5, 'sonnvt6', '$2a$10$IVR8PjHhwZZvPiwA5xcHNuexKIVGfBS/56eBqgSOYd7mnIpDjltd2', 'sonnvt061@gmail.com', 'sonngo', '0923151911'),
(6, 'sonnvt33', '$2a$10$nyhdr03NqEC.WlHzoW/crun8JUomh8D76UgSUkQcoMEr90mAIHGSq', 'sonnvt0361@gmail.com', 'sonngo', '0923151911'),
(7, 'sonnvt313', '$2a$10$3gxtdlxupfTnDUky/3YdiOdjAsZmA75/7XFWWam7yrn4d5jFtdlp6', 'sonnvt0161@gmail.com', 'sonngo', '0923151911'),
(8, 'sonnvtcheck', '$2a$10$SNxKGfwx5Xp.YkbbRrlnROn5EMCh15aeIUQ23VbcgSYxvX0JvKHp6', 'sonnvtcheck@gmail.com', 'sonngo', '0923151911'),
(9, 'sonnvtcheck1', '$2a$10$xVtzUvkKxBHLMv1hEQUUquH3qiVsPp317q0FWGYzeTfMIVQGHp1kS', 'sonnvtcheck1@gmail.com', 'sonngo', '0923151911'),
(10, 'sonnvtcheck2', '$2a$10$HGxUVfCH5xlBx1AoscWbwu/oiCES9xgyHZqnYvjd4XDku7JWd.G7q', 'sonnvtcheck2@gmail.com', 'sonngo', '0923151911'),
(11, 'sonnvtcheck3', '$2a$10$35R53XGzS0rrp8ZaPnb6kO3dUp1wiOkx1izVhxhfhQiBk2jnYR8qe', 'sonnvtcheck3@gmail.com', 'sonngo', '0923151911'),
(12, 'sonnvtcheck13', '$2a$10$hO884jjGAHkDqJ/IutKBIO/ivkogslCklwK.mog6oWO5HF0N.Q3fC', 'sonnvtcheck13@gmail.com', 'sonngo', '0923151911'),
(32, 'sonnvtcheck14', '$2a$10$Jy5kiTzjsSdLKuInfJH3fOkU3eLVhenrfz0jOUZdIrzegeKFOWhiK', 'sodua0350@gmail.com', 'sonngo', '0923151911'),
(33, 'sonnvtcheck15', '$2a$10$I.pR0zi9kCaC4L0sv2QYxe6TLGC4EXzdj/hDjZhH6dqsJFdOIyfpa', 'sonnvt03@gmail.com', 'sonngo', '0923151911'),
(34, 'sonnvtcheck16', '$2a$10$KT6Rdwms0pq.Uxn5QtIEcuuxMPi7K7cy5b4DiUDgJYMCi7M.r0LFu', 'sonnvt05@gmail.com', 'sonngo', '0923151911'),
(35, 'sonnvtcheck17', '$2a$10$8.XcuU3bkgY60YHQTykgRe1/XRU5gc2f.OKWlePjQJqiBet6PcbPe', 'sonnvtf8@gmail.com', 'sonngo', '0923151911');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_process`
--

CREATE TABLE `user_process` (
  `ID` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `lesson_id` bigint(20) NOT NULL,
  `is_complete` bit(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_process`
--

INSERT INTO `user_process` (`ID`, `create_at`, `user_id`, `lesson_id`, `is_complete`) VALUES
(6, '2023-11-06 13:46:18', 1, 5, b'01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_role`
--

CREATE TABLE `user_role` (
  `user_ID` bigint(20) NOT NULL,
  `role_ID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_role`
--

INSERT INTO `user_role` (`user_ID`, `role_ID`) VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2),
(9, 2),
(10, 2),
(11, 2),
(12, 2),
(32, 2),
(33, 2),
(34, 2),
(35, 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_ID` (`userID`),
  ADD KEY `post_ID` (`postID`);

--
-- Chỉ mục cho bảng `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `lessons`
--
ALTER TABLE `lessons`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `course_ID` (`lesson_id`);

--
-- Chỉ mục cho bảng `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_ID` (`user_id`);

--
-- Chỉ mục cho bảng `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_ID` (`userID`);

--
-- Chỉ mục cho bảng `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `refreshtoken`
--
ALTER TABLE `refreshtoken`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `register_course`
--
ALTER TABLE `register_course`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_ID` (`user_ID`),
  ADD KEY `course_ID` (`course_ID`);

--
-- Chỉ mục cho bảng `reset_password_token`
--
ALTER TABLE `reset_password_token`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_ID` (`user_ID`),
  ADD KEY `user_ID_2` (`user_ID`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Chỉ mục cho bảng `user_process`
--
ALTER TABLE `user_process`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `comments`
--
ALTER TABLE `comments`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `courses`
--
ALTER TABLE `courses`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `lessons`
--
ALTER TABLE `lessons`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `media`
--
ALTER TABLE `media`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `notification`
--
ALTER TABLE `notification`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `posts`
--
ALTER TABLE `posts`
  MODIFY `ID` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `purchase`
--
ALTER TABLE `purchase`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `refreshtoken`
--
ALTER TABLE `refreshtoken`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=146;

--
-- AUTO_INCREMENT cho bảng `reset_password_token`
--
ALTER TABLE `reset_password_token`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT cho bảng `user_process`
--
ALTER TABLE `user_process`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
