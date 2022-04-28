package today.sleek.base.value.value;

import today.sleek.base.value.Value;
import today.sleek.client.modules.impl.Module;

public class StringValue extends Value<String> {

    String value;

    public StringValue(String name, Module owner, String value) {
        super(name, owner, value);

        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
