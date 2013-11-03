package denoflionsx.PluginsforForestry.Net.Packets;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandler;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class HandlerInvalidSync implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        String[] hashes = PfFPacketHandler.PacketMaker.getInvalidHashPacketData(packet);
        PfF.Proxy.warning("Sync failed! Invalid hash response from client! " + " Client Hash: " + hashes[0] + " | Server Hash: " + hashes[1]);
    }
}
