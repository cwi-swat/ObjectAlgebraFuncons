match 1 with
    2 -> 2
  | _ -> 1
;;
(* - : int = 1 *)

match 1 with 
    x -> x
  | _ -> 2
;;
(* - : int = 1 *)

match 1 with 
    _ -> 1
  | _ -> 2 
;;
(* - : int = 1 *)

match 1 with 
    1 -> 1
  | _ -> 2
;;
(* - : int = 1 *)

match 1 with 
    1 as x -> x
  | _ -> 2
;;
(* - : int = 1 *)

match 1 with 
    2 as x -> x
  | _ -> 2
;;
(* - : int = 2 *)

match 1 with
    (1:int) -> 1
  | _ -> 2
;;
(* - : int = 1 *)