package part2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class ASTAnalyzerGUI extends JFrame {

    // Déclaration des composants de l'interface graphique
    private JTextField projectPathField;
    private JButton analyzeButton;
    private JTabbedPane tabbedPane;
    private JLabel totalClassesLabel, totalLinesLabel, totalMethodsLabel, totalPackagesLabel;
    private JLabel avgMethodsPerClassLabel, avgLinesPerMethodLabel, avgAttributesPerClassLabel;
    private JTable classesTable, methodsTable;
    private JTextArea parametersTextArea;

    // Définition des couleurs et polices utilisées
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 245);
    private static final Color HEADER_COLOR = new Color(60, 60, 60);
    private static final Color LABEL_COLOR = new Color(50, 50, 50);
    private static final Color VALUE_COLOR = new Color(0, 100, 0);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 14);

    // Déclaration du composant pour le graphe d'appel
    private JTextArea callGraphTextArea;

    // Constructeur de la classe
    public ASTAnalyzerGUI() {
        initComponents();
    }

    // Initialisation des composants de l'interface graphique
    private void initComponents() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Analyseur AST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Création du panneau supérieur
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Création du champ de texte pour le chemin du projet
        projectPathField = new JTextField();
        projectPathField.setFont(VALUE_FONT);

        // Création des boutons "Parcourir" et "Analyser"
        JButton browseButton = new JButton("Parcourir");
        browseButton.setFont(LABEL_FONT);
        browseButton.addActionListener(e -> browseForProject());
        analyzeButton = new JButton("Analyser");
        analyzeButton.setFont(LABEL_FONT);
        analyzeButton.setBackground(new Color(100, 180, 100));
        analyzeButton.setForeground(Color.WHITE);

        topPanel.add(projectPathField, BorderLayout.CENTER);
        topPanel.add(browseButton, BorderLayout.WEST);
        topPanel.add(analyzeButton, BorderLayout.EAST);

        // Création du panneau à onglets
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(HEADER_FONT);
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.addTab("Statistiques générales", createGeneralStatsPanel());
        tabbedPane.addTab("Classes", createClassesPanel());
        tabbedPane.addTab("Méthodes", createMethodsPanel());
        tabbedPane.addTab("Paramètres", createParametersPanel());
        tabbedPane.addTab("Graphe d'appel", createCallGraphPanel());


        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        analyzeButton.addActionListener(e -> analyzeProject());
        
    }

    // Création du panneau pour le graphe d'appel
    private JPanel createCallGraphPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        callGraphTextArea = new JTextArea();
        callGraphTextArea.setEditable(false);
        callGraphTextArea.setFont(VALUE_FONT);
        callGraphTextArea.setBackground(Color.WHITE);
        callGraphTextArea.setForeground(LABEL_COLOR);
        panel.add(new JScrollPane(callGraphTextArea), BorderLayout.CENTER);
        return panel;
    }


    // Création du panneau pour les statistiques générales
    private JPanel createGeneralStatsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Ajout des labels et valeurs pour les statistiques générales
        addLabelAndValue(panel, gbc, "Nombre total de classes :", totalClassesLabel = new JLabel("0"));
        addLabelAndValue(panel, gbc, "Nombre total de lignes de code :", totalLinesLabel = new JLabel("0"));
        addLabelAndValue(panel, gbc, "Nombre total de méthodes :", totalMethodsLabel = new JLabel("0"));
        addLabelAndValue(panel, gbc, "Nombre total de packages :", totalPackagesLabel = new JLabel("0"));
        addLabelAndValue(panel, gbc, "Nombre moyen de méthodes par classe :", avgMethodsPerClassLabel = new JLabel("0"));
        addLabelAndValue(panel, gbc, "Nombre moyen de lignes de code par méthode :", avgLinesPerMethodLabel = new JLabel("0"));
        addLabelAndValue(panel, gbc, "Nombre moyen d'attributs par classe :", avgAttributesPerClassLabel = new JLabel("0"));

        return panel;
    }

    // Ajout d'un label et d'une valeur dans un panneau
    private void addLabelAndValue(JPanel panel, GridBagConstraints gbc, String labelText, JLabel valueLabel) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        panel.add(label, gbc);
        gbc.gridx++;
        valueLabel.setFont(VALUE_FONT);
        valueLabel.setForeground(VALUE_COLOR);
        panel.add(valueLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
    }

    // Création du panneau pour les classes
    private JPanel createClassesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] columnNames = {"Nom de la classe", "Nombre de méthodes", "Nombre d'attributs"};
        classesTable = new JTable(new DefaultTableModel(columnNames, 0));
        classesTable.setFont(VALUE_FONT);
        classesTable.setRowHeight(25);
        JTableHeader header = classesTable.getTableHeader();
        header.setFont(HEADER_FONT);
        header.setBackground(HEADER_COLOR);
        header.setForeground(Color.WHITE);
        panel.add(new JScrollPane(classesTable), BorderLayout.CENTER);
        return panel;
    }

    // Création du panneau pour les méthodes
    private JPanel createMethodsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] columnNames = {"Nom de la classe", "Nom de la méthode", "Nombre de lignes"};
        methodsTable = new JTable(new DefaultTableModel(columnNames, 0));
        methodsTable.setFont(VALUE_FONT);
        methodsTable.setRowHeight(25);
        JTableHeader header = methodsTable.getTableHeader();
        header.setFont(HEADER_FONT);
        header.setBackground(HEADER_COLOR);
        header.setForeground(Color.WHITE);
        panel.add(new JScrollPane(methodsTable), BorderLayout.CENTER);
        return panel;
    }

    // Création du panneau pour les paramètres
    private JPanel createParametersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        parametersTextArea = new JTextArea();
        parametersTextArea.setEditable(false);
        parametersTextArea.setFont(VALUE_FONT);
        parametersTextArea.setBackground(Color.WHITE);
        parametersTextArea.setForeground(LABEL_COLOR);
        panel.add(new JScrollPane(parametersTextArea), BorderLayout.CENTER);
        return panel;
    }

    // Ouvre une boîte de dialogue pour choisir un projet
    private void browseForProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            projectPathField.setText(selectedFile.getAbsolutePath());
        }
    }

    // Analyse le projet situé dans le chemin spécifié
    private void analyzeProject() {
        String projectPath = projectPathField.getText();
        ASTAnalyzer analyzer = new ASTAnalyzer();
        analyzer.analyze(projectPath, 10); // 10 est la valeur par défaut pour methodThreshold
        analyzer.printResults(); // Affiche les résultats dans la console
        updateResults(analyzer);
    }

    // Met à jour les résultats dans l'interface graphique
    private void updateResults(ASTAnalyzer analyzer) {
        // Mise à jour des statistiques générales
        totalClassesLabel.setText(String.valueOf(analyzer.getTotalClasses()));
        totalLinesLabel.setText(String.valueOf(analyzer.getTotalLines()));
        totalMethodsLabel.setText(String.valueOf(analyzer.getTotalMethods()));
        totalPackagesLabel.setText(String.valueOf(analyzer.getTotalPackages()));
        avgMethodsPerClassLabel.setText(String.format("%.2f", analyzer.getAvgMethodsPerClass()));
        avgLinesPerMethodLabel.setText(String.format("%.2f", analyzer.getAvgLinesPerMethod()));
        avgAttributesPerClassLabel.setText(String.format("%.2f", analyzer.getAvgAttributesPerClass()));

        // Mise à jour du tableau des classes
        DefaultTableModel classModel = (DefaultTableModel) classesTable.getModel();
        classModel.setRowCount(0);
        for (OOMetricsVisitor.ClassInfo classInfo : analyzer.getAllClassInfos()) {
            classModel.addRow(new Object[]{classInfo.name, classInfo.methodCount, classInfo.attributeCount});
        }

        // Mise à jour du tableau des méthodes
        DefaultTableModel methodModel = (DefaultTableModel) methodsTable.getModel();
        methodModel.setRowCount(0);
        for (OOMetricsVisitor.ClassInfo classInfo : analyzer.getAllClassInfos()) {
            for (OOMetricsVisitor.MethodInfo methodInfo : classInfo.methodInfos) {
                methodModel.addRow(new Object[]{classInfo.name, methodInfo.name, methodInfo.lineCount});
            }
        }

        // Mise à jour des informations sur les paramètres
        parametersTextArea.setText("Nombre maximal de paramètres : " + analyzer.getMaxParameters() + "\n\n");
        parametersTextArea.append("Méthodes avec le plus grand nombre de paramètres :\n");
        for (OOMetricsVisitor.ClassInfo classInfo : analyzer.getAllClassInfos()) {
            for (OOMetricsVisitor.MethodInfo methodInfo : classInfo.methodInfos) {
                if (methodInfo.parameterCount == analyzer.getMaxParameters()) {
                    parametersTextArea.append(classInfo.name + "." + methodInfo.name + "\n");
                }
            }
        }
        
     // Mise à jour du graphe d'appel
        Map<String, Set<String>> callGraph = analyzer.getCallGraph();
        StringBuilder sb = new StringBuilder();
        sb.append("Graphe d'appel :\n\n");
        for (Map.Entry<String, Set<String>> entry : callGraph.entrySet()) {
        	if (!entry.getValue().isEmpty()) {
            sb.append(entry.getKey()).append(" appelle : ").append(entry.getValue()).append("\n");
        	}
        }
        callGraphTextArea.setText(sb.toString());
    }

    // Méthode principale
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ASTAnalyzerGUI().setVisible(true));
    }
}
