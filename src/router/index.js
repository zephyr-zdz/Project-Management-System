import Vue from 'vue'
import VueRouter from 'vue-router'
import Welcome from '../components/Welcome'
import Login from '../components/Login';
import Register from "../components/Register";
import UserHome from "../components/user/UserHome";
import UserDash from "../components/user/UserDash";
import ShowProject from "../components/user/service/ShowProject";
import ShowInvitation from "../components/user/service/ShowInvitation";
import ProjectContent from "../components/user/service/ProjectContent.vue";
import ShowMessage from "../components/user/service/ShowMessage.vue";

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
        //{
        //  path: 'edit-info',
        //  name: 'StudentEdit',
        //  component: UserEdit,
        //  //meta: {
        //  //  roles: [ 'user', 'guest']
        //  //}
        //},
        //    {
        //      path: 'change-password',
        //      name: 'ChangePassword',
        //      component: ChangePassword,
        //      //meta: {
        //      //  roles: [ 'user']
        //      //}
        //    },
        {
          path: 'my-invitation',
          name: 'MyInvitation',
          component: ShowInvitation,
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
