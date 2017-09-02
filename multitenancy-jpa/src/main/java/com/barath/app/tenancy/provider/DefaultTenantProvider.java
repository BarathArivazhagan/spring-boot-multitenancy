/*******************************************************************************
 * Copyright (c) 2010, 2011 SpringSource, a division of VMware 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Tasktop Technologies Inc. - initial API and implementation
 *******************************************************************************/

package com.barath.app.tenancy.provider;

import com.barath.app.tenancy.core.Tenant;

/**
 * Basic implementation of tenant provider which provides a {@link DefaultTenant} with the given identity. This is
 * useful when only the identity of the tenant is needed to be stored.
 * 
 * @author clint.morgan 
 * 
 */
public class DefaultTenantProvider implements TenantProvider {

	@Override
	public Tenant findTenant(String identity) {
		DefaultTenant result = new DefaultTenant();
		result.setTenantIdentifier(identity);
		return result;
	}


}
