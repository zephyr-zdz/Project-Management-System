import Vue from 'vue'
import VueRouter from 'vue-router'
import Welcome from '../components/Welcome'
import Login from '../components/Login';
import Register from "../components/Register";
import UserHome from "../components/user/UserHome";
import UserDash from "../components/user/UserDash";
import ShowProject from "../components/user/ShowProject.vue";
import ShowInvitation from "../components/user/ShowInvitation.vue";
import ProjectContent from "../components/user/ProjectContent.vue";
import ShowMessage from "../components/user/ShowMessage.vue";
import UserInfo from "../components/user/UserInfo.vue";
import ShowMyAssignedList from "../components/user/ShowMyAssignedList.vue";

Vue.use(VueRouter)

export default new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome,
      meta: {
        roles: [ 'guest', 'user' ]
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta: {
        roles: [ 'guest', 'user' ]
      }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
      meta: {
        roles: [ 'guest', 'user' ]
      }
    },
    {
      path: '/user-home',
      name: 'StudentHome',
      component: UserHome,
      children: [
        {
          path: 'dashboard',
          name: 'StudentDash',
          component: UserDash,
          meta: {
            roles: [ 'user' ]
          }
        },
        {
          path: 'user-info',
          name: 'UserInfo',
          component: UserInfo,
          meta: {
            roles: [ 'user' ]
          }
        },
        {
          path: 'my-invitation',
          name: 'MyInvitation',
          component: ShowInvitation,
          meta: {
            roles: [ 'user' ]
          }
        },
        {
          path: 'assigned-mission',
          name: 'MyAssignedList',
          component: ShowMyAssignedList,
          meta: {
            roles: [ 'user' ]
          }
        },
        {
          path: 'my-project',
          name: 'MyProject',
          component: ShowProject,
          meta: {
            roles: [ 'user' ]
          }
        },
        {
          path: 'message-list',
          name: 'MessageList',
          component: ShowMessage,
          meta: {
            roles: [ 'user' ]
          }
        },
        {
          path: 'my-project/:id',
          name: 'ProjectContent',
          component: ProjectContent,
          meta: {
            roles: [ 'user' ]
          }
        }
      ]
    }
   ]
})
