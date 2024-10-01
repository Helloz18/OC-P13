# Your Car Your Way

## Base de donnée
- Installez MongoDB : https://www.mongodb.com/products/self-managed/community-edition

- Créer une base de données nommées <strong>chat_db</strong>

- Importez la collection chat_db.user.json présente dans le dossier <strong>db</strong>

## Backend
Le backend est développé avec :
- Maven 
- Java 21
- Spring Boot 3.3.3

### Configuration de la connexion à la base chat_db
Dans le dossier resources du backend, ajouter un fichier <strong>env.properties</strong>
Indiquez vos connections à la base de données (par exemple) :

spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=chat_db


### Démarrer le backend
Dans le terminal, entrez la commande : 

|mvn spring-boot:run|
|-----|

Le backend tourne sur le port 8080. Quelques endpoints sont disponibles.
http://localhost:8080/swagger-ui/index.html
![Aperçu des endpoints disponibles dans le swagger](/img/swagger.png "Endpoints Swagger")


## Frontend
Le frontend est développé avec :
- Angular 17

### Installer les librairies
Dans le terminal, à la racine du dossier <strong>frontend</strong>, entrez : 
|npm i|
|---|

### Démarrer le frontend
Dans le terminal, à la racine du dossier <strong>frontend</strong>, entrez :
|ng serve --open|
|---|

Le frontend tourne sur le port 4200

## Simuler une conversation avec le support
- Ouvrir deux fenêtres de navigateur
- Dans la première, se connecter avec le <strong>support</strong>

    Login = support
- Dans la seconde, se connecter avec le <strong>client</strong>

    Login = client

- Poser une question avec le client, le message s'affiche dans la fenêtre du client et du support.
- Repondre avec le support, le message est affiché dans les deux fenêtres.
- En base de données, une conversation (chatRoom) est créé entre les deux membres contenant les deux messages.