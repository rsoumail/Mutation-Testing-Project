# VV_MutationTesting

## Usage

Lancement du projet :

  > Compilation des programmes (cible et mutateur)
  Se placer à la racine du projet :
  >
  Lancer la commande : mvn package 
  >
  (Cette commande compile les projets et les unitaires du programme cible)
  
  > Exécution du programme mutateur (module mutation)
  >
  Se place dans le dossier VV_MutationTesting/mutation/target
  
  Lancer la commande : 
  > 
  java -cp ./mutation-with-dependecies-mutation-1.0-SNAPSHOT.jar:.chemin_absolue_projet_cible/target/fr.istic.m2il.vv.input-1.0-SNAPSHOT.jar fr.istic.m2il.vv.mutation.App chemin_absolue_projet_cible/input
  
  > Où chemin_absolue_projet_cible represente le chemin absolue du programme cible à muter.
  
    
    
    

## Fonctionnalités implémentées 

 ### Programme :
 
 Dans le projet /VV_MutationTesting/input
 
    > Création de nos classes arithmétiques (+,-,*,/)
    > Test unitaire de chaque classe arithmétique
 
  Dans le projet /VV_MutationTesting/mutation
  
    > Dans le package ../vv/mutation/mutator les mutants implémentées sont:
      
     Opération + est remplacé par -
     Opération - est remplacé par +
     Opération * est remplacé par /
     Opération / est remplacé par *
     
     Suppression des méthodes de type void
     
     Modification aléatoire de la valeur de retour des méthodes de types void
     
     
## Couverture du code avec PIT

  Se placer à la racine du projet:
 
  > Lancer la commande : mvn clean install org.pitest:pitest-maven:mutationCoverage
  le rapport au format HTML se trouve dans le dossier VV_MutationTesting/mutation/
  
  

  
     
