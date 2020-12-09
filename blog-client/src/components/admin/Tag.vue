<template>
  <a-row>
    <a-col :span="12" :offset="6">

      <a-table :columns="columns" :data-source="dataSource" bordered :pagination="pagination" rowKey="id">
        <template v-for="col in ['tagname']" :slot="col" slot-scope="text, record, index">
          <div :key="col">
            <a-input v-if="record.editable" style="margin: -5px 0" :value="text" v-model="tagName"/>
            <template v-else>
              {{ text }}
            </template>
          </div>
        </template>
        <template slot="operation" slot-scope="text, record, index">
          <div class="editable-row-operations">
            <span v-if="record.editable">
              <a @click="() => save(record.id)">保存</a>
            </span>
            <span v-else>
              <a :disabled="editingKey !== ''" @click="() => edit(record.id)">编辑</a>
            </span>
            <a-divider v-if="!record.editable" type="vertical"/>&nbsp;
            <a v-if="!record.editable" :disabled="editingKey !== ''" @click="onDelete(record.id)">删除</a>
          </div>
        </template>
        <template slot="footer" slot-scope="currentPageData">
          <a-button>
            <router-link :disabled="editingKey !== ''" to="/admin/tag/-1">添加</router-link>
          </a-button>
        </template>
      </a-table>
    </a-col>
  </a-row>
</template>
<script>
const columns = [
  {
    title: '',
    dataIndex: 'index',
    align: 'center',
    scopedSlots: { customRender: 'index' },
    width: '10%'
  },
  {
    title: '标签名称',
    dataIndex: 'tagname',
    align: 'center',
    scopedSlots: { customRender: 'tagname' },
  },
  {
    title: '操作',
    dataIndex: 'operation',
    align: 'center',
    scopedSlots: { customRender: 'operation' },
  },
]
const pagination = {
  pageSize: 5
}

const dataSource = []
export default {
  name: 'Tag',
  data () {
    return {
      dataSource: [],
      columns: columns,
      editingKey: '',
      pagination: pagination,
      tagName: ''
    }
  },
  methods: {
    edit (key) {
      const newData = [...this.dataSource]
      const target = newData.filter(item => key === item.id)[0]
      this.editingKey = key
      if (target) {
        target.editable = true
        this.dataSource = newData
      }
      this.tagName = target.tagname
    },
    save (id) {
      const newData = [...this.dataSource]
      const target = newData.filter(item => id === item.id)[0]
      if (target) {
        delete target.editable
        this.dataSource = newData
      }
      this.editingKey = ''
      this.tagName = this.tagName.trim() === '' ? 'null' : this.tagName
      this.$http.put('/admin/tag/' + id, { tagname: this.tagName.trim() })
        .then(response => {
          const code = response.data.code
          if (code === 2003) {
            this.$message.warn('标签已存在')
          } else {
            this.$message.success('Save successfully !', 1)
          }
        })
    },
    onDelete (id) {
      const _this = this
      this.$confirm({
        title: '确认删除？',
        content: '删除后无法找回',
        okText: '确认',
        cancelText: '取消',
        onOk () {
          _this.$http.delete('/admin/tag/' + id)
            .then(response => {
              if (response.data.code === 1000) {
                const ds = [..._this.dataSource]
                _this.dataSource = ds.filter(item => item.id !== id)
                _this.resetIndex()
                _this.$message.info('成功删除', 1)
              } else {
                this.$message.error('请先删除与之关联的文章')
              }
            })
        },
        onCancel () {
          _this.$message.info('取消删除', 1)
        }
      })
    },
    getAllTags: function () {
      this.$http.get('/admin/tags')
        .then(response => {
          this.dataSource = response.data.data
          this.dataSource.forEach((item, idx) => {
            if (item.tagname === 'default') {
              this.dataSource.splice(idx, 1)
            }
          })
          this.resetIndex()
        })
    },
    resetIndex: function () {
      let index = 1
      this.dataSource.forEach(item => item.index = index++)
    }
  },
  mounted: function () {
    this.getAllTags()
  }
}
</script>
<style scoped>
.ant-row {
  position: relative;
  top: 100px;
}

.editable-row-operations a {
  margin-right: 8px;
}

.editable-add-btn {

}
</style>
