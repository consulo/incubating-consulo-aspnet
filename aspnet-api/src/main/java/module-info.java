/**
 * @author VISTALL
 * @since 16/01/2023
 */
module consulo.aspnet.api
{
	requires consulo.application.content.api;
	requires transitive consulo.module.api;
	requires consulo.process.api;
	requires consulo.ui.api;

	requires transitive consulo.dotnet.api;

	exports consulo.aspnet.icon;
	exports consulo.aspnet.localize;
	exports consulo.aspnet.module.extension;
}