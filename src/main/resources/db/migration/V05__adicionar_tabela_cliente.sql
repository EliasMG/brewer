CREATE TABLE cliente (
    codigo SERIAL PRIMARY KEY NOT NULL,
    nome TEXT NOT NULL,
    tipo_pessoa TEXT NOT NULL,
    cpf_cnpj TEXT,
    telefone TEXT,
    email TEXT NOT NULL,
    logradouro TEXT,
    numero TEXT,
    complemento TEXT,
    cep TEXT,
    codigo_cidade INTEGER,
    FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo)
);