<template>
	<view class="h-100 bg-white">
		<!-- 状态栏 -->
		<!-- #ifndef MP-WEIXIN  -->
		<uni-status-bar></uni-status-bar>
		<!-- 返回按钮 -->
		<view @tap="back()" class="iconfont icon-guanbi mt-5 ml-5 font-lg text-muted size-100">
		</view>
		<!-- #endif -->


		<!-- 账号密码登录 -->
		<template v-if="choice">
			<view>
				<view class="text-center" style="padding-top: 130rpx;padding-bottom: 130rpx;">
					<text class="h3 text-body">账号密码登录</text>
				</view>
				<view class="flex mb-1 p-1">
					<input type="text" v-model="phone" placeholder="手机号" class="border-bottom p-2 flex-1">
				</view>
				<view class="flex mb-1 p-1">
					<input type="text" v-model="password" placeholder="请输入密码" class="p-2 flex-1">
					<view class="font-sm text-muted px-3 flex align-center">
						忘记密码?
					</view>
				</view>
				<view class="p-2">
					<button class="rounded-circle  text-white" :disabled="disabled" :loading="loading"
						:class="disabled ? 'bg-pink-disabled' : 'bg-pink'" @tap="passwordLogin()">
						登录</button>
				</view>

				<view class="text-center mt-3 text-primary font-sm">
					<text @tap="choice=!choice">验证码登录 </text>
					<text class="mx-2">|</text>
					<text>登录遇到问题</text>
				</view>

				<view class="text-center text-muted mt-3">
					<label class="flex justify-center">
						<checkbox :value="checked" />
						<view>
							<text>注册即代表同意 </text>
							<text class="text-primary">《xxx协议》</text>
						</view>
					</label>
				</view>
			</view>

		</template>


		<!-- 验证码登录 -->
		<template v-else>
			<view>
				<view class="text-center" style="padding-top: 130rpx;padding-bottom: 130rpx;">
					<text class="h3 text-body">手机验证码登录</text>
				</view>
				<view class="flex mb-1 p-1">
					<view class="border-right font px-2 flex align-center">+86</view>
					<input type="text" v-model="phone" placeholder="手机号" class="border-bottom p-2 flex-1">
				</view>
				<view class="flex md-2 p-2">
					<input type="text" v-model="code" placeholder="请输入验证码" class="p-2 flex-1">
					<view @tap="getCode()" :class="limitTime > 0 ? 'bg-pink-disabled': 'bg-pink'"
						class="font-sm  col-2  rounded text-white flex align-center justify-center">
						{{limitTime > 0 ? limitTime + ' s' : '获取验证码'}}
					</view>
				</view>
				<view class="p-2">
					<button class="rounded-circle bg-pink text-white bg-pink" @tap="smsLogin()">登录</button>
				</view>

				<view class="text-center mt-3 text-primary font-sm">
					<text @tap="choice=!choice">账号密码登录 </text>
					<text class="mx-2">|</text>
					<text>登录遇到问题</text>
				</view>

				<view class="text-center text-muted mt-3">
					注册即代表同意
					<text class="text-primary">《xxx协议》</text>
				</view>
			</view>

			<!-- 第三方登录 -->
			<view class="flex align-center px-5 py-3 mt-3">
				<view class="flex-1 flex align-center justify-center">
					<view @tap="appLogin"
						class="iconfont icon-weixin  bg-success font-lg text-white flex align-center justify-center rounded-circle size-100">
					</view>
				</view>
				<view class="flex-1 flex align-center justify-center">
					<view
						class="iconfont icon-QQ  bg-primary font-lg text-white flex align-center justify-center rounded-circle size-100">
					</view>
				</view>
				<view class="flex-1 flex align-center justify-center">
					<view
						class="iconfont icon-xinlangweibo  bg-warning font-lg text-white flex align-center justify-center rounded-circle size-100">
					</view>
				</view>
			</view>
		</template>
	</view>
</template>

<script>
	import uniStatusBar from '@/components/uni/uni-status-bar/uni-status-bar.vue';

	export default {
		components: {
			uniStatusBar
		},
		data() {
			return {
				choice: true,
				checked: false,
				phone: '15162310144',
				password: '',
				code: '',
				limitTime: 0,
				loading: false,
			}
		},
		computed: {
			disabled() {
				if ((this.phone === '' || this.password === '') && (this.phone === '' || this.code === '')) {
					return true;
				}
				return false;
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			// 初始化表单
			initForm() {
				this.phone = '';
				this.password = '';
				this.code = '';
			},
			validate() {
				//手机号正则验证
				var mPattern = /^1[34578]\d{9}$/;
				if (!mPattern.test(this.phone)) {
					this.$msg.toast('手机号格式错误', 'err')
					return false;
				}
				return true;
			},
			passwordLogin() {
				const flag = this.validate();
				if (!flag) {
					return
				}
				//账号密码登录
				let data = {
					phone: this.phone,
					password: this.password
				}
				this.$http.post('/users/login', data, 'json').then((res) => {
					if (res.code === 1) {
						this.$msg.toast('登录成功', 'suc');
						uni.setStorageSync('user', res.data)
						uni.switchTab({
							url: '../my/my'
						})
					} else {
						this.$msg.toast(res.msg)
						return false
					}
				})
			},
			getCode() {
				// 防止重复获取
				if (this.limitTime > 0) {
					return;
				}
				// 验证手机号没通过
				if (!this.validate()) {
					return;
				}
				// 请求验证码接口
				this.$http.post('/users/sms?phone=' + this.phone).then((res) => {
					if (res.code === 1) {
						this.$msg.toast('验证码已发送', 'suc');
						this.limitTime = 60;
						let timer = setInterval(() => {
							if (this.limitTime >= 1) {
								this.limitTime--;
							} else {
								this.limitTime = 0;
								clearInterval(timer);
							}
						}, 1000);
					} else {
						this.$msg.toast(res.msg)
					}
				})
			},
			smsLogin() {
				let data = {
					phone: this.phone,
					code: this.code
				}
				this.$http.post('/users/login/sms', data, 'json').then((res) => {
					console.log(res)
					if (res.code === 1) {
						this.$msg.toast('登录成功', 'suc');
						uni.setStorageSync('user', res.data)
						uni.switchTab({
							url: '../my/my'
						})
					} else {
						this.$msg.toast(res.msg)
						return false
					}
				})
			},
			appLogin() {
				let self = this;
				uni.login({
					provider: 'weixin',
					success: function(loginRes) {
						console.log(loginRes)
						uni.getUserInfo({
							provider: 'weixin',
							success: infoRes => {
								console.log(infoRes);
								let wxLoginDto = {
									wxOpenId: infoRes.userInfo.openId,
									nickname: infoRes.userInfo.nickName,
									avatar: infoRes.userInfo.avatarUrl,
									gender: infoRes.userInfo.gender
								};
								self.$http
									.post('/users/login/wx', wxLoginDto, 'json')
									.then((res) => {
										console.log(res);
										if (res.code === 1) {
											uni.showModal({
												title: '登录成功',
												success() {
													uni.setStorageSync('user', res.data);
													// 已经绑定手机号,直接进主页
													if (res.data.phone.length > 0) {
														uni.switchTab({
															url: '../my/my'
														})
													} else {
														// 没有绑定，调到绑定手机页面
														uni.navigateTo({
															url: '../bind-phone/bind-phone'
														})
													}

												}
											});

										} else {
											uni.showModal({
												title: '登录失败'
											});
											return false;
										}
									})
							}
						});
					}
				});
			},
		},
	}
</script>

<style>

</style>
