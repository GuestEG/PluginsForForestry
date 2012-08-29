package net.minecraft.src.denoflionsx.plugins;

import forestry.api.core.ItemInterface;
import forestry.api.liquids.LiquidStack;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.denoflionsx.API.API;
import net.minecraft.src.denoflionsx.core.ItemIDManager;
import net.minecraft.src.denoflionsx.core.core;
import net.minecraft.src.denoflionsx.denLib.Config.Config;
import net.minecraft.src.denoflionsx.plugins.Core.EnumToolTextures;
import net.minecraft.src.denoflionsx.plugins.Core.LiquidVacuum;
import net.minecraft.src.denoflionsx.plugins.Core.MilkBag;
import net.minecraft.src.denoflionsx.plugins.Core.WoodenBucket;
import net.minecraft.src.denoflionsx.plugins.Forestry.pipette;

public class pluginCoreItems extends pluginBase{
    
    ItemIDManager IDs = new ItemIDManager(2,"LiquidVacuum");
    ItemIDManager woodenbucketIDs = new ItemIDManager(1,"WoodenBucket");
    LiquidVacuum lv;
    MilkBag mb;
    WoodenBucket wb;

    public pluginCoreItems() {
        this.name = "pluginCoreItems";
        this.config = new Config(this.name + ".cfg");
        this.register();
    }

    @Override
    public void register() {
        if (!this.loaded){
            this.defaults();
            this.runConfig();
            if (this.loaded = this.init()){
                this.recipes();
                 core.print(this.name + " loaded!");
            }
        }
    }
    
    @Override
    protected void recipes() {
        RecipeManagers.squeezerManager.addRecipe(5, new ItemStack[]{API.getItem("milkbag")}, new LiquidStack(API.getItem("milk").getItem(),1000));
        ModLoader.addRecipe(API.getItem("liquidvacuum"),new Object[]{
                "PpP",
                "MbM",
                "GBG",
                Character.valueOf('P'),ItemInterface.getItem("propolis"),
                Character.valueOf('p'),new ItemStack(pipette.pipette),
                Character.valueOf('M'),ItemInterface.getItem("sturdyMachine"),
                Character.valueOf('b'),new ItemStack(Item.glassBottle),
                Character.valueOf('G'),ItemInterface.getItem("gearBronze"),
                Character.valueOf('B'),new ItemStack(Item.bucketEmpty)});
    }

    @Override
    protected boolean init() {
        this.hooked = true;
        lv = new LiquidVacuum(this.config.getOptionInt("LiquidVacuum_ItemID"),"liquidvacuum");
        lv.add("liquidvacuum",0,EnumToolTextures.ToolTextures.LIQUIDVACUUM.getIndex(),"Liquid Vacuum");
        mb = new MilkBag(this.config.getOptionInt("MilkBag_ItemID"),"milkbag");
        mb.add("milkbag",0,EnumToolTextures.ToolTextures.MILKBAG.getIndex(),"Milk Bag");
        wb = new WoodenBucket(this.config.getOptionInt("WoodenBucket_ItemID"),"woodenbucket");
        wb.add("woodenbucket",wb.metaMap.get("Wooden Bucket"),EnumToolTextures.ToolTextures.WOODENBUCKET_EMPTY.getIndex(),"Wooden Bucket");
        wb.add("filledwoodenbucket",wb.metaMap.get("Filled Wooden Bucket"),EnumToolTextures.ToolTextures.WOODENBUCKET_FULL.getIndex(),"Filled Wooden Bucket");
        WoodenBucket.bucketWorksInNether = this.config.getOptionBool("bucketWorksInNether");
        return this.hooked;
    }

    @Override
    protected void defaults() {
        this.config.addDefault("[Core Items Config]");
        this.config.addDefault("LiquidVacuum_ItemID=" + IDs.getItemIDs().get(0));
        this.config.addDefault("MilkBag_ItemID=" + IDs.getItemIDs().get(1));
        this.config.addDefault("WoodenBucket_ItemID=" + woodenbucketIDs.getItemIDs().get(0));
        this.config.addDefault("bucketWorksInNether=false");
    }
}
