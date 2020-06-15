package recoil

import scala.scalajs.js
import js.|

import react._


package object extras {

    /** Use for passing in an atom through props or creating a local
    * one for local state management.
    *
    * @see https://github.com/facebookexperimental0/Recoil/issues/216
    */
    def useAtom[T](upAtom: js.UndefOr[RecoilState[T]], init: T) = {
        val (newAtom, _) = useState[RecoilState[T]](() => makeAtom[T](
            js.Date.now().toString,
            init
        ))
        upAtom getOrElse newAtom
    }

}
