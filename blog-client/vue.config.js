const webpack = require('webpack')

module.exports = {
  devServer: {
    port: 9998,
    open: true
  },
  lintOnSave: false,
  // chainWebpack: config => {
  //   config.plugin('provide').use(webpack.ProvidePlugin, [{
  //     'window.Quill': 'quill/dist/quill.js',
  //     'Quill': 'quill/dist/quill.js'
  //   }])
  // }
}
