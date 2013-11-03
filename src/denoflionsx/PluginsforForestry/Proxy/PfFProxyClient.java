package denoflionsx.PluginsforForestry.Proxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import denoflionsx.PluginsforForestry.Core.PfF;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class PfFProxyClient extends PfFProxy {

    private static ResourceManager resourcemanager;

    static {
        resourcemanager = Minecraft.getMinecraft().getResourceManager();
    }

    @Override
    public String getIconCode(Icon icon) {
        try {
            String t = icon.getIconName();
            String s1 = "minecraft";
            String s2 = t;
            int i = t.indexOf(':');

            if (i >= 0) {
                s2 = t.substring(i + 1, t.length());

                if (i > 1) {
                    s1 = t.substring(0, i);
                }
            }

            s1 = s1.toLowerCase();
            s2 = "textures/blocks/" + s2 + ".png";

            Resource resource = resourcemanager.getResource(new ResourceLocation(s1, s2));

            InputStream inputstream = resource.getInputStream();
            BufferedImage bufferedimage = ImageIO.read(inputstream);
            String hex = "0x" + Integer.toHexString(bufferedimage.getRGB(1, 1)).toUpperCase();
            return hex;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return "0xFFFFFF";
    }

    @SideOnly(Side.CLIENT)
    public static Icon registerIcon(IconRegister par1IconRegister, String icon) {
        PfF.Proxy.print("Registering icon " + icon);
        return par1IconRegister.registerIcon(icon);
    }

    @Override
    public void sendMessageToPlayer(String msg) {
        Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("[PfF]: " + msg));
    }

    @Override
    public String getLang() {
        return Minecraft.getMinecraft().gameSettings.language;
    }
}
