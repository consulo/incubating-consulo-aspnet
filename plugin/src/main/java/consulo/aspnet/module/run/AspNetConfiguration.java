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

import consulo.aspnet.module.extension.AspNetModuleExtension;
import consulo.aspnet.module.extension.AspNetServerBundle;
import consulo.content.bundle.Sdk;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.execution.configuration.*;
import consulo.execution.configuration.ui.SettingsEditor;
import consulo.execution.configuration.ui.SettingsEditorGroup;
import consulo.execution.executor.Executor;
import consulo.execution.runner.ExecutionEnvironment;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.process.ExecutionException;
import consulo.process.ProcessHandler;
import consulo.process.local.ProcessHandlerFactory;
import consulo.util.xml.serializer.InvalidDataException;
import consulo.util.xml.serializer.WriteExternalException;
import consulo.util.xml.serializer.XmlSerializer;
import org.jdom.Element;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public class AspNetConfiguration extends ModuleBasedConfiguration<RunConfigurationModule> {
    public AspNetConfiguration(String name, RunConfigurationModule configurationModule, ConfigurationFactory factory) {
        super(name, configurationModule, factory);
    }

    public AspNetConfiguration(RunConfigurationModule configurationModule, ConfigurationFactory factory) {
        super(configurationModule, factory);
    }

    @Override
    public Collection<Module> getValidModules() {
        List<Module> list = new ArrayList<Module>();
        for (Module module : ModuleManager.getInstance(getProject()).getModules()) {
            if (ModuleUtilCore.getExtension(module, AspNetModuleExtension.class) != null) {
                list.add(module);
            }
        }
        return list;
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);

        XmlSerializer.deserializeInto(this, element);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);

        XmlSerializer.serializeInto(this, element);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        SettingsEditorGroup group = new SettingsEditorGroup();
        group.addEditor("General", new AspNetConfigurationEditor(getProject()));
        return group;
    }

    @Nullable
    @Override
    public RunProfileState getState(@Nonnull Executor executor, @Nonnull final ExecutionEnvironment executionEnvironment) throws ExecutionException {
        final Module module = getConfigurationModule().getModule();
        if (module == null) {
            throw new ExecutionException("Module is null");
        }

        DotNetModuleExtension dotNetModuleExtension = ModuleUtilCore.getExtension(module, DotNetModuleExtension.class);
        if (dotNetModuleExtension == null) {
            throw new ExecutionException("Module don't have .NET extension");
        }

        Sdk sdk = dotNetModuleExtension.getSdk();
        if (sdk == null) {
            throw new ExecutionException("Module don't have .NET SDK");
        }

        final AspNetModuleExtension<?> extension = ModuleUtilCore.getExtension(module, AspNetModuleExtension.class);

        if (extension == null) {
            throw new ExecutionException("Module don't have ASP .NET extension");
        }

        final List<AspNetServerBundle> bundles = extension.getBundles(dotNetModuleExtension, sdk);
        if (bundles.isEmpty()) {
            throw new ExecutionException("No servers");
        }

        return new CommandLineState(executionEnvironment) {
            @Nonnull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                AspNetServerBundle aspNetServerBundle = bundles.get(0);
                return ProcessHandlerFactory.getInstance().createProcessHandler(aspNetServerBundle.createCommandLine(module));
            }
        };
    }
}
