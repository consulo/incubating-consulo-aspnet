/**
 * @author VISTALL
 * @since 16/01/2023
 */
module consulo.aspnet.impl.mono {
    requires consulo.application.content.api;
    requires consulo.disposer.api;
    requires consulo.localize.api;
    requires consulo.module.api;
    requires consulo.module.content.api;
    requires consulo.process.api;
    requires consulo.ui.api;
    requires consulo.util.lang;

    requires consulo.aspnet.api;
    requires consulo.dotnet.mono;
}