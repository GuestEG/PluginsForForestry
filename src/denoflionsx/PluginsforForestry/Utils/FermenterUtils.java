package denoflionsx.PluginsforForestry.Utils;

import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.ModAPIWrappers.Forestry;
import denoflionsx.denLib.Mod.Handlers.NewFluidHandler.IDenLibFluidHandler;
import java.util.ArrayList;
import java.util.regex.Pattern;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FermenterUtils implements IDenLibFluidHandler {

    public static FluidStack biomass;
    public static final String[] forestryFermenterBoosters = new String[]{"juice", "honey", "water"};

    @Override
    public String lookingForFluid() {
        return "biomass";
    }

    @Override
    public void onEvent(FluidStack fluid) {
        biomass = fluid;
    }

    public static void registerFermentable(ItemStack item, int amount) {
        Forestry.fermentable(item, amount);
    }

    public static void registerFermenterBooster(FluidStack liquid, float bonus) {
        try {
            ArrayList<FermenterRecipe> r = new ArrayList();
            Class c = Class.forName("forestry.factory.gadgets.MachineFermenter");
            for (Class c2 : c.getDeclaredClasses()) {
                if (Pattern.compile("$", Pattern.LITERAL).split(c2.getName())[1].equals("RecipeManager")) {
                    ArrayList<Object> recipes = (ArrayList<Object>) c2.getField("recipes").get(null);
                    for (Class c3 : c.getDeclaredClasses()) {
                        if (Pattern.compile("$", Pattern.LITERAL).split(c3.getName())[1].equals("Recipe")) {
                            for (Object o : recipes) {
                                ItemStack resource = (ItemStack) c3.getField("resource").get(c3.cast(o));
                                int value = Integer.valueOf(String.valueOf(c3.getField("fermentationValue").get(c3.cast(o))));
                                FluidStack l = (FluidStack) c3.getField("liquid").get(c3.cast(o));
                                if (l == null) {
                                    continue;
                                }
                                FluidStack output = (FluidStack) c3.getField("output").get(c3.cast(o));
                                if (output == null) {
                                    PfF.Proxy.severe("Found null in Forestry Fermenter!");
                                    continue;
                                }
                                if (output.isFluidEqual(biomass)) {
                                    if (l.isFluidEqual(new FluidStack(FluidRegistry.WATER, 1000))) {
                                        r.add(new FermenterRecipe(resource, value));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            PfF.Proxy.print("Adapting " + liquid.getFluid().getName() + " to fermenter. " + r.size() + " recipes.");
            for (FermenterRecipe z : r) {
                Forestry.fermenter(z, 1.5f, liquid, "biomass");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class FermenterRecipe {

        private ItemStack fermentable;
        private int amount;

        public FermenterRecipe(ItemStack fermentable, int amount) {
            this.fermentable = fermentable;
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public ItemStack getFermentable() {
            return fermentable;
        }
    }
}
