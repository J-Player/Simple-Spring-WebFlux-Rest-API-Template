# Simple Spring WebFlux Rest Template
Uma simples **API REST Template** usando **Spring WebFlux**.

# Como configurar
- Escolha um [Driver R2DBC](https://r2dbc.io/drivers/) e adicione-o às dependências do projeto.
- Configure o arquivo `application.yml` com as respectivas propriedades: `url`, `username` e `password`.
- Caso não tenha um banco de dados criado, crie-o utilizando o arquivo `schema.sql` e _popule-o_ utilizando o arquivo `data.sql`. Caso contrário, basta excluir a pasta `configs/data` e os arquivos `schema.sql` e `data.sql`.
    - **Nota**: A tabela User é necessária, pois é utilizada pela parte de segurança da API.
  - **Obs**.: Lembre-se de adiciona antecipadamente os usuários _roots_ no seu banco de dados, pois todos os endpoints de `users` requer usuários autenticados e autorizados.