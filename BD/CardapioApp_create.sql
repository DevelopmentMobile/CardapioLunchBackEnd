-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2015-06-03 12:27:52.0



-- tables
-- Table: funcionario
CREATE TABLE funcionario (
    _id integer NOT NULL  PRIMARY KEY,
    login text NOT NULL,
    senha text NOT NULL,
    tipo_funcionario integer NOT NULL
);

-- Table: pacote
CREATE TABLE pacote (
    _id integer NOT NULL  PRIMARY KEY,
    nome_pacote text,
    tipo_pacote integer,
    descricao_pacote text NOT NULL,
    preco decimal(6,2)
);

-- Table: pedidos
CREATE TABLE pedidos (
    _id integer NOT NULL  PRIMARY KEY,
    produto_id integer NOT NULL,
    funcionario_id integer NOT NULL,
    pacote_id integer NOT NULL,
    status_pedido integer NOT NULL,
    tempo_total_pedido integer NOT NULL,
    num_mesa integer,
    quantidade integer,
    FOREIGN KEY (produto_id) REFERENCES produto (_id),
    FOREIGN KEY (pacote_id) REFERENCES pacote (_id),
    FOREIGN KEY (funcionario_id) REFERENCES funcionario (_id)
);

-- Table: produto
CREATE TABLE produto (
    _id integer NOT NULL  PRIMARY KEY,
    unidade_estoque integer,
    nome text NOT NULL,
    preco decimal(12,2),
    descricao text NOT NULL,
    nome_imagem text NOT NULL,
    tempo_pronto_produto integer,
    categoria text
);





-- End of file.

