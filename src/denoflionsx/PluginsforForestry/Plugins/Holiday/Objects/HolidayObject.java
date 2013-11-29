package denoflionsx.PluginsforForestry.Plugins.Holiday.Objects;

import denoflionsx.PluginsforForestry.Config.PfFTuning;
import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Lang.MasterLangEnum;
import denoflionsx.PluginsforForestry.Lang.PfFTranslator;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.client.IconConstants;
import denoflionsx.denLib.Lib.denLib;

public class HolidayObject {

    public static final boolean holidayDebug = false;
    private final String name;
    private final DateObject runningDate;
    private String icon;
    private String key;

    public HolidayObject(String name, DateObject runningDate, String icon, String key) {
        this.name = name;
        this.runningDate = runningDate;
        this.icon = icon;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public DateObject getRunningDate() {
        return runningDate;
    }

    public void setupHolidayContent() {
        IconConstants.woodenBucket = icon;
        PfFTranslator.instance.overrideKey(MasterLangEnum.item_woodenbucket.toString(), key);
        PfF.Proxy.print(name.concat(" mode activated!"));
    }

    public boolean isHolidayValid() {
        if (holidayDebug) {
            PfF.Proxy.print("Holiday: " + this.name + " | " + this.getRunningDate().toString());
        }
        return this.getRunningDate().isValidDate() || PfFTuning.config.get("holiday", denLib.StringUtils.removeSpaces("force".concat(name)).replace("(", "").replace(")", ""), false).getBoolean(false);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
