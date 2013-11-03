package denoflionsx.PluginsforForestry.Plugins.MFR;

import cpw.mods.fml.common.Loader;
import denoflionsx.PluginsforForestry.API.Plugin.IPfFPlugin;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.ModAPIWrappers.Forestry;
import denoflionsx.denLib.Mod.denLibMod;
import denoflionsx.denLib.NewConfig.ConfigField;
import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.Property;

public class PluginMFR implements IPfFPlugin {

    private ItemStack portaSpawner;
    @ConfigField(category = "MFR.features")
    public static boolean MFRToForestryFertilizer = true;
    @ConfigField(category = "MFR.features")
    public static boolean recipeReplacement_MFR_PortableSpawner = false;

    @Override
    public void onPreLoad() {
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onPostLoad() {
        if (!Loader.isModLoaded("MineFactoryReloaded")) {
            return;
        }
        recipeReplacement();
        apatite();
    }

    private void apatite() {
        if (MFRToForestryFertilizer) {
            try {
                ItemStack fert;
                fert = new ItemStack((Item) Class.forName("powercrystals.minefactoryreloaded.MineFactoryReloadedCore").getField("fertilizerItem").get(null));
                ItemStack ap = Forestry.items("fertilizerCompound");
                if (ap != null) {
                    PfF.Proxy.registerShapelessRecipe(ap, new ItemStack[]{fert});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void recipeReplacement() {
        try {
            Property p = (Property) Class.forName("powercrystals.minefactoryreloaded.setup.MFRConfig").getField("vanillaRecipes").get(null);
            if (!p.getBoolean(true)) {
                PfF.Proxy.print("MFR Recipe mode is not set to vanilla. Force disabling all recipe replacements.");
                recipeReplacement_MFR_PortableSpawner = false;
                return;
            }
            if (!recipeReplacement_MFR_PortableSpawner) {
                return;
            }
            portaSpawner = new ItemStack((Item) Class.forName("powercrystals.minefactoryreloaded.MineFactoryReloadedCore").getField("portaSpawnerItem").get(null));
            if (portaSpawner == null) {
                PfF.Proxy.print("Failed to reflect MFR!");
                return;
            }
            Object instance = null;
            for (Object o : CraftingManager.getInstance().getRecipeList()) {
                if (o instanceof ShapedRecipes) {
                    // This should work even if the obf changes.
                    for (Field f : o.getClass().getDeclaredFields()) {
                        Object o1 = f.get(o);
                        // Only one ItemStack field in the target class.
                        if (o1 instanceof ItemStack) {
                            ItemStack output = (ItemStack) o1;
                            if (output.isItemEqual(portaSpawner)) {
                                PfF.Proxy.print("Found MFR portaspawner recipe. Replacing with easier version.");
                                instance = o;
                                break;
                            }
                        }
                    }
                }
            }
            if (instance != null) {
                CraftingManager.getInstance().getRecipeList().remove(instance);
                PfF.Proxy.registerRecipe(portaSpawner, new Object[]{"gGg", "DED", "gGg", Character.valueOf('g'), Item.ingotGold, Character.valueOf('G'), Block.glass, Character.valueOf('D'), Item.diamond, Character.valueOf('E'), Item.enderPearl});
            }
        } catch (Exception ex) {
            denLibMod.error(this.getClass().getName(), "recipeReplacement", ex);
        }
    }
}
