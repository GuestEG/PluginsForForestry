package denoflionsx.PluginsforForestry.Net.Packets;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandler;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class HandlerSyncComplete implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        PfF.Proxy.print(PfFPacketHandler.PacketMaker.readOkPacket(packet) + " Syncing complete and verified for " + PfFPacketHandler.getUserName(player));
    }
}
