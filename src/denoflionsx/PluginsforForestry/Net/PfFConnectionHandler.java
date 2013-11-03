package denoflionsx.PluginsforForestry.Net;

import com.google.common.collect.BiMap;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import denoflionsx.denLib.Lib.denLib;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

public class PfFConnectionHandler implements IConnectionHandler {

    public static final boolean netDebug = true;

    public static String hashTheMap(BiMap<Integer, String> map) {
        String temp = "";
        ArrayList<Integer> i = new ArrayList();
        i.addAll(map.keySet());
        Collections.sort(i);
        for (Integer a : i) {
            temp += String.valueOf(a) + ", " + map.get(a) + " | ";
        }
        return denLib.StringUtils.Hash(temp);
    }

    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
        PfF.Proxy.print(PfFPacketHandler.getUserName(player) + " is logging in. Sending container maps...");
        PacketDispatcher.sendPacketToPlayer(PfFPacketHandler.PacketMaker.createSyncPacket(PfFPacketHandler.Packets.WoodenBucket_Sync.getId(), PluginLR.woodenBucket.getFluids(), !netDebug, hashTheMap(PluginLR.woodenBucket.getFluids())), player);
        PacketDispatcher.sendPacketToPlayer(PfFPacketHandler.PacketMaker.createSyncPacket(PfFPacketHandler.Packets.Barrel_Sync.getId(), PluginLR.barrel.getFluids(), !netDebug, hashTheMap(PluginLR.barrel.getFluids())), player);
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
    }

    @Override
    public void connectionClosed(INetworkManager manager) {
    }

    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
    }
}
