# sales-analise-java
Implementa estatísticas sobre dados de venda disponibilizados em um arquivo CSV.
# Problema
Criar um sistema de análise de dados de venda que irá importar lotes de arquivos e produzir
um relatório baseado em informações presentes no mesmo.
Existem 3 tipos de dados dentro dos arquivos e eles podem ser distinguidos pelo seu
identificador que estará presente na primeira coluna de cada linha, onde o separador de
colunas é o caractere “ç”.

####Dados do vendedor
Os dados do vendedor possuem o identificador 001 e seguem o seguinte formato:

001çCPFçNameçSalary

####Dados do cliente
Os dados do cliente possuem o identificador 002 e seguem o seguinte formato:

002çCNPJçNameçBusiness Area

#### Dados de venda
Os dados de venda possuem o identificador 003 e seguem o seguinte formato:

003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

####Exemplo de conteúdo total do arquivo:

```
001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
```

O sistema deverá ler continuamente todos os arquivos dentro do diretório padrão
HOMEPATH/data/in e colocar o arquivo de saída em HOMEPATH/data/out.
No arquivo de saída o sistema deverá possuir os seguintes dados:

- Quantidade de clientes no arquivo de entrada

- Quantidade de vendedores no arquivo de entrada

- ID da venda mais cara

- O pior vendedor

#Solução
Não ficou tão claro se o arquivo de saída deve ser processado de forma cumulativa ou independentemente.
Eu escolhi processar cada arquivo independentemente. Então quando a aplicação receive um arquivo no diretório de entrada será gerado um arquivo de saída com o resultado do processamento referente a este arquivo.

O arquivo de saída tem o sufixo "done" no seu nome. Se o arquivo de entrada for "vendas1.dat" então o arquivo de saída será "vendas1.done.dat"  

## Input Example:
```sh
001ç1234567891234çDiegoç50000 
001ç3245678865434çRenatoç40000.99
002ç2345675434544345çJose da SilvaçRural 
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
```

## Output
```sh
amountSalesman=2
amountCustomer=2
worstSellers=[Renato]
mostExpensiveSales=[10]
```
Piores vendedores e vendas mais caras serão separados por vírgula quando houver empate.

## Solution
A solução foi implementada utilizando as seguintes tecnologias:
- Spring Boot
- Gradle
- Apache Camel
- Apache Camel CSV
- Apache Camel HazelCast
- JUnit

## Arquitetura
Foi utilizada uma arquitetura hexagonal na solução.
Principais pacotes e suas responsabilidades:

#### application
Contém a parte de negócio, é independente de tecnologia. Poderia ser plugada em uma aplicação rest ou em em um sistema de mensageria, por exemplo.

Pacote | Descrição
--- | ---
domain| Contém as entidades do sistema. Poderia conter também objetos de valor e serviços de domínio
ports |`driver ports` são as portas de entrada para a aplicação, ou seja, para executar os casos de uso. `Driven ports` são portas que a aplicação utilizada para acessar o mundo externo, como um arquivo csv
usecases|Coordena a lógica de implementação do caso de uso. Utiliza os objetos do domínio e as `driven ports` para realizar este trabalho 

#### adapters
Implenta as portas definidas na aplicação

Pacote | Descrição
--- | ---
primary|adapta os dados do Apachel Camel para obter o conteúdo do arquivo e invocar o caso de uso via driver port. Poderia ter adaptadores para outras entradas, como um rest controller ou um sistema de mensageria.
secondary|Converte o conteúdo do arquivo no formato csv para os objetos de domínio. Utilizado pela aplicação para resolver o caso de uso. Poderia ter adapters implementando a mesma interface (porta) para outros tipos de arquivo

## Escalabilidade
Foi utilizado o Hazelcast para prover escalabilidade nos consumidores dos arquivos. Quando um arquivo é processado, é gerado um log informando o ID e o nome da thread que o processou. Com isto, percebemos que os arquivos são processados por threads distintas.
O Hazelcast está utilizando a configuração padrão
Exemplo de log de processamento: 
```
2019-08-20 07:37:59.273  INFO 4460 --- [-seda://csvfile] b.c.m.s.a.p.SalesCsvCamelProcessor       : File: arquivo1.dat Thread Id: 115 - Thread Name: Camel (camel-1) thread #2 - hazelcast-seda://csvfile
2019-08-20 07:37:59.288  INFO 4460 --- [-seda://csvfile] b.c.m.s.a.p.SalesCsvCamelProcessor       : File: arquivo2.dat Thread Id: 116 - Thread Name: Camel (camel-1) thread #3 - hazelcast-seda://csvfile
```

## Configurações
É possível realizar as seguintes configurações no arquivo `application.properties`
```
app.concurrent-consumers=2
app.input-path=data/in
app.output-path=data/out
```
- **app.concurrent-consumers**=número de consumidores. É o que possibilita escalar a aplicação.
- **app.input-path**=diretório de entrada dos arquivos de vendas
- **app.output-path**=diretório que contém o resultado dos arquivos processados
## Como rodar
É necessário Java 11 ou superior.

- Clonar o código fonte: `git clone https://github.com/mauroscl/sales-analise-java.git`
- A partir do diretório raiz dos fontes aplicação pode ser rodado com o comando
```gradle bootRun``` ou ```./gradlew bootRun```
- Criar arquivos configuradas na pasta de entrada configurar na aplicação. A pasta deve ser criada manualmente, caso não exista. Na configuração padrão o diretório de entrada é o caminho `data/in`, a partir do diretório raiz do código fonte. 

Importante destacar que foram adicionadas alguns argumentos por conta do Hazelcast para melhorar a sua performance, sugeridos pelo próprio log do Hazelcast.

```
--add-modules=java.se --add-exports=java.base/jdk.internal.ref=ALL-UNNAMED 
--add-opens=java.base/java.lang=ALL-UNNAMED 
--add-opens=java.base/java.nio=ALL-UNNAMED 
--add-opens=java.base/sun.nio.ch=ALL-UNNAMED 
--add-opens=java.management/sun.management=ALL-UNNAMED 
--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED
```
Estes parâmetros encontram-se na configuração da task `bootRun` no arquivo `build.gradle`

