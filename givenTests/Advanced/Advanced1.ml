let x = 1 ;;
(* x : int = 1 *)

let y = function _ -> x ;;
(* y : 'a -> int = <fun> *)

let f = function x -> if x then y () else 2 ;;
(* f : bool -> int = <fun> *)

f true ;;
(* - : int = 1 *)
