<template>
  <div style="width:60%;margin-left: 50%;transform: translate(-50%)">
    <div style="margin-top: 50px;">
      <div>
        <div style="width:60%;margin-left: 50%;transform: translate(-50%);margin-top: 50px">
          <router-link to="/index" style="font-size:20px;color: gray;margin-right:10px">用户列表</router-link>
          <router-link :to="'/user/' + userId" style="font-size:20px;color: gray;margin-right:10px">首页</router-link>
          <router-link :to="'/user/' + userId + '/types'" style="font-size:20px;color: gray;margin-right:10px">分类</router-link>
          <router-link :to="'/user/' + userId + '/tags'" style="font-size:20px;color: gray;margin-right:10px">标签</router-link>
        </div>
        <div style="height: 100px"></div>
        <div v-for="a in articleList"
             style="margin-left:50%;transform:translate(-50%);font-size:20px;margin-bottom:20px;">
          <router-link :to="'/user/'+ userId +'/article/' + a.id" style="color: gray"
                       onmousemove="this.style.color='#74b9ff'" onmouseleave="this.style.color='gray'">
            {{a.title +'&nbsp;'+ a.updateTime}}
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ArticleOV',
  data: function () {
    return {
      userId: this.$route.params.id,
      articleList: []
    }
  },
  methods: {
    getArticleList: function () {
      this.$http.get('/user/' + this.$route.params.id)
        .then(response => {
          this.articleList = response.data.articleList
          this.articleList.forEach(e => {
            if (e.updateTime === null) e.updateTime = e.createTime
            e.updateTime = e.updateTime.substring(0, 10)
          })
        })
    }
  },
  mounted: function () {
    this.getArticleList()
  }
}
</script>

<style scoped>

</style>
