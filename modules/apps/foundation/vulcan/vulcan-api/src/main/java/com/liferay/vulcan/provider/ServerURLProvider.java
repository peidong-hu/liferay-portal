/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.vulcan.provider;

import aQute.bnd.annotation.ConsumerType;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides the final URL based on the HTTP servlet request. The implementation
 * depends on any proxy configuration, SSO, and so on.
 *
 * @author Javier Gamarra
 */
@ConsumerType
public interface ServerURLProvider {

	/**
	 * Returns the HTTP servlet request's original URL.
	 *
	 * @param  httpServletRequest the HTTP servlet request
	 * @return a string URL constructed from the HTTP servlet request
	 */
	public String getServerURL(HttpServletRequest httpServletRequest);

}