package art.redoc.sourcegenerator.impl;

import art.redoc.sourcegenerator.AbstractGenerator;
import art.redoc.sourcegenerator.ContentsFilter;
import art.redoc.sourcegenerator.conf.GeneratorConfiguration;

import java.util.List;
import java.util.Map;

import static art.redoc.sourcegenerator.utils.CodeGenerateUtils.contents2value;
import static art.redoc.sourcegenerator.utils.CodeGenerateUtils.removeUnusedImport;
import static art.redoc.sourcegenerator.utils.CodeGenerateUtils.value2contents;

public class ServiceImplGenerator extends AbstractGenerator {

    // 模板路径
    private static final String templatePath = "/codetemplate/service-impl.template";
    private static final String defaultTemplatePath = "/codetemplate/service-impl-default.template";

    private ContentsFilter filter;

    private final String templateContents;

    public ServiceImplGenerator(final GeneratorConfiguration config) {
        super(config, "serviceImpl");
        this.initFilter();
        this.templateContents = this.getFileString(this.getTemplatePath(templatePath, defaultTemplatePath));
    }

    @Override
    public void generate() {
        final String value = this.filter.filter(this.templateContents);
        final List<String> content = value2contents(value);
        removeUnusedImport(content);
        this.output(contents2value(content));
    }

    private void initFilter() {
        final String servicePackage = this.getPackage("service") + ".impl";

        final Map<String, String> filterMap = super.getFilterMapWithIdType();
        filterMap.put("@Package@", servicePackage);
        filterMap.put("@ServicePath@", this.getClassPath("service"));
        filterMap.put("@ModelPath@", this.getModelPath());
        filterMap.put("@Model@", this.getModelName());
        filterMap.put("@model@", this.getModelNameWithHeadLow());
        filterMap.put("@RepositoryPath@", this.getClassPath("repository"));
        this.filter = new ReplaceFilter(filterMap);
    }
}
