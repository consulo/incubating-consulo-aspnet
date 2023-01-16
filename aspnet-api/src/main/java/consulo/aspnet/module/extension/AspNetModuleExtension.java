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

import consulo.content.bundle.Sdk;
import consulo.dotnet.module.extension.DotNetModuleExtension;
import consulo.module.extension.ModuleExtension;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public interface AspNetModuleExtension<T extends ModuleExtension<T>> extends ModuleExtension<T>
{
	@Nonnull
	List<AspNetServerBundle> getBundles(DotNetModuleExtension dotNetModuleExtension, Sdk sdk);
}
