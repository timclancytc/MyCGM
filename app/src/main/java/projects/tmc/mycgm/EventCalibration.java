package projects.tmc.mycgm;

import java.util.Date;

public class EventCalibration {

    private Date mSystemTime;
    private Date mDisplayTime;
    private float mValue;
    private String mUnit;

    public Date getSystemTime() {
        return mSystemTime;
    }

    public void setSystemTime(Date systemTime) {
        mSystemTime = systemTime;
    }

    public Date getDisplayTime() {
        return mDisplayTime;
    }

    public void setDisplayTime(Date displayTime) {
        mDisplayTime = displayTime;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }
}
