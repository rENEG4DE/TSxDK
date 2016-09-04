package com.tsxbot.tsxdk.utility;

import com.tsxbot.tsxdk.base.SystemDescriptors;
import com.tsxbot.tsxdk.base.TSX;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 15. of Mai
 * 2016
 * 20:44
 */
class ConfigAccess {
    private static final String DEFAULT_PROPERTIES = "default.properties";
    private static final String TSXDK_PROPERTIES = "tsxdk.properties";
    private static final String CLIENT_PROPERTIES = "client.properties";
    @SuppressWarnings("CanBeFinal")
    private static Configuration cfg;

    static {
        try {
            final PropertiesConfiguration config = new PropertiesConfiguration(DEFAULT_PROPERTIES);
            config.copy(new PropertiesConfiguration(TSXDK_PROPERTIES));
            config.copy(new PropertiesConfiguration(CLIENT_PROPERTIES));
            cfg = config;
        } catch (ConfigurationException e) {
            org.slf4j.LoggerFactory.getLogger(TSX.createLoggerDescriptor(SystemDescriptors.UTILITY, ConfigAccess.class))
                    .error("Unable to read configuration", e);
        }
    }

    public static Configuration get() {
        return cfg;
    }
}
