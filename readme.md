# RESTDesignFirstCxfCde

## Fonctionnel
Gestion des commandes d'un client.
Les ressources du domaine sont :
* Client avec adresses de livraison et facturation
* Commandes avec des lignes de commandes comprendant une quantité et un lien vers le produit
* Produits qui contient la liste des produits du catalogue

## Technique
Projet Springboot pour montrer l'utilisation de la spécification OPEN API
* génération de code serveur pour Apache CXF
* data binding XML et JSON

Utilisation d'openapi-generator (https://openapi-generator.tech/) avec le plugin maven.

La génération jaxrs-cxf est utilisée avec génération de l'interface Java pour l'API ainsi
que le modèle de ressources.

Afin de conserver des méthodes d'implémentation le plus proche du métier, l'option useGenericResponse n'a pas été utilisée.

Les messages sont validés.



