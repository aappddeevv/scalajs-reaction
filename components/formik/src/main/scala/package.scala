
import scala.scalajs.js
import js.|
import js.annotation._

package object formik {
    import formik._

    type TouchedInit = js.UndefOr[js.Object|js.Dynamic|js.Dictionary[Boolean]]
    type ErrorsInit = js.UndefOr[js.Object|js.Dynamic|js.Dictionary[String]]
    type Touched = js.UndefOr[js.Object|js.Dictionary[Boolean]]
    type Errors = js.UndefOr[js.Object|js.Dictionary[String]]
    type SetOrUpdateArg[P] = P | js.Function1[P, P]
    type FieldValidator = js.Function1[Any, String|Unit|js.Promise[String|Unit]]
    type UseFieldResult[P] = js.Tuple3[FieldInputProps[P], FieldMetaProps[P], FieldHelperProps[P]]
    
    @js.native
    @JSImport("formik", "useField")
    def useField[A](props: String|UseFieldProps[A]): UseFieldResult[A] = js.native

    @js.native
    @JSImport("formik", "useField")
    def useFieldByName[A](props: String): UseFieldResult[A]= js.native
    
    @js.native
    @JSImport("formik", "useFormikContext")
    def useFormikContext[P](): ContextType[P] = js.native
}
