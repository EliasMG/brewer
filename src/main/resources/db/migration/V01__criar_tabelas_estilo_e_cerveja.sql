CREATE TABLE estilo (
    codigo SERIAL PRIMARY KEY NOT NULL,
    nome TEXT NOT NULL
);

CREATE TABLE cerveja (
    codigo SERIAL PRIMARY KEY NOT NULL,
    sku TEXT NOT NULL,
    nome TEXT NOT NULL,
    descricao TEXT NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    teor_alcoolico NUMERIC(10, 2) NOT NULL,
    comissao NUMERIC(10, 2) NOT NULL,
    sabor TEXT NOT NULL,
    origem TEXT NOT NULL,
    codigo_estilo INTEGER NOT NULL,
    FOREIGN KEY (codigo_estilo) REFERENCES estilo(codigo)
);

INSERT INTO estilo(nome) VALUES ( 'Amber Lager');
INSERT INTO estilo(nome) VALUES ( 'Dark Lager');
INSERT INTO estilo(nome) VALUES ( 'Pale Lager');
INSERT INTO estilo(nome) VALUES ( 'Pilsner');