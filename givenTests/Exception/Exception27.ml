exception E1 and E2 and E3 ;;
(*
Exception E1 defined.
Exception E2 defined.
Exception E3 defined.
*)

for i = raise E1 to raise E2
do
  raise E3
done ;;
(* Uncaught exception: E1 *)
(* But E2 would also be acceptible as the reference manual does not specify. *)