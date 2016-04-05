package funcons.algebras;

public interface LogicIfTrueAlg<E> {
    E bool(java.lang.Boolean b);

    E ifTrue(E e, E c1, E c2);
}