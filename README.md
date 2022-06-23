# LibraryBooks
Application to save books favourite


Este projeto é um desafio pessoal para criar um recurso em JAVA ultilizando os recursos de API Google.

Neste, momento foi desenvolvildo a rota:

* /Book  {GET}

Ela tem a capacidade de buscar a partir do nome do livro e uma paginação de inicio e fim para buscar a lista de livros com aquele nome.

Exemplo:

localhost:8080/books?q=senhor%20dos%20aneis&startIndex=0&maxResults=3

* /with-stars {GET}

Esta rota encontra a lista de favoritos da pratilheira.


* /Books/{id} {POST}

Nesta rota, em primeiro lugar é preciso criar buscar as autorizações de acesso para então adicionar na lista de favorito.

Este, foi o primeiro empecilho, sem o uso de um front-end a manipulação de usos dessas rotas é muito complicado.