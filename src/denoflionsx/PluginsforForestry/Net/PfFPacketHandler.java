package denoflionsx.PluginsforForestry.Net;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import denoflionsx.PluginsforForestry.Net.Packets.HandlerBarrelSync;
import denoflionsx.PluginsforForestry.Net.Packets.HandlerSyncComplete;
import denoflionsx.PluginsforForestry.Net.Packets.HandlerWoodenBucketSync;
import denoflionsx.PluginsforForestry.Net.Packets.PacketProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PfFPacketHandler implements IPacketHandler {
    
    public static final PacketProcessor PacketMaker = new PacketProcessor();

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
    }
    
    public static String getUserName(Player player){
        EntityPlayer player1 = (EntityPlayer) player;
        return player1.username;
    }

    public static enum Packets {

        WoodenBucket_Sync(0, new HandlerWoodenBucketSync()),
        Barrel_Sync(1, new HandlerBarrelSync()),
        Sync_Complete(2, new HandlerSyncComplete());
        //-------------
        private int id;
        private IPacketHandler handler;

        private Packets(int id, IPacketHandler handler) {
            this.id = id;
            this.handler = handler;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public IPacketHandler getHandler() {
            return handler;
        }

        public void setHandler(IPacketHandler handler) {
            this.handler = handler;
        }
    }
}
