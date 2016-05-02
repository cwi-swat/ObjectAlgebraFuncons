package funcons.values;

import java.lang.*;
import java.lang.String;

public class Undefined implements Value {
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Undefined;
    }

    @Override
    public String toString() {
        return "UNDEFINED";
    }
}
