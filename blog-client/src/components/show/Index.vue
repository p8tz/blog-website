<template>
  <div style="width:60%;margin-left: 50%;transform: translate(-50%)">
    <div style="margin-top: 50px;">
      <div v-for="u in userList" style="margin-left:50%;transform:translate(-50%);font-size:20px;margin-bottom:20px;">
        <router-link :to="'/user/' + u.id" style="color: gray"
                     onmousemove="this.style.color='#74b9ff'" onmouseleave="this.style.color='gray'">
          {{u.username}}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Index',
  data: function () {
    return {
      userList: []
    }
  },
  methods: {
    getUserList: function () {
      this.$http.get('/users')
        .then(response => {
          this.userList = response.data.userList
        })
    }
  },
  mounted: function () {
    this.getUserList()
  }
}
</script>

<style scoped>

</style>
