CREATE TABLE refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(300) NOT NULL UNIQUE,
    utilizado BOOLEAN NOT NULL DEFAULT false,
    usuario_id BIGINT,
    CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
    );