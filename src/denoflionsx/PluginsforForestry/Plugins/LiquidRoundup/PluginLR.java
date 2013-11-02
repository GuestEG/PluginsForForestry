package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup;

import denoflionsx.PluginsforForestry.API.PfFAPI;
import denoflionsx.PluginsforForestry.API.Plugin.IPfFPlugin;
import denoflionsx.PluginsforForestry.Config.PfFTuning;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Fluid.FluidIconHandler;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Fluid.PfFFluid;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.BarrelRecipeManager;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemBarrel;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemContainerBase;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemHammer;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemRings;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.ItemWoodenBucket;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items.WoodenBucketRecipeManager;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.client.IconConstants;
import denoflionsx.PluginsforForestry.Utils.FermenterUtils;
import denoflionsx.denLib.Mod.Handlers.DictionaryHandler;
import denoflionsx.denLib.Mod.Handlers.IDictionaryListener;
import denoflionsx.denLib.Mod.Handlers.NewWorldHandler.IDenLibWorldHandler;
import denoflionsx.denLib.Mod.denLibMod;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class PluginLR implements IPfFPlugin {
    
    public static HashMap<String, ArrayList<String>> blackLists = new HashMap();
    //-----
    public static ItemWoodenBucket woodenBucket;
    public static ItemBarrel barrel;
    public static Item hammer;
    public static Item rings;
    public static WoodenBucketRecipeManager woodenBucketRecipes;
    public static BarrelRecipeManager barrelRecipes;
    public static HashMap<String, ItemStack> stacks = new HashMap();
    public static Fluid veggie;
    public static Fluid peat;
    public static Fluid melon;
    public static FluidIconHandler iconHandler;
    
    @Override
    public void onPreLoad() {
        PfF.Proxy.registerRenderable(this);
        PfFAPI.sendBlacklistRequest("woodenBucket", FluidRegistry.LAVA.getName());
        PfFAPI.sendBlacklistRequest("barrel", FluidRegistry.LAVA.getName());
    }
    
    @Override
    public void onLoad() {
        this.registerFluids();
        if (PfFTuning.getInt(PfFTuning.Buckets.woodenbucket_ItemID) > 0) {
            woodenBucket = new ItemWoodenBucket(PfFTuning.getInt(PfFTuning.Buckets.woodenbucket_ItemID), 1000, "item.pff.woodenbucket.name", "woodenBucket", IconConstants.woodenBucket);
            woodenBucketRecipes = new WoodenBucketRecipeManager();
        }
        if (PfFTuning.getInt(PfFTuning.Items.hammer_ItemID) > 0) {
            hammer = new ItemHammer(PfFTuning.getInt(PfFTuning.Items.hammer_ItemID));
        }
        if (PfFTuning.getInt(PfFTuning.Items.rings_ItemID) > 0) {
            rings = new ItemRings(PfFTuning.getInt(PfFTuning.Items.rings_ItemID));
        }
        if (PfFTuning.getInt(PfFTuning.Items.barrel_ItemID) > 0) {
            barrel = new ItemBarrel(PfFTuning.getInt(PfFTuning.Items.barrel_ItemID), PfFTuning.getInt(PfFTuning.Barrel.barrel_capacity), "item.pff.barrel.name", "barrel", "barrel");
            barrelRecipes = new BarrelRecipeManager();
        }
    }
    
    @Override
    public void onPostLoad() {
        try {
            Class c = this.getClass();
            for (Field f : c.getDeclaredFields()) {
                Object o = f.get(null);
                if (o != null) {
                    if (o instanceof ItemContainerBase) {
                        denLibMod.DictionaryHandler.registerListener((IDictionaryListener) o, DictionaryHandler.channels.FLUID);
                        denLibMod.worldHandler.registerHandler((IDenLibWorldHandler) o);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        FermenterUtils.registerFermenterBooster(FluidRegistry.getFluidStack(peat.getName(), 1), 1.5f);
        FermenterUtils.registerFermenterBooster(FluidRegistry.getFluidStack(veggie.getName(), 1), 1.5f);
    }
    
    public void registerFluids() {
        PfF.Proxy.print("Setting up fluids...");
        //------------------------------------------------------
        // Init
        //------------------------------------------------------
        peat = new PfFFluid("peat", 0xFF4D2C02);
        //melon = new PfFFluid("melon");
        veggie = new PfFFluid("vegetable", 0xFFFF2131);
        //-------------------------------------------------------
        // Localization
        //-------------------------------------------------------
        peat.setUnlocalizedName("liquid.pff.liquidpeat.name");
        //melon.setUnlocalizedName("liquid.pff.melonjuice.name");
        veggie.setUnlocalizedName("liquid.pff.veggiejuice.name");
        //-------------------------------------------------------
        // Icons
        //-------------------------------------------------------
        iconHandler = new FluidIconHandler();
        //-------------------------------------------------------
        // Register
        //-------------------------------------------------------
        FluidRegistry.registerFluid(peat);
        //FluidRegistry.registerFluid(melon);
        FluidRegistry.registerFluid(veggie);
        //-------------------------------------------------------
    }
}
