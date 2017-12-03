# VV_MutationTesting

## Usage

Lancement du projet :

  > Compilation des programmes (cible)
  
    Dans le dossier input : mvn package    
  
  > Exécution de notre outil mutateur 
      
    Compilation des programmes 
  
    Dans le dossier mutator : mvn package 
    
    Configuration : dans le fichier application.propreties se trouvant dans mutator/src/main/resources/
  
    Fournir les propriétés suivants: 
           -  Les mutateurs souhaitée  
           Exemple:  mutators = ARITHMETIC_MUTATOR, VOID_MUTATOR, COMPARISON_MUTATOR, BOBLEAN_MUTATOR
             
           -  Le target du projet cible
           Exemple: target.project = /home/waberi/Documents/VV/Mutation_Testing/VV_MutationTesting/input
    
           -  Le maven home 
           Exemple: maven.home = /usr/share/maven 
    
    

## Fonctionnalités implémentées 

 ### Programme :
 
 Dans le projet /VV_MutationTesting/input
 
    > Création de nos classes arithmétiques 
                   
    > Test unitaire de chaque classe arithmétique

 
  Dans le projet /VV_MutationTesting/mutation
  
    > Dans le package mutator les mutants implémentées sont:
      
     Opération + est remplacé par -
     Opération - est remplacé par +
     Opération * est remplacé par /
     Opération / est remplacé par *
     
     Suppression du corps des méthodes de type void
     
     Renvoyer true pour les méthodes de types boolean
  
     Opérateur de comparaison
      
      Opération < est remplacé par >=
      Opération > est remplacé par <=
      Opération <= est remplacé par >
      Opération >= est remplacé par <
   
     
     
## Couverture d'analyse de mutation

 Notre outil génère des informations concernant les opérations des mutations sur le projet spécifié.
  

  
     
