Principais ferramentas
Java: 21
IDE: Intelij
SpringBoot: 3.2.2
Design Orientado a Domínio (DDD)
Docker
SQL
Dependências
Spring Data JPA
Primavera Web
SpringBoot
arrogância
commons-csv
Lombok
PostgreSQL
Arquivo application.properties
A propriedade CREATE TABLE contas_dbvai criar a tabela do banco de dados da api, mude para updatepersistir mais no fundo dos dados.
spring.datasource.url=jdbc:postgresql://localhost:5432/contas_db
spring.datasource.username=postgres
spring.datasource.password=@Morango272622
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

### SQL
- Dentro da pasta resources/migration/ encontra-se os arquivos sql para preparar o banco de dados.

# Como executar o projeto
- SQL PORT: 5432 
- API PORT- 8080

**********************************************************************************************************************************************
### Testando no Postman

    Abra o Postman e crie uma nova requisição:
        Método: GET
        URL: http://localhost:8080/contas
**********************************************************************************************************************************************

### Adicionando mais endpoints

Para adicionar mais funcionalidades (como criar, atualizar, ou deletar registros), você pode adicionar mais rotas no seu servidor Express:

## Adicionar uma nova contas

app.post('/contas', async (req, res) => {
  const { data_vencimento, data_pagamento, valor, descricao, situacao } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO contas_db (data_vencimento, data_pagamento, valor, descricao, situacao) VALUES ($1, $2, $3, $4, $5) RETURNING *',
      [data_vencimento, data_pagamento, valor, descricao, situacao]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

**********************************************************************************************************************************************

### Atualizar uma conta:

app.put('/contas/:id', async (req, res) => {
  const { id } = req.params;
  const { data_vencimento, data_pagamento, valor, descricao, situacao } = req.body;
  try {
    const result = await pool.query(
      'UPDATE contas_db SET data_vencimento = $1, data_pagamento = $2, valor = $3, descricao = $4, situacao = $5 WHERE id = $6 RETURNING *',
      [data_vencimento, data_pagamento, valor, descricao, situacao, id]
    );
    res.status(200).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

**********************************************************************************************************************************************

## Deletar uma conta:

app.delete('/contas/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM contas_db WHERE id = $1', [id]);
    res.status(204).send();
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

Após adicionar essas rotas, você poderá testar as novas funcionalidades no Postman ajustando o método HTTP e o corpo da requisição conforme necessário.

Essa configuração básica deve permitir que você acesse e manipule os dados da tabela contas_db usando o Postman.
