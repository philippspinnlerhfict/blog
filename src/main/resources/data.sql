INSERT INTO user (id, username, `password`) VALUES
(1, 'homer', '1234'),
(2, 'marge', '5678');

INSERT INTO post (id, title, content, user_id) VALUES
(1, 'First Post', 'Super useful post', 1),
(2, 'Second Post', 'Best post ever', 2);

INSERT INTO comment (id, `text`, user_id, post_id) VALUES
(1, 'Super comment', 1, 2),
(2, 'Even better comment', 2, 1);