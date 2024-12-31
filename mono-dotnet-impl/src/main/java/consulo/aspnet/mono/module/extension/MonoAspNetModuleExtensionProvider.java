package consulo.aspnet.mono.module.extension;

import consulo.annotation.component.ExtensionImpl;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.ModuleExtension;
import consulo.module.extension.MutableModuleExtension;
import consulo.mono.dotnet.icon.MonoDotNetIconGroup;
import consulo.ui.image.Image;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 16/01/2023
 */
@ExtensionImpl
public class MonoAspNetModuleExtensionProvider implements ModuleExtensionProvider<MonoAspNetModuleExtension> {
    @Nonnull
    @Override
    public String getId() {
        return "mono-aspnet";
    }

    @Nullable
    @Override
    public String getParentId() {
        return "mono-dotnet";
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.of("ASP .NET");
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return MonoDotNetIconGroup.mono();
    }

    @Nonnull
    @Override
    public ModuleExtension<MonoAspNetModuleExtension> createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new MonoAspNetModuleExtension(getId(), moduleRootLayer);
    }

    @Nonnull
    @Override
    public MutableModuleExtension<MonoAspNetModuleExtension> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new MonoAspNetMutableModuleExtension(getId(), moduleRootLayer);
    }
}
