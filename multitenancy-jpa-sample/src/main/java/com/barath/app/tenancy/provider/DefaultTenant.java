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
 * A default implementation of a {@link Tenant}. In addition to the tenant identity, it provides a link to arbitrary
 * data for the tenant.
 * 
 * @author Clint Morgan (Tasktop Technologies Inc.)
 */
public class DefaultTenant implements Tenant {

	private static final long serialVersionUID = 1L;
	
	private String tenantIdentifier;
	
	private static final String DEFAULT_TENANT_ID="testdb";
	
	public DefaultTenant() {
		this.tenantIdentifier=DEFAULT_TENANT_ID;
	}

	@Override
	public String getIdentity() {
		
		return getTenantIdentifier();
	}

	public String getTenantIdentifier() {
		return tenantIdentifier;
	}

	public void setTenantIdentifier(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}
	
	
	
	

}
