<template>
  <a-row>
    <a-col :span="12" :offset="6">
      <a-table bordered :columns="columns" :data-source="dataSource" :pagination="pagination" rowKey="id">
        <!-- record返回当前行信息 -->
        <span slot="action" slot-scope="text, record">
          <!-- 传入id值 -->
          <router-link :to="'/admin/article/'+record.id">编辑</router-link>
          <a-divider type="vertical"/>
          <a @click="del(record)">删除</a>
        </span>

        <template slot="footer" slot-scope="currentPageData">
          <a-button>
            <!-- id为-1代表新增 -->
            <router-link to="/admin/article/-1">添加</router-link>
          </a-button>
        </template>
      </a-table>
    </a-col>
  </a-row>
</template>

<script>
const columns = [
  {
    dataIndex: 'index',      // 指向要渲染数据的键
    title: '',            // 列名
    align: 'center',      // 居中对齐
    key: 'id',            // Vue 需要的 key，如果已经设置了唯一的 dataIndex，可以忽略这个属性
  },
  {
    dataIndex: 'title',
    title: '标题',
    align: 'center',
    // key: 'title',
  },
  {
    dataIndex: 'type.typename',
    title: '分类',
    align: 'center',
    // key: 'type',
  },
  {
    dataIndex: 'updateTime',
    title: '更新时间',
    align: 'center',
    // key: 'updateTime',
  },
  {
    title: '操作',
    // key: 'action',
    align: 'center',
    scopedSlots: { customRender: 'action' },  // 自定义渲染内容
  }
]

const dataSource = []

const pagination = {
  pageSize: 5
}
export default {
  name: 'Article',
  data: function () {
    return {
      dataSource: dataSource,
      columns: columns,
      pagination: pagination
    }
  },
  methods: {

    del: function (record) {
      const articleId = record.id
      const _this = this
      this.$confirm({
        title: 'Do you want to delete these items?',
        content: 'When clicked the OK button, this dialog will be closed after 1 second',
        onOk () {
          _this.$http.delete('/admin/article/' + articleId)
          const ds = [..._this.dataSource]
          _this.dataSource = ds.filter(item => item.id !== record.id)

          let index = 1
          _this.resetIndex()
        },
        onCancel () {
          _this.$message.info('You have cancelled deletion !', 1)
        }
      })
    },
    resetIndex: function () {
      let index = 1
      this.dataSource.forEach(item => item.index = index++)
    },
    update: function (record) {
    },
    getAllArticles: function () {
      this.$http.get('/admin/articles')
        .then(response => {
          this.dataSource = response.data.data
          this.dataSource.forEach(e => {
            if (e.updateTime === null) e.updateTime = e.createTime
            e.updateTime = e.updateTime.substring(0, 10)
          })
          // 主键有断层, 所以额外增加序号列,
          this.resetIndex()
        })
    }
  },
  watch: {
    // TODO
    '$route': 'getAllArticles'
  },
  created: function () {
    this.getAllArticles()
  }
}
</script>
<style lang="less" scoped>
.ant-row {
  position: relative;
  top: 100px;
}

.ant-table-pagination.ant-pagination {
  float: right;
  margin: -30px 0 !important;
}
</style>
