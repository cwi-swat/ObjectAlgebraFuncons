"Hello world";;
(* - : string = "Hello world" *)

let l = (["is", "a", "tale", "told", "etc."] : string list);;
(* l : string list = ["is"; "a"; "tale"; "told"; "etc."] *)

("Life" :: l : string list);;
(* - : string list = ["Life"; "is"; "a"; "tale"; "told"; "etc."] *)
