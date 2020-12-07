<template>
  <div>

    <!--<a-form-model-item ref="typeName" prop="typeName">-->
    <!--  <a-input v-model="form.typeName"-->
    <!--           @blur="() => $refs.typeName.onFieldBlur()"/>-->
    <!--</a-form-model-item>-->
    <!--<a-form-model-item style="float: right">-->
    <!--  <a-button class="submit-btn" @click="onSubmit">Submit</a-button>-->
    <!--</a-form-model-item>-->


    <a-row>
      <a-col :span="12" :offset="6">

        <a-form-model ref="ruleForm" :model="form" :rules="rules">

          <!-- 标题 -->
          <div class="title">

            <a-form-model-item ref="title" prop="title">

              <div style="margin-bottom: 16px">
                <a-input addon-before="Title: " v-model="form.title"
                         placeholder="Input your title here"
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

                <a-select v-model="form.typeId" placeholder="Please select" style="width: 100%"
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
                <a-select mode="multiple" style="width: 100%" placeholder="Please select"
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
            message: 'title is required !'
          },
          {
            type: 'string',
            min: 1,
            max: 20,
            message: 'Length must between 1 and 20 !'
          }
        ],
        content: [
          {
            type: 'string',
            whitespace: true,
            required: true,
            message: 'content is required !'
          },
        ]
      },

      editorOption: {
        // Some Quill options...
      }
    }
  },
  methods: {
    onSubmit () {
      const _this = this
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          this.$confirm({
            title: 'Do you want to sumbit these items?',
            content: 'When clicked the OK button, this dialog will be closed after 1 second',
            onOk () {
              const tagObjArr = []
              _this.form.tagIds.forEach(item => tagObjArr.push({ id: item }))
              if (tagObjArr.length === 0) tagObjArr.push({ id: 100 })  // default
              const typeObj = {}
              typeObj.id = _this.form.typeId
              if (typeObj.id.length === 0) typeObj.id = 100 // default
              if (_this.$route.params.id === '-1') { // 添加博客
                _this.postArticle(typeObj, tagObjArr)
              } else {  // 更新博客
                _this.putArticle(typeObj, tagObjArr)
              }
            },
            onCancel () {
              _this.$message.info('You have cancelled submit !', 2)
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
            alert('请正确填写博客信息')
          } else {
            this.$router.push('/admin/article')
            this.$message.success('Submit successfully !', 1)
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
            alert('请正确填写博客信息')
          } else {
            this.$router.push('/admin/article')
            this.$message.success('Submit successfully !', 1)
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
