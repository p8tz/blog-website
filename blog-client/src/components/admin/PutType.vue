<template>
  <a-row>
    <a-col :span="12" :offset="6">
      <a-form-model ref="ruleForm" :model="form" :rules="rules">
        <a-form-model-item ref="typeName" prop="typeName" style="margin-top: 30px;margin-bottom: 16px">
          <a-input addon-before="分类：" v-model="form.typeName" placeholder="请输入分类名"
                   @blur="() => $refs.typeName.onFieldBlur()"/>
        </a-form-model-item>
        <a-form-model-item style="float: right">
          <a-button class="submit-btn" @click="onSubmit">添加</a-button>
        </a-form-model-item>
      </a-form-model>
    </a-col>
  </a-row>
</template>

<script>
export default {
  name: 'PutType',
  data: function () {
    return {
      form: {
        typeName: ''
      },
      rules: {
        typeName: [
          {
            type: 'string',
            required: true,
            whitespace: true,
            message: '请输入分类名！'
          },
          {
            type: 'string',
            min: 1,
            max: 8,
            message: '长度在1到8之间！'
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
            title: '确定添加？',
            content: '添加成功后可修改或删除该分类',
            okText: '确认',
            cancelText: '取消',
            onOk () {
              _this.$http.post('/admin/type', { typename: _this.form.typeName.trim() })
                .then(response => {
                  const code = response.data.code
                  if (code === 2002) { // 参数校验错误
                    _this.$message.warn('请正确填写表单信息')
                  } else if(code === 2003) { // 重复添加
                    _this.$message.warn('分类已存在')
                  } else {
                    _this.$message.success('成功添加新分类', 1)
                    _this.$router.push('/admin/type')
                  }
                })
            },
            onCancel () {
              _this.$message.info('已取消', 1)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
