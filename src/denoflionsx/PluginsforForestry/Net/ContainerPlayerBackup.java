package denoflionsx.PluginsforForestry.Net;

import com.google.common.collect.BiMap;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemContainerBase;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.item.ItemStack;

public class ContainerPlayerBackup {
    
    public static ContainerPlayerBackup bucket;
    public static ContainerPlayerBackup barrel;
    //-------------------------------------
    protected BiMap<Integer, String> fluids;
    protected HashMap<Integer, ItemStack> filledMap;
    protected ArrayList<ItemStack> stacks;
    public HashMap<Integer, Integer> colorMapMeta;
    
    public static ContainerPlayerBackup doBackup(ItemContainerBase container) {
        return new ContainerPlayerBackup(container.getFluids(), container.getFilledMap(), container.getStacks(), container.getColorMapMeta());
    }
    
    public static void restore(ItemContainerBase container, ContainerPlayerBackup backup) {
        container.setFluids(backup.getFluids());
        container.setFilledMap(backup.getFilledMap());
        container.setStacks(backup.getStacks());
        container.setColorMapMeta(backup.getColorMapMeta());
    }
    
    public ContainerPlayerBackup(BiMap<Integer, String> fluids, HashMap<Integer, ItemStack> filledMap, ArrayList<ItemStack> stacks, HashMap<Integer, Integer> colorMapMeta) {
        this.fluids = fluids;
        this.filledMap = filledMap;
        this.stacks = stacks;
        this.colorMapMeta = colorMapMeta;
    }
    
    public BiMap<Integer, String> getFluids() {
        return fluids;
    }
    
    public HashMap<Integer, ItemStack> getFilledMap() {
        return filledMap;
    }
    
    public ArrayList<ItemStack> getStacks() {
        return stacks;
    }
    
    public HashMap<Integer, Integer> getColorMapMeta() {
        return colorMapMeta;
    }
}
