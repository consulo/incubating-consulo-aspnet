/**
 * @author VISTALL
 * @since 16/01/2023
 */
module consulo.aspnet.api
{
	requires transitive consulo.ide.api;
	requires transitive consulo.dotnet.api;

	exports consulo.aspnet.icon;
	exports consulo.aspnet.localize;
	exports consulo.aspnet.module.extension;
}