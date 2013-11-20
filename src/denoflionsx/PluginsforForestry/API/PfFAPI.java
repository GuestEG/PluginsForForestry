package denoflionsx.PluginsforForestry.API;
import denoflionsx.PluginsforForestry.API.Interfaces.IPfFContainer;
import denoflionsx.PluginsforForestry.API.Plugin.IPfFPluginManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.creativetab.CreativeTabs;

public class PfFAPI {

    //----------------------------
    public static Object instance;
    //----------------------------
    public static IPfFPluginManager plugins;
    //----------------------------
    public static CreativeTabs tab;

    // This only takes effect if done before init!
    public static void sendBlacklistRequest(String containerTag, String fluidTag) {
        try {
            Class c = Class.forName("denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR");
            Field f = c.getDeclaredField("blackLists");
            f.setAccessible(true);
            HashMap<String, ArrayList<String>> list = (HashMap<String, ArrayList<String>>) f.get(null);
            if (list.get(containerTag) == null) {
                list.put(containerTag, new ArrayList());
            }
            list.get(containerTag).add(fluidTag);
            f.set(null, list);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // This only works after init stage! For development only. Don't depend on it for actual production code.
    public static String[] getContainerTags() {
        ArrayList<String> tags = new ArrayList();
        try {
            Class c = Class.forName("denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR");
            for (Field f : c.getDeclaredFields()) {
                Object o = f.get(null);
                if (o != null) {
                    if (o instanceof IPfFContainer) {
                        IPfFContainer p = (IPfFContainer) o;
                        String tag = p.getContainerTag();
                        tags.add(tag);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return tags.toArray(new String[tags.size()]);
    }
}
