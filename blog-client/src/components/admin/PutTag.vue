<template>
  <a-row>
    <a-col :span="12" :offset="6">
      <a-form-model ref="ruleForm" :model="form" :rules="rules">
        <a-form-model-item ref="tagName" prop="tagName" style="margin-top: 30px;margin-bottom: 16px">
          <a-input addon-before="Tag: " v-model="form.tagName" placeholder="Input tag name here"
                   @blur="() => $refs.tagName.onFieldBlur()"/>
        </a-form-model-item>
        <a-form-model-item style="float: right">
          <a-button class="submit-btn" @click="onSubmit">Submit</a-button>
        </a-form-model-item>
      </a-form-model>
    </a-col>
  </a-row>
</template>

<script>
export default {
  name: 'PutTag',
  data: function () {
    return {
      form: {
        tagName: ''
      },
      rules: {
        tagName: [
          {
            type: 'string',
            required: true,
            whitespace: true,
            message: 'tagname is required !'
          },
          {
            type: 'string',
            min: 1,
            max: 8,
            message: 'Length must between 1 and 8 !'
          }
        ],
      }
    }
  },
  methods: {
    onSubmit () {
      const _this = this
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
        this.$confirm({
          title: 'Do you want to submit these items?',
          content: 'When clicked the OK button, this dialog will be closed after 1 second',
          onOk () {
            _this.$http.post('/admin/tag', { tagname: _this.form.tagName.trim() })
              .then(response => {
                if (response.data.code === 2002) {
                  alert('请正确填写表单信息')
                } else {
                  _this.$message.success('Submit successfully !', 1)
                  _this.$router.push('/admin/tag')
                }
              })
          },
          onCancel () {
            _this.$message.info('You have cancelled submit !', 2)
          }
        })
        } else {
          console.log('error submit!!')
          return false
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
