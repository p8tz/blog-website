<template>
  <a-row>
    <a-col :span="12" :offset="6">
      <a-table :columns="columns" :data-source="dataSource" bordered :pagination="pagination" rowKey="id">
        <template v-for="col in ['typename']" :slot="col" slot-scope="text, record, index">
          <div :key="col">
            <a-input v-if="record.editable" style="margin: -5px 0" :value="text" v-model="typeName"
                     v-decorator="['typename',
                      {
                        rules: validate.typename,
                        initialValue: '',
                        validateTrigger: 'blur'
                      }]"/>
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
            <router-link :disabled="editingKey !== ''" to="/admin/type/-1">添加</router-link>
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
    title: '分类名称',
    dataIndex: 'typename',
    align: 'center',
    scopedSlots: { customRender: 'typename' },
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
  name: 'Type',
  data () {
    return {
      dataSource: [],
      columns: columns,
      editingKey: '',
      pagination: pagination,
      typeName: '',
      validate: {
        typename: [
          {
            type: 'string',
            required: true,
            message: 'typename is required !'
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
    edit (key) {
      const newData = [...this.dataSource]
      const target = newData.filter(item => key === item.id)[0]
      this.editingKey = key
      if (target) {
        target.editable = true
        this.dataSource = newData
      }
      this.typeName = target.typename
    },
    save (id) {
      const newData = [...this.dataSource]
      const target = newData.filter(item => id === item.id)[0]
      if (target) {
        delete target.editable
        this.dataSource = newData
      }
      this.editingKey = ''
      this.typeName = this.typeName.trim() === '' ? 'null' : this.typeName
      this.$http.put('/admin/type/' + id, { typename: this.typeName.trim() })
      this.$message.success('Save successfully !', 1)
    },
    onDelete (id) {
      const _this = this
      this.$confirm({
        title: 'Do you want to delete these items?',
        content: 'When clicked the OK button, this dialog will be closed after 1 second',
        onOk () {
          _this.$http.delete('/admin/type/' + id)
          const ds = [..._this.dataSource]
          _this.dataSource = ds.filter(item => item.id !== id)
          _this.resetIndex()
          _this.$message.success('Delete successfully !', 1)
        },
        onCancel () {
          _this.$message.info('You have cancelled deletion !', 1)
        }
      })
    },
    getAllTypes: function () {
      this.$http.get('/admin/types')
        .then(response => {
          this.dataSource = response.data.data
          this.dataSource.forEach((item, idx) => {
            if (item.typename === 'default') {
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
    this.getAllTypes()
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
