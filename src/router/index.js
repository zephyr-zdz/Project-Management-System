import Vue from 'vue'
import VueRouter from 'vue-router'
import Welcome from '../components/Welcome'
import Login from '../components/Login';
import Register from "../components/Register";
//import StudentHome from "../components/student/StudentHome";
//import AdminDash from "../components/admin/service/AdminDash";
//import AddUser from "../components/admin/service/UserManagement/AddUser";
//import TeacherDash from "../components/teacher/TeacherDash";
//import AdminHome from "../components/admin/AdminHome";
//import StudentDash from "../components/student/StudentDash";
//import ChangePassword from "../components/ChangePassword";
//import StudentEdit from "../components/student/service/StudentEdit";
//import TeacherEdit from "../components/teacher/service/TeacherEdit";
//import TeacherHome from "../components/teacher/TeacherHome";
//import ManageSchool from "../components/admin/service/DepartmentManagement/ManageSchool";
//import ManageCourses from "../components/admin/service/CourseManagement/ManageCourses";
//import ManageClassroom from "../components/admin/service/EducationManagement/ManageClassroom";
//import ManageClassSchedule from "../components/admin/service/EducationManagement/ManageClassSchedule";
//import ManageCourseSelection from "../components/admin/service/EducationManagement/ManageCourseSelection";
//import SelectCourse from "../components/student/service/courseSelection/SelectCourse";
//import TeacherCourse from "../components/teacher/service/course/TeacherCourse";
//import HandleCourseRequest from "../components/admin/service/CourseManagement/HandleCourseRequest";
//import CourseRequest from "../components/teacher/service/course/CourseRequest";
//import MyCourse from "../components/student/service/course/MyCourse";
//import CourseSelectionRequest from "../components/student/service/course/CourseSelectionRequest";
//import ManageCourseSelectionRequest
//  from "../components/admin/service/CourseSelectionManagement/ManageCourseSelectionRequest";

Vue.use(VueRouter)

export default new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome,
      //meta: {
      //  roles: [ 'guest', 'admin', 'teacher', 'student' ]
      //}
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      //meta: {
      //  roles: [ 'guest', 'admin', 'teacher', 'user' ]
      //}
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
    },
    //{
    //  path: '/user-home',
    //  name: 'StudentHome',
    //  component: StudentHome,
    //  children: [
    //    {
    //      path: 'dashboard',
    //      name: 'StudentDash',
    //      component: StudentDash,
    //      //meta: {
    //      //  roles: [ 'user', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'edit-info',
    //      name: 'StudentEdit',
    //      component: StudentEdit,
    //      //meta: {
    //      //  roles: [ 'user', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'change-password',
    //      name: 'ChangePassword',
    //      component: ChangePassword,
    //      //meta: {
    //      //  roles: [ 'user']
    //      //}
    //    },
    //    {
    //      path: 'new-project',
    //      name: 'SelectCourse',
    //      component: SelectCourse,
    //      //meta: {
    //      //  roles: [ 'user', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'project-leader-request',
    //      name: 'CourseSelectionRequest',
    //      component: CourseSelectionRequest,
    //      //meta: {
    //      //  roles: [ 'user', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'my-project',
    //      name: 'MyCourse',
    //      component: MyCourse,
    //      //meta: {
    //      //  roles: [ 'user', 'guest']
    //      //}
    //    }
    //  ],
    //  //meta: {
    //  //  roles: [ 'user' ] // guest is for test
    //  //}
    //},
    //{
    //  path: '/teacher-home',
    //  name: 'TeacherHome',
    //  component: TeacherHome,
    //  children: [
    //    {
    //      path: 'dashboard',
    //      name: 'TeacherDash',
    //      component: TeacherDash,
    //      //meta: {
    //      //  roles: [ 'teacher']
    //      //}
    //    },
    //    {
    //      path: 'edit-info',
    //      name: 'TeacherEdit',
    //      component: TeacherEdit,
    //      //meta: {
    //      //  roles: [ 'teacher', 'guest']
    //      //},
    //    },
    //    {
    //      path: 'my-course',
    //      name: 'TeacherCourse',
    //      component: TeacherCourse,
    //      //meta: {
    //      //  roles: [ 'teacher', 'guest']
    //      //},
    //    },
    //    {
    //      path: 'course-request',
    //      name: 'CourseRequest',
    //      component: CourseRequest,
    //      //meta: {
    //      //  roles: ['teacher', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'change-password',
    //      name: 'ChangePassword',
    //      component: ChangePassword,
    //      //meta: {
    //      //  roles: ['teacher']
    //      //},
    //    }
    //  ],
    //  //meta: {
    //  //  roles: [ 'teacher' ]
    //  //}
    //},
    //{
    //  path: '/admin-home',
    //  name: 'AdminHome',
    //  component: AdminHome,
    //  children: [
    //    {
    //      path: 'dashboard',
    //      name: 'AdminDash',
    //      component: AdminDash,
    //      //meta: {
    //      //  // requireAuth: true,
    //      //  roles: [ 'admin' ]
    //      //}
    //    },
    //    {
    //      path: 'add-user', // edit user
    //      name: 'AddUser',
    //      component: AddUser,
    //      //meta: {
    //      //  // requireAuth: true
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'edit-school',
    //      name: 'ManageSchool',
    //      component: ManageSchool,
    //      //meta: {
    //      //  // requireAuth:true
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'manage-courses',
    //      name: 'ManageCourses',
    //      component: ManageCourses,
    //      //meta: {
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'manage-classroom',
    //      name: 'ManageClassroom',
    //      component: ManageClassroom,
    //      //meta: {
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'manage-schedule',
    //      name: 'ManageClassSchedule',
    //      component: ManageClassSchedule,
    //      //meta: {
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'manage-course-selection',
    //      name: 'ManageCourseSelection',
    //      component: ManageCourseSelection,
    //      //meta: {
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'handle-course-selection-requests',
    //      name: 'ManageCourseSelectionRequest',
    //      component: ManageCourseSelectionRequest,
    //      //meta: {
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    },
    //    {
    //      path: 'handle-course-requests',
    //      name: 'HandleCourseRequest',
    //      component: HandleCourseRequest,
    //      //meta: {
    //      //  roles: [ 'admin', 'guest']
    //      //}
    //    }
    //  ],
    //  //meta: {
    //  // // requireAuth: true,
    //  //  roles: [ 'admin' ]
    //  //}
    //},
  ]
})
