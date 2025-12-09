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

-- Inserindo usuário admin padrão (senha: Admin@123) isso aqui serve só para apresentação, em produção inseriamos os usuarios adm na mão
INSERT INTO usuario_entity (nome, email, telefone, tipo_usuario, cpf, senha, dt_nascimento)
VALUES ('Admin Sistema', 'admin@aejhub.com', '11987654321', 'admin', '12345678900', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1990-01-01');

INSERT INTO livro (titulo, isbn, autor, editora, ano_publicacao, paginas, acabamento_id, conservacao_id, capa, preco, categoria_id, descricao, data_adicao)
VALUES
('Amanhã Estelar', 'ISBN-000001', 'L. Martins', 'Solaris Editora', '2018', 320, 1, 1, NULL, 29.90, 1, 'Ficção científica espacial.', NOW()),
('O Último Horizonte', 'ISBN-000002', 'C. Farias', 'Orbital Press', '2015', 280, 2, 2, NULL, 39.90, 1, 'Exploração interplanetária.', NOW()),
('Amor Entre Dois Mundos', 'ISBN-000003', 'J. Ribeiro', 'Lótus', '2019', 210, 2, 1, NULL, 24.50, 2, 'Romance entre realidades paralelas.', NOW()),
('Coração de Vidro', 'ISBN-000004', 'B. Santos', 'Céu Azul', '2017', 190, 1, 3, NULL, 19.90, 2, 'Drama romântico contemporâneo.', NOW()),
('Sabores da Terra', 'ISBN-000005', 'Chef Aurora', 'GastroBooks', '2020', 150, 2, 1, NULL, 49.90, 3, 'Receitas tradicionais brasileiras.', NOW()),
('Cozinha Cósmica', 'ISBN-000006', 'Chef Luna', 'CulinaTec', '2021', 180, 1, 2, NULL, 59.90, 3, 'Receitas futuristas.', NOW()),
('O Reino Despedaçado', 'ISBN-000007', 'T. Magalhães', 'Nova Era', '2016', 340, 1, 2, NULL, 33.90, 4, 'Distopia pós-guerra.', NOW()),
('Cidade de Ferro', 'ISBN-000008', 'D. Lemos', 'Aço Negro', '2014', 290, 2, 3, NULL, 27.80, 4, 'Sociedade autoritária e vigilância.', NOW()),
('O Eco na Parede', 'ISBN-000009', 'M. Siqueira', 'Sombras', '2019', 260, 1, 1, NULL, 22.90, 5, 'Suspense psicológico.', NOW()),
('Mistério no Lago', 'ISBN-000010', 'G. Prado', 'Riacho Editorial', '2018', 230, 2, 2, NULL, 18.90, 5, 'Investigação em cidade pequena.', NOW()),
('Sombras Noturnas', 'ISBN-000011', 'A. Veloso', 'Escuridão', '2013', 310, 1, 4, NULL, 34.90, 6, 'Terror sobrenatural.', NOW()),
('A Casa Sem Vozes', 'ISBN-000012', 'P. Menezes', 'Lua Nova', '2020', 270, 2, 3, NULL, 29.50, 6, 'Assombração urbana.', NOW()),
('Pensamentos que Mudam', 'ISBN-000013', 'R. Carvalho', 'Filosofar', '2012', 200, 1, 1, NULL, 21.90, 7, 'Introdução à filosofia moderna.', NOW()),
('Caminhos da Razão', 'ISBN-000014', 'S. Duarte', 'Sabedoria Press', '2019', 250, 2, 2, NULL, 26.40, 7, 'Reflexões contemporâneas.', NOW()),
('Contos do Cotidiano', 'ISBN-000015', 'H. Bragança', 'Vila das Letras', '2017', 150, 1, 1, NULL, 14.90, 8, 'Narrativas urbanas variadas.', NOW()),
('Noites em Lisboa', 'ISBN-000016', 'E. Montenegro', 'Maré Alta', '2021', 280, 2, 2, NULL, 32.90, 2, 'Romance europeu.', NOW()),
('Além da Nebulosa', 'ISBN-000017', 'V. Azevedo', 'Cosmos', '2015', 330, 1, 1, NULL, 41.90, 1, 'Exploração científica.', NOW()),
('A Fenda', 'ISBN-000018', 'K. Duarte', 'Sombras', '2018', 260, 2, 3, NULL, 23.90, 5, 'Suspense com viagem no tempo.', NOW()),
('O Corpo Seco', 'ISBN-000019', 'J. Silveira', 'TerrorBras', '2020', 300, 1, 2, NULL, 27.90, 6, 'Lenda folclórica recriada.', NOW()),
('Sabores do Oriente', 'ISBN-000020', 'Chef Nakamura', 'GastroBooks', '2016', 180, 2, 1, NULL, 54.50, 3, 'Culinária asiática.', NOW()),
('O Véu de Aço', 'ISBN-000021', 'L. Correia', 'Império', '2013', 310, 1, 2, NULL, 28.90, 4, 'Regime totalitário distópico.', NOW()),
('Doce Alquimia', 'ISBN-000022', 'Chef Helena', 'CulinaTec', '2021', 160, 2, 1, NULL, 44.90, 3, 'Sobremesas criativas.', NOW()),
('Caminho das Sombras', 'ISBN-000023', 'F. Torres', 'Sombras', '2016', 295, 1, 2, NULL, 25.90, 5, 'Investigação policial.', NOW()),
('Página em Branco', 'ISBN-000024', 'O. Mota', 'Vila das Letras', '2022', 190, 2, 1, NULL, 16.90, 8, 'Histórias curtas.', NOW()),
('O Circulo Partido', 'ISBN-000025', 'M. Ribeiro', 'Cosmos', '2010', 280, 1, 3, NULL, 35.20, 1, 'Aventura espacial.', NOW()),
('Vozes do Silêncio', 'ISBN-000026', 'T. Rodrigues', 'Lua Nova', '2019', 240, 2, 3, NULL, 22.30, 6, 'Terror psicológico.', NOW()),
('O Jardim Vermelho', 'ISBN-000027', 'E. Monteiro', 'Floratta', '2017', 210, 1, 1, NULL, 19.70, 2, 'Romance dramático.', NOW()),
('Códigos Esquecidos', 'ISBN-000028', 'C. Amaral', 'Sabedoria Press', '2014', 260, 2, 2, NULL, 28.10, 7, 'Filosofia antiga.', NOW()),
('O Domo Cinza', 'ISBN-000029', 'H. Souza', 'Nova Era', '2018', 330, 1, 1, NULL, 33.30, 4, 'Sociedade distópica isolada.', NOW()),
('Entre Fumaça e Espelhos', 'ISBN-000030', 'S. Pires', 'Sombras', '2016', 280, 2, 3, NULL, 26.90, 5, 'Suspense urbano.', NOW()),
('Gritos na Escuridão', 'ISBN-000031', 'D. Moura', 'Escuridão', '2015', 305, 1, 4, NULL, 29.90, 6, 'Terror extremo.', NOW()),
('O Sabor da Montanha', 'ISBN-000032', 'Chef Marino', 'GastroBooks', '2020', 175, 2, 1, NULL, 47.80, 3, 'Culinária regional.', NOW()),
('O Guardião da Aurora', 'ISBN-000033', 'L. Brito', 'Cosmos', '2011', 290, 1, 1, NULL, 31.90, 1, 'Exploração interestelar.', NOW()),
('A Cidade que Não Dorme', 'ISBN-000034', 'J. Prado', 'Maré Alta', '2013', 220, 2, 2, NULL, 18.40, 8, 'Crônicas urbanas.', NOW()),
('Sementes do Caos', 'ISBN-000035', 'N. Castilho', 'Império', '2019', 310, 1, 3, NULL, 34.70, 4, 'Distopia com rebelião.', NOW()),
('Receitas do Mar', 'ISBN-000036', 'Chef Duarte', 'CulinaTec', '2018', 160, 2, 1, NULL, 52.10, 3, 'Peixes e frutos do mar.', NOW()),
('Perfume de Outono', 'ISBN-000037', 'M. Vargas', 'Floratta', '2020', 210, 1, 1, NULL, 21.90, 2, 'Romance leve.', NOW()),
('A Lâmina e o Véu', 'ISBN-000038', 'L. Sanches', 'Sombras', '2016', 300, 2, 2, NULL, 27.90, 5, 'Suspense com investigação.', NOW()),
('Pânico na Lua', 'ISBN-000039', 'C. Freire', 'Escuridão', '2021', 280, 1, 4, NULL, 33.90, 6, 'Terror espacial.', NOW()),
('Horizonte Partilhado', 'ISBN-000040', 'R. Avelar', 'Vila das Letras', '2022', 195, 2, 1, NULL, 17.90, 8, 'Histórias contemporâneas.', NOW());

-- ============================================
-- MOCK DE USUÁRIOS E VENDAS PARA APRESENTAÇÃO
-- ============================================
-- Senha padrão para todos os clientes: Cliente@123
-- Hash BCrypt: $2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi

INSERT INTO usuario_entity (nome, email, telefone, tipo_usuario, cpf, senha, dt_nascimento)
VALUES
('Maria Silva', 'maria.silva@email.com', '11987654321', 'cliente', '12345678901', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1995-03-15'),
('João Santos', 'joao.santos@email.com', '11976543210', 'cliente', '23456789012', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1992-07-20'),
('Ana Costa', 'ana.costa@email.com', '11965432109', 'cliente', '34567890123', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1998-11-05'),
('Pedro Oliveira', 'pedro.oliveira@email.com', '11954321098', 'cliente', '45678901234', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1990-02-28'),
('Carla Mendes', 'carla.mendes@email.com', '11943210987', 'cliente', '56789012345', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1994-09-12'),
('Lucas Ferreira', 'lucas.ferreira@email.com', '11932109876', 'cliente', '67890123456', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1996-06-18'),
('Juliana Rocha', 'juliana.rocha@email.com', '11921098765', 'cliente', '78901234567', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1993-12-03'),
('Rafael Lima', 'rafael.lima@email.com', '11910987654', 'cliente', '89012345678', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1997-04-25'),
('Fernanda Alves', 'fernanda.alves@email.com', '11909876543', 'cliente', '90123456789', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1991-08-14'),
('Bruno Cardoso', 'bruno.cardoso@email.com', '11898765432', 'cliente', '01234567890', '$2a$12$Ebaxm7Qqi.6xDI/UDVt7t.62PljaX2QkHN.3OEa1HMfhhQBxbAKsi', '1999-01-30');

-- Vendas/Reservas
-- Frontend Status Calculation Logic (calculateStatusByDeadline):
--   CONFIRMADA: dtLimite >= TODAY (✅ Green - active, within deadline)
--   PENDENTE: 1-2 days after dtLimite (⚠️ Yellow - overdue but recoverable)
--   CANCELADA: 2+ days after dtLimite (❌ Red - too late, hidden from UI)
--   CONCLUIDA: Manual status when user completes reservation (✅ Blue - finished)
--
-- Note: statusReserva in DB always starts as 'CONFIRMADA' or 'CONCLUIDA'
--       Frontend recalculates actual status based on dtLimite value
--
-- IDs dos usuários começam em 2 (id 1 é admin)
-- IDs dos livros vão de 1 a 40
INSERT INTO venda (dt_reserva, dt_limite, status_reserva, total_reserva, usuario_id, livro_id)
VALUES
-- ===== CONFIRMADA Status (Within Deadline - 5 reservations) =====
-- These show as GREEN in dashboard (dtLimite >= today)
(NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 1 DAY, 'CONFIRMADA', 29.90, 2, 1),      -- Maria reservou "Amanhã Estelar" (dt_limite tomorrow)
(NOW() - INTERVAL 2 HOUR, NOW() + INTERVAL 6 DAY, 'CONFIRMADA', 49.90, 3, 5),    -- João reservou "Sabores da Terra" (6 days remaining)
(NOW() - INTERVAL 5 HOUR, NOW() + INTERVAL 4 DAY, 'CONFIRMADA', 29.50, 4, 12),   -- Ana reservou "A Casa Sem Vozes" (4 days remaining)
(NOW() - INTERVAL 1 HOUR, NOW() + INTERVAL 5 DAY, 'CONFIRMADA', 26.90, 5, 30),   -- Pedro reservou "Entre Fumaça e Espelhos" (5 days remaining)
(NOW() - INTERVAL 3 HOUR, NOW() + INTERVAL 2 DAY, 'CONFIRMADA', 19.70, 6, 27),   -- Carla reservou "O Jardim Vermelho" (2 days remaining)

-- ===== PENDENTE Status (1-2 Days Overdue - 2 reservations) =====
-- These show as YELLOW in Inconsistências tab (1-2 days after dtLimite)
(NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 0 DAY, 'CONFIRMADA', 14.90, 7, 15),    -- Lucas reservou "Contos do Cotidiano" (expires TODAY - 0 days overdue)
(NOW() - INTERVAL 3 DAY, NOW() - INTERVAL 1 DAY, 'CONFIRMADA', 54.50, 8, 20),    -- Juliana reservou "Sabores do Oriente" (1 day overdue)

-- ===== CANCELADA Status (2+ Days Overdue - 1 reservation) =====
-- These are HIDDEN from UI (2+ days after dtLimite)
(NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 2 DAY, 'CONFIRMADA', 22.90, 9, 9),     -- Fernanda reservou "O Eco na Parede" (2 days overdue - should be hidden)

-- ===== CONCLUIDA Status (Manually Completed - 1 reservation) =====
-- These show as BLUE (disabled, already finished)
(NOW() - INTERVAL 10 DAY, NOW() - INTERVAL 5 DAY, 'CONCLUIDA', 27.80, 10, 8);    -- Rafael completou "Cidade de Ferro" (finished 5 days ago)

-- ============================================
-- FIM DO MOCK PARA APRESENTAÇÃO
-- ============================================
