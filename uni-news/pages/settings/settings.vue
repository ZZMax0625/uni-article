<template>
	<view class="px-2">
		<view class="bg-white mt-1">
			<uni-list-item title="账号与安全" @tap="open('user-safe')">
			</uni-list-item>
		</view>

		<view class="bg-white mt-1">
			<uni-list-item title="资料编辑" @tap="open('user-edit')">
			</uni-list-item>
		</view>

		<view class="bg-white mt-1">
			<uni-list-item title="清除缓存" showBadge>
				<text slot="right" class="text-muted">{{currentSize | format}}</text>
			</uni-list-item>
		</view>

		<view class="bg-white mt-1">
			<uni-list-item title="意见反馈" @tap="open('user-feedback')">
			</uni-list-item>
		</view>

		<view class="bg-white mt-1">
			<uni-list-item title="关于社区" @tap="open('about')">
			</uni-list-item>
		</view>

		<view class="mt-1">
			<button class="rounded-circle bg-pink text-white shadow" @tap="logout">退出登录</button>
		</view>
	</view>
</template>

<script>
	import uniListItem from '@/components/uni/uni-list-item/uni-list-item.vue';
	export default {
		components: {
			uniListItem
		},
		data() {
			return {
				currentSize: 10010 //缓存大小
			}
		},
		onLoad() {
			//调用methods中的getStorageInfo方法，获取本地存储中的信息
			this.getStorageInfo();
		},
		//过滤器，按照缓存大小显示不同单位
		filters: {
			format(value) {
				return value > 1024 ? (value / 1024).toFixed(2) + 'MB' : value.toFixed(2) + 'KB';
			}
		},
		methods: {
			//获得本地存储内容，并将其大小赋值给当前缓存容量
			getStorageInfo() {
				let res = uni.getStorageInfoSync();
				this.currentSize = res.currentSize;
			},
			// 封装根据path跳转页面的方法
			open(path) {
				uni.navigateTo({
					url: `../${path}/${path}`,
				})
			},
			// 退出登录
			logout() {
				// this.$msg.toast('是否要退出登录','suc')

				uni.showModal({
					content: '是否要退出登录',
					success: res => {
						if (res.confirm) {
							uni.clearStorageSync();
							//返回上一级页面
							uni.navigateBack({
								delta: 1
							});
							uni.showToast({
								title: '退出登录成功',
								icon: 'none'
							});
						}
					}
				});
			}
		}
	}
</script>

<style>

</style>
