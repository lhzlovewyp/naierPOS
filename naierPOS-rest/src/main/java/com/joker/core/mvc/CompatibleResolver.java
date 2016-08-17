package com.joker.core.mvc;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
/**
 * Velocity模板兼容类
 *
 * @Author crell
 * @Date 2016/12/7 11:03
 */
public class CompatibleResolver implements ViewResolver{

	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		
		String suffix = viewResolverKey;
		if (viewName.endsWith(suffix)) {
			ViewResolver viewResolver = viewResolverValue;
			if (null != viewResolver) {
				return viewResolver.resolveViewName(viewName, locale);
			}
		}

		if (defaultViewResolver != null) {
			return defaultViewResolver.resolveViewName(viewName, locale);
		}
		// to allow for ViewResolver chaining
		return null;
	}
	
    private ViewResolver defaultViewResolver = null;
    
    private String viewResolverKey = null;
    
    private ViewResolver viewResolverValue = null;

	public ViewResolver getDefaultViewResolver() {
		return defaultViewResolver;
	}

	public void setDefaultViewResolver(ViewResolver defaultViewResolver) {
		this.defaultViewResolver = defaultViewResolver;
	}

	public String getViewResolverKey() {
		return viewResolverKey;
	}

	public void setViewResolverKey(String viewResolverKey) {
		this.viewResolverKey = viewResolverKey;
	}

	public ViewResolver getViewResolverValue() {
		return viewResolverValue;
	}

	public void setViewResolverValue(ViewResolver viewResolverValue) {
		this.viewResolverValue = viewResolverValue;
	}

}
