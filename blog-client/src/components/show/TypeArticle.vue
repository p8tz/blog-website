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
          <router-link :to="'/user/'+ userId +'/' + a.id" style="color: gray"
                       onmousemove="this.style.color='#74b9ff'" onmouseleave="this.style.color='gray'">
            {{a.title +'&nbsp;'+a.updateTime}}
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TypeArticle',
  data: function () {
    return {
      userId: this.$route.params.id,
      typeId: this.$route.params.typeId,
      articleList: []
    }
  },
  methods: {
    getArticleListByTypeId: function () {
      this.$http.get('/user/' + this.userId + '/type/' + this.typeId)
        .then(response => {
          this.articleList = response.data.articleList
          this.articleList.forEach(e => e.updateTime = e.updateTime.substring(0, 10))
        })
    }
  },
  mounted: function () {
    this.getArticleListByTypeId()
  }
}
</script>

<style scoped>

</style>