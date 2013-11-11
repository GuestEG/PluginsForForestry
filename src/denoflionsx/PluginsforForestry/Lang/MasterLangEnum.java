package denoflionsx.PluginsforForestry.Lang;

import denoflionsx.PluginsforForestry.Core.PfF;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

public enum MasterLangEnum {
    
    lang_tag("en_US"),
    item_barrel("Barrel"),
    item_woodenbucket("Wooden Bucket"),
    item_hammer("Blacksmithing Hammer"),
    item_rings("Iron Rings"),
    //-----------------------
    fluid_liquidpeat("Liquid Peat"),
    //-----------------------------
    item_sugar_charcoal("Sugar Charcoal"),
    item_sugar_coke("Sugar Coke"),
    item_cactus_charcoal("Cactus Charcoal"),
    item_cactus_coke("Cactus Coke"),
    //-------Holiday---------------
    item_woodenbucket_halloween("Pumpkin Bucket"),
    item_woodenbucket_thanksgiving("Pilgrim Bucket"),
    item_woodenbucket_christmas("Christmas Bucket"),
    item_woodenbucket_valentine("Love Bucket"),
    item_woodenbucket_spring("Spring Bucket"),
    item_woodenbucket_merica("Freedom Bucket");
    
    private final String value;
    public static final File master = new File(PfF.core.configDir, "en_US.lang");
    public static final Properties prop = new Properties();
    
    private MasterLangEnum(String value) {
        this.value = value;
    }
    
    public static void save(Properties p, File f) {
        try {
            FileOutputStream out;
            out = new FileOutputStream(f);
            // The writer is needed for special characters.
            Writer w = new OutputStreamWriter(out, "UTF-8");
            p.store(w, "PfF 3.2 Lang File.");
            out.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static void load(Properties p, File f) {
        if (f.exists()) {
            try {
                FileInputStream in = new FileInputStream(f);
                // The reader is needed for special characters.
                Reader r = new InputStreamReader(in);
                p.load(r);
                in.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public static void update(Properties p) {
        for (MasterLangEnum o : MasterLangEnum.values()) {
            if (!p.containsKey(o.name())) {
                PfF.Proxy.print("Adding key " + o.name() + " to lang file.");
                p.setProperty(o.name(), o.value);
            }
        }
    }
    
}
