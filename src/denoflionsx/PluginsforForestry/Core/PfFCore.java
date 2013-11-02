package denoflionsx.PluginsforForestry.Core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import denoflionsx.PluginsforForestry.Config.PfFTuning;
import denoflionsx.denLib.CoreMod.Updater.IDenUpdate;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.*;
import net.minecraftforge.common.Configuration;

public class PfFCore implements IDenUpdate {
    
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
        denLibMod.tuning.registerTunableClass(PfFTuning.class);
        this.test();
        mappingsDir = new File(PfF.core.configDir.getAbsolutePath() + "/Mappings");
        if (!mappingsDir.exists()) {
            mappingsDir.mkdirs();
        }
        denLibMod.configManager.setup(sourceFile, new File(configDir.getAbsolutePath() + "/PluginsforForestry.cfg"));
    }
    
    public void setupRendering() {
        PfF.Proxy.registerClientSide();
    }
    
    public void test() {
    }
    
    @Override
    public String getUpdaterUrl() {
        return "https://dl.dropboxusercontent.com/u/23892866/VersionCheck/PfF3x/PfFTest.txt";
    }
    
    @Override
    public String getUpdaterName() {
        return "Plugins for Forestry 3.X Alpha";
    }
    
    @Override
    public void registerWithUpdater() {
        //denLibCore.updater.registerUpdate(PfF.core);
    }
    
    @Override
    public File getSourceFile() {
        return this.sourceFile;
    }
    
    @Override
    public int getBuildNumber() {
        return Integer.valueOf("@BUILD@");
    }
    
    @Override
    public String getUpdatedModFileUrl() {
        return this.updatedUrl;
    }
    
    @Override
    public void setUpdatedModFileUrl(String url) {
        this.updatedUrl = url;
    }
}
