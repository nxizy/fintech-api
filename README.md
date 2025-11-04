# Fintech - Contrato API

## Visão Geral

API da Fintech com módulos de autenticação, contas, manipulação de saldos, ativos, investimentos e cursos educativos.  
> **Status:** Em desenvolvimento (v1.0.0)
---

## Pré-requisitos

Antes de iniciar o projeto, verifique se você possui os seguintes requisitos instalados:

- **Java 21** ou superior  
  (O projeto utiliza o Spring Boot 3.5.6, que requer Java 17+)
- **Maven 3.8+**
- **Oracle Database 19c ou superior**
    - Driver: `ojdbc11` (já incluso nas dependências)
- **IDE recomendada:** IntelliJ IDEA (com suporte ao Maven)
- **Porta padrão:** `8080`

---

### Dependências principais
O projeto utiliza:
- `spring-boot-starter-web` → API REST
- `spring-boot-starter-data-jpa` → Persistência com JPA/Hibernate
- `spring-boot-starter-security` + `java-jwt` → Autenticação JWT
- `springdoc-openapi-starter-webmvc-ui` → Documentação Swagger UI
- `lombok` → Redução de boilerplate
---


## Como executar o projeto

### 1 -  Clonar o repositório
```bash
  git clone https://github.com/nxizy/fintech-api.git
  cd fintech-api
```

### 2 - Configurar o banco de dados

Este projeto utiliza a instância do Oracle da FIAP, então atualize o arquivo `application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

> O `ddl-auto=update` faz o Hibernate criar e atualizar as tabelas automaticamente.

---

### 3 - Instalar dependências e compilar

```bash
  mvn clean install
```

---

### 4 - Executar o projeto

```bash
  mvn spring-boot:run
```

A aplicação iniciará na porta padrão **8080**:

```
http://localhost:8080
```

---

### 5 - Acessar a documentação Swagger

Após iniciar o servidor, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

Lá estarão listados todos os endpoints disponíveis da API, com exemplos e parâmetros.

---


**Todos os endpoints (exceto /auth/register e /auth/login) requerem o header Authorization: Bearer {token}**

---

# Índice

- [Autenticação](#1-autenticação)
- [Contas](#2-contas)
- [Wallet](#3-wallet)
- [Assets](#4-assets)
- [Investments](#5-investments)
- [Courses](#6-courses)


## **1. Autenticação**

### 1.1 Login do usuário

### *POST /auth/login*

**Descrição:** Autentica o usuário com email e senha, retornando o token JWT e suas contas associadas.

**Request Body**

```json
{
  "email": "email2luiz@gmail.com",
  "password": "password"
}
```

**Response 200**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "name": "Luiz Carvalho",
    "document": "51866223038",
    "phoneNumber": "11999999999",
    "birthDate": "2007-02-21",
    "email": "email2luiz@gmail.com",
    "investorLevel": "AVANCADO"
  },
  "accounts": [
    {
      "account_id": "6e54e570-2d4e-11ef-bf4b-123456789abc",
      "account_name": "Pessoal"
    }
  ]
}
```

#### Erros possíveis

| Código | Motivo                    |
| ------ | ------------------------- |
| 401    | Email ou senha incorretos |
| 500    | Erro interno do servidor  |

---

### 1.2 Registrar usuário

### *POST /auth/register*

**Descrição:** Registra um novo usuário e cria uma conta padrão associada.

**Request Body**

```json
{
  "name": "Luiz Carvalho",
  "document": "51866223038",
  "phoneNumber": "11999999999",
  "birthDate": "2007-02-21",
  "email": "email2luiz@gmail.com",
  "password": "password",
  "investorLevel": "AVANCADO"
}
```

**Response 201**

#### Erros possíveis

| Código | Motivo                   |
| ------ | ------------------------ |
| 400    | Dados inválidos          |
| 409    | Email ou Documento ou número de telefone já cadastrado|
| 500    | Erro interno do servidor |

---

### 1.3 Alterar usuário

### *PUT /user*

**Descrição:** Atualiza os dados do usuário.

**Atenção:** Requer token JWT.

**Request Body**

```json
{
  "name": "Luiz Carvalho",
	"document": "03758045010",
	"phoneNumber": "19999999999",
	"birthDate": "2007-02-21",
	"email": "email3luiz@gmail.com",
	"password" : "password",
	"investorLevel": "AVANCADO"
}
```

**Response 200**

