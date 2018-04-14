package compiler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaKeywords extends Application
{

    public static final String[] KEYWORDS = new String[]{"abstract", "assert",
            "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native", "new", "package",
            "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw", "throws", "transient",
            "try", "void", "volatile", "while"};

    public static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) +
            ")\\b";
    public static final String PAREN_PATTERN = "\\(|\\)";
    public static final String BRACE_PATTERN = "\\{|\\}";
    public static final String BRACKET_PATTERN = "\\[|\\]";
    public static final String SEMICOLON_PATTERN = "\\;";
    public static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    public static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    public static final Pattern PATTERN = Pattern.compile("(?<KEYWORD>" +
            KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" +
            BRACE_PATTERN + ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|" +
            "(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN
            + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")");

    public static final String sampleCode = String.join("\n", new String[]{"package "
            + "com.example;", "", "import java.util.*;", "", "public class Foo extends " +
            "" + "Bar implements Baz {", "", "    /*", "     * multi-line comment", "  " +
            "   " + "*/", "    public static void main(String[] args) {", "        // "
            + "single-line comment", "        for(String arg: args) {", "            " +
            "if" + "(arg.length() != 0)", "                System.out.println(arg);", "" +
            "       " + "     else", "                System.err.println(\"Warning: " +
            "empty string as" + " argument\");", "        }", "    }", "", "}"});


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.richChanges().subscribe(change -> {
            codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
        });
        codeArea.replaceText(0, 0, sampleCode);

        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setAccelerator(KeyCombination.valueOf("SHORTCUT+Z"));
        undoItem.setOnAction(e -> codeArea.undo());
        MenuBar menuBar = new MenuBar(new Menu("Edit", null, undoItem));
        menuBar.setUseSystemMenuBar(false);

        VBox root = new VBox(menuBar, codeArea);
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(JavaKeywords.class.getResource("application.css")
                .toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Java Keywords");
        primaryStage.show();
    }

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {

        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = matcher.group("KEYWORD") != null ? "keyword" : matcher
                    .group("PAREN") != null ? "paren" : matcher.group("BRACE") != null
                    ? "brace" : matcher.group("BRACKET") != null ? "bracket" : matcher
                    .group("SEMICOLON") != null ? "semicolon" : matcher.group("STRING")
                    != null ? "string" : matcher.group("COMMENT") != null ? "comment" :
                    null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher
                    .start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}