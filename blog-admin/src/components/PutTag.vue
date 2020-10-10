<template>
  <a-row>
    <a-col :span="12" :offset="6">
      <div class="tag">
        <div style="margin-bottom: 16px">
          <a-input addon-before="Tag: " v-model="tagName" placeholder="Input tag name here"/>
        </div>
      </div>
      <div class="submit">
        <a-button class="submit-btn" @click="submit">Submit</a-button>
      </div>
    </a-col>
  </a-row>
</template>

<script>
export default {
  name: 'PutTag',
  data: function () {
    return {
      tagName: ''
    }
  },
  methods: {
    submit () {
      const _this = this
      this.$confirm({
        title: 'Do you want to submit these items?',
        content: 'When clicked the OK button, this dialog will be closed after 1 second',
        onOk () {
          _this.$http.post('/admin/tag', { tagname: _this.tagName })
          _this.$message.success('Submit successfully !', 1)
          _this.$router.push('/admin/tag')
        },
        onCancel () {
          _this.$message.info('You have cancelled submit !', 2)
        }
      })
    }
  }
}
</script>

<style scoped>
.tag {
  margin-top: 30px;
}

.submit {
  float: right;
}
</style>
