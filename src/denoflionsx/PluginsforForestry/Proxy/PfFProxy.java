package denoflionsx.PluginsforForestry.Proxy;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import denoflionsx.PluginsforForestry.API.PfFAPI;
import denoflionsx.PluginsforForestry.API.Plugin.IPfFPlugin;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Lang.PfFTranslator;
import denoflionsx.PluginsforForestry.Tab.PfFTab;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class PfFProxy implements IPfFProxy {
    
    @Override
    public File getMappingFile(String tag) {
        return new File(PfF.core.mappingsDir.getAbsolutePath() + "/".concat(tag) + ".bin");
    }
    
    @Override
    public void print(String msg) {
        FMLLog.info("[PfF]" + ": " + msg);
    }
    
    @Override
    public String getIconCode(Icon icon) {
        return String.valueOf(0);
    }
    
    @Override
    public void registerRecipe(ItemStack i, Object[] o) {
        GameRegistry.addRecipe(i, o);
    }
    
    @Override
    public void warning(String msg) {
        FMLLog.warning("[PfF]: " + msg);
    }
    
    @Override
    public void severe(String msg) {
        FMLLog.severe("[PfF]: " + msg);
    }
    
    @Override
    public void sendMessageToPlayer(String msg) {
    }
    
    @Override
    public String translate(String key) {
        return PfFTranslator.instance.translateKey(key);
    }
    
    @Override
    public String getLang() {
        return "en_US";
    }
    
    @Override
    public void setTabs() {
        PfFAPI.tab = new PfFTab();
    }
    
    @Override
    public void findInternalAddons(File source) {
        this.print("Loading plugins...");
        ArrayList<Object> plugins = denLib.FileUtils.getClassesInJar(source, IPfFPlugin.class);
        for (Object o : plugins) {
            PfFAPI.plugins.registerPlugin((IPfFPlugin) o);
        }
        this.print("Done. " + plugins.size() + " plugins loaded.");
    }
    
    @Override
    public void registerShapelessRecipe(ItemStack i, ItemStack[] stacks) {
        GameRegistry.addShapelessRecipe(i, (Object[]) stacks);
    }
    
    @Override
    public void registerOreRecipe(ItemStack i, Object[] o) {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(i, o));
    }
}
