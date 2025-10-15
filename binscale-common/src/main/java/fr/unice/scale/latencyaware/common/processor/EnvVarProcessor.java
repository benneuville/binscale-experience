package fr.unice.scale.latencyaware.common.processor;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import fr.unice.scale.latencyaware.common.doc.EnvVar;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SupportedAnnotationTypes("fr.unice.scale.latencyaware.common.doc.EnvVar")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class EnvVarProcessor extends AbstractProcessor {

    private static StringBuilder markdown = new StringBuilder();

    private final String ENV_SECTION_HEADER = "## 🔧 Environment Variables\n\n";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;

        markdown.append(ENV_SECTION_HEADER);
        markdown.append("*This part is auto generated.*\n\n");
        markdown.append("| Name | Description | Default value |\n");
        markdown.append("|-----|--------------|-------------------|\n");

        for (Element element : roundEnv.getElementsAnnotatedWith(EnvVar.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                VariableElement field = (VariableElement) element;
                EnvVar annotation = field.getAnnotation(EnvVar.class);

                String name = field.getSimpleName().toString();
                String description = annotation.description();
                String defaultValue = annotation.defaultValue();

                if (defaultValue.isEmpty()) {
                    defaultValue = extractDefaultFromInitializer(field);
                }


                markdown.append("| `").append(name).append("` | ")
                        .append(description).append(" | ")
                        .append(defaultValue == null ? "*(non défini)*" : defaultValue)
                        .append(" |\n");
            }
        }
        try {
            Path projectRoot = Paths.get("").toAbsolutePath();
            Path readme = projectRoot.resolve("README.md");

            String existing = Files.exists(readme) ? Files.readString(readme) : "";

            Pattern sectionPattern = Pattern.compile(
                    "(?s)## 🔧 Environment Variables\\s*.*?(?=\\n## |\\Z)"
            );

            Matcher sectionMatcher = sectionPattern.matcher(existing);
            String updated;

            if (sectionMatcher.find()) {
                updated = sectionMatcher.replaceFirst(
                        Matcher.quoteReplacement(markdown.toString())
                );
            } else {
                if (!existing.endsWith("\n")) existing += "\n";
                updated = existing + "\n" + markdown;
            }

            Files.writeString(readme, updated);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    private String extractDefaultFromInitializer(VariableElement field) {
        try {
            Trees trees = Trees.instance(processingEnv);
            TreePath path = trees.getPath(field);
            if (path == null) return null;

            VariableTree variableTree = (VariableTree) path.getLeaf();
            ExpressionTree initializer = variableTree.getInitializer();
            if (initializer == null) return null;

            String expr = initializer.toString().trim();

            if (!expr.startsWith("EnvUtils.envOrDefault")) {
                return null;
            }

            Pattern pattern = Pattern.compile(
                    "EnvUtils\\.envOrDefault\\s*\\(\\s*\"[^\"]+\"\\s*,\\s*(.+)\\s*\\)"
            );
            Matcher matcher = pattern.matcher(expr);

            if (matcher.find()) {
                return matcher.group(1);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
