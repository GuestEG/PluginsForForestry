package denoflionsx.PluginsforForestry.Plugins.Holiday.SpecialSnowflakes;

import denoflionsx.PluginsforForestry.Plugins.Holiday.Objects.DateObject;
import denoflionsx.PluginsforForestry.Plugins.Holiday.Objects.HolidayObject;
import denoflionsx.denLib.Mod.denLibMod;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class Spring extends HolidayObject {
    
    private HashMap<Integer, String> iconMap = new HashMap();
    
    public Spring() {
        super("Spring", new DateObject(Calendar.APRIL).setWholeMonth(true), null, "item.pff.woodenbucket.spring.name");
        iconMap.put(0, "pink");
        iconMap.put(1, "orange");
        iconMap.put(2, "lavendar");
        iconMap.put(3, "green");
        iconMap.put(4, "blue");
    }
    
    @Override
    public void setupHolidayContent() {
        try {
            Random rand = new Random();
            int roll = rand.nextInt(4);
            this.setIcon("holiday/spring/spring_bucket_".concat(iconMap.get(roll)));
        } catch (Throwable t) {
            this.setIcon("holiday/spring/spring_bucket_".concat(iconMap.get(0)));
            denLibMod.log(t.getLocalizedMessage());
        }
        super.setupHolidayContent();
    }
}
