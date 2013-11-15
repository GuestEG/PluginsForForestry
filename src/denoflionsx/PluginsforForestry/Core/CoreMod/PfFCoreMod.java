package denoflionsx.PluginsforForestry.Core.CoreMod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.io.File;
import java.util.Map;

public class PfFCoreMod implements IFMLLoadingPlugin {

    public static boolean runtimeDeobfEnabled;

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    public static void print(String msg) {
        System.out.println("[PfFCore]: " + msg);
    }

    @Override
    public void injectData(Map<String, Object> data) {
        File del = new File("./mods/denLib/ColorOverlayValues.db");
        if (del.exists()) {
            del.deleteOnExit();
        }
        runtimeDeobfEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }
}
