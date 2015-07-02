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

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.module.Module;

/**
 * @author VISTALL
 * @since 02.07.2015
 */
public abstract class AspNetServerBundle
{
	private Icon myIcon;
	private String myName;

	public AspNetServerBundle(Icon icon, String name)
	{
		myIcon = icon;
		myName = name;
	}

	public abstract GeneralCommandLine createCommandLine(@NotNull Module module);

	public Icon getIcon()
	{
		return myIcon;
	}

	public String getName()
	{
		return myName;
	}
}