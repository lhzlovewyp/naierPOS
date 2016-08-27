package com.joker.core.constant;

import java.util.HashMap;
import java.util.Map;

public class BaseResources {

    /**
     * 域名
     */
    public static final String DOMAIN = "domain";

    private static Map<String, String> domains = new HashMap<String, String>();

    public static void addDomain(String domainName, String domianUrl) {
        domains.put(domainName, domianUrl);
    }

    public static Map<String, String> getDomains(){
        return domains;
    }
}
