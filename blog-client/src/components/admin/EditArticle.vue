<template>
  <div>
    <a-row>
      <a-col :span="12" :offset="6">
        <a-form-model ref="ruleForm" :model="form" :rules="rules">
          <!-- 标题 -->
          <div class="title">
            <a-form-model-item ref="title" prop="title">
              <div style="margin-bottom: 16px">
                <a-input addon-before="标题：" v-model="form.title"
                         placeholder="请输入标题"
                         @blur="() => $refs.title.onFieldBlur()"/>
              </div>
            </a-form-model-item>
          </div>
          <!-- 文章内容 -->
          <div class="content">
            <a-form-model-item ref="content" prop="content">
              <quill-editor ref="myQuillEditor" v-model="form.content"
                            :options="editorOption"
                            @blur="() => $refs.content.onFieldBlur()"/>
            </a-form-model-item>
          </div>
          <div class="tat">
            <!-- 分类 -->
            <span class="type">
              <a-form-model-item ref="type" prop="type">

                <a-select v-model="form.typeId" placeholder="请选择分类..." style="width: 100%"
                          @change="() => $refs.type.onFieldChange()">
                  <a-select-option :value="type.id" v-for="(type, key) in form.types"
                                   :key="type.id">
                    {{ type.typename }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
            </span>
            <!-- 标签 -->
            <span class="tag">
              <a-form-model-item ref="tag" prop="tag">
                <a-select mode="multiple" style="width: 100%" placeholder="请选择标签..."
                          v-model="form.tagIds">
                  <a-select-option :value="tag.id" v-for="(tag, key) in form.tags"
                                   :key="tag.id">
                    {{ tag.tagname }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
            </span>
          </div>
          <!-- 提交 -->
          <div class="submit" html-type="submit">
            <a-button class="submit-btn" @click="onSubmit">Submit</a-button>
          </div>
        </a-form-model>
      </a-col>
    </a-row>
  </div>
</template>
<script>
export default {
  name: 'EditArticle',
  data: function () {
    return {
      form: {
        title: '',
        content: '',
        types: [],
        tags: [],
        tagIds: [],
        typeId: []
      },
      rules: {
        title: [
          {
            type: 'string',
            whitespace: true,
            required: true,
            message: '请输入标题！'
          },
          {
            type: 'string',
            min: 1,
            max: 20,
            message: '长度在1到20之间！'
          }
        ],
        content: [
          {
            type: 'string',
            whitespace: true,
            required: true,
            message: '请输入文章内容！'
          },
        ]
      },
      editorOption: {
      }
    }
  },
  methods: {
    onSubmit () {
      const _this = this
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          this.$confirm({
            title: '确定发布？',
            content: '发布成功后可修改或删除该文章',
            okText: '确认',
            cancelText: '取消',
            onOk () {
              const tagObjArr = []
              _this.form.tagIds.forEach(item => tagObjArr.push({ id: item }))
              if (tagObjArr.length === 0) tagObjArr.push({ id: 100 })  // default
              const typeObj = {}
              typeObj.id = _this.form.typeId
              if (typeObj.id.length === 0) typeObj.id = 100 // default
              if (_this.$route.params.id === '-1') { // 发布博客
                _this.postArticle(typeObj, tagObjArr)
              } else {  // 更新博客
                _this.putArticle(typeObj, tagObjArr)
              }
            },
            onCancel () {
              _this.$message.info('取消发布', 2)
            }
          })
        }
      })
    },
    postArticle: function (typeObj, tagObjArr) {
      this.$http.post('/admin/article', {
        title: this.form.title,
        content: this.form.content,
        type: typeObj,
        tags: tagObjArr,
        createTime: new Date().format('yyyy-MM-dd')
      })
        .then(response => {
          if (response.data.code === 2002) {
            this.$message.warn('请正确填写博客信息！')
          } else {
            this.$router.push('/admin/article')
            this.$message.success('发布成功', 1)
          }
        })
    },
    putArticle: function (typeObj, tagObjArr) {
      this.$http.put('/admin/article/' + this.$route.params.id, {
        title: this.form.title,
        content: this.form.content,
        type: typeObj,
        tags: tagObjArr,
        updateTime: new Date().format('yyyy-MM-dd')
      })
        .then(response => {
          if (response.data.code === 2002) {
            this.$message.warn('请正确填写博客信息！')
          } else {
            this.$router.push('/admin/article')
            this.$message.success('更新成功', 1)
          }
        })
    },
    getTypeAndTagInfo: function () {
      this.$http.get('/admin/tags')
        .then(response => {
          this.form.tags = response.data.data
        })
      this.$http.get('/admin/types')
        .then(response => {
          this.form.types = response.data.data
        })
    },
    // 获取原博客信息
    getOriginArticleInfo: function () {
      this.$http.get('/admin/article/' + this.$route.params.id)
        .then(response => {
          const article = response.data.data
          this.form.title = article.title
          this.form.content = article.content
          article.tags.forEach(item => this.form.tagIds.push(item.id))
          this.form.typeId = article.type.id
        })
    }
  },
  computed: {
    editor () {
      return this.$refs.myQuillEditor.quill
    }
  },
  mounted () {
    if (this.$route.params.id !== '-1') {
      this.getOriginArticleInfo()
    }
    this.getTypeAndTagInfo()
  }
}
</script>

<style scoped>
.content,
.type,
.tag {
  margin-top: 30px;
}

.tat {
}

.type {
  float: left;
  width: 300px;
}

.tag {
  float: right;
  width: 300px;
}

.submit {
  width: 100%;
  position: relative;
}

.submit-btn {
  position: absolute;
  right: 0;
  top: 85px;
}

.ant-select {
  width: 100%;
}
</style>
