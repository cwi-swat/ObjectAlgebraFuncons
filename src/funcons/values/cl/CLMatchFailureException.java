package funcons.values.cl;

import org.rascalmpl.value.IValueFactory;

public class CLMatchFailureException extends CLVariant {
    public CLMatchFailureException(IValueFactory vf) {
        super(vf.string("Match_failure"), new funcons.values.Null());
    }
}
