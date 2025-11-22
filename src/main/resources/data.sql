-- Inserindo os 2 tipos fixos de acabamento conforme o enum TipoAcabamento
INSERT IGNORE INTO acabamento (id, tipo_acabamento) VALUES (1, 'CAPA_DURA');
INSERT IGNORE INTO acabamento (id, tipo_acabamento) VALUES (2, 'BROCHURA');


INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Ficção Cientifica');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Romance');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Culinária');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Distopia');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Mistérios/Suspense');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Terror');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Filosofia');
INSERT IGNORE INTO categoria (nome_categoria) VALUES ('Outros');

-- Inserindo os 4 tipos fixos de conservação conforme o enum TipoConservacao
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (1, 'EXCELENTE');
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (2, 'BOM');
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (3, 'RAZOAVEL');
INSERT IGNORE INTO conservacao (id, tipo_conservacao) VALUES (4, 'DEGRADADO');

INSERT INTO livro (titulo, isbn, autor, editora, ano_publicacao, paginas, acabamento_id, conservacao_id, preco, categoria_id, descricao, data_adicao)
VALUES
('1984', '9780451524935', 'George Orwell', 'Companhia das Letras', 1949, 328, 1, 1, 45.90, 4, 'Um romance distópico sobre um regime totalitário que controla todos os aspectos da vida.', '2024-01-15 10:30:00'),
('Fundação', '9788576570646', 'Isaac Asimov', 'Aleph', 1951, 280, 1, 1, 52.00, 1, 'Primeiro livro da série Fundação, uma obra-prima da ficção científica.', '2024-01-16 11:20:00'),
('O Senhor dos Anéis: A Sociedade do Anel', '9788533613379', 'J.R.R. Tolkien', 'Martins Fontes', 1954, 576, 1, 2, 68.50, 1, 'A primeira parte da épica trilogia de fantasia.', '2024-01-18 14:45:00'),
('Dom Casmurro', '9788594318602', 'Machado de Assis', 'Penguin', 1899, 256, 2, 2, 32.90, 2, 'Clássico da literatura brasileira sobre ciúme e traição.', '2024-01-20 09:15:00'),
('O Hobbit', '9788595084742', 'J.R.R. Tolkien', 'HarperCollins', 1937, 336, 1, 1, 54.90, 1, 'A aventura de Bilbo Bolseiro em busca do tesouro guardado pelo dragão Smaug.', '2024-01-22 16:00:00'),
('Cem Anos de Solidão', '9788501012265', 'Gabriel García Márquez', 'Record', 1967, 424, 2, 2, 58.00, 2, 'A saga da família Buendía na cidade fictícia de Macondo.', '2024-01-25 10:30:00'),
('O Iluminado', '9788581050584', 'Stephen King', 'Suma', 1977, 528, 2, 3, 49.90, 6, 'Terror psicológico no isolado Hotel Overlook.', '2024-01-28 13:20:00'),
('Neuromancer', '9788576570356', 'William Gibson', 'Aleph', 1984, 308, 2, 2, 46.00, 1, 'O livro que definiu o gênero cyberpunk.', '2024-02-01 11:40:00'),
('O Guia do Mochileiro das Galáxias', '9788580576481', 'Douglas Adams', 'Arqueiro', 1979, 224, 2, 1, 38.90, 1, 'Comédia de ficção científica sobre a destruição da Terra.', '2024-02-03 15:30:00'),
('Orgulho e Preconceito', '9788544001509', 'Jane Austen', 'Penguin', 1813, 424, 1, 2, 42.00, 2, 'Romance clássico sobre Elizabeth Bennet e Mr. Darcy.', '2024-02-05 09:50:00'),
('Fahrenheit 451', '9788525052544', 'Ray Bradbury', 'Globo', 1953, 216, 2, 2, 39.90, 4, 'Distopia sobre uma sociedade que queima livros.', '2024-02-08 14:15:00'),
('A Revolução dos Bichos', '9788535909555', 'George Orwell', 'Companhia das Letras', 1945, 152, 2, 1, 35.90, 4, 'Fábula política sobre uma fazenda onde os animais se rebelam.', '2024-02-10 10:20:00'),
('O Nome da Rosa', '9788501014535', 'Umberto Eco', 'Record', 1980, 576, 1, 3, 64.90, 5, 'Mistério medieval em um mosteiro italiano.', '2024-02-12 16:45:00'),
('Admirável Mundo Novo', '9788525431134', 'Aldous Huxley', 'Globo', 1932, 312, 2, 2, 44.90, 4, 'Distopia sobre uma sociedade futurista controlada pela tecnologia.', '2024-02-15 11:30:00'),
('Assim Falou Zaratustra', '9788535911893', 'Friedrich Nietzsche', 'Companhia das Letras', 1883, 360, 1, 2, 55.00, 7, 'Obra filosófica sobre o super-homem e a morte de Deus.', '2024-02-18 13:00:00'),
('O Festim dos Corvos', '9788544102213', 'George R.R. Martin', 'Leya', 2005, 752, 1, 1, 79.90, 1, 'Quarto livro da série As Crônicas de Gelo e Fogo.', '2024-02-20 10:10:00'),
('Duna', '9788576573142', 'Frank Herbert', 'Aleph', 1965, 680, 1, 2, 72.00, 1, 'Épico de ficção científica no planeta desértico Arrakis.', '2024-02-22 15:20:00'),
('It: A Coisa', '9788581050591', 'Stephen King', 'Suma', 1986, 1104, 1, 3, 89.90, 6, 'Terror sobre uma entidade maligna que assombra a cidade de Derry.', '2024-02-25 09:40:00'),
('Crime e Castigo', '9788535930856', 'Fiódor Dostoiévski', 'Companhia das Letras', 1866, 568, 1, 2, 62.00, 7, 'Romance psicológico sobre culpa e redenção.', '2024-02-28 14:30:00'),
('A Metamorfose', '9788535908671', 'Franz Kafka', 'Companhia das Letras', 1915, 96, 2, 1, 28.90, 7, 'Gregor Samsa acorda transformado em um inseto gigante.', '2024-03-01 11:15:00');

