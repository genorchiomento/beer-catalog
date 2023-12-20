<h1 align="center">
  Beer Catalog
</h1>

<p align="center">
<a href="https://twitter.com/genorchiomento" target="_blank">
    <img align="center" src="https://img.shields.io/static/v1?label=Twitter&message=@genorchiomento&style=flat&logo=Twitter&logoColor=white&color=00acee&labelColor=000000" alt="twitter"/>  
</a>
<img align="center" src="https://img.shields.io/static/v1?label=Type&message=Demo&color=8257E5&labelColor=000000" alt="Demo"/>

</p>

The Beer Catalog project is a digital platform aimed at
providing beer enthusiasts a unique experience of discovering
and appreciating a variety of beers, including craft beers.
With a user-friendly interface and a robust backend application,
users can explore an extensive catalog of beers, obtain details, rate,
and even discover new beers based on their tastes and preferences.

## Technologies

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)&nbsp;
![Kotlin](https://img.shields.io/badge/Java-white.svg?style=for-the-badge&logo=openjdk&logoColor=black)&nbsp;
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)&nbsp;
![Docker]()&nbsp;

## Features

- Extensive beer catalog with details like alcohol content,
  bitterness (IBU), color, ingredients, and flavor and aroma description.

- (Future features will include user reviews, personalized recommendations,
  and integration with manufacturers for real-time catalog updates.)

## How to Run

- Clone the git repository:

```shell
git clone https://github.com/genorchiomento/beer-catalog.git
```

- Config database with docker:

```shell
docker compose up -d
```

- Execute migrations with Flyway:

```shell
./gradlew flywayMigrate
```

- Build the project:

```shell
./gradlew clean build
```

- Execute:

```shell
./gradlew bootRun
```

- Test

```
POST/http

{

}
```

### Migrações do banco de dados com Flyway

#### Executar as migrações

Caso seja a primeira vez que esteja subindo o banco de dados, é necessário
executar as migrações SQL com a ferramenta `flyway`.
Execute o comando a seguir para executar as migrações:

```shell
./gradlew flywayMigrate
```

Pronto! Agora sim o banco de dados MySQL está pronto para ser utilizado.

<br/>

#### Limpar as migrações do banco

É possível limpar (deletar todas as tabelas) seu banco de dados, basta
executar o seguinte comando:

```shell
./gradlew flywayClean
```

MAS lembre-se: "Grandes poderes, vem grandes responsabilidades".

<br/>

#### Reparando as migrações do banco

Existe duas maneiras de gerar uma inconsistência no Flyway deixando ele no estado de reparação:

1. Algum arquivo SQL de migração com erro;
2. Algum arquivo de migração já aplicado foi alterado (modificando o `checksum`).

Quando isso acontecer o flyway ficará em um estado de reparação
com um registro na tabela `flyway_schema_history` com erro (`sucesso = 0`).

Para executar a reparação, corrija os arquivos e execute:
```shell
./gradlew flywayRepair
```

Com o comando acima o Flyway limpará os registros com erro da tabela `flyway_schema_history`,
na sequência execute o comando FlywayMigrate para tentar migrar-los novamente.

<br/>

#### Outros comandos úteis do Flyway

Além dos comandos já exibidos, temos alguns outros muito úteis como o info e o validate:

```shell
./gradlew flywayInfo
./gradlew flywayValidate
```

Para saber todos os comandos disponíveis: [Flyway Gradle Plugin](https://flywaydb.org/documentation/usage/gradle/info)

<br/>

#### Para executar os comandos em outro ambiente

Lá no `build.gradle` configuramos o Flyway para lêr primeiro as variáveis de
ambiente `FLYWAY_DB`, `FLYWAY_USER` e `FLYWAY_PASS` e depois usar um valor padrão
caso não as encontre. Com isso, para apontar para outro ambiente basta sobrescrever
essas variáveis na hora de executar os comandos, exemplo:

```shell
FLYWAY_DB=jdbc:mysql://prod:3306/adm_videos FLYWAY_USER=root FLYWAY_PASS=123h1hu ./gradlew flywayValidate
```