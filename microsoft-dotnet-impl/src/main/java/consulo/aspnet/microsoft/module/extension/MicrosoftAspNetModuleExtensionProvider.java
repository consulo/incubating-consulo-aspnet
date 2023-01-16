package consulo.aspnet.microsoft.module.extension;

import consulo.annotation.component.ExtensionImpl;
import consulo.dotnet.microsoft.icon.MicrosoftDotNetIconGroup;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.ui.image.Image;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author VISTALL
 * @since 16/01/2023
 */
@ExtensionImpl
public class MicrosoftAspNetModuleExtensionProvider implements ModuleExtensionProvider<MicrosoftAspNetModuleExtension> {
    @Nonnull
    @Override
    public String getId() {
        return "microsoft-aspnet";
    }

    @Nullable
    @Override
    public String getParentId() {
        return "microsoft-dotnet";
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.of("ASP .NET");
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return MicrosoftDotNetIconGroup.dotnet();
    }

    @Nonnull
    @Override
    public ModuleExtension<MicrosoftAspNetModuleExtension> createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new MicrosoftAspNetModuleExtension(getId(), moduleRootLayer);
    }

    @Nonnull
    @Override
    public MutableModuleExtension<MicrosoftAspNetModuleExtension> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new MicrosoftAspNetMutableModuleExtension(getId(), moduleRootLayer);
    }
}
