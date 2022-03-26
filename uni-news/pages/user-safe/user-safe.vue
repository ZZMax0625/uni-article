<template>
	<view class="px-2">
		<view class="bg-white border-bottom mt-2">
			<uni-list-item title="修改密码" @click="open('edit-password')"></uni-list-item>
		</view>
		<view class="bg-white border-bottom">
			<uni-list-item :title="bandFlag === true ?'已绑定':'绑定手机'" @tap="band()" :disabled="bandFlag">
			</uni-list-item>
		</view>
		<view class="bg-white border-bottom">
			<uni-list-item title="绑定微信"></uni-list-item>
		</view>
		<view class="bg-white border-bottom">
			<uni-list-item title="绑定QQ"></uni-list-item> 
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
				user: {},
				bandFlag: false
			};
		},
		onShow() {
			this.user = uni.getStorageSync('user')
			console.log(this.user)
			// 用户的openId和手机号均非空，表示已经绑定
			if (this.user.wxOpenId.length > 0 && this.user.phone.length > 0) {
				this.bandFlag = true;
			}
			console.log(this.bandFlag)
		},
		methods: {
			band() {
				if (this.bandFlag) {
					return;
				}
			},
			open(path) {
				uni.navigateTo({
					url: `../${path}/${path}`
				})
			}
		}
	};
</script>

<style></style>
