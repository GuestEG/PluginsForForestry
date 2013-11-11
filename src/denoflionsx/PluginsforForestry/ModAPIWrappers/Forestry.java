package denoflionsx.PluginsforForestry.ModAPIWrappers;

import denoflionsx.PluginsforForestry.Utils.FermenterUtils;
import denoflionsx.PluginsforForestry.Utils.FermenterUtils.FermenterRecipe;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.recipes.RecipeManagers;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Forestry {

    private static final void template() {
        try {
        } catch (NoClassDefFoundError ex) {
        }
    }

    public static ItemStack items(String tag) {
        try {
            return ItemInterface.getItem(tag);
        } catch (NoClassDefFoundError ex) {
        }
        return null;
    }

    public static void squeezer(int time, ItemStack[] grid, FluidStack f) {
        try {
            if (RecipeManagers.squeezerManager != null) {
                RecipeManagers.squeezerManager.addRecipe(time, grid, f);
            }
        } catch (Throwable ex) {
            //ex.printStackTrace();
        }
    }

    public static boolean doesHaveExistingRecipe(ItemStack crop) {
        try {
            Class Squeezer = Class.forName("forestry.factory.gadgets.MachineSqueezer");
            Class RecipeManager = null;
            for (Class c : Squeezer.getDeclaredClasses()) {
                if (Pattern.compile("$", Pattern.LITERAL).split(c.getName())[1].equals("RecipeManager")) {
                    RecipeManager = c;
                }
            }
            Method findMatch = RecipeManager.getDeclaredMethod("findMatchingRecipe", new Class[]{ItemStack[].class});
            Object o = findMatch.invoke(null, new Object[]{new ItemStack[]{crop}});
            return o != null;
        } catch (Throwable t) {
            //t.printStackTrace();
        }
        return true;
    }

    public static ItemStack block(String name) {
        try {
            return BlockInterface.getBlock(name);
        } catch (NoClassDefFoundError ex) {
        }
        return null;
    }

    public static void fermenter(FermenterRecipe z, float bonus, FluidStack liquid, String target) {
        try {
            if (RecipeManagers.fermenterManager != null) {
                RecipeManagers.fermenterManager.addRecipe(z.getFermentable(), z.getAmount(), 1.5f, FluidRegistry.getFluidStack(target, 1), liquid);
            }
        } catch (NoClassDefFoundError ex) {
        }
    }

    public static void fermentable(ItemStack i, int amount) {
        try {
            for (String s : FermenterUtils.forestryFermenterBoosters) {
                FluidStack in = FluidRegistry.getFluidStack(s, 1);
                if (in != null) {
                    RecipeManagers.fermenterManager.addRecipe(i, amount, 1.5f, in, FermenterUtils.biomass);
                }
            }
        } catch (NoClassDefFoundError ex) {
        }
    }

    public static void carpenter(ItemStack output, Object[] grid, FluidStack liquid) {
        try {
            RecipeManagers.carpenterManager.addRecipe(5, liquid, null, output, grid);
        } catch (NoClassDefFoundError ex) {
        }
    }
}
