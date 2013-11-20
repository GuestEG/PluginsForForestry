package denoflionsx.PluginsforForestry.Plugins.Forestry;

import denoflionsx.PluginsforForestry.API.Plugin.IPfFPlugin;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.ModAPIWrappers.Forestry;
import denoflionsx.denLib.Lib.denLib;
import denoflionsx.denLib.Mod.Handlers.NewDictHandler.IDenLibDictionaryHandler;
import denoflionsx.denLib.Mod.Handlers.NewFluidHandler.IDenLibFluidHandler;
import denoflionsx.denLib.Mod.denLibMod;
import denoflionsx.denLib.NewConfig.ConfigField;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class PluginForestry implements IPfFPlugin, IDenLibDictionaryHandler, IDenLibFluidHandler {
    
    @ConfigField(category = "forestry.squeezer", comment = "Injects anything with crop* into the squeezer to make juice.")
    public static boolean squeezer_InjectAllCrops = true;
    @ConfigField(category = "forestry.squeezer")
    public static int squeezer_time = 5;
    @ConfigField(category = "forestry.squeezer")
    public static int squeezer_cropJuiceAmount = 200;
    private FluidStack juice;
    
    @Override
    public void onPreLoad() {
    }
    
    @Override
    public void onLoad() {
        // Register vanilla crops.
        PfF.Proxy.print("Injecting vanilla crops into ore dictionary...");
        OreDictionary.registerOre("cropPotato", Item.potato);
        OreDictionary.registerOre("cropCarrot", Item.carrot);
        OreDictionary.registerOre("cropPumpkin", Block.pumpkin);
        OreDictionary.registerOre("cropMelon", Item.melon);
    }
    
    @Override
    public void onPostLoad() {
        denLibMod.fluids.register(this);
        denLibMod.dict.register(this);
    }
    
    @Override
    public String lookingFor() {
        return "crop*";
    }
    
    @Override
    public String lookingForFluid() {
        return "juice";
    }
    
    @Override
    public void onEvent(FluidStack fluid) {
        juice = denLib.LiquidStackUtils.getNewStackCapacity(fluid, squeezer_cropJuiceAmount);
    }
    
    @Override
    public void onEvent(ItemStack item) {
        boolean b = Forestry.doesHaveExistingRecipe(item);
        PfF.Proxy.print(item.getDisplayName() + " = " + String.valueOf(b));
        if (!b) {
            //PfF.Proxy.print("Trying to register " + item.getDisplayName() + " with squeezer...");
            Forestry.squeezer(squeezer_time, new ItemStack[]{item}, juice);
        }
    }
}
