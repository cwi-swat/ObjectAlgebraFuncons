package funcons.algebras;

public interface ApplyAlg<E> extends BindAlg<E> {
    E abs(E exp);
    E apply(E abs, E arg);
    E bind(E var);
}