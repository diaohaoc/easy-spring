package fun.easyspring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/11 21:23
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyValue) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyValue)) {
                return pv;
            }
        }
        return null;
    }


}
