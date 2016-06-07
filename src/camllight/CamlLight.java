package camllight;

import camllight.lib.StandardLibrary;
import camllight.parser.CLLexer;
import camllight.parser.CLParser;
import funcons.algebras.RecordAlg;
import funcons.carriers.IEval;
import funcons.entities.Forwards;
import funcons.entities.Store;
import funcons.values.Environment;
import funcons.values.Null;
import funcons.values.Value;
import funcons.values.ids.Id;
import funcons.values.signals.FunconException;
import noa.proxy.Recorder;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class CamlLight {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <X> X parse(String s, camllight.algebras.AllAlg alg) {
        CLLexer lexer = new CLLexer(new ANTLRInputStream(s));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParser parser = new CLParser(tokens);
        parser.setBuilder(alg);
        return (X) parser.prog()._prog;
    }

    public static funcons.values.Value eval(String src) throws FunconException {
        Recorder builder = parse(src, Recorder.create(camllight.algebras.AllAlg.class));
        IEval eval = builder.build((camllight.algebras.AllAlg<IEval>) () -> new funcons.interpreter.RecordFactory() {});
        Environment env = importStandardLibrary(new Environment());
        return eval.eval(env, new Forwards(), new Store(), new Null());
    }

    private static void interpret(String src) throws FunconException {
        System.out.println("== Interpreting ==");
        System.out.println(src);
        System.out.println("Print output: ");
        eval(src);
        System.out.println();
        System.out.println();
    }

    private static void run(String fileLoc) throws IOException, FunconException {
        String fileContent = new String(Files.readAllBytes(Paths.get(fileLoc)));
        System.out.println("== Running: " + fileLoc + " ==");
        interpret(fileContent);
    }

    private static void runAll(String folderLoc) throws IOException, FunconException {
        File folder = new File(folderLoc);
        File[] files = folder.listFiles();

        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".ml")) {
                run(f.getAbsolutePath());
            } else if (f.isDirectory()) {
                runAll(f.getAbsolutePath());
            }
        }
    }

    private static Environment importStandardLibrary(Environment env) throws FunconException {
        funcons.algebras.RecordAlg<IEval> alg = new funcons.interpreter.RecordFactory() {};
        StandardLibrary<IEval> lib = () -> alg;

        for (Method m : lib.getClass().getMethods()) {
            java.lang.String methodName = m.getName();
            if (!methodName.endsWith("Fun")) {
                continue;
            }
            methodName = methodName.substring(0, methodName.length() - 3);

            try {
                final Environment env2 = env;
                env = (Environment)alg.mapUnion(
                        (e,f,s,g) -> env2,
                        alg.bindValue(alg.id(methodName), (funcons.carriers.IEval)m.invoke(lib))
                ).eval(env, new Forwards(), new Store(), new Null());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return env;
    }

    public static void main(String[] args) throws FunconException, IOException {
        interpret("let _1 = 5;; _1;; (make_vect (3 + _1) _1).(2);;");
        //runAll("examples");
        //runAll("givenExamples/Basic");
        //run("examples/fib.cl");
        //run("examples/sieve.cl");
        //interpret("type foo = A of int | B;;\nlet f = function A _ -> true | B -> false;;\nf B;;");
        /*interpret("fun x -> if x mod 2 = 0 then true else false 6;;");
        interpret(
                "let rec filter = function [] -> [] | [h :: t] -> if h = 3 then [h :: filter r] else filter r;;" +
                "filter [1,2,3];;");*/
        //interpret("exception Bar;; exception Foo of int;; try raise Foo 3 with Bar -> 0 | Foo 2 -> 1 | Foo 3 -> 2;;");
        //interpret("type f = Foo of int;; Foo 3;;");
        //interpret("type ('a , 'b) pair = {fst:'a , snd:'b};;");
        //interpret("let f = function (a:'a) -> a;; f 2;;");
        //interpret("type person = {name:string , age:int};;");
        //interpret("type id = Name of string | SS of int * int;; let f = function SS (1, 11) -> 1 | _ -> -1;; f (SS (1, 11));;");
        //interpret("type suit = Heart | Diamond | Club | Spade;;" +
        //        " let f = function Heart -> true | _ -> false;; f Club;;");
        //interpret("let name_of = function {name = n , age = _} -> n;; name_of {age = 3 , name = \"foo\"};;");
        //interpret("not false = false;;");
        //interpret("match `a` with `a` -> true;;");
        //interpret("match \"hello\" with \"hello\" -> true;;");
        //interpret("match 1.0 with 1. -> true;;");
        //interpret("match 3 with | | | | | 3 -> true;;");
        //interpret("let isInt = function | 0 -> true | _ -> false ;; isInt 0;;");
        //interpret("let power a b = if b = 0 then 1 else a * power a (b - 1) in power -3 3;;");
        //interpret("let mul a b = if b = 0 then 0 else a + mul a (b - 1) in mul 3 200;;");
        //interpret("let rec x = 4 and countup x y = if x = y then print y else (print x ; countup (x + 1) y) ;; countup 0 10;;");
        //interpret("let add (x : (foo -> I * can * write * anything * here)) y = x + y in add 5 3;;");
        //interpret("[1 ; 2 ; 3] ;; [] ;; () ;; 1 ;;");
        //interpret("let countup = fun x -> (if x = 3 then 3 else (print x ; countup (x + 1))) ;; (countup 0);;");
        //interpret("let count x = (if x = 3 then 3 else (print x ; count (x + 1))) in count 0;;");
        //interpret("let smallerThan = function | [x :: y :: _] -> x < y in smallerThan [0 ; 1];;");
        //interpret("(  );;");
        //interpret("[ ];;");
        //interpret("let test = function (0 | 1) -> true | _ -> false in test 10;;");
        //interpret("(fun (x : int) -> print x ; x + 1) 0;;");
        //interpret("(1 : int);;");
        //interpret("let test = function | 0 as y -> y + 1 | _ -> 0 in test 0;;");
        //interpret("let isZero = fun x -> match x with | 0 -> true | _ -> false and x = 0 in isZero x;;");
        //interpret("let t = (1,2) and add = fun (x,y) -> x + y in add t;;");
        //interpret("let x = 3 in x + 1;;");
        //interpret("let x = 3 in fun y -> y + x 4;;");
        //interpret("let x = 3 in let y = 5 in x + y;;");
        //interpret("(fun a ((b,c), d) -> a + b + c + d) 1 ((2,3),4);;");
        //interpret("function | 1 -> 4 | 2 -> 5 | 3 -> 6 | _ -> 0 2;;");
        //interpret("fun a b c d e f -> a + b + c + d + e + f 1 2 3 4 5 6;;");
        //interpret("1;;");
        //interpret("for i = 10 downto 1 do print i done;;");
        //interpret("(print 10) ; begin if 7 < 3 then 1 else 2 + 3 end = 5;;");
        //interpret("not false or true;;");
        //interpret("let countup = function | 10 -> print 10 | _ as x -> (print x ; countup x + 1) in countup 0;;");
    }
}