# VV_MutationTesting

## Usage

Lancement du projet :

  > Compilation des programmes (cible et mutateur)
  Se placer à la racine du projet :
  Lancer la commande : mvn package (Cette commande compile les projets et les unitaires du programme cible)
  
  > Exécution du programme mutateur (module mutation)
  Lancer le programme dans votre IDE (Avant de lancer il faut configurer en passant en argument le programme cible 
  du chemin absolue "Mutation_Testing/VV_MutationTesting/input")
  
    
    
    

## Fonctionnalités implémentées 

 ### Programme :
 
 Dans le projet /VV_MutationTesting/input
 
    > Création de nos classes arithmétiques (+,-,*,/)
    > Test unitaire de chaque classe arithmétique
 
  Dans le projet /VV_MutationTesting/mutation
  
    > Dans le package ../vv/mutation les mutants implémentées sont:
      
     Opération + est remplacé par -
     Opération - est remplacé par +
     Opération * est remplacé par /
     Opération / est remplacé par *
     
     Suppression des méthodes de type void
     
     
## Couverture du code avec PIT

  Se placer à la racine du projet:
  Lancer la commande : mvn fr.istic.m2il.vv:pitest-maven:mutationCoverage
  
  

  
     
