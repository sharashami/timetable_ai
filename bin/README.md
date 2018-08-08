# README #

This README would normally document whatever steps are necessary to get your application up and running.


### Como configurar o projeto WEB? ###

Para configurar o ambiente de desenvolvimento:

1) Instalar o Java 8.x;
2) Instalar PostgreSQL + PgAdmin;
3) Baixar o Tomcat 9.0 que está no Bitbucket;
4) Instalar uma ferramenta que facilite o gerenciamento do repositório, como o SourceTree;
5) Clonar a branch webservice-1.0 do repositório utilizando o SourceTree;
6) Baixar o Eclipse JEE (última versão: Neon).

Inicie o SourceTree e clone o projeto do quadro de horários. Você vai escolher um diretório onde o projeto vai ser colocado no computador. Escolha o diretório onde se encontra o workspace do Eclipse que você está utilizando.

Logo após, vá ao Eclipse e crie um novo projeto do tipo Dynamic Web Project chamado "horarioaula" (que é justamente a pasta onde clonamos o repositório pelo SourceTree).

Para realizar a configuração do banco de dados, crie um novo banco no PostgreSQL, chamado "timetable". Agora vá ao arquivo persistence.xml e altere os campos de usuário e senha para o utilizado no seu PostgreSQL.

Após importar o projeto, é necessário a configuração do Maven, que irá gerenciar as dependências do sistema. Para configurar o Maven, vá em Configure -> Convert to Maven Project.

O Maven apresenta alguns problemas quando adicionado o repositório do PrimeFaces, que é localizado em outro site, diferente do Maven. Para resolver esse problema, há algumas possibilidades, como a atualização do Java e
a adição de um certificado de segurança (SSL). Para o último veja os seguintes links: http://stackoverflow.com/questions/21705509/primefaces-all-themes-v-1-0-10-installation, https://letsencrypt.org/certificates/ e https://dzone.com/articles/eclipse-plug-ins-via-https-amp-lets-encrypt.

Após ter realizado os passos dos links anteriores, realize um "Maven Build" no projeto, adicionando ao campo "Goals" os seguintes parâmetros: "clean install". Pronto! O projeto está configurado.

AVISO 1: O arquivo pom.xml só deve ser alterado em comum acordo da equipe, já que ele gerencia as dependências de todo o sistema e alterações simples podem ser catastróficas.

AVISO 2: O arquivo .gitignore contém a lista de arquivos que nunca devem ser enviados para o servidor de produção, como os arquivos de configuração do Eclipse. Assim como o arquivo pom.xml, esse arquivo deve ser alterado em comum acordo com a equipe.

AVISO 3: Toda vez que algum membro da equipe fizer alteração no código-fonte, este deve "comittar" sua alteração para prevenir que ela se perca. Logo após, é necessário realizar um "pull" para obter a última versão presente no servidor de produção. Caso haja divergências entre os dois fontes, o(a) programador(a) deve consertar o que houver de errado e realizar um "push". Caso contrário, pode realizar o "push".

AVISO 4: Algumas dados precisam ser inseridos para que o projeto funcione corretamente: Turnos: Manhã, Tarde e Noite e os Períodos AB, CD e EF 
