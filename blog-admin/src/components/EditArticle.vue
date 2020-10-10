<template>
  <div>
    <a-row>
      <a-col :span="12" :offset="6">
        <!-- 标题 -->
        <div class="title">
          <div style="margin-bottom: 16px">
            <a-input addon-before="Title: " v-model="title" placeholder="Input your title here"/>
          </div>
        </div>

        <!-- 文章内容 -->
        <div class="content">
          <quill-editor ref="myQuillEditor" v-model="content"
                        :options="editorOption"/>
        </div>

        <div class="tat">
          <!-- 分类 -->
          <span class="type">
              <a-select placeholder="Please select" style="width: 100%" v-model="typeId" @change="typeChange">
                <a-select-option :value="type.id" v-for="(type, key) in types" :key="type.id">
                  {{ type.typename }}
                </a-select-option>
              </a-select>
            </span>
          <!-- 标签 -->
          <span class="tag">
              <a-select mode="multiple" style="width: 100%" placeholder="Please select"
                        v-model="tagIds" @change="tagChange">
                <a-select-option :value="tag.id" v-for="(tag, key) in tags" :key="tag.id">
                  {{ tag.tagname }}
                </a-select-option>
              </a-select>
            </span>
        </div>
        <!-- 提交 -->
        <div class="submit" html-type="submit">
          <a-button class="submit-btn" @click="submit">Submit</a-button>
        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script>
export default {
  name: 'EditArticle',
  data: function () {
    return {
      title: '',
      content: '',
      types: [],
      tags: [],
      tagIds: [],
      typeId: [],
      editorOption: {
        // Some Quill options...
      }
    }
  },
  methods: {
    submit () {
      const _this = this
      this.$confirm({
        title: 'Do you want to sumbit these items?',
        content: 'When clicked the OK button, this dialog will be closed after 1 second',
        onOk () {
          const tagObjArr = []
          _this.tagIds.forEach(item => tagObjArr.push({ id: item }))
          const typeObj = {}
          typeObj.id = _this.typeId
          if (_this.$route.params.id === '-1') {
            _this.$http.post('/admin/article', {
              title: _this.title,
              content: _this.content,
              type: typeObj,
              tags: tagObjArr,
              createTime: new Date()
            })
          } else {
            _this.$http.put('/admin/article/' + _this.$route.params.id, {
              title: _this.title,
              content: _this.content,
              type: typeObj,
              tags: tagObjArr,
              updateTime: new Date()
            })
          }
          _this.$router.push('/admin/article')
          _this.$message.success('Submit successfully !', 1)
        },
        onCancel () {
          _this.$message.info('You have cancelled submit !', 2)
        }
      })
    },
    getTypeAndTagInfo: function () {
      this.$http.get('/admin/tags')
        .then(response => {
          this.tags = response.data.tags
        })
      this.$http.get('/admin/types')
        .then(response => {
          this.types = response.data.types
        })
    },
    // 获取原博客信息
    getOriginArticleInfo: function () {
      this.$http.get('/admin/article/' + this.$route.params.id)
        .then(response => {
          const article = response.data.article
          this.title = article.title
          this.content = article.content
          article.tags.forEach(item => this.tagIds.push(item.id))
          this.typeId = article.type.id
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
