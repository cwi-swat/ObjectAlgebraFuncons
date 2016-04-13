package funcons.tests;

import funcons.Store;
import funcons.algebras.ExceptionAlg;
import funcons.interpreter.ExceptionFactory;
import funcons.sorts.IEval;
import funcons.types.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionFactoryTest {

    private ExceptionAlg<IEval> alg;

    @Before
    public void setUp() throws Exception {
        alg = new ExceptionFactory() {};
    }


    @Test
    public void testFail() throws Exception {
        try {
            alg.fail().eval(new Environment(), new Store(), new Null());
        } catch (FailureTrueException ignored) {
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testElse_() throws Exception {
        Int i = (Int)alg.else_(alg.fail(), alg.lit(3)).eval(new Environment(), new Store(), new Null());
        assertEquals(i.intValue(), new Integer(3));

        i = (Int)alg.else_(alg.lit(2), alg.lit(3)).eval(new Environment(), new Store(), new Null());
        assertEquals(i.intValue(), new Integer(2));
    }

    @Test
    public void testThrow_() throws Exception {
        try {
            alg.throw_((env, store, given) -> new FailureTrueException()).eval(new Environment(), new Store(), new Null());
        } catch(FailureTrueException s) {
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testCatch_() throws Exception {
        IEval c = alg.catch_(alg.throw_((env, store, given) -> new FailureTrueException()), alg.abs(alg.given()));
        FailureTrueException e = (FailureTrueException)c.eval(new Environment(), new Store(), new Null());
        assertNotNull(e);
    }

    @Test
    public void testCatchElseRethrow() throws Exception {
        IEval fail = alg.throw_((env, store, given) -> new FailureTrueException());

        IEval c = alg.catchElseRethrow(fail, alg.abs(alg.given()));
        FailureTrueException e = (FailureTrueException)c.eval(new Environment(), new Store(), new Null());
        assertNotNull(e);

        try {
            alg.catchElseRethrow(fail, fail).eval(new Environment(), new Store(), new Null());
        } catch(FailureTrueException exception) {
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testPreferOver() throws Exception {
        IEval f1 = alg.abs(alg.seq(alg.fail(), alg.bool(false)));
        IEval f2 = alg.abs(alg.bool(true));

        Bool b = (Bool)alg.apply(alg.preferOver(f1, f2), alg.lit(0)).eval(new Environment(), new Store(), new Null());
        assertTrue(b.boolValue());

        b = (Bool)alg.apply(alg.preferOver(f2, f1), alg.lit(1)).eval(new Environment(), new Store(), new Null());
        assertTrue(b.boolValue());
    }
}
