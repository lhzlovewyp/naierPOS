package com.joker.core.listener;

import com.joker.core.constant.BaseResources;
import com.joker.core.util.PropertiesUtil;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.util.Properties;

public class ApplicationListener extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Properties properties = PropertiesUtil.loadProperties("domainConfig.properties");
        BaseResources.addDomain(BaseResources.DOMAIN,properties.getProperty("domain"));
        super.contextInitialized(event);
    }
}
