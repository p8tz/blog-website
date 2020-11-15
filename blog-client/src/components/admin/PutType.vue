<template>
  <a-row>
    <a-col :span="12" :offset="6">
      <div class="type">
        <div style="margin-bottom: 16px">
          <a-input addon-before="Type: " v-model="typeName" placeholder="Input type name here"/>
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
  name: 'PutType',
  data: function () {
    return {
      typeName: ''
    }
  },
  methods: {
    submit () {
      const _this = this
      this.$confirm({
        title: 'Do you want to submit these items?',
        content: 'When clicked the OK button, this dialog will be closed after 1 second',
        onOk () {
          _this.$http.post('/admin/type', { typename: _this.typeName })
          _this.$message.success('Submit successfully !', 1)
          _this.$router.push('/admin/type')
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
.type {
  margin-top: 30px;
}

.submit {
  float: right;
}
</style>
