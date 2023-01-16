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
import consulo.configurable.ConfigurationException;
import consulo.execution.configuration.ui.SettingsEditor;
import consulo.language.util.ModuleUtilCore;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.project.Project;
import consulo.ui.ComboBox;
import consulo.ui.Component;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.layout.VerticalLayout;
import consulo.ui.util.LabeledBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public class AspNetConfigurationEditor extends SettingsEditor<AspNetConfiguration> {
    private ComboBox<Module> myModuleComboBox;
    private Project myProject;

    public AspNetConfigurationEditor(Project project) {
        myProject = project;
    }

    @Override
    protected void resetEditorFrom(AspNetConfiguration runConfiguration) {
        myModuleComboBox.setValue(runConfiguration.getConfigurationModule().getModule());
    }

    @Override
    protected void applyEditorTo(AspNetConfiguration runConfiguration) throws ConfigurationException {
        runConfiguration.getConfigurationModule().setModule((Module) myModuleComboBox.getValue());
    }

    @RequiredUIAccess
    @Nullable
    @Override
    protected Component createUIComponent() {
        List<Module> modules = new ArrayList<>();
        for (Module module : ModuleManager.getInstance(myProject).getModules()) {
            if (ModuleUtilCore.getExtension(module, AspNetModuleExtension.class) != null) {
                modules.add(module);
            }
        }

        myModuleComboBox = ComboBox.create(modules);
        myModuleComboBox.setRender((presentation, i, module) -> {
            if (module == null) {
                presentation.append(LocalizeValue.localizeTODO("<none>"));
            }
            else {
                presentation.withIcon(ModuleManager.getInstance(module.getProject()).getModuleIcon(module));
                presentation.append(module.getName());
            }
        });

        VerticalLayout root = VerticalLayout.create();
        root.add(LabeledBuilder.filled(LocalizeValue.localizeTODO("Module"), myModuleComboBox));

        return root;
    }
}
