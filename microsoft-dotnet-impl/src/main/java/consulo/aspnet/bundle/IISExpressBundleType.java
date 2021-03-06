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

package consulo.aspnet.bundle;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.swing.Icon;

import javax.annotation.Nullable;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import consulo.ui.image.Image;

/**
 * @author VISTALL
 * @since 01.07.2015
 */
public class IISExpressBundleType extends SdkType
{
	@Nonnull
	public static IISExpressBundleType getInstance()
	{
		return EP_NAME.findExtension(IISExpressBundleType.class);
	}

	@Nonnull
	public static String getExecutable(@Nonnull String homePath)
	{
		return homePath + "/iisexpress.exe";
	}

	public IISExpressBundleType()
	{
		super("IIS_EXPRESS_BUNDLE");
	}

	@Override
	public boolean canCreatePredefinedSdks()
	{
		return true;
	}

	@Nonnull
	@Override
	public Collection<String> suggestHomePaths()
	{
		if(SystemInfo.isWindows)
		{
			Collection<String> list = new ArrayDeque<String>(2);
			String programFiles = System.getenv("ProgramFiles");
			if(programFiles != null)
			{
				list.add(programFiles + "/IIS Express");
			}
			programFiles = System.getenv("ProgramFiles(x86)");
			if(programFiles != null)
			{
				list.add(programFiles + "/IIS Express");
			}
			return list;
		}
		return super.suggestHomePaths();
	}

	@Override
	public boolean isValidSdkHome(String path)
	{
		return SystemInfo.isWindows && new File(getExecutable(path)).exists();
	}

	@Nullable
	@Override
	public String getVersionString(String sdkHome)
	{
		try
		{
			return WindowsVersionHelper.getVersion(getExecutable(sdkHome));
		}
		catch(Exception e)
		{
			return "0.0.0.0";
		}
	}

	@Override
	public String suggestSdkName(String currentSdkName, String sdkHome)
	{
		String suffix = null;
		String env = System.getenv("ProgramFiles(x86)");
		if(env != null)
		{
			if(FileUtil.isAncestor(env, sdkHome, false))
			{
				suffix = " 32 bit";
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append(getPresentableName()).append(" ").append(getVersionString(sdkHome));
		if(suffix != null)
		{
			builder.append(suffix);
		}
		return builder.toString();
	}

	@Nonnull
	@Override
	public String getPresentableName()
	{
		return "IIS Express";
	}

	@Nullable
	@Override
	public Image getIcon()
	{
		return AllIcons.Providers.Microsoft;
	}
}