```json
{
  "name": "Luiz Carvalho",
	"document": "03758045010",
	"phoneNumber": "19999999999",
	"birthDate": "2007-02-21",
	"email": "email3luiz@gmail.com",
	"password" : "password",
	"investorLevel": "AVANCADO"
}
```

#### Erros possíveis

| Código | Motivo                   |
| ------ | ------------------------ |
| 400    | Dados inválidos          |
| 401    | Acesso negado            |
| 404    | Usuário não encontrado   |
| 409    | E-mail, Documento ou Número de Telefone já registrados |
| 500    | Erro interno do servidor |

---

### 1.4 Apagar usuário

### *DELETE /user*

**Descrição:** Apaga usuário do banco de dados

**Atenção:** Requer token JWT.

**Request Body:** O usuário será identificado pelo JWT dado junto a requisição.

**Response 204**

#### Erros possíveis

| Código | Motivo                   |
| ------ | ------------------------ |
| 404    | Usuário não encontrado   |
| 500    | Erro interno do servidor |

---

## **2. Contas**

### 2.1 Listar contas do usuário logado

### *GET /accounts*

**Descrição:** Retorna todas as contas vinculadas ao usuário autenticado.

**Atenção:** Requer token JWT.

**Response 200**

```json
{
  "content": [
    {
      "account_id": "6e54e570-2d4e-11ef-bf4b-123456789abc",
      "account_name": "Pessoal"
    },
    {
      "account_id": "7a7a6780-2d4e-11ef-bf4b-123456789abc",
      "account_name": "Investimentos"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "unpaged": false,
    "paged": true
  },
  "last": true,
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "empty": true
}
```

#### Erros possíveis

| Código | Motivo                     |
| ------ | -------------------------- |
| 401    | Token inválido ou expirado |
| 500    | Erro interno do servidor   |

---

### 2.2 Atualizar dados da conta.

### *PUT /accounts/{id}*

**Descrição:** Atualiza dados da conta (nome).

**Atenção:** Requer token JWT.

**Request Body**

```json
{
  "name" : "Empresa"
}
```

**Response 200**

```json
{
  "id": "7a7a6780-2d4e-11ef-bf4b-123456789abc",
  "name": "Empresa",
  "balance": 0.0,
  "createdAt": "2025-10-28T09:57:10.15959"
}
```

#### Erros possíveis

| Código | Motivo                           |
| ------ | -------------------------------- |
| 400    | Dados inválidos                  |
| 401    | Token inválido |
| 403    | Usuário sem permissão de alteração desta conta |
| 404    | Usuário ou conta não encontrados |
| 500    | Erro interno do servidor         |

---

### 2.3 Criar nova conta do usuário logado

### *POST /accounts*

**Descrição:** Cria uma nova conta vinculada ao usuário autenticado.
**Atenção:** Requer token JWT.

**Request Body**

```json
{
  "name": "Poupança Viagem"
}
```

**Response 201**

```json
{
  "id": "fa7004e2-3c45-4bd3-b201-a7f9b58de8bf",
  "name": "Poupança Viagem",
  "balance": 0.0,
  "createdAt": "2025-10-28T09:57:10.15959"
}
```

#### Erros possíveis

| Código | Motivo                                              |
| ------ | --------------------------------------------------- |
| 400    | Dados inválidos (ex.: nome vazio) |
| 401    | Token inválido ou expirado                          |
| 500    | Erro interno do servidor                            |

---

### 2.4 Excluir uma conta do usuário logado

### *DELETE /accounts/{id}*

**Descrição:** Exclui a conta especificada do usuário autenticado.
**Atenção:** Requer token JWT.


**Response 204**
*(sem corpo)*

#### Erros possíveis

| Código | Motivo                                                          |
| ------ | --------------------------------------------------------------- |
| 401    | Token inválido ou expirado                                      |
| 403    | Usuário não tem permissão para excluir esta conta               |
| 404    | Conta não encontrada                                            |
| 500    | Erro interno do servidor                                        |

---

## **3. Wallet**

### 3.1 Consultar saldo e movimentações

### *GET /accounts/{id}/wallet*

**Descrição:** Retorna o saldo e o histórico de movimentações (saques e depósitos) da conta.

**Atenção:** Requer token JWT.

**Response 200**

