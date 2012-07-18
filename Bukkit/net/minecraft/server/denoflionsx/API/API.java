package net.minecraft.server.denoflionsx.API;

import java.lang.reflect.Method;
import net.minecraft.server.ItemStack;

public class API
{
    public static final boolean verbose = false;

    public static ItemStack getItem(String var0)
    {
        ItemStack var1 = (ItemStack)PFFItems.registeredItems.get(var0);

        if (var1 != null)
        {
            return var1;
        }
        else
        {
            System.out.println("Unable to retrieve item: " + var0 + " from Plugins for Forestry!");
            return null;
        }
    }

    public static boolean isPluginLoaded(String var0)
    {
        try
        {
            Class var2 = Class.forName("denoflionsx.plugins.pluginCore");
            Method var3 = var2.getMethod("isPluginAlive", new Class[] {String.class});
            String var4 = var3.invoke((Object)null, new Object[] {var0}).toString();
            boolean var1;

            if (var4.toLowerCase().equals("true"))
            {
                var1 = true;
            }
            else
            {
                var1 = false;
            }

            return var1;
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
            return false;
        }
    }
}