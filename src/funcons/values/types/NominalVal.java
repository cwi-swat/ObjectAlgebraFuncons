package funcons.values.types;

import funcons.values.Value;

public class NominalVal implements Value {

    private final NominalTag tag;
    private final Value value;

    public NominalVal(NominalTag tag, Value v) {
        this.tag = tag;
        this.value = v;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NominalVal &&
                ((NominalVal)obj).tag.equals(this.tag) &&
                ((NominalVal)obj).value.equals(this.value);
    }
}