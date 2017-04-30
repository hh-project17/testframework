package ru.hh.school.testframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.testframework.db.Database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private static final Logger LOG = LoggerFactory.getLogger(Database.class);

    private static Properties config;

    private PropertyLoader(){}

    public static Properties load() {
        if (config != null) {
            return config;
        }
        config = new Properties();
        try (InputStream in = Database.class.getResourceAsStream("/app.properties")) {
            config.load(in);
        } catch (IOException e) {
            LOG.error("Can't read props");
        }
        return config;
    }

}
