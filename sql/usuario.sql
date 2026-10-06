-- Execute DEPOIS de importar o mercado.sql
-- (o mercado.sql começa com DROP DATABASE e apagaria estas tabelas).
USE supermercado;

-- 1) Tabela exigida pela atividade
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomeusuario VARCHAR(255) NOT NULL UNIQUE,
    senhahash VARCHAR(60) NOT NULL,
    email VARCHAR(255)
);

-- 2) Tokens de recuperação de senha ("Esqueci minha senha")
--    Guarda apenas o SHA-256 do token (nunca o token do link).
CREATE TABLE IF NOT EXISTS token_recuperacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    token_hash CHAR(64) NOT NULL UNIQUE,
    expira_em DATETIME NOT NULL,
    usado TINYINT(1) NOT NULL DEFAULT 0,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_token_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuario(id) ON DELETE CASCADE
);

-- Opcional: impedir e-mails repetidos também no banco (a aplicação já verifica).
-- ALTER TABLE usuario ADD UNIQUE (email);
