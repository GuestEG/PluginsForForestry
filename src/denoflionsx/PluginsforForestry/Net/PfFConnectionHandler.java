package denoflionsx.PluginsforForestry.Net;

import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

public class PfFConnectionHandler implements IConnectionHandler {

    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
        PfF.Proxy.print(PfFPacketHandler.getUserName(player) + " is logging in. Sending container maps...");
        PacketDispatcher.sendPacketToPlayer(PfFPacketHandler.PacketMaker.createSyncPacket(PfFPacketHandler.Packets.WoodenBucket_Sync.getId(), PluginLR.woodenBucket.getFluids()), player);
        PacketDispatcher.sendPacketToPlayer(PfFPacketHandler.PacketMaker.createSyncPacket(PfFPacketHandler.Packets.Barrel_Sync.getId(), PluginLR.barrel.getFluids()), player);
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
