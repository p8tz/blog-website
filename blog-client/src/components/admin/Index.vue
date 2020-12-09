<template>
  <div>
    <div id="components-layout-demo-basic">
      <a-layout>
        <!-- header -->
        <a-layout-header :style="{ background: '#fcfcfc' }">
          <!-- username -->
          <span style="position: absolute; left: 20%; font-size: 17px">
            <h1>ADMIN</h1>
          </span>
          <a-menu v-model="current" mode="horizontal" style="background-color: #fcfcfc">
            <!-- 各个分类 -->
            <a-menu-item v-bind:key="item.key" v-for="item in menuItems">
              <a-icon :type="item.type"/>
              <router-link :to="item.to" style="float: right">
                {{ item.value }}
              </router-link>
            </a-menu-item>
            <!-- 退出 -->
            <a-menu-item key="logout" @click="logout">
              <a-icon type="logout"/>
              <router-link to="/admin/logout" style="float: right">
                注销
              </router-link>
            </a-menu-item>
          </a-menu>
        </a-layout-header>
        <!-- main -->
        <a-layout-content style="height: 797px;background-color: #fcfcfc">
          <router-view></router-view>
        </a-layout-content>
        <!-- footer -->
        <a-layout-footer style="background-color: #fcfcfc">
          <a-row style="text-align: center">
            <a-col :span="4" :offset="10">
              <div>
                <a-icon type="copyright"/>
                2020&nbsp;
                <a-icon type="user"/>
                极夜
                <p style="font-weight: bold;margin-top: 5px">
                  Designed by Polarnight
                </p>
              </div>
            </a-col>
          </a-row>
        </a-layout-footer>
      </a-layout>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Index',
  data () {
    return {
      current: ['home'],
      menuItems: [
        {
          key: 'home',
          type: 'home',
          to: '/admin/home',
          value: '首页'
        },
        {
          key: 'article',
          type: 'book',
          to: '/admin/article',
          value: '文章'
        },
        {
          key: 'type',
          type: 'folder',
          to: '/admin/type',
          value: '分类'
        },
        {
          key: 'tag',
          type: 'tags',
          to: '/admin/tag',
          value: '标签'
        }
      ],
    }
  },
  methods: {
    logout: function () {
      window.sessionStorage.removeItem('token')
      this.$router.push('/admin/login')
    }
  }
}
</script>

<style lang="less" scoped>
.ant-menu {
  position: absolute;
  right: 20%;
}

.a-layout-content {
  height: 800px;
}
</style>
