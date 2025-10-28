# 📋 Massas de Teste - AEJ Hub API

Este documento contém JSONs prontos para testar todos os endpoints da API do sistema AEJ Hub.

---

## 🔐 1. AUTENTICAÇÃO

### 1.1. Cadastrar Usuário (POST `/usuarios`)

#### ✅ Cenário: Usuário Administrador
```json
{
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "telefone": "11987654321",
  "tipo_usuario": "admin",
  "cpf": "12345678900",
  "senha": "SenhaForte123!",
  "dtNascimento": "1990-05-15"
}
```

#### ✅ Cenário: Usuário Cliente
```json
{
  "nome": "Maria Oliveira",
  "email": "maria.oliveira@example.com",
  "telefone": "11976543210",
  "tipo_usuario": "cliente",
  "cpf": "98765432100",
  "senha": "Maria@2024Segura",
  "dtNascimento": "1995-08-22"
}
```

#### ✅ Cenário: Usuário Vendedor
```json
{
  "nome": "Carlos Santos",
  "email": "carlos.santos@example.com",
  "telefone": "11965432109",
  "tipo_usuario": "vendedor",
  "cpf": "45678912300",
  "senha": "Carlos#Vende123",
  "dtNascimento": "1988-12-03"
}
```

#### ❌ Cenário: Email Inválido
```json
{
  "nome": "Pedro Costa",
  "email": "email-invalido",
  "telefone": "11954321098",
  "tipo_usuario": "cliente",
  "cpf": "78912345600",
  "senha": "Senha123!",
  "dtNascimento": "1992-03-10"
}
```

#### ❌ Cenário: Senha Curta (menos de 8 caracteres)
```json
{
  "nome": "Ana Paula",
  "email": "ana.paula@example.com",
  "telefone": "11943210987",
  "tipo_usuario": "cliente",
  "cpf": "32165498700",
  "senha": "123",
  "dtNascimento": "1998-07-20"
}
```

### 1.2. Login (POST `/usuarios/login`)

#### ✅ Cenário: Login Bem-sucedido
```json
{
  "email": "joao.silva@example.com",
  "senha": "SenhaForte123!"
}
```

#### ❌ Cenário: Senha Incorreta
```json
{
  "email": "joao.silva@example.com",
  "senha": "SenhaErrada123"
}
```

#### ❌ Cenário: Usuário Não Existe
```json
{
  "email": "naoexiste@example.com",
  "senha": "Senha123!"
}
```

---

## 📚 2. LIVROS

### 2.1. Criar Livro (POST `/livros`)

#### ✅ Cenário: Romance Clássico Brasileiro
```json
{
  "isbn": "978-3-16-148410-0",
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "editora": "Companhia das Letras",
  "anoPublicacao": 2001,
  "paginas": 320,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": 49.90,
  "nomeCategoria": "Romance",
  "descricao": ""
}
```

#### ✅ Cenário: Ficção Científica
```json
{
  "isbn": "978-0-7653-2635-5",
  "titulo": "Neuromancer",
  "autor": "William Gibson",
  "editora": "Ace Books",
  "anoPublicacao": 1984,
  "paginas": 271,
  "acabamentoId": 2,
  "conservacaoId": 2,
  "preco": 59.90,
  "nomeCategoria": "Ficção Científica",
  "descricao": ""
}
```

#### ✅ Cenário: Livro Técnico de Programação
```json
{
  "isbn": "978-0-13-468599-1",
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "editora": "Prentice Hall",
  "anoPublicacao": 2008,
  "paginas": 464,
  "acabamentoId": 1,
  "conservacaoId": 3,
  "preco": 89.90,
  "nomeCategoria": "Programação",
  "descricao": ""
}
```

#### ✅ Cenário: Livro de Fantasia
```json
{
  "isbn": "978-0-06-112008-4",
  "titulo": "O Senhor dos Anéis: A Sociedade do Anel",
  "autor": "J.R.R. Tolkien",
  "editora": "HarperCollins",
  "anoPublicacao": 1954,
  "paginas": 576,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": 79.90,
  "nomeCategoria": "Fantasia",
  "descricao": ""
}
```

#### ✅ Cenário: Livro Usado Barato
```json
{
  "isbn": "978-85-254-0123-4",
  "titulo": "O Cortiço",
  "autor": "Aluísio Azevedo",
  "editora": "Ática",
  "anoPublicacao": 1997,
  "paginas": 232,
  "acabamentoId": 2,
  "conservacaoId": 4,
  "preco": 15.00,
  "nomeCategoria": "Romance",
  "descricao": ""
}
```

