<template>
  <div class="login_container">
    <div class="login_box">
      <!--    头像    -->
      <div class="avatar_box">
        <img src="../../assets/logo.png" alt="">
      </div>
      <!--    表单    -->
      <a-form :form="form" :label-col="{ span: 7 }" :wrapper-col="{ span: 12 }"
              class="login_form">
        <!--      账号      -->
        <a-form-item label="账号">
          <a-input v-decorator="['username',
                      {
                        rules: validate.username,
                        initialValue: 'polarnight',
                        validateTrigger: 'blur'
                      }]"/>
        </a-form-item>
        <!--      密码      -->
        <a-form-item label="密码">
          <a-input v-decorator="['password',
                      {
                        rules: validate.password,
                        initialValue: '123456',
                        validateTrigger: 'blur'
                      }]"
                   type="password"/>
        </a-form-item>
        <!--      提交      -->
        <a-form-item :wrapper-col="{ span: 12, offset: 8 }">
          <a-button type="primary" html-type="submit" @click="login">
            登录
          </a-button>
          <a-button type="primary" html-type="submit" @click="register" style="margin-left: 20px">
            注册
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script>
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
            message: '请填写用户名！'
          },
          {
            type: 'string',
            min: 6,
            max: 16,
            message: '长度在6到16位之间！'
          }
        ],
        password: [
          {
            type: 'string',
            required: true,
            message: '请填写密码！'
          },
          {
            type: 'string',
            min: 6,
            max: 20,
            message: '长度6到20位之间！'
          }
        ]
      }
    }
  },
  methods: {
    login: function () {
      this.form.validateFields((err, values) => {
        if (err) return
        this.$http.post('/admin/login', {
          username: this.form.getFieldValue('username'),
          password: this.form.getFieldValue('password')
        })
          .then(response => {
            if (response.data.code === 1000) {
              window.sessionStorage.setItem('token', response.data.data.token)
              this.$router.push('/admin/index')
              this.$message.success('成功登录', 1)
            } else {
              this.$message.error('用户名或密码错误', 1)
            }
          })
      })
    },
    register: function () {
      this.form.validateFields((err, values) => {
        if (err) return
        this.$http.post('/admin/register', {
          username: this.form.getFieldValue('username').trim(),
          password: this.form.getFieldValue('password').trim()
        })
          .then(response => {
            const code = response.data.code
            if (code === 2003) {
              this.$message.error('用户名已存在', 1)
            } else {
              this.$message.info('注册成功', 1)
            }
          })
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