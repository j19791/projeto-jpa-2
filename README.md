# projeto-jpa-2
Java e JPA: Otimizações com JPA2 e Hibernate


## Aula 2 - Mapeando relacionamentos Muitos-para-Muitos


## Aula 3 - Consultas dinâmicas com Criteria API

## Aula 4 -O comportamento Lazy e OpenEntityManagerInView


## Aula 5 - Gerenciando conexões com Pool de conexão


## Aula 6 -Evitando conflitos com Lock otimista

## Aula 7 - Melhorando o desempenho com cache

## Aula 8 - Caçando seus gargalos com o Hibernate Statistics


## Estratégias de cache:
*READ_ONLY*: quando uma entidade não é modificada.

*READ_WRITE*: uma entidade pode ser modificada e há grandes chances que modificações em seu estado ocorram simultaneamente. Essa estratégia é a que mais consome recursos.

*NONSTRICT_READ_WRITE*: entidade pode ser modificada, mas é incomum que as alterações ocorram ao mesmo tempo. Ela consome menos recursos que a estratégia READ_WRITE e é ideal quando não há problemas de dados inconsistentes serem lidos quando ocorrem alterações simultâneas.

*TRANSACTIONAL*: utilizada em ambientes JTA, como por exemplo em servidores de aplicação. Como utilizamos Tomcat com Spring (sem JTA) essa opção não funcionará.