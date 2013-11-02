package denoflionsx.PluginsforForestry.Net.Packets;

import com.google.common.collect.BiMap;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandler;
import denoflionsx.denLib.Lib.denLib;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketProcessor {

    public int getInternalIdFromPacket(Packet250CustomPayload packet) {
        NBTTagCompound tag = new NBTTagCompound();
        try {
            tag = CompressedStreamTools.decompress(packet.data);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return tag.getInteger("id");
    }

    public BiMap<Integer, String> getSyncDataFromPacket(Packet250CustomPayload packet) {
        NBTTagCompound tag = new NBTTagCompound();
        try {
            tag = CompressedStreamTools.decompress(packet.data);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        BiMap<Integer, String> fluids = (BiMap<Integer, String>) denLib.FileUtils.readObjectFromByteArray(tag.getByteArray("bimap"));
        return fluids;
    }

    public Packet250CustomPayload createSyncPacket(int id, BiMap<Integer, String> map) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("id", id);
        tag.setByteArray("bimap", denLib.FileUtils.turnObjectToByteArray(map));
        Packet250CustomPayload packet = new Packet250CustomPayload();
        try {
            packet.data = CompressedStreamTools.compress(tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        packet.channel = PfF.channel;
        packet.length = packet.data.length;
        packet.isChunkDataPacket = false;
        return packet;
    }

    public String readOkPacket(Packet250CustomPayload packet) {
        NBTTagCompound tag = new NBTTagCompound();
        try {
            tag = CompressedStreamTools.decompress(packet.data);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        String p = tag.getString("container");
        return p;
    }

    public Packet250CustomPayload createOkPacket(String container) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("id", PfFPacketHandler.Packets.Sync_Complete.getId());
        tag.setString("container", container);
        Packet250CustomPayload packet = new Packet250CustomPayload();
        try {
            packet.data = CompressedStreamTools.compress(tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        packet.channel = PfF.channel;
        packet.length = packet.data.length;
        packet.isChunkDataPacket = false;
        return packet;
    }
}
