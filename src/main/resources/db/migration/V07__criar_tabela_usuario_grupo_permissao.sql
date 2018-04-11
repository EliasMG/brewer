CREATE TABLE usuario (
    codigo SERIAL PRIMARY KEY NOT NULL,
    nome TEXT NOT NULL,
    email TEXT NOT NULL,
    senha TEXT NOT NULL,
    ativo BOOLEAN DEFAULT true,
    data_nascimento TIMESTAMP WITH TIME ZONE 
);
-- CHARACTER(60)
CREATE TABLE grupo (
    codigo SERIAL PRIMARY KEY NOT NULL,
    nome TEXT NOT NULL
);

CREATE TABLE permissao (
    codigo SERIAL PRIMARY KEY NOT NULL,
    nome TEXT NOT NULL
);

CREATE TABLE usuario_grupo (
    codigo_usuario INTEGER NOT NULL,
    codigo_grupo INTEGER NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_grupo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(codigo)
);

CREATE TABLE grupo_permissao (
    codigo_grupo INTEGER NOT NULL,
    codigo_permissao INTEGER NOT NULL,
    PRIMARY KEY (codigo_grupo, codigo_permissao),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(codigo),
    FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);
