/*
 * Copyright 2013-2015 must-be.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.aspnet.microsoft.bundle;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.Application;
import consulo.aspnet.icon.AspNetIconGroup;
import consulo.content.bundle.SdkType;
import consulo.platform.Platform;
import consulo.platform.PlatformOperatingSystem;
import consulo.platform.os.WindowsOperatingSystem;
import consulo.ui.image.Image;
import consulo.util.io.FileUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author VISTALL
 * @since 01.07.2015
 */
@ExtensionImpl
public class IISExpressBundleType extends SdkType {
    @Nonnull
    public static IISExpressBundleType getInstance() {
        return Application.get().getExtensionPoint(IISExpressBundleType.class).findExtensionOrFail(IISExpressBundleType.class);
    }

    @Nonnull
    public static String getExecutable(@Nonnull String homePath) {
        return homePath + "/iisexpress.exe";
    }

    public IISExpressBundleType() {
        super("IIS_EXPRESS_BUNDLE");
    }

    @Override
    public boolean canCreatePredefinedSdks() {
        return true;
    }

    @Nonnull
    @Override
    public Collection<String> suggestHomePaths() {
        if (Platform.current().os().isWindows()) {
            Collection<String> list = new ArrayDeque<String>(2);
            String programFiles = System.getenv("ProgramFiles");
            if (programFiles != null) {
                list.add(programFiles + "/IIS Express");
            }
            programFiles = System.getenv("ProgramFiles(x86)");
            if (programFiles != null) {
                list.add(programFiles + "/IIS Express");
            }
            return list;
        }
        return super.suggestHomePaths();
    }

    @Override
    public boolean isValidSdkHome(String path) {
        return Platform.current().os().isWindows() && new File(getExecutable(path)).exists();
    }

    @Nullable
    @Override
    public String getVersionString(String sdkHome) {
        try {
            PlatformOperatingSystem os = Platform.current().os();
            if (os instanceof WindowsOperatingSystem win) {
                return win.getWindowsFileVersion(Path.of(getExecutable(sdkHome)));
            }
        }
        catch (Exception ignored) {
        }
        return "0.0.0.0";
    }

    @Override
    public String suggestSdkName(String currentSdkName, String sdkHome) {
        String suffix = null;
        String env = System.getenv("ProgramFiles(x86)");
        if (env != null) {
            if (FileUtil.isAncestor(env, sdkHome, false)) {
                suffix = " 32 bit";
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(getPresentableName()).append(" ").append(getVersionString(sdkHome));
        if (suffix != null) {
            builder.append(suffix);
        }
        return builder.toString();
    }

    @Nonnull
    @Override
    public String getPresentableName() {
        return "IIS Express";
    }

    @Nullable
    @Override
    public Image getIcon() {
        return AspNetIconGroup.microsoft();
    }
}
