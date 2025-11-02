# Fintech - Contrato API

# Índice

- [Usuário](#1-autenticação)
- [Contas](#2-contas)
- [Wallet](#3-wallet)
- [Crypto](#4-assets)
- [Stocks](#5-stocks)
- [Currencies](#6-currencies)
- [Cursos](#7-cursos)


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
  "id": "fa7004e2-3c45-4bd3-b201-a7f9b58de8bf",
  "name": "Empresa",
  "balance": null,
  "createdAt": null
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
  "name": "Pessoal",
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
  "balance": 1540.75,
  "operations": [
    {
      "operation_id": "d93c8c30-2d4e-11ef-bf4b-123456789abc",
      "type": "DEPOSIT",
      "amount": 200.0,
      "created_at": "2025-10-12T10:00:00"
    },
    {
      "operation_id": "e1b77e40-2d4e-11ef-bf4b-123456789abc",
      "type": "WITHDRAWAL",
      "amount": 100.0,
      "created_at": "2025-10-13T09:30:00"
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
  "type": "DEPOSIT",
  "amount": 500.00
}
```

**Response 201**

```json
{
  "operation_id": "e5f23470-2d4e-11ef-bf4b-123456789abc",
  "type": "DEPOSIT",
  "amount": 500.0,
  "created_at": "2025-10-15T17:00:00",
  "balance_after": 2040.75
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
  "assets": [
    {
      "asset_id" : "3e67f35b-9169-47f2-b51e-f166345026c2",
      "asset_type": "crypto",
      "name": "Bitcoin",
      "symbol" : "BTC",
      "current_price": 618540.95,
      "market_cap" : 2305300302602,
      "market_sector" : "payments"
    },
    {
      "asset_id" : "0d95486e-0846-42dd-8d07-6a7452619e7b",
      "asset_type": "crypto",
      "name" : "Ether",
      "symbol" : "ETH",
      "current_price": 22582.84,
      "market_cap" : 507048331030.67,
      "market_sector" : "platforms"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 115,
  "totalPages": 12,
  "last": false,
  "first": true,
  "numberOfElements": 2
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

**Descrição:** Retorna os investimentos feitos na conta.

**Atenção:** Requer token JWT.

**Response 200**

```json
{
  "investments": [
    {
      "investment_id" : "1383bdf0-989c-4365-a4c4-088f3ce451ca",
      "asset_type": "crypto",
      "asset_name": "Bitcoin",
      "amount": 0.002,
      "purchase_price": 200.0,
      "current_price": 618540.95,
      "created_at": "2025-10-12T10:00:00"
    },
    {
      "investment_id" : "a77858a5-c00c-4476-8277-83541fbbcb81",
      "asset_name" : "BBAS3",
      "asset_type": "stock",
      "amount": 2.0,
      "purchase_price": 41.22,
      "current_price": 20.61,
      "created_at": "2025-10-15T15:15:00"
    }
  ]
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
    "asset_name" : "BBAS3",
    "asset_type": "stock",
    "amount": 2.0,
    "purchase_price": 41.22,
}
```

**Response 201**

```json
{
    "asset_name" : "BBAS3",
    "asset_type": "stock",
    "amount": 2.0,
    "purchase_price": 41.22,
    "created_at": "2025-10-15T15:15:00"
}
```

#### Erros possíveis

| Código | Motivo                           |
| ------ | -------------------------------- |
| 401    | Token inválido            |
| 404    | Conta não encontrada          |
| 422    | Saldo insuficiente            |
| 500    | Erro interno do servidor      |

---

### 5.3 Excluir investimento da conta.

### *DELETE /accounts/{account_id}/investments/{investment_id}*

**Descrição:** Exclui o investimneto especificado da conta logada.

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

## **6. Cursos**

### 6.1 Listar cursos disponíveis

### *GET /courses*

**Descrição:** Retorna todos os cursos disponíveis na plataforma.

**Response 200**

```json
[
  {
    "course_id": "a12f5670-2d4e-11ef-bf4b-123456789abc",
    "title": "Introdução aos Investimentos",
    "summary": "Aprenda os fundamentos essenciais para começar a investir com segurança.",
    "level" : "basico",
    "thumbnail": "https://cdn.borainvestir.b3.com.br/2024/04/17112249/fundos-de-investimentos-como-escolher.jpg"
  }
]
```

---

### 6.2 Detalhar curso e listar aulas

### *GET /courses/{id}*

**Descrição:** Retorna detalhes do curso e suas aulas.

**Response 200**

```json
{
  "course_id": "a12f5670-2d4e-11ef-bf4b-123456789abc",
  "title": "Introdução aos Investimentos",
  "description": "Curso completo para aprender sobre tipos de investimento, riscos e rentabilidade.",
  "thumbnail": "https://cdn.borainvestir.b3.com.br/2024/04/17112249/fundos-de-investimentos-como-escolher.jpg",
  "level" : "basico",
  "lessons": [
    { 
      "lesson_id": 1,
      "title": "O que é investimento?",
      "video_url" : "https://youtu.be/Mca-HLZkaB8?si=95M8eDyMHJJMHl8R",
      "duration" : "241",
      "lesson_order" : "1",
      "current_time" : "241",
      "completed" : "true"
    },
    { 
      "lesson_id": 2, 
      "title": "Perfil do investidor",
      "video_url" : "https://youtu.be/enDUY7pHwsU?si=66az5prpSvP-UF3z",
      "duration" : "543",
      "lesson_order" : 2,
      "current_time" : 325,
      "completed" : false
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
| 401    |
| 404    | Conta, Curso ou Aula não encontrados |
| 500    | Erro interno do servidor         |

---