package denoflionsx.PluginsforForestry.Proxy;

import java.io.File;
import net.minecraft.item.ItemStack;

public interface IPfFProxy {
    
    public void print(String msg);
    
    public void warning(String msg);
    
    public void severe(String msg);
    
    public void registerRenderable(Object o);
    
    public void registerClientSide();
    
    public void registerRecipe(ItemStack i, Object[] o);
    
    public void registerShapelessRecipe(ItemStack i, ItemStack[] stacks);
    
    public void registerAllRecipes();
    
    public void sendMessageToPlayer(String msg);
    
    public String translate(String key);
    
    public String getLang();
    
    public void setTabs();
    
    public void findInternalAddons(File source);
    
}
