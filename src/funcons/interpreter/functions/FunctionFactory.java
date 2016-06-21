package funcons.interpreter.functions;

import funcons.algebras.controlflow.LogicControlAlg;
import funcons.algebras.functions.FunctionAlg;
import funcons.algebras.storage.EnvironmentAlg;
import funcons.algebras.storage.SupplyGivenAlg;
import funcons.algebras.values.IntAlg;
import funcons.carriers.IEval;
import funcons.entities.Forwards;
import funcons.entities.Store;
import funcons.values.Abs;
import funcons.values.Environment;
import funcons.values.List;
import funcons.values.Null;
import funcons.values.properties.Value;

public interface FunctionFactory extends
        IntAlg<IEval>,
        SupplyGivenAlg<IEval>,
        EnvironmentAlg<IEval>,
        LogicControlAlg<IEval>,
        FunctionAlg<IEval> {

    @Override
    default IEval abs(IEval exp) {
        return (env, forward, store, given) -> new Abs<>(exp);
    }

    @Override
    default IEval abs(IEval patt, IEval exp) {
        return (env, forward, store, given) -> new Abs<IEval>((e, f, s, g) -> {
            @SuppressWarnings("unchecked")
            IEval environment = ((Abs<IEval>)patt.eval(e, f, s, g)).body();
            return scope(environment, exp).eval(e, f, s, g);
        });
    }

    @Override
    default IEval apply(IEval abs, IEval arg) {
        return (env, forward, store, given) -> supply(arg, unAbs(abs, env, forward, store, given)).eval(env, forward, store, given);
    }

    @Override
    default IEval applyToEach(IEval a, IEval l) {
        return (env, forward, store, given) -> {
            List list = (List)l.eval(env, forward, store, given);
            Value head = list.head();
            List tail = list.tail();

            if (list.equals(new List())) {
                return new Null();
            }

            return seq(apply(a, (e,f,s,g) -> head), applyToEach(a, (e,f,s,g) -> tail)).eval(env, forward, store, given);
        };
    }

    @Override
    default IEval compose(IEval f, IEval g) {
        return abs(apply(f, apply(g, given())));
    }

    @Override
    default IEval closure(IEval x, IEval environment) {
        return (env, forward, store, given) -> x.eval((Environment)environment.eval(env, forward, store, given), forward, store, given);
    }

    @Override
    default IEval close(IEval abs) {
        return (env, forward, store, given) ->
                abs(closure(unAbs(abs, env, forward, store, given), (e, f, s, g) -> env)).eval(env, forward, store, given);
    }

    @Override
    default IEval bind(IEval id) {
        return abs(bindValue(id, given()));
    }

    default IEval unAbs(IEval abs, Environment e, Forwards f, Store s, Value g) {
        return (env, forward, store, given) -> {
            @SuppressWarnings("unchecked")
            IEval result = ((Abs<IEval>)abs.eval(e, f, s, g)).body();
            return result.eval(env, forward, store, given);
        };
    }
}