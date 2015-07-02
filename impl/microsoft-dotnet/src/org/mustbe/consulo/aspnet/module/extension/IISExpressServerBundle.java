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

package org.mustbe.consulo.aspnet.module.extension;

import org.jetbrains.annotations.NotNull;
import org.mustbe.consulo.aspnet.bundle.IISExpressBundleType;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.util.io.FileUtil;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public class IISExpressServerBundle extends AspNetServerBundle
{
	private Sdk mySdk;

	public IISExpressServerBundle(@NotNull Sdk sdk)
	{
		super(((SdkType)sdk.getSdkType()).getIcon(), sdk.getSdkType().getName());
		mySdk = sdk;
	}

	@Override
	public GeneralCommandLine createCommandLine(@NotNull Module module)
	{
		GeneralCommandLine commandLine = new GeneralCommandLine();
		commandLine.setExePath(IISExpressBundleType.getExecutable(mySdk.getHomePath()));
		commandLine.setWorkDirectory(mySdk.getHomePath());
		commandLine.addParameter("/path:" + FileUtil.toSystemDependentName(module.getModuleDirPath()));
		commandLine.addParameter("/port:8081");
		return commandLine;
	}
}
