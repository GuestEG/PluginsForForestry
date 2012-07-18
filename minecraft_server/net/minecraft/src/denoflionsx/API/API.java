package net.minecraft.src.denoflionsx.API;

import java.lang.reflect.Method;
import net.minecraft.src.ItemStack;

public class API {

    public static final boolean verbose = false;

    // Use this to access PFF items from outside the modules/plugins.
    // This method expects a string that matches the internal name of the item.
    // Make sure your mod loads after PFF to ensure the HashMap is populated!
    // Liquid Peat: liquidpeat, peatcap, peatcan, peatcap_red, peatbucket, peatbottle.
    // Sugary Peat: liquidsugarypeat, sugarypeatcap, sugarypeatcan, sugarypeatcap_red, sugarypeatbucket, sugarypeatbottle, sugarypeat.
    // Blue's Wax Stuff: waxtablet, waxcast, filledwaxcast, waxcast_red, wascastfilled_red, waxcastlava_red, rodoffreezing.
    // Solid Fuel Module: solidbiofuelbar.
    // Buildcraft Milk Module: bcliquidmilk, bcmilkcap, bcmilkcan, bcmilkcap_red, bcmilkbottle.
    // Citrus Juice: citrusjuice, citruscap, citruscan, citruscap_red, citrusbucket, citrusbottle.
    // Radioactive Waste: radioactivewaste, radioactivegoo, containmentbarrel, filledcontainmentbarrel.
    // Core: liquidvacuum, woodenbucket, filledwoodenbucket, milkbag.
    // APS: heavywatercap, heavywatercan, heavywatercap_red.
    // Obsolete: milkcan_depricated, milkcap_depricated, milkcap_red_depricated, milkbottle_depricated.
    
    // Be sure to check for nulls in case an item has been disabled by the user!
    
    public static ItemStack getItem(String s) {

        ItemStack I = PFFItems.registeredItems.get(s);

        if (I != null) {
            return I;
        } else {
            System.out.println("Unable to retrieve item: " + s + " from Plugins for Forestry!");
            return null;
        }
    }

    // Use this method to check if a plugin is loaded or not.
    // Please check via this before using getItem on a plugin based item!
    // Valid plugin names: BetterFarming, MFR, Forestry, IC2, Buildcraft, APS,
    // RedPowerWorld, EE.
    
    public static boolean isPluginLoaded(String name) {
        boolean loaded;
        try {
            Class pluginCore = Class.forName("denoflionsx.plugins.pluginCore");
            Method isPluginAlive = pluginCore.getMethod("isPluginAlive", new Class[]{String.class});
            String l = isPluginAlive.invoke(null, name).toString();
            if (l.toLowerCase().equals("true")) {
                if (API.verbose) {
                    System.out.println(name + " is loaded.");
                }
                loaded = true;
            } else {
                if (API.verbose) {
                    System.out.println(name + " is not loaded.");
                }
                loaded = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return loaded;
    }
}