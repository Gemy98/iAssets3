/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

import com.iassets.common.util.LocalizationManager;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class MultiFilesResourceBundle
extends ResourceBundle {
    private static final ResourceBundle.Control CONTROL = new MultiFilesResourceBundleControl();
    private Properties properties;

    public MultiFilesResourceBundle(Locale locale) {
        this.setParent(ResourceBundle.getBundle("", locale, CONTROL));
    }

    protected MultiFilesResourceBundle(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected Object handleGetObject(String key) {
        return this.properties != null ? this.properties.get(key) : this.parent.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return this.properties != null ? (Enumeration<String>) this.properties.propertyNames() : this.parent.getKeys();
    }

    public static void main(String[] args) {
        MultiFilesResourceBundle rb = new MultiFilesResourceBundle(new Locale("en"));
        Enumeration<String> enumr = rb.getKeys();
        while (enumr.hasMoreElements()) {
            String literalKey = enumr.nextElement();
            System.out.println("key (" + literalKey + ") literal of ( en ) language is: " + rb.getString(literalKey));
        }
    }

    protected static class MultiFilesResourceBundleControl
    extends ResourceBundle.Control {
        protected MultiFilesResourceBundleControl() {
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
            return new MultiFilesResourceBundle(LocalizationManager.getLocaleLiterals(locale));
        }
    }
}
