module.exports = {
    "env": {
        "browser": true,
        "es2020": true,
        "node": true
    },
    "extends": [
        "eslint:recommended",
        "plugin:vue/essential"
        // "plugin:@typescript-eslint/eslint-recommended",
        // "plugin:@typescript-eslint/recommended"
    ],
    "parserOptions": {
        parser :'babel-eslint'
       // "ecmaVersion": 11,
       // "parser": "@typescript-eslint/parser",
       // "sourceType": "module"
    },
    "plugins": [
        "vue"
         //"@typescript-eslint"
    ],
    "rules": {
        "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
        'no-undef' :'off',
        'vue/no-unused-vars':'off',
        'vue/require-v-for-key':'off',
        "no-irregular-whitespace":"off",
        'no-unused-vars':'off',
        "vue/no-unused-components": "off",
        "space-before-function-paren":0,
    }
};