```json
{
  "balance": 1250.0,
  "operations": [
    {
      "operation_id": "2a667ba6-fe59-496b-bd8d-43d39e06d42e",
      "type": "deposit",
      "amount": 550.0,
      "created_at": "2025-11-04T16:25:48.838559"
    },
    {
      "operation_id": "ee10a89f-5cbc-4e10-b4a3-d0790c0f46af",
      "type": "deposit",
      "amount": 550.0,
      "created_at": "2025-11-02T13:51:14.642911"
    },
    {
      "operation_id": "9cf8ff15-4ca3-48ea-ab0e-dedb5d92cfed",
      "type": "withdraw",
      "amount": 250.0,
      "created_at": "2025-11-02T13:38:40.048176"
    },
    {
      "operation_id": "633a4c76-07f2-4a92-bf93-b50290b0df84",
      "type": "deposit",
      "amount": 400.0,
      "created_at": "2025-11-02T13:37:07.928512"
    }
  ]
}
```

#### Erros possíveis

| Código | Motivo                   |
| ------ | ------------------------ |
| 401    | Token inválido           |
| 403    | Usuário não pode visualizar carteira da conta solicitada |
| 404    | Conta não encontrada     |
| 500    | Erro interno do servidor |

---

### 3.2 Cadastrar movimentação

### *POST /accounts/{id}/wallet/transactions*

**Descrição:** Cadastra uma movimentação de depósito ou saque na conta.

**Atenção:** Requer token JWT.

**Request Body**

```json
{
  "type" : "DEPOSIT",
  "amount" : 550.00
}
```

**Response 201**

```json
{
  "operation_id": "2a667ba6-fe59-496b-bd8d-43d39e06d42e",
  "type": "deposit",
  "amount": 550.0,
  "created_at": "2025-11-04T16:25:48.824488",
  "balance_after": 1250.0
}
```

#### Erros possíveis

| Código | Motivo                        |
| ------ | ----------------------------- |
| 400    | Corpo inválido |
| 401    | Token inválido                |
| 404    | Conta não encontrada          |
| 422    | Saldo insuficiente            |
| 500    | Erro interno do servidor      |

---

## **4. Assets**

### 4.1 Retorna os ativos disponíveis.

### *GET /assets*

**Descrição:** Retorna os ativos disponíveis no site.

**OBS:** Tem suporte a filtros por parâmetros como tipo e paginação, a URL completa poderá ser assim:
```
http://localhost:8080/assets?page=0&size=10&sort=name,asc&asset_type=crypto
```
**Response 200**

```json
{
  "content": [
    {
      "id": "af5f8ee9-7f1a-476b-9b5d-1b0962af309d",
      "name": "Bitcoin",
      "symbol": "BTC",
      "asset_type": "crypto",
      "current_price": 618540.95,
      "market_cap": 2.305300302602E12,
      "market_sector": "payments"
    },
    {
      "id": "9073c358-d07b-4c42-8d2f-ddc690272c55",
      "name": "Ether",
      "symbol": "ETH",
      "asset_type": "crypto",
      "current_price": 22582.84,
      "market_cap": 5.0704833103067E11,
      "market_sector": "platforms"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 2,
  "totalPages": 1,
  "first": true,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "empty": false
}
```

#### Erros possíveis

| Código | Motivo                           |
| ------ | -------------------------------- |
| 500    | Erro interno do servidor  |

---



## **5. Investments**

### 5.1 Retorna os investimentos da conta.

### *GET /accounts/{id}/investments*

**Descrição:** Retorna os investimentos feitos na conta. O "amount" e o "purchase_price" são dados sobre a quantidade e o valor do investimento feito, e o "current_price" é o valor atual de uma unidade do ativo adquirido.

**Atenção:** Requer token JWT.

**Response 200**

```json
{
  "content": [
    {
      "amount": 0.002,
      "investment_id": "b1ef08cd-c219-43e4-99a1-937206eba4c1",
      "asset_name": "Bitcoin",
      "asset_type": "crypto",
      "purchase_price": 200.0,
      "created_at": "2025-11-03T16:38:39.071934"
    },
    {
      "amount": 0.004,
      "investment_id": "422259af-5247-4e7d-a78e-784a54f4818f",
      "asset_name": "Bitcoin",
      "asset_type": "crypto",
      "purchase_price": 400.0,
      "created_at": "2025-11-03T20:30:37.291579"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 2,
  "totalPages": 1,
  "first": true,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "empty": false
}
```

#### Erros possíveis

| Código | Motivo                           |
| ------ | -------------------------------- |
| 401    | Token inválido                   |
| 404    | Conta não encontrada |

---

### 5.2 Cria novo investimento na conta.

### *POST /accounts/{id}/investments*

**Descrição:** Cria um novo investimento na conta;

**Atenção:** Requer token JWT.

**Request Body**

