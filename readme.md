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

### HATEOAS : gestion des hyperliens
openapi-generator tout comme swagger-generator ne tiennent pas comptent des balises links
de la spécification OpenApiSpec 3.0 pour la génération de code Java Server (CXF, Jersey, etc..)

Les solutions pour implémenter la génération de liens :
* Modification de l'implémentation de l'API
Pour retourner un objet Réponse qui contiendra la ressource (objet du model) plus les liens, nous allons modifier
la configuration du générateur et activer l'option useGenericResponse (true)

*Exemple de code générant des liens dans la méthode de création de commande*

            return Response.ok(Utility.toResource(cde))
                .links(
                    Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                               .path(GestionDesCommandesApi.class,"getCommandes"))
                        .rel("self")
                        .build(cde.getId()),
                    Link.fromUriBuilder(uriInfo
                            .getBaseUriBuilder()
                            .path(GestionDesCommandesApi.class)
                            .path(GestionDesCommandesApi.class,"getCommandes"))
                         .rel("liste")
                         .build(),
                    Link.fromUriBuilder(uriInfo
                           .getBaseUriBuilder()
                           .path(GestionDesCommandesApi.class)
                           .path(GestionDesCommandesApi.class, "deleteCommande"))
                        .rel("supp")
                        .build(cde.getId())
                 )
                .build();
    }    

Les liens sont mis dans un élément link dans l'en-tête HTTP de la réponse

*Ecriture d'un provider ContainerResponseFilter pour modifier le contenu de la réponse et rajouter des liens dans le corps de la réponse

*Exemple de code pour le provider*
    @Provider
    @Priority(value = 10)
    public class LinkResponseFilter implements ContainerResponseFilter {

        @Override
        public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
            if (responseContext.hasEntity()) {
               if (requestContext.getUriInfo().getMatchedResources().contains(GestionDesCommandesApi.class));
                  List<Link> links=CommandeLink.add(requestContext, responseContext);
                  responseContext.setEntity(
                          new CdeResWithLink().modelRes(responseContext.getEntity())
                                              .links(links));
            }

        }
    }

