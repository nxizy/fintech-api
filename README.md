# Fintech - Contrato API

# Índice

- [Login](#1-login)
- [Contas](#2-contas)
- [Wallet](#3-wallet)
- [Crypto](#4-crypto)
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
  "user_id": "c61a3b98-2d4e-11ef-bf4b-123456789abc",
  "accounts": [
    {
      "account_id": "6e54e570-2d4e-11ef-bf4b-123456789abc",
      "account_name": "Pessoal",
      "is_default": true
    }
  ]
}
```

#### Erros possíveis

| Código | Motivo                    |
| ------ | ------------------------- |
| 400    | Campos inválidos          |
| 401    | Email ou senha incorretos |
| 500    | Erro interno do servidor  |

---

### 1.2 Registrar usuário

### *POST /auth/register*

**Descrição:** Registra um novo usuário e cria uma conta padrão associada.

**Request Body**

```json
{
  "user_name": "Luiz Carvalho",
  "birth_date": "2007-02-21",
  "document_number": "00000000000",
  "phone_number": "11999999999",
  "email": "email2luiz@gmail.com",
  "password": "password"
}
```

**Response 201**

```json
{
  "user_id": "c61a3b98-2d4e-11ef-bf4b-123456789abc",
  "account_id": "6e54e570-2d4e-11ef-bf4b-123456789abc",
  "account_name": "Pessoal"
}
```

#### Erros possíveis

| Código | Motivo                   |
| ------ | ------------------------ |
| 400    | Dados inválidos          |
| 409    | Email já cadastrado      |
| 500    | Erro interno do servidor |

---

## **2. Contas**

### 2.1 Listar contas do usuário logado

### *GET /accounts*

**Descrição:** Retorna todas as contas vinculadas ao usuário autenticado.

**Atenção:** Requer token JWT.

**Response 200**

```json
[
  {
    "account_id": "6e54e570-2d4e-11ef-bf4b-123456789abc",
    "account_name": "Pessoal",
    "is_default": true
  },
  {
    "account_id": "7a7a6780-2d4e-11ef-bf4b-123456789abc",
    "account_name": "Investimentos",
    "is_default": false
  }
]
```

#### Erros possíveis

| Código | Motivo                     |
| ------ | -------------------------- |
| 401    | Token inválido ou expirado |
| 500    | Erro interno do servidor   |

---

### 2.2 Atualizar dados do usuário e da conta logada

### *PUT /accounts/{id}*

**Descrição:** Atualiza dados do usuário e preferências da conta (nome e se é padrão ou não).

**Atenção:** Requer token JWT.

**Request Body**

```json
{
  "user_name": "Luiz Victor Carvalho",
  "birth_date": "2007-02-21",
  "document_number": "00000000000",
  "phone_number": "11995994999",
  "email": "email2luiz@gmail.com",
  "account_name": "Pessoal",
  "is_default": false,
  "created_at": "2022-10-25T10:05:00",
  "password": "password"
}
```

**Response 200**

```json
{
  "user_name": "Luiz Victor Carvalho",
  "birth_date": "2007-02-21",
  "document_number": "00000000000",
  "phone_number": "11995994999",
  "email": "email2luiz@gmail.com",
  "account_name": "Pessoal",
  "is_default": false,
  "created_at": "2022-10-25T10:05:00"
}
```

#### Erros possíveis

| Código | Motivo                           |
| ------ | -------------------------------- |
| 400    | Dados inválidos                  |
| 404    | Usuário ou conta não encontrados |
| 500    | Erro interno do servidor         |

---

### 2.3 Criar nova conta do usuário logado

### *POST /accounts*

**Descrição:** Cria uma nova conta vinculada ao usuário autenticado.
**Atenção:** Requer token JWT.

**Comportamento sugerido:**

* `is_default` default = `false`.
* Se for a **primeira conta** do usuário, automaticamente `is_default = true`.

**Request Body**

```json
{
  "account_name": "Poupança Viagem",
  "initial_balance": 0.00,
  "is_default": false
}
```

**Response 201**

```json
{
  "account_id": "9b1e7f20-2d4e-11ef-bf4b-123456789abc",
  "account_name": "Poupança Viagem",
  "balance": 0.00,
  "is_default": false,
  "created_at": "2025-10-17T22:00:00Z"
}
```

#### Erros possíveis

| Código | Motivo                                              |
| ------ | --------------------------------------------------- |
| 400    | Dados inválidos (ex.: nome vazio, balance negativo) |
| 401    | Token inválido ou expirado                          |
| 409    | Nome de conta já existe para o usuário (opcional)   |
| 500    | Erro interno do servidor                            |

---

### 2.4 Excluir uma conta do usuário logado

### *DELETE /accounts/{id}*

**Descrição:** Exclui a conta especificada do usuário autenticado.
**Atenção:** Requer token JWT.

**Regras sugeridas:**

* Não permitir exclusão se `is_default = true` (exigir trocar padrão antes).
* Não permitir exclusão se houver saldo ou investimentos (exigir zerar previamente ou migrar).

**Response 204**
*(sem corpo)*

#### Erros possíveis

| Código | Motivo                                                          |
| ------ | --------------------------------------------------------------- |
| 400    | Conta não pode ser excluída (ex.: saldo > 0, tem investimentos) |
| 401    | Token inválido ou expirado                                      |
| 403    | Usuário não tem permissão para excluir esta conta               |
| 404    | Conta não encontrada                                            |
| 500    | Erro interno do servidor                                        |

---

## **3. Wallet**

### 3.1 Consultar saldo e movimentações

### *GET /accounts/{id}/wallet*

**Descrição:** Retorna o saldo e o histórico de movimentações (saques e depósitos) da conta.

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
| 404    | Conta não encontrada     |
| 500    | Erro interno do servidor |

---

### 3.2 Cadastrar movimentação

### *POST /accounts/{id}/wallet/transactions*

**Descrição:** Cadastra uma movimentação de depósito ou saque na conta.

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
| 400    | Tipo de movimentação inválido |
| 403    | Saldo insuficiente            |
| 404    | Conta não encontrada          |
| 500    | Erro interno do servidor      |

---

## **4. Crypto**

### *GET /accounts/{id}/crypto*

**Descrição:** Retorna as criptomoedas da conta.

**Response 200**

```json
[
  {
    "symbol": "BTC",
    "name": "Bitcoin",
    "quantity": 0.02,
    "purchase_value": 1200.0,
    "created_at" : "2025-10-15T12:00:00"
  }
]
```

#### Erros possíveis

| Código | Motivo                           |
| ------ | -------------------------------- |
| 404    | Conta não encontrada |

---

## **5. Stocks**

### *GET /accounts/{id}/stocks*

**Descrição:** Retorna as ações compradas na conta.

**Response 200**

```json
[
  {
    "symbol": "AAPL",
    "name": "Apple Inc.",
    "quantity": 10,
    "purchase_value": 1750.0,
    "created_at" : "2025-10-18T16:00:00"
  }
]
```

#### Erros possíveis

| Código | Motivo                          |
| ------ | ------------------------------- |
| 404    | Conta não encontrada            |

---

## **6. Currencies**

### *GET /accounts/{id}/currencies*

**Descrição:** Retorna as moedas estrangeiras adquiridas na conta.

**Response 200**

```json
[
  {
    "symbol": "USD",
    "name": "Dólar Americano",
    "quantity": 200,
    "purchase_value": 980.0,
    "created_at" : "2025-10-14T16:00:00"
  }
]
```

#### Erros possíveis

| Código | Motivo                     |
| ------ | -------------------------- |
| 404    | Conta não encontrada       |

---

## **7. Cursos**

### 7.1 Listar cursos disponíveis

### *GET /courses*

**Descrição:** Retorna todos os cursos disponíveis na plataforma.

**Response 200**

```json
[
  {
    "course_id": "a12f5670-2d4e-11ef-bf4b-123456789abc",
    "title": "Introdução aos Investimentos",
    "summary": "Aprenda os fundamentos essenciais para começar a investir com segurança.",
    "thumbnail": "https://cdn.veyto.com/images/course1.png"
  }
]
```

---

### 7.2 Detalhar curso e listar aulas

### *GET /courses/{id}*

**Descrição:** Retorna detalhes do curso e suas aulas.

**Response 200**

```json
{
  "course_id": "a12f5670-2d4e-11ef-bf4b-123456789abc",
  "title": "Introdução aos Investimentos",
  "description": "Curso completo para aprender sobre tipos de investimento, riscos e rentabilidade.",
  "thumbnail": "https://cdn.veyto.com/images/course1.png",
  "level" : "basico",
  "lessons": [
    { "lesson_id": "1", "title": "O que é investimento?" },
    { "lesson_id": "2", "title": "Perfil do investidor" }
  ]
}
```

#### Erros possíveis

| Código | Motivo                   |
| ------ | ------------------------ |
| 404    | Curso não encontrado     |
| 500    | Erro interno do servidor |

---
