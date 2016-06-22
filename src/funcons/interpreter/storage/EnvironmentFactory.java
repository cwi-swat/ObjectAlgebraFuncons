package funcons.interpreter.storage;

import funcons.algebras.collections.MapAlg;
import funcons.algebras.storage.EnvironmentAlg;
import funcons.carriers.IEval;
import funcons.values.Environment;
import funcons.values.ids.Id;
import funcons.values.ids.NameId;

public interface EnvironmentFactory extends
        MapAlg<IEval>,
        EnvironmentAlg<IEval> {

    @Override
    default IEval id(java.lang.String s) {
        return (env, forward, store, given) -> new Id(s);
    }

    @Override
    default IEval nameId(java.lang.String namespace, java.lang.String id) {
        return (env, forward, store, given) -> new NameId(namespace, id);
    }

    @Override
    default IEval bindValue(IEval id, IEval exp) {
        return (env, forward, store, given) ->
                new Environment(((Id)id.eval(env, forward, store, given)), exp.eval(env, forward, store, given));
    }

    @Override
    default IEval boundValue(IEval id) {
        return null;
        //return (env, forward, store, given) -> env.val((Id) id.eval(env, forward, store, given));
    }

    @Override
    default IEval closure(IEval x, IEval environment) {
        return null;
        //return (env, forward, store, given) -> x.eval((Environment)environment.eval(env, forward, store, given), forward, store, given);
    }

    @Override
    default IEval scope(IEval localBindings, IEval exp) {
        return null;
        //return (env, forward, store, given) ->
        //        exp.eval(env.join((Environment)localBindings.eval(env, forward, store, given)), forward, store, given);
    }

    @Override
    default IEval environment() {
        return (env, forward, store, given) -> new Environment();
    }

    @Override
    default IEval accum(IEval environment, IEval decl) {
        return (env, forward, store, given) -> {
            Environment currentEnv = (Environment)environment.eval(env, forward, store, given);
            return scope((e,f,s,g) -> currentEnv, mapOver(decl, (e,f,s,g) -> currentEnv)).eval(env, forward, store, given);
        };
    }
}
