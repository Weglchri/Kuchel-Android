package at.kuchel.kuchelapp.builder;

import at.kuchel.kuchelapp.model.GlobalParamEntity;

/**
 * Created by bernhard on 31.03.2018.
 */

public final    class GlobalParamBuilder {

    private String key;
    private String value;

    public final  GlobalParamBuilder setKey(String key) {
        this.key=key;
        return this;
    }

    public final GlobalParamBuilder setValue(String value) {
        this.value=value;
        return this;
    }

    public final GlobalParamEntity build() {
        GlobalParamEntity entity = new GlobalParamEntity();
        entity.setKey(getKey());
        entity.setValue(getValue());
        return entity;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }


}
