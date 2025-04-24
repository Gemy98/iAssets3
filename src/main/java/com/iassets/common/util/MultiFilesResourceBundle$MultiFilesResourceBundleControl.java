///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//import com.iassets.common.util.LocalizationManager;
//import com.iassets.common.util.MultiFilesResourceBundle;
//import java.io.IOException;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//protected static class MultiFilesResourceBundle.MultiFilesResourceBundleControl
//extends ResourceBundle.Control {
//    protected MultiFilesResourceBundle.MultiFilesResourceBundleControl() {
//    }
//
//    @Override
//    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
//        return new MultiFilesResourceBundle(LocalizationManager.getLocaleLiterals(locale));
//    }
//}
