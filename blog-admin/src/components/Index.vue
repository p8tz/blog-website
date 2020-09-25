<template>
  <div>
    <div id="components-layout-demo-basic">
      <a-layout>
        <!-- header -->
        <a-layout-header :style="{ background: '#fcfcfc' }">

          <!-- username -->
          <span style="position: absolute; left: 20%; font-size: 17px">
            <h1> Hi!&nbsp;{{ username }}</h1>
          </span>

          <a-menu v-model="current" mode="horizontal" style="background-color: #fcfcfc">
            <!-- 各个分类 -->
            <a-menu-item v-bind:key="item.key" v-for="item in menuItems">
              <a-icon :type="item.type"/>
              <router-link :to="item.to" style="float: right">
                {{ item.key }}
              </router-link>
            </a-menu-item>

            <!-- 退出 -->
            <a-menu-item key="logout" @click="logout">
              <a-icon type="logout"/>
              <router-link to="/logout" style="float: right">
                Logout
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
                  Designed by 小N
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
          to: '/home'
        },
        {
          key: 'blog',
          type: 'book',
          to: '/blog'
        },
        {
          key: 'type',
          type: 'folder',
          to: '/type'
        },
        {
          key: 'tag',
          type: 'tags',
          to: '/tag'
        }
      ],
      username: 'Polarnight'
    }
  },
  methods: {
    logout: function () {
      window.sessionStorage.removeItem('token')
      this.$router.push('/login')
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

<!--      <a-menu-item key="home">-->
<!--        <a-icon type="home"/>-->
<!--        <router-link to="/home" style="float: right">-->
<!--          Home-->
<!--        </router-link>-->
<!--      </a-menu-item>-->
<!--      <a-menu-item key="blog">-->
<!--        <a-icon type="book"/>-->
<!--        <router-link to="/blog" style="float: right">-->
<!--          Blog-->
<!--        </router-link>-->
<!--      </a-menu-item>-->
<!--      <a-menu-item key="type">-->
<!--        <a-icon type="folder"/>-->
<!--        <router-link to="/type" style="float: right">-->
<!--          Type-->
<!--        </router-link>-->
<!--      </a-menu-item>-->
<!--      <a-menu-item key="tag">-->
<!--        <a-icon type="tags"/>-->
<!--        <router-link to="/tag" style="float: right">-->
<!--          Tag-->
<!--        </router-link>-->
<!--      </a-menu-item>-->
