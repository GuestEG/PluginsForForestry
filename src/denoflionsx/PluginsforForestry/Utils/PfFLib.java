package denoflionsx.PluginsforForestry.Utils;

import com.google.common.collect.BiMap;
import denoflionsx.denLib.Lib.denLib;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class PfFLib {

    public static class PffStringUtils {

        public static final String error = "BROKEN LIQUID NAME";

        public static String cleanLiquidNameFromEvent(LiquidDictionary.LiquidRegisterEvent event) {
            return cleanName(event.Name);
        }

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

    public static class MathUtils {

        public static int getLastID(BiMap<Integer, String> map) {
            int ids[] = new int[map.size()];
            int temp = 0;
            for (Object o : map.keySet()) {
                Integer i = (Integer) o;
                ids[temp] = i;
                temp++;
            }
            Arrays.sort(ids);
            int f = 0;
            if (ids.length != 0) {
                f = ids[ids.length - 1];
            }
            f++;
            return f;
        }
    }

    public static class LiquidUtils {

        public static ItemStack getEmptyContainer(ItemStack s) {
            for (LiquidContainerData a : LiquidContainerRegistry.getRegisteredLiquidContainerData()) {
                if (a.filled.isItemEqual(s)) {
                    return a.container;
                }
            }
            return null;
        }

        public static ArrayList<ItemStack> getAllContainersForLiquid(LiquidStack s) {
            ArrayList<ItemStack> containers = new ArrayList();
            for (LiquidContainerData a : LiquidContainerRegistry.getRegisteredLiquidContainerData()) {
                if (a.stillLiquid.isLiquidEqual(s)) {
                    containers.add(a.filled);
                }
            }
            return containers;
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
