let rec fib = function n ->
  if n < 2 then n else ((fib(n-1)) + (fib(n-2)));;
(* fib : int -> int = <fun> *)

fib 6;;
(* - : int = 8 *)