#### ✅ Cenário: Livro de Autoajuda
```json
{
  "isbn": "978-1-59184-021-6",
  "titulo": "O Poder do Agora",
  "autor": "Eckhart Tolle",
  "editora": "Sextante",
  "anoPublicacao": 1997,
  "paginas": 224,
  "acabamentoId": 2,
  "conservacaoId": 2,
  "preco": 44.90,
  "nomeCategoria": "Autoajuda",
  "descricao": ""
}
```

#### ✅ Cenário: Livro de História
```json
{
  "isbn": "978-85-359-0277-1",
  "titulo": "Sapiens: Uma Breve História da Humanidade",
  "autor": "Yuval Noah Harari",
  "editora": "L&PM",
  "anoPublicacao": 2011,
  "paginas": 464,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": 64.90,
  "nomeCategoria": "História",
  "descricao": ""
}
```

#### ❌ Cenário: ISBN Duplicado
```json
{
  "isbn": "978-3-16-148410-0",
  "titulo": "Livro Duplicado",
  "autor": "Autor Teste",
  "editora": "Editora Teste",
  "anoPublicacao": 2020,
  "paginas": 100,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": 29.90,
  "nomeCategoria": "Teste",
  "descricao": ""
}
```

#### ❌ Cenário: Ano de Publicação Futuro
```json
{
  "isbn": "978-1-23-456789-0",
  "titulo": "Livro do Futuro",
  "autor": "Autor Futurista",
  "editora": "Editora 2030",
  "anoPublicacao": 2030,
  "paginas": 200,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": 49.90,
  "nomeCategoria": "Ficção",
  "descricao": ""
}
```

#### ❌ Cenário: Páginas Inválidas (negativo)
```json
{
  "isbn": "978-1-11-111111-1",
  "titulo": "Livro Sem Páginas",
  "autor": "Autor Teste",
  "editora": "Editora Teste",
  "anoPublicacao": 2020,
  "paginas": -50,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": 29.90,
  "nomeCategoria": "Teste",
  "descricao": ""
}
```

#### ❌ Cenário: Preço Negativo
```json
{
  "isbn": "978-2-22-222222-2",
  "titulo": "Livro de Graça",
  "autor": "Autor Generoso",
  "editora": "Editora Teste",
  "anoPublicacao": 2020,
  "paginas": 150,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": -10.00,
  "nomeCategoria": "Teste",
  "descricao": ""
}
```

#### ❌ Cenário: AcabamentoId Inválido
```json
{
  "isbn": "978-3-33-333333-3",
  "titulo": "Livro Mal Acabado",
  "autor": "Autor Teste",
  "editora": "Editora Teste",
  "anoPublicacao": 2020,
  "paginas": 200,
  "acabamentoId": 99,
  "conservacaoId": 1,
  "preco": 39.90,
  "nomeCategoria": "Teste",
  "descricao": ""
}
```

#### ❌ Cenário: ConservacaoId Inválido
```json
{
  "isbn": "978-4-44-444444-4",
  "titulo": "Livro Destruído",
  "autor": "Autor Teste",
  "editora": "Editora Teste",
  "anoPublicacao": 2020,
  "paginas": 200,
  "acabamentoId": 1,
  "conservacaoId": 10,
  "preco": 39.90,
  "nomeCategoria": "Teste",
  "descricao": ""
}
```

### 2.2. Atualizar Livro (PUT `/livros/{id}`)

#### ✅ Cenário: Atualizar Apenas Preço
```json
{
  "preco": 39.90
}
```

#### ✅ Cenário: Atualizar Apenas Páginas
```json
{
  "paginas": 350
}
```

#### ✅ Cenário: Atualizar Descrição (Após ChatGPT Gerar)
```json
{
  "descricao": "Dom Casmurro é uma obra-prima da literatura brasileira que narra a história de Bentinho e sua obsessão por Capitu, explorando temas como ciúme, traição e memória."
}
```

#### ✅ Cenário: Atualizar Múltiplos Campos
```json
{
  "preco": 54.90,
  "conservacaoId": 2,
  "descricao": "Edição especial revisada com prefácio inédito."
}
```

### 2.3. Buscar Livros (GET `/livros`)

**Endpoint:** `GET /livros`

