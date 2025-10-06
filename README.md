# SalesCoding
**API REST de facturation pour les achats de biens**

---

## Présentation

Ce projet est une implémentation du test technique **SalesCoding**, réalisée sous la forme d’une **API REST** développée en **Java Spring Boot**.

L’API reçoit dans le corps d’une requête HTTP `POST` un **JSON** représentant une liste d’achats (panier), contenant les biens, leurs prix et quantités.  
Le service calcule alors :
- le **prix TTC** de chaque bien après application des taxes ;
- le **montant total des taxes** appliquées ;
- le **prix total du panier**.

Le service retourne ensuite ces informations sous la forme d’un **JSON**.

---

## Choix et interprétations de l’énoncé

Certains points de l’énoncé n’étant pas explicitement définis, plusieurs décisions de conception ont été prises pour garantir la cohérence et l’extensibilité du projet :

- **Format des données** :  
  L’énoncé ne spécifie pas le format d’échange.  
  J’ai choisi **JSON**, plus lisible et mieux supporté par la majorité des outils et frameworks modernes (Postman, front-end JS, etc.).

- **Gestion des quantités** :  
  Bien que les exemples de l’énoncé présentent uniquement des quantités égales à 1, j’ai préféré gérer le cas général.  
  Les taxes sont calculées sur le **prix unitaire**, puis multipliées par la quantité.

- **Identification des biens** :  
  Aucun mécanisme de persistance ni de catalogue produit n’étant demandé, j’ai choisi d’intégrer la **catégorie du bien** directement dans le JSON d’entrée.  
  Cela permet à un client externe de spécifier s’il s’agit d’un livre, d’un produit alimentaire, médical ou autre, sans dépendre d’une base de données.

---

## Stack technique

| Élément | Technologie |
|----------|--------------|
| **Langage** | Java 21 |
| **Framework** | Spring Boot |
| **Génération du code** | OpenAPI Generator (à partir d’un fichier Swagger) |
| **Tests unitaires** | JUnit + JaCoCo (rapport de couverture) |
| **Build / Run** | Maven |
| **IDE recommandé** | IntelliJ IDEA |

---

## Exécution

L’API peut être :
1. **Compilée et lancée** avec Maven :
   ```bash
   mvn spring-boot:run
   ```
2. **Ou exécutée** directement depuis IntelliJ.

Une **collection Postman (format 2.1)** nommée "Sales_API_test.postman_collection.json" est fournie dans les fichiers sources du projet, permettant de **tester facilement l’API** et de reproduire les cas d’usage de l’énoncé.
Ci-dessous les json utilisés:

Input 1
```json
{
  "id": 1,
  "goods": [
    {
      "name": "Book",
      "quantity": 1,
      "category": "book",
      "imported": false,
      "price": 12.49
    },
    {
      "name": "Music CD",
      "quantity": 1,
      "category": "other",
      "imported": false,
      "price": 14.99
    },
    {
      "name": "Chocolate bar",
      "quantity": 1,
      "category": "food",
      "imported": false,
      "price": 0.85
    }
  ]
}
```
Input 2
```json
{
  "id": 2,
  "goods": [
    {
      "name": "box of chocolates",
      "quantity": 1,
      "category": "food",
      "imported": true,
      "price": 10.00
    },
    {
      "name": "bottle of perfume",
      "quantity": 1,
      "category": "other",
      "imported": true,
      "price": 47.50
    }
  ]
}
```

Input 3
```json
{
  "id": 3,
  "goods": [
    {
      "name": "bottle of perfume",
      "quantity": 1,
      "category": "other",
      "imported": true,
      "price": 27.99
    },
    {
      "name": "bottle of perfume",
      "quantity": 1,
      "category": "other",
      "imported": false,
      "price": 18.99
    },
    {
      "name": "Packet of headache pills",
      "quantity": 1,
      "category": "medical",
      "imported": false,
      "price": 9.75
    },
    {
      "name": "box of chocolates",
      "quantity": 1,
      "category": "food",
      "imported": true,
      "price": 11.25
    }
  ]
}
```

---

## Points d’accès utiles

| URL | Description |
|------|--------------|
| [http://localhost:8080/sales-api/swagger-ui.html](http://localhost:8080/sales-api/swagger-ui.html) | Documentation Swagger auto-générée |
| [http://localhost:8080/sales-api/actuator/health](http://localhost:8080/sales-api/actuator/health) | État d’exécution de l’API |
| [http://localhost:8080/sales-api/sales](http://localhost:8080/sales-api/sales) | Endpoint principal (service de facturation) |

---

## Notes techniques

- Les **DTOs** et **interfaces de contrôleur** ont été générés automatiquement à partir du fichier **Swagger** de spécification (`openapi-sales-v1.0.0.yaml`).
- La logique de calcul des taxes repose sur une architecture **orientée objet**, avec l’utilisation de **patterns** tels que *Factory* et *Decorator*, afin de garantir l’évolutivité du code.
- Aucun stockage persistant (BDD) n’est utilisé — toutes les données sont traitées en mémoire.

---
