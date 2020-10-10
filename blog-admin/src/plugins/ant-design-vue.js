import Vue from 'vue'
import {
  Pagination,
  Button,
  LocaleProvider,
  Form,
  FormModel,
  Input,
  message,
  Menu,
  Icon,
  Layout,
  Row,
  Col,
  Table,
  Divider,
  Tag,
  Modal,
  Tooltip,
  Select,
  Popconfirm
} from 'ant-design-vue'

// Vue.component(LocaleProvider.name, LocaleProvider)
// Vue.component(Pagination.name, Pagination)
// Vue.component(Button.name, Button)

// Vue.use(LocaleProvider)
Vue.use(Pagination)
Vue.use(Button)
Vue.use(Form)
Vue.use(FormModel)
Vue.use(Input)
Vue.use(Menu)
Vue.use(Icon)
Vue.use(Layout)
Vue.use(Row)
Vue.use(Col)
Vue.use(Table)
Vue.use(Divider)
Vue.use(Tag)
Vue.use(Tooltip)
Vue.use(Select)
Vue.use(Popconfirm)

Vue.prototype.$message = message
Vue.prototype.$confirm = Modal.confirm
