## vue学习记录

1.使用vue-cli生成项目

2.引入element-ui
    2.1 安装：npm i element-ui -S
        安装完成后package.json会添加类似Java中jar依赖的js依赖
        ##package.json和package-lock.json的知识点：
            npm install的时候会把安装的内容记录在package.json中，为了系统的稳定性考虑，
            每次执行完npm install之后会对应生成package-lock文件，该文件记录了上一次安装的具体的版本号。
            当执行npm install的时候， node会先从package.json文件中读取所有dependencies信息，然后根据dependencies中的信息与node_modules中的模块进行对比，
            没有的直接下载，node是从package.json文件读取模块名称，从package-lock.json文件中获取版本号，然后进行下载或者更新。
            当package.json与package-lock.json都不存在，执行"npm install"时，node会重新生成package-lock.json文件，然后把node_modules中的模块信息全部记入package-lock.json文件，
            但不会生成package.json文件。但是，你可以通过"npm init --yes"来生成package.json文件
    2.2 main.js中添加
       2.2.1 import ElementUI from 'element-ui';
             import 'element-ui/lib/theme-chalk/index.css';
       2.2.2 Vue.use(ElementUI);
       
3.引入 normalize.css（Normalize的作用就是统一浏览器的初始样式）
    3.1 安装：npm install --save normalize.css
    3.2 引用：main.js添加 import 'normalize.css/normalize.css'
    3.3 报错：如果引入报错，可能没有安装css-loader 和style-loader, npm install css-loader style-loader

4.引入svg-icon
      4.1 components文件下新建SvgIcon组件
      4.2 将所需svg文件放置icons的svg文件夹下
      4.3 main.js中引入icons文件 
      4.4 安装svg-sprite-loader （1. npm install sass-loader   2. npm install node-sass）
      4.5 在vue.config.js文件下配置
      4.6 在@/icons/index.js中组件注册 Vue.component('svg-icon', SvgIcon)
      
5.配置vue-router
    5.1 安装 ：npm install vue-router
    5.2 router目录下生成index.vue
    5.3 配置router的index.vue
    ...
    
    
6.配置vuex
    6.1 安装：npm install vuex --save  
    6.2 vuex核心概念: 
        State(共享状态,即变量) 
        Getter(基于state的派生状态，可理解为组件中的计算属性)
        Mutation(更改vuex的store中状态的唯一方法,同步操作规则上是不允许异步操作的，虽然异步也可以执行，但是对devtool调试的状态跟踪或多个状态更改操作相互依赖是很不好的)
        Action(类似mutation，不同之处，1.通过提交mutation修改状态   2.支持异步操作)
        Module(模块，在大型项目中为了方便状态的管理和协作开发将store拆分为多个子模块（modules），
        每个子模块拥有完整的state、mutation、action、getter)
    6.3 创建store文件夹，创建index.js(名字随意)，引入vue和vuex
    6.4 main.js中引入import store from './store' 注册store实例
    6.5 创建modules文件夹，创建user.js
        在user.js中声明user模块：const user = { state：{},mutations:{},actions:{} }
    ...
    
    
7.配置axios
    7.1 安装：npm install --save axios
    ...
8.配置vue-treeselect
    8.1 安装：npm install --save @riophae/vue-treeselect

9.配置 vue-cropper
    9.1 安装：npm install --save vue-cropper
    
    
10.