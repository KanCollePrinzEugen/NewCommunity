<template>
  <h1>欢迎使用本系统</h1>
  <div class='container'>
    <el-form
        v-model='formData'
        label-width='70px'
    >
      <h2>请输入登录信息</h2>
      <el-form-item label='用户名' prop='name'>
        <el-input v-model='formData.loginName'></el-input>
      </el-form-item>
      <el-form-item label='登录密码' prop='password'>
        <el-input v-model='formData.loginPwd' type='password'></el-input>
      </el-form-item>
      <el-form-item label='验证码'>
        <el-input v-model='formData.validCode' style='width: 52%'></el-input>
        <el-image :src='captchaUrl' style='cursor: pointer'  @click='code'></el-image>
      </el-form-item>
      <el-form-item>
        <el-button type='primary' v-on:click='submitForm'>登录</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// import axios from 'axios'
import { getCode, getLogin } from '@/api/login'

export default {
  name: 'LoginView',
  data () {
    return {
      captchaUrl: '',
      captcha: '',
      formData: {
        loginName: '',
        loginPwd: '',
        validCode: ''
      }
    }
  },
  created () {
    this.code()
  },
  methods: {
    code: async function () {
      const { code, codeImg } = await getCode(5)
      this.captcha = code
      this.captchaUrl = codeImg
    },
    submitForm: async function () {
      const data = {
        loginName: this.formData.loginName,
        loginPwd: this.formData.loginPwd,
        validCode: this.formData.validCode
      }
      if (!data.loginName) {
        this.$err('请输入用户名')
        return
      }
      if (!data.loginPwd) {
        this.$err('请输入密码')
        return
      }
      if (!data.validCode) {
        this.$err('请输入验证码')
        return
      }
      if (String(data.validCode).toLowerCase() !== String(this.captcha).toLowerCase()) {
        await this.code()
        this.$err('验证码输入不正确')
        return
      }
      const res = await getLogin(data)
      console.log(res)
    }
  }
}
</script>

<style scoped>
  .container{
    width: 300px;
    margin: auto;
    background-color: aliceblue;
    border-radius: 5px;
    box-shadow: 4px 4px 5px #363636;
    color: black;
    padding: 1em;
  }
  h1{
    text-align: center;
  }
  el-form-item{
    margin: auto;
  }
</style>