**Resposta Esperada:** Lista de todos os livros cadastrados

### 2.4. Buscar Livro por ID (GET `/livros/{id}`)

**Endpoint:** `GET /livros/1`

**Resposta Esperada:** Detalhes do livro com ID 1

### 2.5. Deletar Livro (DELETE `/livros/{id}`)

**Endpoint:** `DELETE /livros/1`

**Resposta Esperada:** Status 204 No Content

---

## 🛒 3. VENDAS / RESERVAS

### 3.1. Criar Venda/Reserva (POST `/vendas`)

#### ✅ Cenário: Reserva Simples para Hoje
```json
{
  "dtReserva": "2025-10-16T14:30:00",
  "dtLimite": "2025-10-18T18:00:00",
  "statusReserva": "Pendente",
  "totalReserva": 49
}
```

#### ✅ Cenário: Reserva Confirmada para Semana que Vem
```json
{
  "dtReserva": "2025-10-20T10:00:00",
  "dtLimite": "2025-10-22T20:00:00",
  "statusReserva": "Confirmada",
  "totalReserva": 89
}
```

#### ✅ Cenário: Reserva com Prazo Longo
```json
{
  "dtReserva": "2025-10-16T09:00:00",
  "dtLimite": "2025-10-30T23:59:59",
  "statusReserva": "Aguardando Pagamento",
  "totalReserva": 150
}
```

#### ✅ Cenário: Reserva Expressa (Mesmo Dia)
```json
{
  "dtReserva": "2025-10-16T15:00:00",
  "dtLimite": "2025-10-16T23:59:59",
  "statusReserva": "Urgente",
  "totalReserva": 79
}
```

#### ❌ Cenário: Data de Reserva no Passado
```json
{
  "dtReserva": "2023-01-01T10:00:00",
  "dtLimite": "2023-01-03T18:00:00",
  "statusReserva": "Pendente",
  "totalReserva": 49
}
```

#### ❌ Cenário: Data Limite Antes da Reserva
```json
{
  "dtReserva": "2025-10-20T14:00:00",
  "dtLimite": "2025-10-18T14:00:00",
  "statusReserva": "Confirmada",
  "totalReserva": 59
}
```

#### ❌ Cenário: Total Reserva Inválido (negativo)
```json
{
  "dtReserva": "2025-10-16T14:30:00",
  "dtLimite": "2025-10-18T18:00:00",
  "statusReserva": "Pendente",
  "totalReserva": -50
}
```

### 3.2. Atualizar Venda/Reserva (PUT `/vendas/{id}`)

#### ✅ Cenário: Atualizar Status para Confirmada
```json
{
  "statusReserva": "Confirmada"
}
```

#### ✅ Cenário: Atualizar Status para Cancelada
```json
{
  "statusReserva": "Cancelada"
}
```

#### ✅ Cenário: Estender Data Limite
```json
{
  "dtLimite": "2025-10-25T23:59:59"
}
```

#### ✅ Cenário: Atualizar Total da Reserva
```json
{
  "totalReserva": 120
}
```

### 3.3. Buscar Vendas (GET `/vendas`)

**Endpoint:** `GET /vendas`

**Resposta Esperada:** Lista de todas as vendas/reservas

### 3.4. Buscar Venda por ID (GET `/vendas/{id}`)

**Endpoint:** `GET /vendas/1`

**Resposta Esperada:** Detalhes da venda com ID 1

### 3.5. Deletar Venda (DELETE `/vendas/{id}`)

**Endpoint:** `DELETE /vendas/1`

**Resposta Esperada:** Status 204 No Content

---

## 🔢 4. VALORES DE REFERÊNCIA

### 4.1. Acabamento (IDs válidos)
- **1** - Capa Dura
- **2** - Brochura

### 4.2. Conservação (IDs válidos)
- **1** - Novo
- **2** - Seminovo
- **3** - Usado
- **4** - Desgastado

### 4.3. Categorias (Exemplos)
- Romance
- Ficção Científica
- Fantasia
- Programação
- História
- Autoajuda
- Biografia
- Suspense
- Terror
- Aventura

### 4.4. Tipos de Usuário
- **admin** - Administrador do sistema
- **cliente** - Cliente/Comprador
- **vendedor** - Vendedor de livros

### 4.5. Status de Reserva (Exemplos)
- Pendente
- Confirmada
- Aguardando Pagamento
- Cancelada
- Finalizada
- Urgente

---

