/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.configuration.ConfigurationException
 *  org.apache.commons.configuration.PropertiesConfiguration
 */
package com.iassets.common.util;

import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class LocalizationManager {
    public static final List<String> SUPPORTED_LANGUAGES = Enums.SUPPORTED_LANGUAGES.getSupportedLanguagesCodes();
    public static final String DEFAULT_LANGUAGE_CODE = Enums.SUPPORTED_LANGUAGES.getDefaultLanguageCode();
    private static final String FILE_RESOURCE_BUNDLE_PACKAGE = "i18n";
    private static final String DB_VARIABLES_FILE_BASE_NAME = "db_variable_literals";
    private static final String[] BASE_NAMES = new String[]{"db_lookup_literals", "db_variable_literals", "jasperreport_literals", "java_literals", "js_literals", "jsp_literals", "servlet_literals"};
    private static Map<Locale, Properties> Literals = new HashMap<Locale, Properties>();

    public static String getLiteral(String key, String langCode) {
        if (!SUPPORTED_LANGUAGES.contains(langCode)) {
            throw new Error("Language code is not valid, or requested language not registered or supported");
        }
        Properties localeLiterals = Literals.get(new Locale(langCode));
        String literal = localeLiterals.getProperty(key.trim());
        if (literal == null || literal.isEmpty()) {
            return key;
        }
        return literal;
    }

    public static String getParameterizedLiteral(String key, String langCode, Object ... arguments) {
        String parameterizedLiteral = LocalizationManager.getLiteral(key, langCode);
        return MessageFormat.format(parameterizedLiteral, arguments);
    }

    private static void addOrUpdateLiteralInMemory(String key, String value, String langCode) {
        Properties localeLiterals = Literals.get(new Locale(langCode));
        if (localeLiterals == null) {
            throw new Error("File literals of (" + langCode + ") language are not supplied");
        }
        localeLiterals.put(key, value);
    }

    public static void addOrUpdateLiteral(String key, String value, String langCode) throws ConfigurationException {
        String tKey = key.trim();
        String localFileName = "i18n/db_variable_literals_" + langCode + ".properties";
        PropertiesConfiguration config = new PropertiesConfiguration(localFileName);
        config.setProperty(tKey, (Object)value);
        config.save();
        LocalizationManager.addOrUpdateLiteralInMemory(tKey, value, langCode);
    }

    public static Properties getLocaleLiterals(Locale locale) {
        return Literals.get(locale);
    }

    public static void main(String[] args) {
        String parameterizedString = "Hello {0}, you have {1} as credit";
        System.out.print(MessageFormat.format(parameterizedString, "Islam", 30));
    }

    static {
        ClassLoader loader = LocalizationManager.class.getClassLoader();
        for (String lang : SUPPORTED_LANGUAGES) {
            Locale langLocale = new Locale(lang);
            Properties localeLiterals = new Properties();
            String file = null;
            for (String baseName : BASE_NAMES) {
                file = "i18n/" + baseName + "_" + lang + ".properties";
                Properties oneFileLocaleLiterals = new Properties();
                try {
                    oneFileLocaleLiterals.load(loader.getResourceAsStream(file));
                    localeLiterals.putAll(oneFileLocaleLiterals);
                }
                catch (Exception e) {
                    Common.log(e);
                }
            }
            Literals.put(langLocale, localeLiterals);
        }
    }
}
