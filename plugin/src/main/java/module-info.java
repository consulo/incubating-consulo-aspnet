/**
 * @author VISTALL
 * @since 17/01/2023
 */
module consulo.aspnet {
    requires consulo.application.content.api;
    requires consulo.configurable.api;
    requires consulo.execution.api;
    requires consulo.language.api;
    requires consulo.localize.api;
    requires consulo.module.api;
    requires consulo.process.api;
    requires consulo.project.api;
    requires consulo.ui.api;
    requires consulo.util.xml.serializer;

    requires consulo.aspnet.api;

    opens consulo.aspnet.module.run to consulo.util.xml.serializer;
}