package denoflionsx.PluginsforForestry.Lang;

import java.io.File;
import java.util.Properties;

public class LangObject {

    private final Properties prop;
    private final File input;

    public LangObject(File input) {
        this.input = input;
        prop = new Properties();
    }

    public LangObject(Properties prop) {
        this.prop = prop;
        this.input = null;
    }

    public void loadTranslations() {
        MasterLangEnum.load(prop, input);
    }

    public String translate(String key) {
        return prop.getProperty(key);
    }

    public String getTag() {
        return prop.getProperty("lang_tag");
    }

    public Properties getProp() {
        return prop;
    }

    public void overrideValue(String key, String newValue) {
        prop.setProperty(key, newValue);
    }

}
