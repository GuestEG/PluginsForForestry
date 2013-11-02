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
import denoflionsx.PluginsforForestry.Changelog.PfFChangelogHandler;
import denoflionsx.PluginsforForestry.Config.PfFTuning;
import denoflionsx.PluginsforForestry.IMC.IMCHandler;
import denoflionsx.PluginsforForestry.Lang.PfFTranslator;
import denoflionsx.PluginsforForestry.Managers.PfFPluginManager;
import denoflionsx.PluginsforForestry.Net.PfFConnectionHandler;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandlerClient;
import denoflionsx.PluginsforForestry.Net.PfFPacketHandlerCommon;
import denoflionsx.PluginsforForestry.Proxy.PfFProxy;
import denoflionsx.PluginsforForestry.Utils.FermenterUtils;
import denoflionsx.denLib.Mod.Handlers.DictionaryHandler;
import denoflionsx.denLib.Mod.denLibMod;

@Mod(modid = "@NAME@", name = "@NAME@", version = "@VERSION@", dependencies = "@DEPENDS@")
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
        clientPacketHandlerSpec =
        @NetworkMod.SidedPacketHandler(channels = {PfF.channel}, packetHandler = PfFPacketHandlerClient.class),
        serverPacketHandlerSpec =
        @NetworkMod.SidedPacketHandler(channels = {PfF.channel}, packetHandler = PfFPacketHandlerCommon.class),
        connectionHandler = PfFConnectionHandler.class)
public class PfF {

    @Mod.Instance("@NAME@")
    public static Object instance;
    @SidedProxy(clientSide = "@PROXYCLIENT@", serverSide = "@PROXYSERVER@")
    public static PfFProxy Proxy;
    public static PfFCore core;
    public static final String channel = "PluginsFF";

    public PfF() {
        PfFAPI.plugins = new PfFPluginManager();
    }

    @EventHandler
    public void preLoad(FMLPreInitializationEvent event) {
        PfFAPI.instance = this;
        core = new PfFCore(event.getSourceFile());
        core.setupConfig(event);
        PfFTranslator.createInstance();
        Proxy.findInternalAddons(event.getSourceFile());
        PfFAPI.plugins.runPluginLoadEvent(event);
        core.registerWithUpdater();
        denLibMod.DictionaryHandler.registerListener(new FermenterUtils(), DictionaryHandler.channels.FLUID);
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
        PfF.Proxy.registerAllRecipes();
        PfF.Proxy.setTabs();
        denLibMod.Proxy.registerChangelogHandler(new PfFChangelogHandler());
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
