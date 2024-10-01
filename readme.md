# Outil d'Analyse de Code Orienté Objet

## Introduction

Cet outil d'analyse de code orienté objet est conçu pour évaluer la qualité et la structure des projets Java. Il fournit des métriques détaillées et une interface graphique pour visualiser les résultats.

## Installation

### Prérequis

- Java Development Kit (JDK) 8 ou supérieur
- Un IDE Java (comme IntelliJ IDEA ou Eclipse) ou un terminal

### Étapes d'installation

1. Clonez le dépôt ou téléchargez les fichiers sources.
2. Ouvrez le projet dans votre IDE ou naviguez vers le répertoire du projet dans votre terminal.
3. Assurez-vous que les dépendances nécessaires (comme JDT) sont correctement configurées.

## Utilisation

### Via l'interface graphique

1. Exécutez la commande : `java -jar ASTAnalyzerGUI.jar `

2. Dans l'interface graphique :
- Cliquez sur "Parcourir" pour sélectionner le projet à analyser.
- Cliquez sur "Analyser" pour lancer l'analyse.
- Consultez les résultats dans les différents onglets.

### En ligne de commande

1. Exécutez la commande :  `java -jar ASTAnalyzer.jar <chemin_du_projet> <seuil_methodes>`

- `<chemin_du_projet>` : Chemin vers le répertoire du projet à analyser.
- `<seuil_methodes>` : Nombre de méthodes au-delà duquel une classe est considérée comme complexe.

## Fonctionnalités

L'outil calcule et affiche les métriques suivantes :

- Nombre total de classes, méthodes, lignes de code et packages
- Moyennes de méthodes par classe, lignes par méthode, et attributs par classe
- Classes avec le plus grand nombre de méthodes et d'attributs
- Méthodes les plus longues
- Nombre maximal de paramètres dans les méthodes

## Structure du projet

- `ASTAnalyzer` : Classe principale pour l'analyse du code
- `OOMetricsVisitor` : Visiteur AST pour la collecte des métriques
- `ASTAnalyzerGUI` : Interface graphique pour l'affichage des résultats


## Conclusion

Cet outil facilite l'évaluation et l'amélioration de la qualité du code Java en fournissant des métriques détaillées et une visualisation claire des résultats.

## Auteur

Ce projet a été réalisé par :

- DAIA Adam
- DAFAOUI Mohamed

Étudiants en M2 Génie Logiciel à l'Université de Montpellier