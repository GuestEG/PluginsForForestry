package denoflionsx.PluginsforForestry.Core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import denoflionsx.PluginsforForestry.Config.ItemNameBridge;
import denoflionsx.PluginsforForestry.Config.PfFTuning;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.*;
import net.minecraftforge.common.Configuration;

public class PfFCore{
    
    public File mappingsDir;
    public File configDir;
    private File sourceFile;
    private String updatedUrl;
    
    public PfFCore(File sourceFile) {
        this.sourceFile = sourceFile;
    }
    
    public void setupContainers() {
    }
    
    public File getMappingFile(String name) {
        return PfF.Proxy.getMappingFile(name);
    }
    
    public void setupConfig(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory().getAbsolutePath() + "/denoflionsx/PluginsforForestry/");
        PfFTuning.config = new Configuration(new File(configDir.getAbsolutePath() + "/PluginsforForestry.cfg"));
        mappingsDir = new File(PfF.core.configDir.getAbsolutePath() + "/Mappings");
        if (!mappingsDir.exists()) {
            mappingsDir.mkdirs();
        }
        denLibMod.configManager.setup(sourceFile, new File(configDir.getAbsolutePath() + "/PluginsforForestry.cfg"));
        ItemNameBridge.setupBridge(new File(mappingsDir, "ItemNameBridge.bin"));
    }
    
    public void setupRendering() {
    }
}
