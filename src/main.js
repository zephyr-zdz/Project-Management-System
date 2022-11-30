// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

import {
  Menu, Submenu, MenuItem, MenuItemGroup,
  Button, ButtonGroup,
  Dialog, Message, MessageBox,
  Form, FormItem, Input,
  Select, Option, OptionGroup,
  Container, Header, Aside, Main,
  Radio, RadioButton,
  Table, TableColumn,
  Card,
  Descriptions,DescriptionsItem,
  Popconfirm, Popover,
  Switch,
  TimeSelect, DatePicker,
  Upload,
  Divider,
  Row, Col,
} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(Submenu)
Vue.use(MenuItemGroup)
Vue.use(Button)
Vue.use(ButtonGroup)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Container)
Vue.use(Header)
Vue.use(Aside)
Vue.use(Main)
Vue.use(Radio)
Vue.use(RadioButton)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Dialog)
Vue.use(Select)
Vue.use(Option)
Vue.use(OptionGroup)
Vue.use(Card)
Vue.use(Descriptions)
Vue.use(DescriptionsItem)
Vue.use(Popconfirm)
Vue.use(Switch)
Vue.use(TimeSelect)
Vue.use(DatePicker)
Vue.use(Upload)
Vue.use(Popover)
Vue.use(Divider)
Vue.use(Row)
Vue.use(Col)
Vue.component(MessageBox.name, MessageBox)
Vue.component(Message.name, Message)


var axios = require('axios')
axios.defaults.baseURL = '/api/'
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.config.productionTip = false


router.beforeEach((to, from, next) => {
    let role = 'guest'
    if (window.localStorage.user !== undefined) {
      console.log(typeof(window.localStorage.user)) // string
      let jsonObj = JSON.parse(window.localStorage.user);
      // console.log(typeof(jsonObj)) // obj
      console.log(jsonObj)
      role = jsonObj.user.role
    }
    console.log("role is " + role)
    if(to.meta.roles.includes(role)){
      next()
    } else {
      next({
        path: 'login',
        query: {redirect: to.fullPath}
      })
    }
  }
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store, // for hook
  components: { App },
  template: '<App/>'
})

Vue.prototype.$alert = MessageBox
Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox.confirm;
