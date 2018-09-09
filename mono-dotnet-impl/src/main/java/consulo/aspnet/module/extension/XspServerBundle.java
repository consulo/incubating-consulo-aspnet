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

package consulo.aspnet.module.extension;

import javax.annotation.Nonnull;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.text.StringUtil;
import consulo.mono.dotnet.MonoDotNetIcons;
import consulo.mono.dotnet.module.extension.MonoDotNetModuleExtension;

/**
 * @author VISTALL
 * @since 03.07.2015
 */
public class XspServerBundle extends AspNetServerBundle
{
	private Sdk mySdk;

	public XspServerBundle(@Nonnull Sdk sdk)
	{
		super(MonoDotNetIcons.Mono, "XSP");
		mySdk = sdk;
	}

	@Override
	public GeneralCommandLine createCommandLine(@Nonnull Module module) throws ExecutionException
	{
		GeneralCommandLine commandLine = MonoDotNetModuleExtension.createDefaultCommandLineImpl(mySdk, null, mySdk.getHomePath() + "/xsp4.exe");
		commandLine.addParameter("--nonstop");
		commandLine.addParameter("--root");
		commandLine.addParameter(StringUtil.QUOTER.fun(module.getModuleDirPath()));
		commandLine.addParameter("--verbose");
		commandLine.addParameter("--port");
		commandLine.addParameter(String.valueOf(8081));

		return commandLine;
	}
}
