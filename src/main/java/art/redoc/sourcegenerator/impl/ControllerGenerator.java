package art.redoc.sourcegenerator.impl;

import art.redoc.sourcegenerator.AbstractGenerator;
import art.redoc.sourcegenerator.ContentsFilter;
import art.redoc.sourcegenerator.conf.GeneratorConfiguration;

import java.util.Map;


public class ControllerGenerator extends AbstractGenerator {

    // User-defined template path
    private static final String templatePath = "/codetemplate/controller.template";
    // Default template path
    private static final String defaultTemplatePath = "/codetemplate/controller-default.template";

    private ContentsFilter filter;

    private final String templateContents;

    public ControllerGenerator(final GeneratorConfiguration config) {
        super(config, "controller");
        this.initFilter();
        this.templateContents = this.getFileString(this.getTemplatePath(templatePath, defaultTemplatePath));
    }

    @Override
    public void generate() {
        final String value = this.filter.filter(this.templateContents);
        this.output(optimizeCode(value));
    }

    private void initFilter() {
        final Map<String, String> filterMap = super.getFilterMapWithIdType();
        filterMap.put("@Package@", this.getPackage("controller"));
        filterMap.put("@ModelPath@", this.getModelPath());
        filterMap.put("@Model@", this.getModelName());
        filterMap.put("@SeparateModel@", this.getSeparateModelName());
        filterMap.put("@model@", this.getModelNameWithHeadLow());
        filterMap.put("@DTOPath@", this.getClassPath("dto"));
        filterMap.put("@ConvertorPath@", this.getClassPath("convertor"));
        filterMap.put("@ServicePath@", this.getClassPath("service"));
        filterMap.put("@RepositoryPath@", this.getClassPath("repository"));
        this.filter = new ReplaceFilter(filterMap);
    }
}
