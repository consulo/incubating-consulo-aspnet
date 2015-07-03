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

import java.util.ArrayList;
import java.util.List;

import org.consulo.module.extension.impl.ModuleExtensionImpl;
import org.jetbrains.annotations.NotNull;
import org.mustbe.consulo.aspnet.bundle.IISExpressBundleType;
import org.mustbe.consulo.dotnet.module.extension.DotNetModuleExtension;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTable;
import com.intellij.openapi.roots.ModuleRootLayer;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public class MicrosoftAspNetModuleExtension extends ModuleExtensionImpl<MicrosoftAspNetModuleExtension> implements
		AspNetModuleExtension<MicrosoftAspNetModuleExtension>

{
	public MicrosoftAspNetModuleExtension(@NotNull String id, @NotNull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@NotNull
	@Override
	public List<AspNetServerBundle> getBundles(DotNetModuleExtension dotNetModuleExtension, Sdk t)
	{
		List<Sdk> sdksOfType = SdkTable.getInstance().getSdksOfType(IISExpressBundleType.getInstance());
		List<AspNetServerBundle> list = new ArrayList<AspNetServerBundle>(sdksOfType.size());
		for(Sdk sdk : sdksOfType)
		{
			list.add(new IISExpressServerBundle(sdk));
		}
		return list;
	}
}
