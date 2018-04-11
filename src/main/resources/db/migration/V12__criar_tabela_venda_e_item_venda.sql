CREATE TABLE venda (
    codigo SERIAL PRIMARY KEY NOT NULL,
    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL,
    valor_frete NUMERIC(10,2),
    valor_desconto NUMERIC(10,2),
    valor_total NUMERIC(10,2) NOT NULL,
    status TEXT NOT NULL,
    observacao TEXT,
    data_hora_entrega TIMESTAMP WITH TIME ZONE,
    codigo_cliente INTEGER NOT NULL,
    codigo_usuario INTEGER NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
);

CREATE TABLE item_venda (
    codigo SERIAL PRIMARY KEY NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_unitario NUMERIC(10,2) NOT NULL,
    codigo_cerveja INTEGER NOT NULL,
    codigo_venda INTEGER NOT NULL,
    FOREIGN KEY (codigo_cerveja) REFERENCES cerveja(codigo),
    FOREIGN KEY (codigo_venda) REFERENCES venda(codigo)
);