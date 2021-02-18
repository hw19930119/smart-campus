// 额外配置，会和默认配置合并
// env下的配置会和其他配置合并，对应不同环境下的最终配置
const port = 4046;
const ROOT_PATH = "/smart-campus";
const clientPath = 'http://localhost:8080';
// const clientPath = 'http://192.168.2.109:8080';
let path = require('path');
const { default : getBabelOptions } = require('@yo/share-kit/lib/utils/getBabelOptions');
module.exports = {
    port,
    env: {
        development: {
            extraBabelOptions: {
                plugins: ['dva-hmr'],
            }
        },
        production: {
            extraBabelOptions: {},
            cleanExclude: [ 'index.html'],
            outputPath: '../src/main/resources/public'
        }
    },
    extraBabelOptions: {
        plugins: [['import', {libraryName: 'antd', libraryDirectory: 'es', style: 'css'}]],
    },
    extraProvidePlugin: {
        $: 'jquery',
        jquery: 'jquery',
        jQuery: 'jquery'
    },
    alias: {
        components: './src/components',
        '@':path.resolve('src')
    },
    proxy: {
        '/remote.action': {
            target: clientPath,
            secure: false,
            changeOrigin: true
        },
        '/scurd/remote.action': {
            target: clientPath,
        },
        '/smart-campus/': {
            target: clientPath,
        },
        '/smart-campus/*': {
            target: clientPath
        },
        '/api': {
            target: 'http://192.168.0.62:3000/mock/677',
            changeOrigin: true
        }
    },
    theme: './src/theme.js',
    extraEntrys: {
        query: './node_modules/@share/scurd/main.js',
        adminIndex: './node_modules/@share/scurd/admin/index.js',
        apply:'./src/pages/apply/index.js',
        manager:'./src/pages/manager/index.js',
        audit:'./src/pages/audit/index.js',
        role:'./src/admin/index.js',
    },
    extraHtmls: [
        {
            filename: 'query.html',
            title: '在线表单',
            inject: true,
            template: './node_modules/@share/scurd/index.html',
            chunks: ['query']
        },
        {
            filename: 'admin/index.html',
            title: '在线表单',
            inject: true,
            template: './node_modules/@share/scurd/admin/index.html',
            chunks: ['adminIndex']
        },
        {
            filename: 'apply.html',
            title: '我要申报',
            inject: true,
            template: './src/pages/apply/index.html',
            chunks: ['apply']
        },
        {
            filename: 'manager.html',
            title: '配置指标',
            inject: true,
            template: './src/pages/manager/index.html',
            chunks: ['manager']
        },
        {
            filename: 'audit.html',
            title: '智慧校园流程审批',
            inject: true,
            template: './src/pages/audit/index.html',
            chunks: ['audit']
        },
        {
            filename: 'role.html',
            title: '角色权限控制',
            inject: true,
            template: './src/admin/index.html',
            chunks: ['role']
        }
    ],
    extraRules: [
        {
            test: /\.(js|jsx)$/,
            loader: 'babel-loader',
            include: [ path.resolve('./node_modules/copy-text-to-clipboard')],
            options: getBabelOptions([],[])
        },
        {
            test: /eos3(\.min)?\.js$/,
            use: [
                {
                    loader: 'imports-loader?defined=>false,this=>window'
                },
                {
                    loader: 'exports-loader?eos'
                }
            ],
            exclude: /node_modules/
        },
        {
            test: /Service\.js$/,
            use: 'imports-loader?define=>false,this=>window',
            exclude: /node_modules/
        },
        {
            test: /\.js$/,
            use: 'imports-loader?define=>false,this=>window,template=art-template',
            include: /ulynlist-ext/
        },
        {
            test: /(ulynlist\.js$)|(ulynlist.table\.js$)|(ulynlist.pagebar\.js$)/,
            use: 'imports-loader?define=>false,this=>window,template=art-template'
        },
        {
            test: /template\.js$/,
            use: [
                {
                    loader: 'imports-loader?this=>window,define=>false'
                },
                {
                    loader: 'exports-loader?template=window.template'
                }
            ]
        },
        {
            test: /ext\.js$/,
            use: 'imports-loader?define=>false,this=>window,template=art-template',
            include: /shareTab/
        },
        {
            test: /zeus\.auth/,
            use: [
                {
                    loader: 'imports-loader?define=>false,this=>window'
                }
            ]
        },
        {
            test: /\.(js|jsx)/,
            include: [
                path.resolve(__dirname, './node_modules/@bundled-es-modules'),
                path.resolve(__dirname, './node_modules/react-pdf-js'),
            ],
            loader: 'babel-loader',
            options: getBabelOptions([], [])
        },

    ],
    isDve: true,
    publicPath: ROOT_PATH,
// port: 4000,
// extraEntrys: {},//额外的入口
// extraHtmls: [],//额外的html页面，搭配extraEntrys使用
// extraRules: [],//额外的规则
// disableCSSModules: false,//是否开启css_modules
// cssModulesExclude: [],//指定文件或文件不需要css_modules
// publicPath: '/',//指定资源文件引用的目录 ，同webpack的publicPath
// outputPath: '/',//用来配置打包生成的文件输出的位置，同webpack的outputPath
// extraBabelOptions: {},//额外的babel选项
// extraResolveExtensions: [],//额外的解析扩展
// hash: true,//打包的文件名是否带hash
    devtool:'#cheap-module-source-map', // 此选项控制是否生成，以及如何生成 source map
// autoprefixer: {},//postcss的插件autoprefixer配置
// proxy: {},//webpack-dev-server的proxy设置,用于代理接口请求
// externals: {},//webpack的外部扩展，不会打包进源码中，同webpack的externals
// library为生成的函数名称的输出，libraryTarget为控制 webpack 打包的内容是如何暴露的，同webpack的libraryTarget
// library: '',//配合libraryTarget使用，同webpack的library
// libraryTarget: 'var',
// 同webpack中的webpack.DefinePlugin配置项，编译时期创建全局变量，该特性适用于开发版本同线上版本在某些常量上有区别的场景
// define: {},
// build的时候保留输出目录下不想被删除的文件夹或文件,shareui-kit版本需大于0.7.2;路径相对于打包完的路径
// cleanExclude: ["./wxFront"],
// sassOption: {},//sass-loader的配置
// theme: '',//less-loader的theme配置的文件路径
// extraProvidePlugin: {},//自动加载模块，而不必到处 import 或 require
// alias: {}, // 别名,让后续引用的地方减少路径的复杂度,同webpack的alias配置
// isDve: false,//是否是在线表单项目或者dve项目
}
;
