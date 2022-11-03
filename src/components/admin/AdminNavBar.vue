<template>
 <div>
   <el-menu
     style="width: 200px; min-height: calc(100vh); color: #1f1e33;"
     text-color="#1F1E33"
     background-color="#fff"
     active-text-color="#575766"
     router
     :default-active="$route.path"
     class="el-menu">
     <el-menu-item index="/admin-home/dashboard">
       <span>首页</span>
     </el-menu-item>
     <el-submenu index="1">
       <template slot="title">
         <i class="el-icon-menu"></i>
         <span>用户信息管理</span>
       </template>
       <el-menu-item index="/admin-home/add-user">
         <span>用户信息</span>
       </el-menu-item>
       <el-menu-item index="/admin-home/edit-school">
         <span>专业信息</span>
       </el-menu-item>
       <el-menu-item index="" >
         <span>教师信息[down]</span>
       </el-menu-item>
       <el-menu-item index="">
         <span>学生信息[down]</span>
       </el-menu-item>
     </el-submenu>
     <el-submenu index="">
       <template slot="title">
         <i class="el-icon-menu"></i>
         <span>课程信息管理</span>
       </template>
       <el-menu-item index="/admin-home/manage-courses">
         <span>课程信息</span>
       </el-menu-item>
       <el-menu-item index="/admin-home/handle-course-requests">
         <span>审批课程</span>
       </el-menu-item>
     </el-submenu>
     <el-submenu index="">
       <template slot="title">
         <i class="el-icon-menu"></i>
         <span>教务信息管理</span>
       </template>
       <el-menu-item index="/admin-home/manage-classroom">
         <span>教室管理</span>
       </el-menu-item>
       <el-menu-item index="/admin-home/manage-schedule">
         <span>课时安排</span>
       </el-menu-item>
       <el-menu-item index="/admin-home/manage-course-selection">
         <span>选课安排</span>
       </el-menu-item>
       <el-menu-item index="/admin-home/handle-course-selection-requests">
         <span>处理选课申请</span>
       </el-menu-item>
     </el-submenu>
   </el-menu>
 </div>
</template>

<script>
export default {
  name: "AdminNavBar"
}
</script>

<style scoped>

</style>
