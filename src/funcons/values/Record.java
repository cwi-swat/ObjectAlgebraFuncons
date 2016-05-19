package funcons.values;

import java.lang.*;
import java.lang.String;
import java.util.Iterator;

public class Record implements Value {
    private Map<Field, Value> map;

    public Record(Map<Field, Value> m) {
        map = m;
    }

    public Value select(Field f) {
        return map.val(f);
    }

    public Record over(Record other) {
        return new Record(other.map.extend(map));
    }

    public Record union(Record other) {
        return new Record(map.extend(other.map));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        Iterator<Field> it = map.keys().iterator();
        while (it.hasNext()) {
            Field f = it.next();
            sb.append(f);
            sb.append("=");
            sb.append(map.val(f));

            if (it.hasNext()) {
                sb.append("; ");
            }
        }
        sb.append("}");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Record && ((Record)obj).map.equals(map);
    }
}
