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

package org.mustbe.consulo.aspnet.module.run;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mustbe.consulo.aspnet.module.extension.AspNetModuleExtension;
import org.mustbe.consulo.aspnet.module.extension.AspNetServerBundle;
import org.mustbe.consulo.dotnet.module.extension.DotNetModuleExtension;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.xmlb.XmlSerializer;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public class AspNetConfiguration extends ModuleBasedConfiguration<RunConfigurationModule>
{
	public AspNetConfiguration(String name, RunConfigurationModule configurationModule, ConfigurationFactory factory)
	{
		super(name, configurationModule, factory);
	}

	public AspNetConfiguration(RunConfigurationModule configurationModule, ConfigurationFactory factory)
	{
		super(configurationModule, factory);
	}

	@Override
	public Collection<Module> getValidModules()
	{
		List<Module> list = new ArrayList<Module>();
		for(Module module : ModuleManager.getInstance(getProject()).getModules())
		{
			if(ModuleUtilCore.getExtension(module, AspNetModuleExtension.class) != null)
			{
				list.add(module);
			}
		}
		return list;
	}

	@Override
	public void readExternal(Element element) throws InvalidDataException
	{
		super.readExternal(element);
		readModule(element);

		XmlSerializer.deserializeInto(this, element);
	}

	@Override
	public void writeExternal(Element element) throws WriteExternalException
	{
		super.writeExternal(element);
		writeModule(element);

		XmlSerializer.serializeInto(this, element);
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public SettingsEditor<? extends RunConfiguration> getConfigurationEditor()
	{
		SettingsEditorGroup group = new SettingsEditorGroup();
		group.addEditor("General", new AspNetConfigurationEditor(getProject()));
		return group;
	}

	@Nullable
	@Override
	public RunProfileState getState(@NotNull Executor executor, @NotNull final ExecutionEnvironment executionEnvironment) throws ExecutionException
	{
		final Module module = getConfigurationModule().getModule();
		if(module == null)
		{
			throw new ExecutionException("Module is null");
		}

		DotNetModuleExtension dotNetModuleExtension = ModuleUtilCore.getExtension(module, DotNetModuleExtension.class);
		if(dotNetModuleExtension == null)
		{
			throw new ExecutionException("Module don't have .NET extension");
		}

		Sdk sdk = dotNetModuleExtension.getSdk();
		if(sdk == null)
		{
			throw new ExecutionException("Module don't have .NET SDK");
		}

		final AspNetModuleExtension<?> extension = ModuleUtilCore.getExtension(module, AspNetModuleExtension.class);

		if(extension == null)
		{
			throw new ExecutionException("Module don't have ASP .NET extension");
		}

		final List<AspNetServerBundle> bundles = extension.getBundles(dotNetModuleExtension, sdk);
		if(bundles.isEmpty())
		{
			throw new ExecutionException("No servers");
		}

		return new CommandLineState(executionEnvironment)
		{
			@NotNull
			@Override
			protected ProcessHandler startProcess() throws ExecutionException
			{
				AspNetServerBundle aspNetServerBundle = bundles.get(0);
				return new OSProcessHandler(aspNetServerBundle.createCommandLine(module));
			}
		};
	}
}
