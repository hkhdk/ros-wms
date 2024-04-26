const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    // lintOnSave: false
    devServer: {
        proxy:{
          '/wms':{
            target:'http://192.168.0.100',
            changeOrigin:true
          }
        }
      }
})
