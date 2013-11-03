package denoflionsx.PluginsforForestry.Proxy;

import java.io.File;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public interface IPfFProxy {
    
    public File getMappingFile(String tag);
    
    public void print(String msg);
    
    public void warning(String msg);
    
    public void severe(String msg);
    
    public void registerRecipe(ItemStack i, Object[] o);
    
    public void registerShapelessRecipe(ItemStack i, ItemStack[] stacks);
    
    public void registerOreRecipe(ItemStack i, Object[] o);
    
    public void sendMessageToPlayer(String msg);
    
    public String translate(String key);
    
    public String getLang();
    
    public void setTabs();
    
    public void findInternalAddons(File source);
    
    public String getIconCode(Icon icon);
    
}
