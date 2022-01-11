## TQI Evolution 2022 - Backend

---

### Tecnologias utilizadas:
- Docker
- PostgreSQL
- Java
- Maven
- JDK 11
- Spring:
  - Dependências: Data JPA, Lombok, Web, PostgreSQL Driver, Security, Model Mapper e Auth0

### Softwares para desenvolvimento:
- IntelliJ
- DBeaver
- Postman

---

### Divisão do código:
O projeto foi estruturado em 4 camadas, sendo:
 
- **entity:** definem as classes de modelo (*models*) e as sincronizam com o banco por meio dos decoradores do Spring.
- **repository:** realizam a conexão e fazem as operações no banco de dados por meio de uma interface genérica. (Caso mude o banco, o restante do código não quebra).
- **service:** é responsável pelas regras de negócio e faz as chamadas aos *repositories* que realizarão a persistência no banco.
- **controller:** onde estão direcionadas as rotas, por onde chegam as requisições que recebe a entrada dos dados e chama o *service*. Caso tudo ocorra com o esperado, o resultado é retornado ao usuário.

![tqi-diagram](https://user-images.githubusercontent.com/86791739/148865644-c3694316-7fd7-4908-bf0b-7dba3ae6e52b.png)

---

### Rotas
**1. Autenticação**
- Método: POST
- Endpoint: /login
- Tipo de Autorização: Bearer Token
- Descrição: esta rota é responsável pela autenticação do cliente.

Entrada:
```
{
  "email": "nome@email.com",
  "password": "teste@123"
}
```
Saída (Número do Token Gerado):
```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aW5pY2l1c0BlbWFpbC5jb20iLCJleHAiOjE2NDE4NDM3Njh9.00dW_qvqQ1KCuSQPMj3DC4GAEF4vs8HBkFiSVweSxS_gFh3IzsUzkxCXavrtQLQcdmVCUDPgSQlseKrsGjXqDA
```

---

**2. Cadastro de clientes**
   - Método: POST
   - Endpoint: /register
   - Descrição: esta rota cria um cliente.

Entrada:
```
{
    "name": "Nome",
    "email": "nome@email.com",
    "cpf": "01201201233",
    "rg": "123456789",
    "income": 2000,
    "address": "Avenida Teste 123",
    "password": "teste@123"
}
```

Saída:
```
{
    "id": "ccee5908-65f2-42e5-9d28-90486d049ac2",
    "name": "Nome",
    "email": "nome@email.com",
    "cpf": "01201201233",
    "rg": "123456789",
    "address": "Avenida Teste 123",
    "income": 2000.0
}
```

---

**3. Solicitação de empréstimo**
   - Método: POST
   - Endpoint: /loanApplication
   - Descrição: esta rota cria uma solicitação de empréstimo e realiza a associação com o cliente autenticado. 

Entrada:
```
{
    "value": 15000,
    "amountInstallments": 30,
    "firstInstallmentDate": "2022-02-08"
}
```

Saída:
```
{
    "id": "b95b2d95-08bb-4c47-8a80-f05e7d321fed",
    "clientEntity": {
        "id": "ccee5908-65f2-42e5-9d28-90486d049ac2",
        "name": "Nome",
        "email": "nome@email.com",
        "cpf": "01201201233",
        "rg": "123456789",
        "address": "Avenida Teste 123",
        "income": 2000.0
    },
    "value": 15000.0,
    "firstInstallmentDate": "2022-02-08T00:00:00.000+00:00",
    "amountInstallments": 30
}
```

---

**4. Acompanhamento das solicitações de empréstimo**
   - Método: GET
   - Endpoint: /loanApplication
   - Descrição: esta rota retorna todas as solicitações de empréstimo feitas pelo cliente autenticado. 

Saída:
```
[
    {
        "id": "b95b2d95-08bb-4c47-8a80-f05e7d321fed",
        "value": 15000.0,
        "amountInstallments": 30
    }
]
```

---

**5. Detalhe do empréstimo**
   - Método: GET
   - Endpoint: /loanApplication/{id}
   - Descrição: esta rota retorna os valores de apenas uma solicitação de empréstimo através do id da solicitação criada pelo cliente autenticado. 

Saída:
```
{
    "loanId": "b95b2d95-08bb-4c47-8a80-f05e7d321fed",
    "value": 15000.0,
    "amountInstallments": 30,
    "firstInstallmentDate": "2022-02-08T00:00:00.000+00:00",
    "clientEmail": "nome@email.com",
    "clientIncome": 2000.0
}
```

---

### Relacionamento 
O relacionamento que está sendo feito é o @ManyToOne (decorador do Spring). Uma ou várias solicitações podem ser realizadas por apenas um cliente. Um cliente pode fazer uma ou várias solicitações. Quando um cliente realiza uma solicitação, o seu id é associado a coluna *client_entity_id* da tabela *loan_application*.

![er-diagram-tqi](https://user-images.githubusercontent.com/86791739/148865692-21c32441-1a1f-4c86-8c7a-454d803e7fa8.png)



