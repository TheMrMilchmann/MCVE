/*
 * Copyright (c) 2023-2024 Leon Linhart
 * All rights reserved.
 */
// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    srcDir: "src",

    app: {
        baseURL: "/"
    },

    content: {
        contentHead: false,
        documentDriven: true,
        highlight: {
            preload: [
                "java",
            ],
            theme: {
                default: "github-light"
            }
        }
    },

    modules: [
        "@nuxt/content"
    ],

    typescript: {
        strict: true,
        typeCheck: true
    }
})