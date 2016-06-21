package funcons.interpreter.functions;

import funcons.algebras.collections.TupleAlg;
import funcons.algebras.functions.CurryAlg;
import funcons.algebras.functions.FunctionAlg;
import funcons.algebras.storage.SupplyGivenAlg;
import funcons.algebras.values.IntAlg;
import funcons.carriers.IEval;
import funcons.values.Abs;
import funcons.values.Int;

public interface CurryFactory extends
        FunctionAlg<IEval>,
        TupleAlg<IEval>,
        IntAlg<IEval>,
        SupplyGivenAlg<IEval>,
        CurryAlg<IEval> {

    @Override
    default IEval partialApp(IEval f, IEval x) {
        return abs(apply(f, tuple(x, given())));
    }

    @Override
    default IEval partialAppN(IEval f, IEval x) {
        return abs(apply(f, tuplePrefix(x, given())));
    }

    @Override
    default IEval curry(IEval a) {
        return abs((env, forward, store, given) -> partialApp(a, (e,f,s,g) -> given).eval(env, forward, store, given));
    }

    @Override
    default IEval curryN(IEval n, IEval a) {
        return (env, forward, store, given) -> {
            Int index = (Int)n.eval(env, forward, store, given);
            if (index.intValue() == 0) {
                return apply(a, tuple()).eval(env, forward, store, given);
            }

            return new Abs<IEval>(
                    (localEnv, localForward, localStore, localGiven) -> curryN(
                            intSubtract(n, lit(1)),
                            partialAppN(a, (e,f,s,g) -> localGiven))
                            .eval(env, forward, store, given)
            );
        };
    }

    @Override
    default IEval uncurry(IEval f) {
        return abs(apply(apply(f, project(lit(0), given())), project(lit(1), given())));
    }

}