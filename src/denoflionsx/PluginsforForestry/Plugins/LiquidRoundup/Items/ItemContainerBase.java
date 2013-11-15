package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import denoflionsx.PluginsforForestry.API.Interfaces.IPfFContainer;
import denoflionsx.PluginsforForestry.API.PfFAPI;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Lang.PfFTranslator;
import denoflionsx.PluginsforForestry.ModAPIWrappers.Forestry;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import denoflionsx.denLib.Lib.denLib;
import denoflionsx.denLib.Mod.Handlers.NewFluidHandler.IDenLibFluidHandler;
import denoflionsx.denLib.Mod.Handlers.NewWorldHandler.IDenLibWorldHandler;
import denoflionsx.denLib.Mod.denLibMod;
import denoflionsx.denLib.NewConfig.ConfigField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;

public class ItemContainerBase extends Item implements IPfFContainer, IDenLibFluidHandler, IDenLibWorldHandler {

    public static final String seperator = "-";
    public static boolean saved = false;
    private String unloc;
    private String tag;
    @ConfigField(category = "fluid.containers")
    public static String[] fluidConfig = new String[0];
    protected BiMap<Integer, String> fluids = HashBiMap.create();
    protected HashMap<Integer, ItemStack> filledMap = new HashMap();
    protected ArrayList<ItemStack> stacks = new ArrayList();
    private ItemStack empty;
    private boolean isBucket = false;
    private int capacity;
    private Icon overlay;
    private String[] iconStrings = new String[2];
    public HashMap<Integer, Integer> colorMapMeta = new HashMap();

    public ItemContainerBase(int itemID, int capacity, String unloc, String tag, String icon) {
        super(itemID);
        this.setCreativeTab(PfFAPI.tab);
        this.setUnloc(unloc);
        this.setTag(tag);
        for (String s : fluidConfig) {
            String[] parse = s.split(seperator);
            String f = parse[0];
            int m = Integer.valueOf(parse[1]);
            if (!fluids.containsKey(m)) {
                fluids.put(m, f);
            }
        }
        this.capacity = capacity;
        empty = new ItemStack(this);
        stacks.add(empty);
        this.setCreativeTab(PfFAPI.tab);
        PluginLR.stacks.put(tag, empty);
        iconStrings[0] = icon;
        iconStrings[1] = icon.concat("_overlay");
    }

    private int parseInt(String s) {
        if (s == null) {
            return -1;
        }
        return (int) Long.parseLong(s.replace("0x", ""), 16);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String getContainerTag() {
        return this.tag;
    }

    public boolean isIsBucket() {
        return isBucket;
    }

    public void setIsBucket(boolean isBucket) {
        this.isBucket = isBucket;
        if (this.isBucket) {
            this.setContainerItem(this);
            this.setMaxStackSize(1);
        } else {
            this.setContainerItem(null);
            this.setMaxStackSize(64);
        }
    }

    public void setUnloc(String unloc) {
        this.unloc = unloc;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("PluginsForForestry:".toLowerCase().concat(iconStrings[0]));
        this.overlay = par1IconRegister.registerIcon("PluginsForForestry:".toLowerCase().concat(iconStrings[1]));
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        return PfFTranslator.instance.translateKey(unloc);
    }

    @Override
    public void onEvent(FluidStack f) {
        doMapping(f, false);
    }

    private void doMapping(FluidStack f, boolean regen) {
        String tag = f.getFluid().getName();
        if (f.getFluid().isGaseous()) {
            return;
        }
        if (!PluginLR.blackLists.get(this.getContainerTag()).contains(tag) && !tag.toLowerCase().contains("molten")) {
            int id;
            if (!fluids.values().contains(tag)) {
                id = denLib.MathUtils.getLastID(fluids);
            } else {
                id = fluids.inverse().get(tag);
            }
            fluids.put(id, tag);
            if (!regen) {
                PfF.Proxy.print("Generating " + this.tag + " for " + tag + " on " + String.valueOf(id));
            }
            ItemStack filled = new ItemStack(this, 1, id);
            stacks.add(filled);
            FluidContainerData d = new FluidContainerData(f, filled, empty, isBucket);
            FluidContainerRegistry.registerFluidContainer(d);
            Forestry.squeezer(5, new ItemStack[]{filled}, f);
            if (f.getFluid().canBePlacedInWorld() && this.isBucket) {
                filledMap.put(f.getFluid().getBlockID(), filled);
                if (!regen) {
                    PfF.Proxy.print(f.getFluid().getName() + " is placable. Registering as such.");
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        if (par2 > 0) {
            if (colorMapMeta.containsKey(par1ItemStack.getItemDamage())) {
                return colorMapMeta.get(par1ItemStack.getItemDamage());
            } else {
                colorMapMeta.put(par1ItemStack.getItemDamage(), this.getColorForMeta(par1ItemStack));
            }
        }
        return 0xFFFFFF;
    }

    private int getColorForMeta(ItemStack par1ItemStack) {
        if (par1ItemStack.getItemDamage() == 0) {
            return 0xFFFFFF;
        }
        FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(par1ItemStack);
        if (fluid == null) {
            return 0xFFFFFF;
        }
        if (fluid.getFluid().getColor() == 0xFFFFFF) {
            String hex = PfF.Proxy.getIconCode(fluid.getFluid().getIcon());
            return this.parseInt(hex);
        }
        return fluid.getFluid().getColor();
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int par1, int par2) {
        if (par2 > 0 && par1 > 0) {
            return this.overlay;
        } else {
            return this.itemIcon;
        }
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.addAll(stacks);
    }

    public BiMap<Integer, String> getFluids() {
        return fluids;
    }

    public void setFluids(BiMap<Integer, String> fluids) {
        this.fluids = fluids;
    }

    @Override
    public void onWorldLoaded(World world) {
        if (!saved) {
            ArrayList<String> a = new ArrayList();
            ArrayList<Integer> b = new ArrayList();
            b.addAll(fluids.keySet());
            Collections.sort(b);
            PfF.Proxy.print("Sorting fluids...");
            for (Integer i : b) {
                String t = fluids.get(i);
                a.add(t + seperator + String.valueOf(i));
            }
            fluidConfig = a.toArray(new String[a.size()]);
            PfF.core.setupConfig(PfF._event);
            PfF.Proxy.print("Saving fluid mappings to config file.");
        }
        denLibMod.worldHandler.removeHandler(this);
    }

    @Override
    public String lookingForFluid() {
        return null;
    }

    public String getUnloc() {
        return unloc;
    }

    public String getTag() {
        return tag;
    }

    public HashMap<Integer, ItemStack> getFilledMap() {
        return filledMap;
    }

    public ArrayList<ItemStack> getStacks() {
        return stacks;
    }

    public ItemStack getEmpty() {
        return empty;
    }

    public int getCapacity() {
        return capacity;
    }

    public Icon getOverlay() {
        return overlay;
    }

    public String[] getIconStrings() {
        return iconStrings;
    }

    public HashMap<Integer, Integer> getColorMapMeta() {
        return colorMapMeta;
    }

    public void setFilledMap(HashMap<Integer, ItemStack> filledMap) {
        this.filledMap = filledMap;
    }

    public void setStacks(ArrayList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public void setEmpty(ItemStack empty) {
        this.empty = empty;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOverlay(Icon overlay) {
        this.overlay = overlay;
    }

    public void setIconStrings(String[] iconStrings) {
        this.iconStrings = iconStrings;
    }

    public void setColorMapMeta(HashMap<Integer, Integer> colorMapMeta) {
        this.colorMapMeta = colorMapMeta;
    }
}
