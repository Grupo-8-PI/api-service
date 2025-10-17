-- Inserindo os 2 tipos fixos de acabamento conforme o enum TipoAcabamento
INSERT IGNORE INTO acabamento (id, tipo_acabamento) VALUES (1, 'CAPA_DURA');
INSERT IGNORE INTO acabamento (id, tipo_acabamento) VALUES (2, 'BROCHURA');


INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Ficção');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Não-ficção');


-- Inserindo os 4 tipos fixos de conservação conforme o enum TipoConservacao
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (1, 'EXCELENTE');
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (2, 'BOM');
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (3, 'RAZOAVEL');
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (4, 'DEGRADADO');
