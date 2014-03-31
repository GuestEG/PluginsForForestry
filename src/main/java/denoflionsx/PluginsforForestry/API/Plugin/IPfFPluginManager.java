package denoflionsx.PluginsforForestry.API.Plugin;

import java.util.ArrayList;

public interface IPfFPluginManager {
    
    public void registerPlugin(IPfFPlugin plugin);
    
    public ArrayList<IPfFPlugin> getPluginsList();
    
    public void runPluginLoadEvent(Object o);
    
}
