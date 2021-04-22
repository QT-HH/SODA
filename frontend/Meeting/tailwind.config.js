const colors = require("tailwindcss/colors");

module.exports = {
  purge: { content: ["./public/**/*.html", "./src/**/*.vue"] },
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {},
    colors: {
      transparent: "transparent",
      current: "currentColor",
      black: colors.black,
      white: colors.white,
      gray: colors.coolGray,
      coolGray: colors.trueGray,
      blue: colors.blue,
      cyan: colors.cyan,
      lightBlue: colors.lightBlue,
      violet: colors.violet,
      purple: colors.purple,
      amber: colors.amber,
      teal: colors.teal,
      red: colors.red
    }
  },
  variants: {
    extend: {}
  },
  plugins: [require("@tailwindcss/forms")]
};
