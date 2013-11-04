package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import denoflionsx.denLib.Mod.denLibMod;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;

public class FluidIconHandler {
    
    private static final int BLOCK = 0;
    //private static final int ITEM = 1;

    public FluidIconHandler() {
        denLibMod.Proxy.registerForgeSubscribe(this);
    }

    @ForgeSubscribe
    @SideOnly(Side.CLIENT)
    public void onTexture(TextureStitchEvent.Pre event) {
        if (event.map.textureType == BLOCK) {
            this.setupIcon(PluginLR.peat, event, new String[]{"peat.flowing", "peat.still"});
        }
    }

    public void setupIcon(Fluid f, TextureStitchEvent.Pre event, String[] texture) {
        f.setFlowingIcon(event.map.registerIcon("@NAME@".toLowerCase().concat(":fluid/").concat(texture[0])));
        f.setStillIcon(event.map.registerIcon("@NAME@".toLowerCase().concat(":fluid/").concat(texture[1])));
    }
}
