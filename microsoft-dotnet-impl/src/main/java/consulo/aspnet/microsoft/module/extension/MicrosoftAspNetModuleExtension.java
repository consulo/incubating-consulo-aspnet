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

package consulo.aspnet.microsoft.module.extension;

import consulo.aspnet.microsoft.bundle.IISExpressBundleType;
import consulo.aspnet.module.extension.AspNetModuleExtension;
import consulo.aspnet.module.extension.AspNetServerBundle;
import consulo.content.bundle.Sdk;
import consulo.content.bundle.SdkTable;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.content.layer.extension.ModuleExtensionBase;

import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public class MicrosoftAspNetModuleExtension extends ModuleExtensionBase<MicrosoftAspNetModuleExtension> implements AspNetModuleExtension<MicrosoftAspNetModuleExtension>
{
	public MicrosoftAspNetModuleExtension(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer)
	{
		super(id, moduleRootLayer);
	}

	@Nonnull
	@Override
	public List<AspNetServerBundle> getBundles(DotNetModuleExtension dotNetModuleExtension, Sdk t)
	{
		List<Sdk> sdksOfType = SdkTable.getInstance().getSdksOfType(IISExpressBundleType.getInstance());
		List<AspNetServerBundle> list = new ArrayList<>(sdksOfType.size());
		for(Sdk sdk : sdksOfType)
		{
			list.add(new IISExpressServerBundle(sdk));
		}
		return list;
	}
}