## 📝 5. OBSERVAÇÕES IMPORTANTES

### 5.1. Autenticação
- A maioria dos endpoints requer autenticação via **Bearer Token**
- Faça login primeiro e pegue o token JWT
- Adicione o token no header: `Authorization: Bearer {seu-token}`

### 5.2. Validações de Livro
- **ISBN**: Deve ser único no sistema
- **Ano de Publicação**: Entre 1450 e o ano atual (2025)
- **Páginas**: Entre 1 e 10.000
- **Preço**: Valor positivo até 999.999,99
- **AcabamentoId**: Apenas 1 ou 2
- **ConservacaoId**: Apenas 1, 2, 3 ou 4
- **Descrição**: Campo opcional (será preenchido pelo ChatGPT após criação)

### 5.3. Validações de Usuário
- **Email**: Deve ser único e em formato válido
- **CPF**: Deve ser único
- **Senha**: Mínimo 8 caracteres
- **Telefone**: Máximo 14 caracteres (com DDD)

### 5.4. Validações de Venda/Reserva
- **Data de Reserva**: Deve ser presente ou futura
- **Data Limite**: Deve ser após a data de reserva
- **Total Reserva**: Valor inteiro positivo

### 5.5. Fluxo de Sinopse Assíncrona
1. Criar livro com `descricao: ""`
2. Sistema envia mensagem para fila RabbitMQ
3. Consumer processa e gera sinopse via ChatGPT
4. Consumer atualiza o livro com a sinopse gerada
5. Buscar livro novamente para ver a descrição preenchida

---

## 🚀 6. EXEMPLOS DE FLUXOS COMPLETOS

### 6.1. Fluxo Completo: Criar Usuário e Fazer Reserva

**Passo 1:** Cadastrar usuário
```json
POST /usuarios
{
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "telefone": "11987654321",
  "tipo_usuario": "cliente",
  "cpf": "12345678900",
  "senha": "SenhaForte123!",
  "dtNascimento": "1990-05-15"
}
```

**Passo 2:** Fazer login
```json
POST /usuarios/login
{
  "email": "joao.silva@example.com",
  "senha": "SenhaForte123!"
}
```

**Passo 3:** Criar livro (com token de admin)
```json
POST /livros
Authorization: Bearer {token}
{
  "isbn": "978-3-16-148410-0",
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "editora": "Companhia das Letras",
  "anoPublicacao": 2001,
  "paginas": 320,
  "acabamentoId": 1,
  "conservacaoId": 1,
  "preco": 49.90,
  "nomeCategoria": "Romance",
  "descricao": ""
}
```

**Passo 4:** Criar reserva
```json
POST /vendas
Authorization: Bearer {token}
{
  "dtReserva": "2025-10-16T14:30:00",
  "dtLimite": "2025-10-18T18:00:00",
  "statusReserva": "Confirmada",
  "totalReserva": 49
}
```

### 6.2. Fluxo Completo: Atualizar Livro Após Sinopse

**Passo 1:** Criar livro (sinopse vazia)
```json
POST /livros
{
  "isbn": "978-0-13-468599-1",
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "editora": "Prentice Hall",
  "anoPublicacao": 2008,
  "paginas": 464,
  "acabamentoId": 1,
  "conservacaoId": 3,
  "preco": 89.90,
  "nomeCategoria": "Programação",
  "descricao": ""
}
```

**Passo 2:** Aguardar processamento (ChatGPT)

**Passo 3:** Buscar livro atualizado
```
GET /livros/1
```

**Resposta esperada:**
```json
{
  "id": 1,
  "isbn": "978-0-13-468599-1",
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "descricao": "Clean Code é um guia essencial para desenvolvedores...",
  ...
}
```

---

## 🎯 7. DICAS PARA TESTES

1. **Sempre comece criando usuários** antes de testar outros endpoints
2. **Pegue o token JWT** após o login e use em todos os requests autenticados
3. **Teste cenários de erro** para garantir que as validações estão funcionando
4. **Use IDs sequenciais** (1, 2, 3...) para facilitar os testes
5. **Limpe o banco** entre testes para evitar conflitos de ISBN/Email/CPF duplicados
6. **Aguarde alguns segundos** após criar um livro para a sinopse ser gerada
7. **Verifique os logs** do RabbitMQ para acompanhar o processamento assíncrono

---

**Última atualização:** 16 de outubro de 2025  
**Versão da API:** 0.0.1-SNAPSHOT

