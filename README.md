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
