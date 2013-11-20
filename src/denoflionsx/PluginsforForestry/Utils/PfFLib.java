package denoflionsx.PluginsforForestry.Utils;

import denoflionsx.denLib.Lib.denLib;
import java.security.MessageDigest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import net.minecraft.item.ItemStack;

public class PfFLib {

    public static class PffStringUtils {

        public static final String error = "BROKEN LIQUID NAME";

        public static String cleanName(String n) {
            try {
                return Character.toUpperCase(n.charAt(0)) + n.substring(1);
            } catch (Exception ex) {
            }
            return error;
        }

        public static String getItemName(ItemStack stack) {
            try {
                return stack.getDisplayName();
            } catch (Exception ex) {
                return "";
            }
        }

        public static String getTextureFromName(String liquid) {
            return "item.pff." + denLib.StringUtils.removeSpaces(liquid.toLowerCase()) + ".still";
        }

        public static String Hash(String s) {
            try {
                return (new HexBinaryAdapter()).marshal(MessageDigest.getInstance("MD5").digest(s.getBytes()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return denLib.StringUtils.readError;
        }
    }
    
    public static class FluidUtils {

        public static String fixName(String n) {
            // This is for random Forestry and Mystcraft stuff that has dots in the name for whatever reason.
            if (n.contains(".")) {
                n = n.replace(".", " ");
            }
            return Character.toUpperCase(n.charAt(0)) + n.substring(1);
        }
    }
}
