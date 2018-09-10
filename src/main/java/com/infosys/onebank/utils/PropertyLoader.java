package com.infosys.onebank.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by chirag.ganatra on 7/17/2018.
 */
public class PropertyLoader {

    private static final String PROPERTY_FILE_PATH = "/onebank.properties";
    private static PropertyLoader propertyLoader;
    private static Properties accountProperties;

    static {
        accountProperties = new Properties();
        propertyLoader = new PropertyLoader();
        propertyLoader.loadProperties();
    }

    public static PropertyLoader getInstance() {
        return propertyLoader;
    }

    private PropertyLoader(){}

    private void loadProperties() {
        InputStream inputStream = this.getClass().getResourceAsStream(PROPERTY_FILE_PATH);

        try {
            accountProperties.load(inputStream);
        } catch(IOException iox) {
            throw new RuntimeException(iox.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                ;
            }

        }
    }

    public String getPropertyValue(String propertyKey) {
        return accountProperties.getProperty(propertyKey);
    }

    public static void main(String... args) {
        System.out.println(PropertyLoader.getInstance().getPropertyValue("username"));
    }

}
