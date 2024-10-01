package behavioral.visitor;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Map;

public class ASTParserExample {
    public static void main(String[] args) {
        // Crée un nouvel ASTParser configuré pour utiliser la spécification Java Language Specification (JLS) version 2
        ASTParser parser = ASTParser.newParser(AST.JLS2);

        // Définit le code source Java à analyser en tant que tableau de caractères
        parser.setSource(getSourceOfVisitor().toCharArray()); 

        // Configure le parser pour analyser une unité de compilation complète (un fichier .java)
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        // Active la résolution des bindings pour obtenir des informations supplémentaires sur les types et références
        parser.setResolveBindings(true);

        // Récupère les options par défaut du compilateur Java et les applique au parser
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);

        // Génère l'AST à partir du code source et le stocke dans une CompilationUnit
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        // Crée un visiteur personnalisé pour parcourir l'AST et extraire des informations spécifiques
        ASTClassVisitor visitor = new ASTClassVisitor();

        // Applique le visiteur à l'AST, ce qui déclenche le parcours de l'arbre syntaxique
        cu.accept(visitor);
        visitor.printClassInfo();
        }

    private static String getSourceOfVisitor() {
        // Retourne une chaîne contenant le code source de la classe Liquor à analyser
        return  ""; 
    }
}
