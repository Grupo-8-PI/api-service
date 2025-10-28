-- Inserindo os 2 tipos fixos de acabamento conforme o enum TipoAcabamento
INSERT INTO acabamento (id, tipo_acabamento) VALUES (1, 'CAPA_DURA');
INSERT INTO acabamento (id, tipo_acabamento) VALUES (2, 'BROCHURA');


INSERT INTO categoria (nome_categoria) VALUES ('Ficção Cientifica');
INSERT INTO categoria (nome_categoria) VALUES ('Romance');
INSERT INTO categoria (nome_categoria) VALUES ('Culinária');
INSERT INTO categoria (nome_categoria) VALUES ('Distopia');
INSERT INTO categoria (nome_categoria) VALUES ('Mistérios/Suspense');
INSERT INTO categoria (nome_categoria) VALUES ('Terror');
INSERT INTO categoria (nome_categoria) VALUES ('Filosofia');
INSERT INTO categoria (nome_categoria) VALUES ('Outros');

-- Inserindo os 4 tipos fixos de conservação conforme o enum TipoConservacao
INSERT INTO conservacao (id, tipo_conservacao) VALUES (1, 'EXCELENTE');
INSERT INTO conservacao (id, tipo_conservacao) VALUES (2, 'BOM');
INSERT INTO conservacao (id, tipo_conservacao) VALUES (3, 'RAZOAVEL');
INSERT INTO conservacao (id, tipo_conservacao) VALUES (4, 'DEGRADADO');
