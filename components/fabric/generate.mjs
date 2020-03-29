// run in this dir: node --experimental-modules generate.mjs

import { default as styling } from "@uifabric/styling";
import { default as fs } from "fs"
//import { ResponsiveMode } from "office-ui-fabric-react/lib/utilities/decorators/withResponsiveMode"
//import { ResponsiveMode } from "office-ui-fabric-react/lib/utilities/decorators/withResponsiveMode"
//import { IconNames } from "@uifabric/icons"

export const log = (str) => console.log(str)

export const native_trait = str =>
  `@js.native\ntrait ${str} extends js.Object {`;

export const with_trait = (str, thunk) => {
  return native_trait(str) + "\n" + thunk() + "\n}\n\n";
};

export const with_module = (str, thunk) => {
  return `object ${str} {\n` + thunk() + "\n}\n\n";
};

export function write(f, thunk) {
  fs.writeFileSync(f, thunk())
}

export const type_map = {
  string: "String",
  number: "Int",
  number_Float: "Float"
};

export const is_type = (arg, possible) => possible.includes(arg)

export const astyles = () => {
  return with_trait("IAnimationStyles", () => {
    return Object.entries(styling.AnimationStyles).map(s => {
      const t = typeof s[1];
      const key = s[0];
      return `val ${key}: IRawStyle = js.native`
    }).join("\n");
  });
};

export const iconfontsizes = () => {
  return with_trait("IIconFontSizes", () => {
    return Object.entries(styling.IconFontSizes).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const fontweights = () => {
  return with_trait("IFontWeights", () => {
    return Object.entries(styling.FontWeights).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const fontsizes = () => {
  return with_trait("IFontSizes", () => {
    return Object.entries(styling.FontSizes).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const commontypes = () => {
  return with_trait("styles_Common", () => {
    return Object.entries(styling).map(s => {
      if (is_type(s[1], ["string", "number"]) && (s[0].startsWith("High") || s[0].startsWith("Screen"))) {
        const t = type_map[s[1]]
        return `val ${s[0]}: ${t} = js.native`
      }
      else return ""
    }).filter(f => f).join("\n")
  })
}

export const effects = () => {
  return with_trait("IEffects", () => {
    return Object.entries(styling.DefaultEffects).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const colorclassnames = () => {
  return with_trait("IColorClassNames", () => {
    return Object.entries(styling.ColorClassNames).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const fontclassnames = () => {
  return with_trait("IFontClassNames", () => {
    return Object.entries(styling.FontClassNames).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const animationclassnames = () => {
  return with_trait("IAnimationClassNames", () => {
    return Object.entries(styling.AnimationClassNames).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const fontstyles = () => {
  return with_trait("IFontStyles", () => {
    return Object.entries(styling.FontClassNames).map(s => {
      return `val ${s[0]}: IRawStyle = js.native`
    }).join("\n")
  })
}

export const semanticcolors = () => {
  return with_trait("ISemanticColors", () => {
    return Object.entries(styling.getTheme().semanticColors).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const palette = () => {
  return with_trait("IPalette", () => {
    return Object.entries(styling.getTheme().palette).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const spacing = () => {
  return with_trait("ISpacing", () => {
    return Object.entries(styling.getTheme().spacing).map(s => {
      return `val ${s[0]}: String = js.native`
    }).join("\n")
  })
}

export const responsive = () => {
  return with_trait("ResponsiveMode", () => {
    return Object.entries(ResponsiveMode).map(s => {
      return `val ${s[0]}: Int = js.native`
    }).join("\n")
  })
}

// IconNames was deprecated
// export const iconnames = () => {
//   return with_module("IconNames", () => {
//     return Object.entries(IconNames).map(s => {
//       return `val ${s[0]}: String = "${s[1]}"`
//     }).join("\n")
//   })
// }

// run everything
const styles1 = "src/main/scala/styling/styling_styles.scala"
const out = fs.openSync(styles1, "w")
write(out, () => `package fabric\npackage styling\nimport scala.scalajs.js\n`)
write(out, astyles)
write(out, iconfontsizes)
write(out, fontweights)
write(out, fontsizes)
write(out, commontypes)
fs.closeSync(out)

const styles2 = "src/main/scala/styling/styling_classnames.scala"
const out2 = fs.openSync(styles2, "w")
write(out2, () => `package fabric\npackage styling\nimport scala.scalajs.js\n`)
write(out2, colorclassnames)
write(out2, fontclassnames)
write(out2, animationclassnames)
fs.closeSync(out2)


const styles3 = "src/main/scala/styling/styling_interfaces.scala"
const out3 = fs.openSync(styles3, "w")
write(out3, () => `package fabric\npackage styling\nimport scala.scalajs.js\n`)
write(out3, fontstyles)
write(out3, semanticcolors)
write(out3, palette)
write(out3, effects)
write(out3, spacing)
fs.closeSync(out3)

// const styles4 = "src/main/scala/components/components_utilities.scala"
// const out4 = fs.openSync(styles4, "w")
// write(out4, () => `package fabric\npackage components\nimport scala.scalajs.js\n`)
// write(out4, responsive)
// fs.closeSync(out4)

// const styles5 = "src/main/scala/icons/icons_generated.scala"
// const out5 = fs.openSync(styles5, "w")
// write(out5, () => `package fabric\npackage icons\nimport scala.scalajs.js\n`)
// write(out5, iconnames)
// fs.closeSync(out5)





