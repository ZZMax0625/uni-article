<template>
	<view>
		<!-- #ifndef MP-WEIXIN -->
		<uni-status-bar></uni-status-bar>
		<view @tap="back" class="iconfont icon-guanbi mt-5 ml-5 font-lg text-muted size-100"></view>
		<!-- #endif -->

		<template v-if="choice">
			<!-- 账号密码登录 -->
			<view>
				<view class="text-center" style="padding-top: 130rpx;padding-bottom: 130rpx;">
					<text class="h3 text-body ">账号密码登录</text>
				</view>
				<view class="flex mb-1 p-1 ">
					<input type="text" v-model="phone" placeholder="手机号" class="border-bottom p-2 flex-1" />
				</view>
				<view class="flex mb-1 p-1 ">
					<input type="password" v-model="password" value="" placeholder="请输入密码" class="p-2 flex-1" />
					<view class=" font-sm text-muted px-3 flex align-center">
						忘记密码?
					</view>
				</view>
				<view class="p-2">
					<button class="rounded-circle bg-pink text-white" :class="disabled ? 'bg-pink-disabled' : 'bg-pink'"
						:loading="loading" @tap="login()">{{loading ? '登陆中。。。' : '登录'}}</button>
				</view>
				<view class="text-center mt-3 text-primary font-sm">
					<text @tap="choice=!choice">验证码登录 |</text>
					<text>登录遇到问题</text>
				</view>
				<view class="text-center text-muted mt-3 ">
					<label class="flex justify-center  ">
						<checkbox :value="checked" />
						<view class="">
							<text>注册即代表同意</text>
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
					<text class="h3 text-body ">手机验证码登录</text>
				</view>
				<view class="flex mb-1 p-1 ">
					<view class="border-right font px-2 flex align-center">
						+86
					</view>
					<input type="text" v-model="phone" placeholder="手机号" class="border-bottom p-2 flex-1" />
				</view>
				<view class="flex md-2 p-2 ">
					<input type="text" v-model="verifyCode"  placeholder="请输入验证码"
						class=" p-2 flex-1" />
					<view @tap="getCode" :class="limitTime > 0 ? 'bg-pink-disabled': 'bg-pink'"
						class=" font-sm col-2 rounded text-white flex align-center justify-center">
						{{limitTime > 0 ? limitTime + 's' : '获取验证码'}}
					</view>
				</view>
				<view class="p-2">
					<button class="rounded-circle bg-pink text-white" :class="disabled ? 'bg-pink-disabled' : 'bg-pink'">登录</button>
				</view>
				<view class="text-center mt-3 text-primary font-sm">
					<text @tap="choice=!choice">账号密码登录 |</text>
					<text>登录遇到问题</text>
				</view>
				<view class="text-center text-muted mt-3">
					注册即代表同意
					<text class="text-primary">《xxx协议》</text>
				</view>
			</view>
			<!-- 第三方登录 -->
			<view class="flex align-center px-5 mt-3">
				<view class="flex-1 flex align-center justify-center" @click="appLogin">
					<view class="iconfont icon-weixin bg-success font-lg text-white flex align-center justify-center rounded-circle size-100"></view>
				</view>
				<view class="flex-1 flex align-center justify-center" @click="appLogin">
					<view class="iconfont icon-QQ bg-primary font-lg text-white flex align-center justify-center rounded-circle size-100"></view>
				</view>
				<view class="flex-1 flex align-center justify-center" @click="appLogin">
					<view class="iconfont icon-xinlangweibo bg-warning font-lg text-white flex align-center justify-center rounded-circle size-100"></view>
				</view>
			</view>
		</template>

	</view>
</template>

<script>
	import uniStatusBar from "@/components/uni-status-bar/uni-status-bar.vue"
	import $C from '@/common/config.js'
	export default {
		components: {
			uniStatusBar
		},
		data() {
			return {
				disabled: false,
				choice: true,
				checked: false,
				loading: false,
				phone: "15162310144",
				password: "000625",
				verifyCode: "",
				limitTime: 0
			}
		},
		methods: {
			back() {
				uni.showToast({
					delta: 1
				})
			},
			validate() {
				//手机号正则
				var mPattern = /^1[34578]\d{9}$/;
				if (!mPattern.test(this.phone)) {
					uni.showToast({
						title: '手机号格式不正确',
						icon: 'none'
					});
					return false;
				}
				// ...更多验证
				return true;
			},
			login(){
				let data = {
					phone: this.phone,
					password: this.password
				}
				const url = $C.serverUrl + '/users/login';
				console.log(url)
				uni.request({
					url: url,
					method: 'POST',
					data: data
				}).then((res) => {
					if(res[1].data.code === 1){
						uni.showToast({
							title:"登录成功",
							duration: 1000
						})
						uni.setStorage({
							key: 'user',
							data: res[1].data.data,
							success: function(){
								uni.switchTab({
									url: '../my/my'
								})
							}
						});
					}else{
						uni.showToast({
							title: res[1].data.msg,
							duration: 1000
						});
					}
				})
			},
			getCode() {
				// 防止重复获取
				if (this.limitTime > 0) {
					return;
				}
				// 验证手机号
				if (!this.validate()) {
					return
				}
				this.limitTime = 5;
				let timer = setInterval(() => {
					if (this.limitTime >= 1) {
						this.limitTime--;
					} else {
						this.limitTime = 0;
						clearInterval(timer);
					}
				}, 1000)
			}
		}
	}
</script>

<style>

</style>
