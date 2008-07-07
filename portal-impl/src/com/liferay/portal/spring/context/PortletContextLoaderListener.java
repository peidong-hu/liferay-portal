/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.spring.context;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.portlet.PortletClassLoaderThreadLocal;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <a href="PortletContextLoaderListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletContextLoaderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		ServletContext servletContext = event.getServletContext();

		ApplicationContext applicationContext =
			WebApplicationContextUtils.getWebApplicationContext(servletContext);

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.BeanLocatorUtil", true,
				PortletClassLoaderThreadLocal.getClassLoader());

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass,
				new Object[] {new BeanLocatorImpl(applicationContext)});
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String[] beanDefinitionNames =
			applicationContext.getBeanDefinitionNames();

		for (String beanDefinitionName : beanDefinitionNames) {
			applicationContext.getBean(beanDefinitionName);
		}
	}

	private static Log _log =
		LogFactory.getLog(PortletContextLoaderListener.class);

}