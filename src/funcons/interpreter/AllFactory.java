package funcons.interpreter;

import funcons.carriers.IEval;
import funcons.interpreter.auxiliary.PrintFactory;
import funcons.interpreter.auxiliary.RascalIntoFloatConversionFactory;
import funcons.interpreter.collections.*;
import funcons.interpreter.controlflow.ExceptionFactory;
import funcons.interpreter.controlflow.RascalLogicControlFactory;
import funcons.interpreter.functions.CurryFactory;
import funcons.interpreter.functions.FunctionFactory;
import funcons.interpreter.functions.PatternFactory;
import funcons.interpreter.recursion.RecursiveFactory;
import funcons.interpreter.storage.RascalEnvironmentFactory;
import funcons.interpreter.storage.StoreFactory;
import funcons.interpreter.storage.SupplyGivenFactory;
import funcons.interpreter.types.PolyTypeFactory;
import funcons.interpreter.types.TypeFactory;
import funcons.interpreter.values.*;

public interface AllFactory extends funcons.algebras.AllAlg<IEval>,
        StoreFactory,
        //EnvironmentFactory,
        RascalEnvironmentFactory,
        SupplyGivenFactory,
        //BoolFactory,
        RascalBoolFactory,
        CurryFactory,
        PatternFactory,
        ExceptionFactory,
        //FloatFactory,
        RascalFloatFactory,
        FunctionFactory,
        //IntFactory,
        RascalIntFactory,
        //IntFloatConversionFactory,
        RascalIntoFloatConversionFactory,
        //ListFactory,
        RascalListFactory,
        //LogicControlFactory,
        RascalLogicControlFactory,
        //MapFactory,
        RascalMapFactory,
        NullFactory,
        PolyTypeFactory,
        PrintFactory,
        //RecordFactory,
        RascalRecordFactory,
        RecursiveFactory,
        StringFactory,
        //TupleFactory,
        RascalTupleFactory,
        TypeFactory,
        //VectorFactory,
        RascalVectorFactory
{}
