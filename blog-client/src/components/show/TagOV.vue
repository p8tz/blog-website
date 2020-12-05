<template>
  <div style="width:60%;margin-left: 50%;transform: translate(-50%)">
    <div style="margin-top: 50px;">
      <div style="width:60%;margin-left: 50%;transform: translate(-50%);margin-top: 50px">
        <router-link to="/index" style="font-size:20px;color: gray;margin-right:10px">用户列表</router-link>
        <router-link :to="'/user/' + userId" style="font-size:20px;color: gray;margin-right:10px">首页</router-link>
        <router-link :to="'/user/' + userId + '/types'" style="font-size:20px;color: gray;margin-right:10px">分类</router-link>
        <router-link :to="'/user/' + userId + '/tags'" style="font-size:20px;color: gray;margin-right:10px">标签</router-link>
      </div>
      <div style="height: 100px"></div>
      <div v-for="t in tagList"
           style="margin-left:50%;transform:translate(-50%);font-size:20px;margin-bottom:20px;">
        <router-link :to="'/user/'+ userId +'/tag/' + t.id" style="color: gray"
                     onmousemove="this.style.color='#74b9ff'" onmouseleave="this.style.color='gray'">
          {{t.tagname}}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TagOV',
  data:function () {
    return {
      userId: this.$route.params.id,
      tagList: []
    }
  },
  methods: {
    getTagList:function () {
      this.$http.get('/user/' + this.userId + '/tags')
        .then(response => {
          this.tagList = response.data.tagList
        })
    }
  },
  mounted:function () {
    this.getTagList()
  }
}
</script>

<style scoped>

</style>