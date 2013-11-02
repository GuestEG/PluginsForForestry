package denoflionsx.PluginsforForestry.Net.Packets;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandler;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class HandlerBarrelSync implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        PluginLR.barrel.setFluids(PfFPacketHandler.PacketMaker.getSyncDataFromPacket(packet));
        PluginLR.barrel.regenerateMaps();
    }
}
