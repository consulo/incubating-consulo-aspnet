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

package consulo.aspnet.module.run;

import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.aspnet.icon.AspNetIconGroup;
import consulo.aspnet.localize.AspNetLocalize;
import consulo.aspnet.module.extension.AspNetModuleExtension;
import consulo.execution.configuration.RunConfiguration;
import consulo.execution.configuration.RunConfigurationModule;
import consulo.execution.configuration.SimpleConfigurationType;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.module.extension.ModuleExtensionHelper;
import consulo.project.Project;

import jakarta.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
@ExtensionImpl
public class AspNetConfigurationType extends SimpleConfigurationType
{
	public AspNetConfigurationType()
	{
		super("#AspNetConfigurationType", AspNetLocalize.aspNetConfigurationName(), AspNetIconGroup.microsoft());
	}

	@Override
	public RunConfiguration createTemplateConfiguration(Project project)
	{
		return new AspNetConfiguration("Unnamed", new RunConfigurationModule(project), this);
	}

	@Override
	@RequiredReadAction
	public void onNewConfigurationCreated(@Nonnull RunConfiguration configuration)
	{
		AspNetConfiguration dotNetConfiguration = (AspNetConfiguration) configuration;

		for(Module module : ModuleManager.getInstance(configuration.getProject()).getModules())
		{
			AspNetModuleExtension extension = ModuleUtilCore.getExtension(module, AspNetModuleExtension.class);
			if(extension != null)
			{
				dotNetConfiguration.setName(module.getName());
				dotNetConfiguration.setModule(module);
				break;
			}
		}
	}

	@Override
	public boolean isApplicable(@Nonnull Project project)
	{
		return ModuleExtensionHelper.getInstance(project).hasModuleExtension(AspNetModuleExtension.class);
	}
}
