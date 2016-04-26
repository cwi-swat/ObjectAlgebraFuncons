package funcons.interpreter;

import funcons.algebras.TypeAlg;
import funcons.sorts.IEval;
import funcons.types.Tag;
import funcons.types.Type;
import funcons.types.Variant;

public interface TypeFactory extends ListFactory, TypeAlg<IEval> {
    @Override
    default IEval type(java.lang.String name) {
        return (env, store, given) -> new Type(name);
    }

    @Override
    default IEval tag(java.lang.String name) {
        return (env, store, given) -> new Tag(name);
    }

    @Override
    default IEval variant(java.lang.String tagName, IEval exp) {
        return (env, store, given) -> new Variant(tagName, exp.eval(env, store, given));
    }

    @Override
    default IEval typed(IEval exp, IEval type) {
        return exp;
    }

    @Override
    default IEval restrictDomain(IEval abs, IEval type) {
        return abs;
    }

    @Override
    default IEval pattAtType(IEval patt, IEval type) {
        return restrictDomain(patt, type);
    }

    @Override
    default IEval variantMatch(IEval tag, IEval variant, IEval patt) {
        return (env, store, given) -> {
            Variant v = (Variant)variant.eval(env, store, given);
            return whenTrue(equal(tag, (e,s,g) -> v.tag), match((e,s,g) -> v.value, patt)).eval(env, store, given);
        };
    }
}