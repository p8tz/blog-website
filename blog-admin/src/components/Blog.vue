<template>

    <a-row>
      <a-col :span="12" :offset="6">
        <a-table bordered :columns="columns" :data-source="data" :pagination="pagination">

          <!-- record返回当前行信息 -->
          <span slot="action" slot-scope="text, record">
          <!-- 传入id值 -->
          <router-link :to="'/blog/'+record.key">编辑</router-link>

            <!--<a @click="update(record)">编辑</a>-->
          <a-divider type="vertical"/>
          <a @click="del(record)">删除</a>
        </span>

          <template slot="footer" slot-scope="currentPageData">
            <a-button>
              <!-- id为-1代表新增 -->
              <router-link to="/blog/-1">新增</router-link>
            </a-button>
          </template>
        </a-table>
      </a-col>
    </a-row>

</template>

<script>
const columns = [
  {
    dataIndex: 'number',  // 指向要渲染数据的键
    title: '',            // 列名
    align: 'center',      // 居中对齐
    key: 'number',        // Vue 需要的 key，如果已经设置了唯一的 dataIndex，可以忽略这个属性
  },
  {
    dataIndex: 'title',
    title: '标题',
    align: 'center',
    key: 'title',
  },
  {
    dataIndex: 'type',
    title: '分类',
    align: 'center',
    key: 'type',
  },
  {
    dataIndex: 'updateTime',
    title: '更新时间',
    align: 'center',
    key: 'updateTime',
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    scopedSlots: { customRender: 'action' },  // 自定义渲染内容
  }
]

const data = [
  {
    key: '1',
    number: 1,
    title: '动态代理',
    type: '设计模式',
    updateTime: '2020-09-24',
  },
  {
    key: '2',
    number: 2,
    title: 'AQS',
    type: '多线程',
    updateTime: '2020-09-24',
  },
  {
    key: '3',
    number: 3,
    title: '容器数据卷',
    type: 'docker',
    updateTime: '2020-09-24',
  },
  {
    key: '4',
    number: 4,
    title: '容器数据卷',
    type: 'docker',
    updateTime: '2020-09-24',
  },
  {
    key: '5',
    number: 5,
    title: '容器数据卷',
    type: 'docker',
    updateTime: '2020-09-24',
  },
  {
    key: '6',
    number: 6,
    title: '容器数据卷',
    type: 'docker',
    updateTime: '2020-09-24',
  }
]

const pagination = {
  pageSize: 5
}
export default {
  name: 'Blog',
  data: function () {
    return {
      data: data,
      columns: columns,
      pagination: pagination
    }
  },
  methods: {
    change: function (pagination, filters, sorter, currentDataSource) {
      console.log(pagination)
      console.log(filters)
      console.log(sorter)
      console.log(currentDataSource)
      this.data = [
        {
          key: '1',
          number: 1,
          title: '动态代理',
          type: '设计模式',
          updateTime: '2020-09-24',
        }
      ]
    },
    // 删除数据
    del: function (record) {
      const _this = this
      this.$confirm({
        title: 'Do you want to delete these items?',
        content: 'When clicked the OK button, this dialog will be closed after 1 second',
        onOk () {
          const dataSource = [..._this.data]
          _this.data = dataSource.filter(item => item.key !== record.key)
          _this.$message.success('Delete successfully !', 1)
        },
        onCancel () {
          _this.$message.info('You have cancelled deletion !', 2)
        }
      })
    },
    update: function (record) {
    }
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
  margin: -30px 0!important;
}
</style>