```json
{
  "asset_name" : "Bitcoin",
  "amount" : 0.004,
  "purchase_price" : 400.0
}
```

**Response 201**

```json
{
  "amount": 0.004,
  "asset_name": "Bitcoin",
  "asset_type": "crypto",
  "purchase_price": 400.0,
  "created_at": "2025-11-03T20:32:40.771429"
}
```

#### Erros possíveis

| Código | Motivo                           |
|--------| -------------------------------- |
| 400    | Quantidade e valor devem ser positivos |
| 401    | Token inválido            |
| 404    | Conta não encontrada          |
| 422    | Saldo insuficiente            |
| 500    | Erro interno do servidor      |

---

### 5.3 Excluir investimento da conta.

### *DELETE /accounts/{account_id}/investments/{investment_id}*

**Descrição:** Exclui o investimento especificado da conta logada. (Funciona também como saque do valor do investimento, já que calcula a quantidade sobre o preço atual e "deposita" na conta)

**Atenção:** Requer token JWT.

**Response 204**
*(sem corpo)*

#### Erros possíveis

| Código | Motivo                                                          |
| ------ | --------------------------------------------------------------- |
| 401    | Token inválido               |
| 404    | Investimento não encontrado  |
| 500    | Erro interno do servidor     |

---

## **6. Courses**

### 6.1 Listar cursos disponíveis

### *GET /courses*

**Descrição:** Retorna todos os cursos disponíveis na plataforma.

**Response 200**

```json
{
  "content": [
    {
      "course_id": "a12f5670-2d4e-11ef-bf4b-123456789abc",
      "title": "Introdução aos Investimentos",
      "summary": "Aprenda os fundamentos essenciais para começar a investir com segurança.",
      "level": "BASICO",
      "thumbnail": "https://cdn.borainvestir.b3.com.br/2024/04/17112249/fundos-de-investimentos-como-escolher.jpg"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 2,
  "totalPages": 1,
  "first": true,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "empty": false
}
```

---

### 6.2 Detalhar curso e listar aulas

### *GET /courses/{courseId}/accounts/{accountId}*

**Descrição:** Retorna detalhes do curso e suas aulas (inclui id da conta para verificar completude do curso e etc).

**Response 200**

```json
{
  "course_id": "a12f5670-2d4e-11ef-bf4b-123456789abc",
  "title": "Introdução aos Investimentos",
  "description": "Curso completo para aprender sobre tipos de investimento, riscos e rentabilidade.",
  "thumbnail": "https://cdn.borainvestir.b3.com.br/2024/04/17112249/fundos-de-investimentos-como-escolher.jpg",
  "level": "BASICO",
  "lessons": [
    {
      "lesson_id": "63190b5a-08c2-4b77-87e0-d71b4286367f",
      "title": "O que é investimento?",
      "video_url": "https://youtu.be/Mca-HLZkaB8?si=95M8eDyMHJJMHl8R",
      "duration": 241,
      "lesson_order": 1,
      "current_time": 0,
      "completed": false
    },
    {
      "lesson_id": "36ef136e-5728-4dc2-a0e6-9d5730e7d81c",
      "title": "Perfil do investidor",
      "video_url": "https://youtu.be/enDUY7pHwsU?si=66az5prpSvP-UF3z",
      "duration": 543,
      "lesson_order": 2,
      "current_time": 543,
      "completed": true
    }
  ]
}
```

#### Erros possíveis

| Código | Motivo                   |
| ------ | ------------------------ |
| 404    | Curso não encontrado     |
| 500    | Erro interno do servidor |

---


### 6.3 Atualizar progressão da aula da conta.

### *PUT /accounts/{account_id}/courses/{course_id}/lessons/{lesson_id}/progress*

**Descrição:** Atualiza a progressão da aula feita pela conta logada.

**Atenção:** Requer token JWT.


**Request Body**

```json
{
  "current_time" : 241
}
```

**Response 200**

```json
{
  "current_time" : 241,
  "completed" : true
}
```

#### Erros possíveis

| Código | Motivo                           |
| ------ | -------------------------------- |
| 400    | Dados inválidos                  |
| 404    | Conta, Curso ou Aula não encontrados |
| 500    | Erro interno do servidor         |

---

---

### Autores
**Luiz Carvalho**  
Desenvolvedor Full Stack em formação - FIAP  
[GitHub](https://github.com/nxizy)

**Maria Eduarda**  
Desenvolvedor Full Stack em formação - FIAP  
[GitHub](https://github.com/dudisxqz)
