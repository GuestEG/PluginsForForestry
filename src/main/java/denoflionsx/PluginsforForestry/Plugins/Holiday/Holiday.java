package denoflionsx.PluginsforForestry.Plugins.Holiday;

import denoflionsx.PluginsforForestry.API.Plugin.IPfFPlugin;
import denoflionsx.PluginsforForestry.Plugins.Holiday.Objects.DateObject;
import denoflionsx.PluginsforForestry.Plugins.Holiday.Objects.HolidayObject;
import denoflionsx.PluginsforForestry.Plugins.Holiday.SpecialSnowflakes.Spring;
import java.util.Calendar;

public class Holiday implements IPfFPlugin {

    private HolidayObject[] days = new HolidayObject[]{
        new HolidayObject("Halloween", new DateObject(Calendar.OCTOBER).setWholeMonth(true), "holiday/wooden_bucket_spooky", "item.pff.woodenbucket.halloween.name"),
        new HolidayObject("Thanksgiving (US)", new DateObject(Calendar.NOVEMBER, 21, 28), "holiday/pilgrim_bucket", "item.pff.woodenbucket.thanksgiving.name"),
        new HolidayObject("Christmas", new DateObject(Calendar.DECEMBER).setWholeMonth(true), "holiday/christmas_bucket", "item.pff.woodenbucket.christmas.name"),
        new HolidayObject("Valentines", new DateObject(Calendar.FEBRUARY, 14, 14), "holiday/love_bucket", "item.pff.woodenbucket.valentine.name"),
        // Spring is a special snowflake due to random sprites.
        new Spring(),
        new HolidayObject("Independence Day (US)", new DateObject(Calendar.JULY, 4, 4), "holiday/murica_bucket", "item.pff.woodenbucket.murica.name")
    };

    @Override
    public void onPreLoad() {
        for (HolidayObject o : days) {
            if (o.isHolidayValid()) {
                o.setupHolidayContent();
            }
        }
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onPostLoad() {
    }
}
