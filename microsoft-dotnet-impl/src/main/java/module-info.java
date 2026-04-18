/**
 * @author VISTALL
 * @since 16/01/2023
 */
module consulo.aspnet.impl.microsoft {
    requires consulo.application.api;
    requires consulo.application.content.api;
    requires consulo.disposer.api;
    requires consulo.localize.api;
    requires consulo.module.api;
    requires consulo.module.content.api;
    requires consulo.platform.api;
    requires consulo.process.api;
    requires consulo.ui.api;
    requires consulo.util.io;

    requires consulo.aspnet.api;
    requires consulo.dotnet.microsoft;
}