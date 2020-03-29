# fabric

scalajs-react bindings to [office-ui-fabric-react](https://github.com/OfficeDev/office-ui-fabric-react).

Pure WIP but highy usable. fabric is changing rapidly still but starting to gel.

To use this, ensure you include the js library in your bundling process.

# Styling

To integrate with fabric's `merge-styles` we need to define the appropriate
scala side data structures, ideally without any significant "build'
overhead. `merge-styles` is fabric's css-in-js solution that is a replacement
for their current SCSS and glamour based styling model.

Here's the essential nut we need to express in scala. Notice the recursive data
structures which is quite popular in typescript. Those are hard to do in the
current scala compiler. Dotty should help with this but that's a future.

```typescript
export interface IRawStyleBase {
  backgroundColor: string;
  // ...100 more CSS styling properties...
}

export interface IRawStyle extends IRawStyleBase {
  displayName?: string;
  selectors?: {
    [key: string]: IStyle;
  };
}

export type IStyleBase = IRawStyle | string | false | null | undefined;
export interface IStyleBaseArray extends Array<IStyle> {}
export type IStyle = IStyleBase | IStyleBaseArray;
```

- Can we express that in pure scala.js js data structures while remaining typed?
- If not, what's the minimal that we need? Abstract data types, fixed point types.

# Generator

The generator generates some large constant objects...to save me typing.
