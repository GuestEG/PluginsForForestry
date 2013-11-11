package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup;

import denoflionsx.PluginsforForestry.API.PfFAPI;
import denoflionsx.PluginsforForestry.API.Plugin.IPfFPlugin;
import denoflionsx.PluginsforForestry.Config.ItemNameBridge;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.ModAPIWrappers.Forestry;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Fluid.FluidIconHandler;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Fluid.PfFFluid;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.BarrelRecipeManager;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemBarrel;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemHammer;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemRings;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemWoodenBucket;
import denoflionsx.PluginsforForestry.Utils.FermenterUtils;
import denoflionsx.denLib.Mod.denLibMod;
import denoflionsx.denLib.NewConfig.ConfigField;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class PluginLR implements IPfFPlugin {
    
    public static HashMap<String, ArrayList<String>> blackLists = new HashMap();
    //-----
    public static ItemWoodenBucket woodenBucket;
    public static ItemBarrel barrel;
    public static Item hammer;
    public static Item rings;
    public static HashMap<String, ItemStack> stacks = new HashMap();
    public static Fluid peat;
    public static FluidIconHandler iconHandler;
    //-----------------------------------------
    @ConfigField(category = "features")
    public static boolean barrel_enabled = true;
    @ConfigField(category = "features")
    public static boolean woodenBucket_enabled = true;
    
    @Override
    public void onPreLoad() {
        PfFAPI.sendBlacklistRequest("woodenBucket", FluidRegistry.LAVA.getName());
        PfFAPI.sendBlacklistRequest("barrel", FluidRegistry.LAVA.getName());
    }
    
    @Override
    public void onLoad() {
        this.registerFluids();
        if (woodenBucket_enabled) {
            woodenBucket = (ItemWoodenBucket) ItemNameBridge.registerItem("PfF:woodenBucket", ItemWoodenBucket.class);
            PfF.Proxy.registerRecipe(PluginLR.stacks.get("woodenBucket"), new Object[]{"LXL", "XLX", "XXX", Character.valueOf('L'), Block.wood});
            PfF.Proxy.registerRecipe(PluginLR.stacks.get("woodenBucket"), new Object[]{"XXX", "LXL", "XLX", Character.valueOf('L'), Block.wood});
            //--
            PfF.Proxy.registerOreRecipe(PluginLR.stacks.get("woodenBucket"), new Object[]{"LXL", "XLX", "XXX", Character.valueOf('L'), "logWood"});
            PfF.Proxy.registerOreRecipe(PluginLR.stacks.get("woodenBucket"), new Object[]{"XXX", "LXL", "XLX", Character.valueOf('L'), "logWood"});
        }
        if (barrel_enabled) {
            hammer = ItemNameBridge.registerItem("PfF:hammer", ItemHammer.class);
            rings = ItemNameBridge.registerItem("PfF:rings", ItemRings.class);
            barrel = (ItemBarrel) ItemNameBridge.registerItem("PfF:Barrel", ItemBarrel.class);
            BarrelRecipeManager barrelRecipes = new BarrelRecipeManager();
            barrelRecipes.barrel();
            barrelRecipes.hammer();
            barrelRecipes.rings();
        }
    }
    
    @Override
    public void onPostLoad() {
        denLibMod.fluids.register(woodenBucket);
        denLibMod.worldHandler.registerHandler(woodenBucket);
        denLibMod.fluids.register(barrel);
        denLibMod.worldHandler.registerHandler(barrel);
        FermenterUtils.registerFermenterBooster(FluidRegistry.getFluidStack(peat.getName(), 1), 1.5f);
    }
    
    public void registerFluids() {
        PfF.Proxy.print("Setting up fluids...");
        //------------------------------------------------------
        // Init
        //------------------------------------------------------
        peat = new PfFFluid("peat", 0xFF4D2C02);
        //-------------------------------------------------------
        // Localization
        //-------------------------------------------------------
        peat.setUnlocalizedName("liquid.pff.liquidpeat.name");
        //-------------------------------------------------------
        // Icons
        //-------------------------------------------------------
        iconHandler = new FluidIconHandler();
        //-------------------------------------------------------
        // Register
        //-------------------------------------------------------
        FluidRegistry.registerFluid(peat);
        Forestry.squeezer(5, new ItemStack[]{Forestry.items("peat")}, new FluidStack(peat, 250));
    }
}
