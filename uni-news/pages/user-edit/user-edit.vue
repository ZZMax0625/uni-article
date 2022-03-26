<template>
	<view>
		<uni-list-item title="头像" @click="changeAvatar">
			<view class="flex align-center" slot="right">
				<image :src="avatar" class="size-100 rounded-circle"></image>
				<text class="iconfont icon-bianji1 ml-2"></text>
			</view>
		</uni-list-item>

		<uni-list-item title="昵称">
			<view class="flex align-center" slot="right">
				<input class="uni-input text-right" v-model="nickname" />
				<text class="iconfont icon-bianji1 ml-2"></text>
			</view>
		</uni-list-item>

		<uni-list-item title="性别" @click="changeGender">
			<view class="flex align-center" slot="right">
				<text>{{ genderText }}</text>
				<text class="iconfont icon-bianji1 ml-2"></text>
			</view>
		</uni-list-item>

		<picker mode="date" :value="birthday" @change="onDateChange">
			<uni-list-item title="生日">
				<view class="flex align-center" slot="right">
					<text>{{ birthday }}</text>
					<text class="iconfont icon-bianji1 ml-2"></text>
				</view>
			</uni-list-item>
		</picker>

		<uni-list-item title="地址" @click="showCityPicker">
			<view class="flex align-left" slot="right">
				<text class="flex-1">{{ address }}</text>
				<text class="iconfont icon-bianji1 ml-2"></text>
			</view>
		</uni-list-item>

		<view class="py-2 px-3">
			<button class="bg-pink text-white rounded-circle" @tap="submit">
				完成
			</button>
		</view>

		<mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault"
			@onConfirm="onConfirm"></mpvue-city-picker>
	</view>
</template>

<script>
	const genderArray = ['保密', '男', '女'];
	import uniListItem from '@/components/uni/uni-list-item/uni-list-item.vue';
	import mpvueCityPicker from '@/components/uni/mpvue-citypicker/mpvueCityPicker.vue';
	import config from '@/lib/config.js';

	export default {
		components: {
			uniListItem,
			mpvueCityPicker
		},
		data() {
			return {
				themeColor: '#FC5C82',
				cityPickerValueDefault: [0, 0, 1],
				user: {},
				nickname: '',
				avatar: '',
				gender: 0,
				birthday: '',
				address: '',
				flag:false
			};
		},
		// 监听返回
		onBackPress() {
			if (this.$refs.mpvueCityPicker.showPicker) {
				this.$refs.mpvueCityPicker.pickerCancel();
				return true;
			}
		},
		// 监听页面卸载
		onUnload() {
			if (this.$refs.mpvueCityPicker.showPicker) {
				this.$refs.mpvueCityPicker.pickerCancel();
			}
		},
		//  监听页面加载
		onShow() {
			this.user = uni.getStorageSync('user');
			this.avatar = this.user.avatar;
			this.nickname = this.user.nickname;
			this.gender = this.user.gender;
			this.birthday = this.user.birthday;
			this.address = this.user.address;
		},
		computed: {
			genderText: {
				set(newValue) {
					this.genderText = newValue;
				},
				get() {
					return genderArray[this.gender];
				}
			},
		},
		methods: {
			//显示三级联动城市选择组件
			showCityPicker() {
				this.$refs.mpvueCityPicker.show();
			},
			// 三级联动选择组件提交事件，监听相应的值并显示
			onConfirm(e) {
				this.address = e.label;
			},
			// 监听日期选取，改变生日显示的值
			onDateChange(e) {
				this.birthday = e.detail.value;
			},
			// 选择头像
			changeAvatar() {
				uni.chooseImage({
					count: 1,
					sizeType: ['compressed'],
					sourceType: ['album', 'camera'],
					success: res => {
						// console.log(res.tempFilePaths[0]);
						// 改变响应式数据，实时预览
						this.avatar = res.tempFilePaths[0]
						this.flag = true
					}
				});
			},
			// 修改性别
			changeGender() {
				uni.showActionSheet({
					itemList: genderArray,
					success: res => {
						this.gender = res.tapIndex;
						if (this.gender == 0) {
							this.genderText = '保密';
						} else if (this.gender == 1) {
							this.genderText = '男';
						} else if (this.gender == 2) {
							this.genderText = '女';
						}
					}
				});
			},
			// 提交修改请求
			submit() {
				// 先上传头像,这里没用封装的上传，所以导入了config模块，便于真机调试的时候，使用穿透的地址
				uni.uploadFile({
					url: config.baseUrl + '/users/upload',
					filePath: this.avatar,
					name: 'file',
					success: (uploadFileRes) => {
						// 注意这里的返回值是string，需要反序列化
						console.log(typeof uploadFileRes.data)
						this.avatar = JSON.parse(uploadFileRes.data).data
						// 上传成功后,再调用修改个人信息接口，没有新值的就保留user的原值
						let data = {
							id: this.user.id,
							wxOpenId: this.user.wxOpenId,
							phone: this.user.phone,
							password: this.user.password,
							nickname: this.nickname === '' ? this.user.nickname : this.nickname,
							avatar: this.flag === false ? this.user.avatar : this.avatar,
							gender: this.gender,
							birthday: this.birthday === '' ? this.user.birthday : this.birthday,
							address: this.address === '' ? this.user.address : this.address,
							banner: this.user.banner,
							createTime: this.user.createTime
						};
						this.$http.post('/users/update', data, 'json').then(res => {
							console.log(res);
							uni.setStorageSync('user', res)
							uni.switchTab({
								url: '../my/my'
							})
						});
					},
				});

			}
		}
	};
</script>

<style scoped>
	.uni-list-item {
		background-color: #FFFFFF;
		border-bottom: 1px solid #CCCCCC;
		padding: 2px;
	}
</style>
