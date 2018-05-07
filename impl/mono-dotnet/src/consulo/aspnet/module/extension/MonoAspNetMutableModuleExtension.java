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

import javax.annotation.Nullable;
import javax.swing.JComponent;

import javax.annotation.Nonnull;

import consulo.annotations.RequiredDispatchThread;
import consulo.roots.ModuleRootLayer;

/**
 * @author VISTALL
 * @since 03.07.2015
 */
public class MonoAspNetMutableModuleExtension extends MonoAspNetModuleExtension implements AspNetMutableModuleExtension<MonoAspNetModuleExtension>
{
	public MonoAspNetMutableModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@RequiredDispatchThread
	@Nullable
	@Override
	public JComponent createConfigurablePanel(@Nonnull Runnable updateOnCheck)
	{
		return null;
	}

	@Override
	public void setEnabled(boolean val)
	{
		myIsEnabled = val;
	}

	@Override
	public boolean isModified(@Nonnull MonoAspNetModuleExtension originalExtension)
	{
		return myIsEnabled != originalExtension.isEnabled();
	}
}
