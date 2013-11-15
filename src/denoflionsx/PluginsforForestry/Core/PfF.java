package denoflionsx.PluginsforForestry.Core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import denoflionsx.PluginsforForestry.API.PfFAPI;
import denoflionsx.PluginsforForestry.Config.PfFTuning;
import denoflionsx.PluginsforForestry.IMC.IMCHandler;
import denoflionsx.PluginsforForestry.Lang.PfFTranslator;
import denoflionsx.PluginsforForestry.Managers.PfFPluginManager;
import denoflionsx.PluginsforForestry.Net.PfFConnectionHandler;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandlerClient;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandlerCommon;
import denoflionsx.PluginsforForestry.Proxy.PfFProxy;
import denoflionsx.PluginsforForestry.Utils.FermenterUtils;
import denoflionsx.denLib.Lib.denLib;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.File;

@Mod(modid = "PluginsForForestry", name = "PluginsForForestry", version = "@VERSION@", dependencies = "required-after:denLib;after:MineFactoryReloaded;after:BuildCraft|Core;after:Forestry")
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
        clientPacketHandlerSpec
        = @NetworkMod.SidedPacketHandler(channels = {PfF.channel}, packetHandler = PfFPacketHandlerClient.class),
        serverPacketHandlerSpec
        = @NetworkMod.SidedPacketHandler(channels = {PfF.channel}, packetHandler = PfFPacketHandlerCommon.class),
        connectionHandler = PfFConnectionHandler.class)
public class PfF {
    
    public static final String proxyPath = "denoflionsx.PluginsforForestry.Proxy";
    public static final String proxyClient = proxyPath + ".PfFProxyClient";
    public static final String proxyCommon = proxyPath + ".PfFProxyCommon";
    @SidedProxy(clientSide = proxyClient, serverSide = proxyCommon)
    public static PfFProxy Proxy;
    public static PfFCore core;
    public static final String channel = "PluginsFF";
    public static File source;
    public static FMLPreInitializationEvent _event;
    
    public PfF() {
        PfFAPI.plugins = new PfFPluginManager();
    }
    
    @EventHandler
    public void preLoad(FMLPreInitializationEvent event) {
        _event = event;
        PfFAPI.instance = this;
        core = new PfFCore();
        source = event.getSourceFile();
        if (source == null || source.getAbsolutePath().contains("minecraft.jar") || source.isDirectory()) {
            source = denLib.FileUtils.findMeInMods(new File("./mods"), "PluginsForForestry");
        }
        core.setupConfig(event);
        PfFTranslator.createInstance();
        Proxy.findInternalAddons(source);
        PfFAPI.plugins.runPluginLoadEvent(event);
        denLibMod.fluids.register(new FermenterUtils());
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
        core.setupContainers();
        PfFAPI.plugins.runPluginLoadEvent(event);
    }
    
    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent evt) {
        PfFAPI.plugins.runPluginLoadEvent(evt);
        core.setupRendering();
        PfF.Proxy.setTabs();
        PfF.Proxy.print("This is PfF version " + "@VERSION@");
        PfFTuning.config.save();
    }
    
    @EventHandler
    public void IMCCallback(IMCEvent event) {
        IMCHandler h = new IMCHandler();
        for (IMCMessage m : event.getMessages()) {
            h.onIMCMessage(m);
        }
    }
}
