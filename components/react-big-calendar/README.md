# react-big-calendar

From [react-big-calendar](https://github.com/intljusticemission/react-big-calendar).

You'll need to load the .css styles:
```scala
// Don't forget...and it will be dead-code removed if you don't reference it elsewhere
@JSImport("react-big-calendar/lib/css/react-big-calendar.css", JSImport.Namespace)
// or if you have odd settings on your import, use !! syntax to revert to defaults
@JSImport("!!style-loader!css-loader!react-big-calendar/lib/css/react-big-calendar.css", JSImport.Namespace)
object styles extends js.Object
```
With the use of any of the imports, the compiler may optimize out the object if its not reference somewhere. So, to ensure its in your final code, you should something like:
```scala
private val _styles = styles
```


The react-big-calendar API is very difficult to model correctly. Be prepared.

# API

More notes here...
