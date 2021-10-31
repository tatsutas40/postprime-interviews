CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    created_at timestamp default CURRENT_TIMESTAMP
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    text varchar(4096) NOT NULL,
    posted_by integer,
    created_at timestamp,
    updated_at timestamp
);
CREATE INDEX posts_idx1 ON posts (posted_by);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    post_id integer,
    text varchar(280) NOT NULL,
    commented_by integer,
    is_spam boolean,
    created_at timestamp,
    updated_at timestamp
);
CREATE INDEX comments_idx1 ON comments (post_id);
CREATE INDEX comments_idx2 ON comments (commented_by);

CREATE TABLE post_likes (
    id SERIAL PRIMARY KEY,
    post_id integer,
    liked_by integer,
    created_at timestamp
);
CREATE INDEX post_likes_idx1 ON post_likes (post_id);
CREATE INDEX post_likes_idx2 ON post_likes (liked_by);

CREATE TABLE comment_likes (
    id SERIAL PRIMARY KEY,
    comment_id integer,
    liked_by integer,
    created_at timestamp
);
CREATE INDEX comment_likes_idx1 ON comment_likes (comment_id);
CREATE INDEX comment_likes_idx2 ON comment_likes (liked_by);
