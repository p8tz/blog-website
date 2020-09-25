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
                        :options="editorOption" @blur="onEditorBlur($event)"/>
          <!--@focus="onEditorFocus($event)"-->
          <!--@ready="onEditorReady($event)"-->
          <!--/>-->

          <!-- Or manually control the data synchronization -->
          <!--<quill-editor-->
          <!--  ref="myQuillEditor"-->
          <!--  v-model="content"-->
          <!--  :options="editorOption"-->
          <!--  @change="onEditorChange($event)"-->
          <!--/>-->
        </div>
        <!-- 分类 -->
        <div class="tat">
          <span class="type">
              <a-select placeholder="Please select" style="width: 100%" @change="typeChange">
                <a-select-option :value="value" v-for="(value, key) in types" :key="key">
                  {{ value }}
                </a-select-option>
              </a-select>
          </span>
          <!-- 标签 -->
          <span class="tag">
            <a-select mode="multiple" :default-value="[]" style="width: 100%"
                      placeholder="Please select" @change="tagChange">
              <a-select-option v-for="(value, key) in tags" :key="key">
                {{ value }}
              </a-select-option>
            </a-select>
          </span>
        </div>
        <!-- 提交 -->
        <div class="submit">
          <a-button class="submit-btn" @click="submit">Submit</a-button>
        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script>
export default {
  name: 'EditBlog',
  data: function () {
    return {
      title: '',
      content: '<h2>I am Example</h2>',
      types: ['设计模式', '多线程', 'Spring'],
      tags: ['阅读', '认知', '思考', '学习', '生活'],
      editorOption: {
        // Some Quill options...
      }
    }
  },
  methods: {
    onEditorBlur (quill) {
      // console.log('editor blur!', quill)
      console.log('content', this.content)
    },
    onEditorFocus (quill) {
      // console.log('editor focus!', quill)
    },
    onEditorReady (quill) {
      // console.log('editor ready!', quill)
    },
    // onEditorChange ({ quill, html, text }) {
    //   console.log('editor change!', quill, html, text)
    //   this.content = html
    // }

    // 单选框
    typeChange (value) {
      console.log(`type: ${value}`)
    },
    // 多选框
    tagChange (value) {
      console.log(`tag: ${value}`)
    },
    // 提交
    submit () {
      const _this = this
      this.$confirm({
        title: 'Do you want to sumbit these items?',
        content: 'When clicked the OK button, this dialog will be closed after 1 second',
        onOk () {
          _this.$message.success('Submit successfully !', 1)
        },
        onCancel () {
          _this.$message.info('You have cancelled submit !', 2)
        }
      })
    }
  },
  computed: {
    editor () {
      return this.$refs.myQuillEditor.quill
    }
  },
  mounted () {
    // 新增
    if (this.$route.params.id === '-1') {
      this.content = ``
    } else {
      // 编辑, 根据id获取blog信息
      this.title = 'I am NOT title'
      this.content = `<h1> I am NOT example ! </h1>`
    }
    // console.log('this is current quill instance object', this.editor)
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
