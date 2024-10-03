package part2;

import org.eclipse.jdt.core.dom.*;
import java.util.*;

public class CallGraphVisitor extends ASTVisitor {
    private Map<String, Set<String>> callGraph = new HashMap<>();
    private String currentMethod;

    //Méthode appelée lorsqu'on visite un noeud de type MethodDeclaration
    @Override
    public boolean visit(MethodDeclaration node) {
        currentMethod = node.getName().getIdentifier();
        callGraph.putIfAbsent(currentMethod, new HashSet<>());
        return true;
    }

    //Méthode appelée lorsqu'on visite un noeud de type MethodInvocation
    @Override
    public boolean visit(MethodInvocation node) {
        if (currentMethod != null) {
            String calledMethod = node.getName().getIdentifier();
            callGraph.get(currentMethod).add(calledMethod);
        }
        return true;
    }

    public Map<String, Set<String>> getCallGraph() {
        return callGraph;
    }
}
