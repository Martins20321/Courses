CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome_completo VARCHAR(100) NOT NULL,
    nick_name VARCHAR(100) NOT NULL UNIQUE,
    head_line VARCHAR(30),
    biografia TEXT
    );