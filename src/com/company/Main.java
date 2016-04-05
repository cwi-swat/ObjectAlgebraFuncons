package com.company;

import DSL.ExpControlFactory;

import DSL.algebras.WhileTrueAlg;
import funcons.algebras.IntCalcAlg;
import funcons.algebras.LogicIfTrueAlg;
import funcons.algebras.LogicWhileTrueAlg;
import funcons.interpreter.LogicIfTrueFactory;
import funcons.interpreter.IntCalcFactory;
import funcons.interpreter.LogicWhileTrueFactory;
import funcons.prettyprinter.PrintableLogicControlFactory;
import funcons.prettyprinter.PrintableIntCalcFactory;
import funcons.sorts.IEval;
import funcons.sorts.IPrint;

public class Main {

    public static void main(java.lang.String[] args) {
        IntCalcAlg<IEval> funcIntCalcAlg = new IntCalcFactory() {};
        LogicIfTrueAlg<IEval> funcIfTrueAlg = new LogicIfTrueFactory() {};
        LogicWhileTrueAlg<IEval> funcWhileTrueAlg = new LogicWhileTrueFactory() {};
        WhileTrueAlg<IEval> expControlAlg = new ExpControlFactory<IEval>() {
            @Override
            public IntCalcAlg<IEval> funconAlg() {
                return funcIntCalcAlg;
            }

            @Override
            public LogicIfTrueAlg<IEval> ifTrueAlg() {
                return funcIfTrueAlg;
            }

            @Override
            public LogicWhileTrueAlg<IEval> whileTrueAlg() {
                return funcWhileTrueAlg;
            }
        };

        IntCalcAlg<IPrint> funcPrintIntCalcAlg = new PrintableIntCalcFactory() {};
        PrintableLogicControlFactory printableLogicControlFactory = new PrintableLogicControlFactory() {};

        WhileTrueAlg<IPrint> printableControlAlg = new ExpControlFactory<IPrint>() {
            @Override
            public IntCalcAlg<IPrint> funconAlg() {
                return funcPrintIntCalcAlg;
            }

            @Override
            public LogicIfTrueAlg<IPrint> ifTrueAlg() {
                return printableLogicControlFactory;
            }

            @Override
            public LogicWhileTrueAlg<IPrint> whileTrueAlg() {
                return printableLogicControlFactory;
            }
        };

        System.out.println("Exp 1");
        System.out.println(((funcons.types.Int)exp1(expControlAlg).eval()).intValue());
        System.out.println(exp1(printableControlAlg).print().stringValue());
        System.out.println();

        System.out.println("Exp 2");
        System.out.println(exp2(expControlAlg).eval());
        System.out.println(exp2(printableControlAlg).print().stringValue());
        System.out.println();
    }

    public static <A> A exp1(WhileTrueAlg<A> alg) {
        return alg.ifElse(alg.lAnd(alg.bool(false), alg.bool(true)),
                alg.add(alg.lit(3), alg.lit(2)),
                alg.multiply(alg.lit(4), alg.lit(5)));
    }

    public static <A> A exp2(WhileTrueAlg<A> alg) {
        return alg.whileTrue(alg.bool(false), alg.add(alg.lit(3), alg.lit(4)));
    }
}