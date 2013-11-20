package denoflionsx.PluginsforForestry.Lang;

import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.util.HashMap;

public class PfFTranslator {

    public static PfFTranslator instance;
    private static final String defaultLang = "en_US";
    private static final HashMap<String, LangObject> trans = new HashMap();

    public static void createInstance() {
        instance = new PfFTranslator();
        PfF.Proxy.print("STARTING LANG SETUP");
        for (String s : denLib.FileUtils.getFileNamesInJar(PfF.source, ".lang")) {
            try {
                String[] split = s.split("/");
                File outFile = new File(PfF.core.configDir, split[split.length - 1]);
                if (!outFile.exists()) {
                    denLib.FileUtils.extractFileFromJar(PfF.source, s, outFile);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        for (File f : PfF.core.configDir.listFiles()) {
            if (!f.isDirectory()) {
                if (f.getAbsolutePath().contains(".lang")) {
                    LangObject o = new LangObject(f);
                    o.loadTranslations();
                    PfF.Proxy.print("Loaded language file " + f.getName());
                    // Update Existing file.
                    MasterLangEnum.load(o.getProp(), f);
                    MasterLangEnum.update(o.getProp());
                    MasterLangEnum.save(o.getProp(), f);
                    trans.put(o.getTag(), o);
                }
            }
        }
    }

    public String translateKey(String key) {
        if (trans.containsKey(PfF.Proxy.getLang())) {
            return trans.get(PfF.Proxy.getLang()).translate(key);
        } else {
            return translateKey(key, defaultLang);
        }
    }

    public void overrideKey(String key, String overrideKey) {
        for (LangObject o : trans.values()) {
            o.overrideValue(key, translateKey(overrideKey));
        }
    }

    public String translateKey(String key, String lang) {
        return trans.get(lang).translate(key);
    }
}
