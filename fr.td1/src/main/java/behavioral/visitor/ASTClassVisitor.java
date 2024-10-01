package behavioral.visitor;

import org.eclipse.jdt.core.dom.*;
import java.util.*;

public class ASTClassVisitor extends ASTVisitor {
    // Liste pour stocker les informations de toutes les classes
    private List<ClassInfo> classInfoList = new ArrayList<>();
    
    // Classe interne pour stocker les informations d'une classe
    private static class ClassInfo {
        String name;
        List<AttributeInfo> attributes = new ArrayList<>();
        List<String> methods = new ArrayList<>();
        List<String> superClasses = new ArrayList<>();
    }
    
    // Classe interne pour stocker les informations d'un attribut
    private static class AttributeInfo {
        String name;
        String encapsulation;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        ClassInfo classInfo = new ClassInfo();
        
        // Extraction du nom de la classe
        classInfo.name = node.getName().getFullyQualifiedName();
        
        // Extraction des superclasses
        if (node.resolveBinding() != null) {
            ITypeBinding superClass = node.resolveBinding().getSuperclass();
            while (superClass != null) {
                classInfo.superClasses.add(superClass.getName());
                superClass = superClass.getSuperclass();
            }
        }
        
        // Extraction des attributs
        for (FieldDeclaration field : node.getFields()) {
            for (Object fragment : field.fragments()) {
                if (fragment instanceof VariableDeclarationFragment) {
                    VariableDeclarationFragment vdf = (VariableDeclarationFragment) fragment;
                    AttributeInfo attributeInfo = new AttributeInfo();
                    attributeInfo.name = vdf.getName().getIdentifier();
                    attributeInfo.encapsulation = getEncapsulation(field);
                    classInfo.attributes.add(attributeInfo);
                }
            }
        }
        
        // Extraction des méthodes
        for (MethodDeclaration method : node.getMethods()) {
            classInfo.methods.add(method.getName().getIdentifier());
        }
        
        classInfoList.add(classInfo);
        return super.visit(node);
    }

    private String getEncapsulation(FieldDeclaration field) {
        int modifiers = field.getModifiers();
        if (Modifier.isPublic(modifiers)) return "public";
        if (Modifier.isProtected(modifiers)) return "protected";
        if (Modifier.isPrivate(modifiers)) return "private";
        return "default";
    }

    // Méthode pour afficher les informations collectées
    public void printClassInfo() {
        for (ClassInfo classInfo : classInfoList) {
            System.out.println("Class Name: " + classInfo.name);
            System.out.println("Attributes:");
            for (AttributeInfo attr : classInfo.attributes) {
                System.out.println("  - " + attr.name + " (" + attr.encapsulation + ")");
            }
            System.out.println("Methods:");
            for (String method : classInfo.methods) {
                System.out.println("  - " + method);
            }
            System.out.println("Superclasses: " + String.join(", ", classInfo.superClasses));
            System.out.println();
        }
    }
}