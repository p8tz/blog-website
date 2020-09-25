<template>
  <div class="login_container">
    <div class="login_box">
      <!--    头像    -->
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="">
      </div>
      <!--    表单    -->
      <a-form :form="form" :label-col="{ span: 7 }" :wrapper-col="{ span: 12 }" @submit="handleSubmit"
              class="login_form">
        <!--      账号      -->
        <a-form-item label="账号">
          <a-input
            v-decorator="['username',
                      {
                        rules: validate.username,
                        initialValue: 'polarnight',
                        validateTrigger: 'blur'
                      }]"
          />
        </a-form-item>
        <!--      密码      -->
        <a-form-item label="密码">
          <a-input
            v-decorator="['password',
                      {
                        rules: validate.password,
                        initialValue: '123456',
                        validateTrigger: 'blur'
                      }]"
            type="password"
          />
        </a-form-item>
        <!--      提交      -->
        <a-form-item :wrapper-col="{ span: 12, offset: 10 }">
          <a-button type="primary" html-type="submit">
            登录
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script>
// test jquery
// $(document).ready(function () {
//   console.log(123)
// })
export default {
  name: 'Login',
  data () {
    return {
      formLayout: 'horizontal',
      form: this.$form.createForm(this, { name: 'coordinated' }),
      validate: {
        username: [
          {
            type: 'string',
            required: true,
            message: 'username is required !'
          },
          {
            type: 'string',
            min: 8,
            max: 16,
            message: 'Length must between 8 and 16 !'
          }
        ],
        password: [
          {
            type: 'string',
            required: true,
            message: 'password is required !'
          },
          {
            type: 'string',
            min: 6,
            max: 20,
            message: 'Length must between 6 and 20 !'
          }
        ]
      }
    }
  },
  methods: {
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (err) {
          return
        }
        console.log('Received values of form: ', values)

        // TODO
        // axios 发送get请求验证用户信息
        this.$http.get('https://jsonplaceholder.typicode.com/users')
          .then(response => {
            console.log('Received values of database: ', response)
          })
          .catch(error => {
            console.log(error)
          })

        // 假信息
        if (values.username === 'polarnight' && values.password === '123456') {
          this.$message.success('Login Successfully', 1)
          // 假token
          window.sessionStorage.setItem('token', 'a fake token')
          this.$router.push('/home')
        } else {
          this.$message.error('Incorrect Username or Password !', 1)
        }
      })
    },
    reset () {
      this.form.setFieldsValue({
        username: 'polarnight',
        password: '123456'
      })
    }
  },
}
</script>

<style lang="less" scoped>
.login_container {
  background-color: #2b4b6b;
  height: 100%
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fcfcfc;
  border-radius: 20px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #bbb;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);

  img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>
