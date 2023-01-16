/**
 * @author VISTALL
 * @since 17/01/2023
 */
module consulo.aspnet {
    requires consulo.aspnet.api;

    opens consulo.aspnet.module.run to consulo.util.xml.serializer;
}