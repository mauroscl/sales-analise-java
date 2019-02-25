# sales-analise-java
implements data analysys over csv files
# Problem
Statement of Work
You must build a data analysis system 100% coded in any of the languages above. The system must be
able to import lots of flat files, read and analyse the data, and output a report.
Please read the following for more information about the input files, data analysis and the needed output.
Flat file layout
There are 3 kinds of data inside those files. For each kind of data there is a different layout.

Salesman data Salesman data has the format id 001 and the line will have the following format.
001çCPFçNameçSalary

Customer data Customer data has the format id 002 and the line will have the following format.

002çCNPJçNameçBusiness Area

Sales data Sales data has the format id 003. Inside the sales row, there is the list of items, which is
wrapped by square brackets []. The line will have the following format.

003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

Sample file data
The following is a sample of the data that the application should be able to read. Note that this is a
sample, real data could be 100% different.

001ç1234567891234çDiegoç50000 
001ç3245678865434çRenatoç40000.99
002ç2345675434544345çJose da SilvaçRural 
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato

Data analysis

Your system must read data from the default directory, located at %HOMEPATH%/data/in. The system
must only read .dat files.
After processing all files inside the input default directory, the system must create a flat file inside the
default output directory, located at %HOMEPATH%/data/out. The filename must follow this pattern,
{flat_file_name}.done.dat.
The output file contents should summarize the following data:

● Amount of clients in the input file

● Amount of salesman in the input file

● ID of the most expensive sale

● Worst salesman ever

This application should be running all the time, without any breaks. Everytime new files become
available, everything should be executed.

#Solution
It is not so clear whether the output file should be processed in a cumulative base or independently.
I chose process each file independently. Then when the application receives a input file it will generate an output file without consider the content of another input files.

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
worstSellers and mostExpensiveSales are wrapped by brackets in case of a tie.

## Solution
The solution was implemented with the following technologies:
- Spring Boot: for bootstrapping, dependency injection and management of the application
- Gradle: for project dependency management, build the application and run tests
- Apache Camel: for configure and orchestration of the pipeline from input file to output file
- Apache Camel CSV: for reading of input files
- JUnit: for unit and integration tests




