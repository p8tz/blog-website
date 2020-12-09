<template>
  <div style="width:60%;margin-left: 50%;transform: translate(-50%)">
    <div style="margin-top: 50px;">
      <router-link to="/index" style="color: gray;font-size:20px;margin-right: 10px">用户列表</router-link>
      <router-link :to="'/user/' + userId" style="color: gray;font-size:20px;margin-right: 10px">首页</router-link>
      <!-- title -->
      <h1 style="text-align: center">{{article.title}}</h1>
      <div style="text-align: center">{{article.updateTime}}</div>
      <!-- content -->
      <div class="content" style="width: 100%;padding-left: 20%;padding-right: 20%;padding-top:20px;
            word-wrap: break-word;word-break: break-all;height: auto;overflow: hidden;"
           v-html="article.content"></div>
      <br>
      <div>
        <router-link :to="'/user/' + userId + '/type/' + article.type.id"
                     style="display: inline-block;padding-left: 20%;color:gray"
                     onmousemove="this.style.color='#74b9ff'" onmouseleave="this.style.color='gray'">
          {{ article.type.typename }}
        </router-link>
        <div style="display: inline-block;padding-left: 40%">
          <router-link :to="'/user/' + userId + '/tag/' + tag.id"
                       style="display: inline-block;margin-right: 10px;color:gray"
                       onmousemove="this.style.color='#74b9ff'" onmouseleave="this.style.color='gray'"
                       v-for="tag in article.tags">
            {{ tag.tagname }}
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Article',
  data: function () {
    return {
      userId: this.$route.params.id,
      article: {
        type: { id: 10 } // 不影响结果, 但是没有这个会报错, why
      }
    }
  },
  methods: {
    getArticleDetails: function () {
      this.$http.get('/user/' + this.userId + '/article/' + this.$route.params.articleId)
        .then(response => {
          this.article = response.data.article
          if (this.article.updateTime === null) this.article.updateTime = this.article.createTime
          this.article.updateTime = this.article.updateTime.substring(0, 10)
        })
    }
  },
  created: function () {
    this.getArticleDetails()
  }
}
</script>

<style scoped>

</style>