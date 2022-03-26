<template>
	<scroll-view>
		<!-- #ifndef MP-WEIXIN  -->
		<uni-status-bar></uni-status-bar>
		<uni-nav-bar></uni-nav-bar>

		<!-- <view  class="bg-pink fixed">导航栏</view> -->

		<view class="banner" :style="{'--bgurl' : ('url(' + user.banner +')')}">
			<image :src="user.avatar" class="size-150 rounded-circle avatar"></image>
			<view class="info text-center">
				<view class="h2 text-white">
					{{user.nickname}}
				</view>
				<view class="h5 text-hover-light">
					{{user.address}}
				</view>
			</view>
		</view>

		<view class="item bg-hover-light m-2 text-center" v-for="count in 10">
			<h3>内容{{count}}</h3>
		</view>
	</scroll-view>
	<!-- #endif -->
</template>

<script>
	import uniStatusBar from '@/components/uni/uni-status-bar/uni-status-bar.vue'
	import uniNavBar from '@/components/uni/uni-nav-bar/uni-nav-bar.vue'

	export default {
		data() {
			return {
				user: {},
			}
		},
		onShow() {
			this.user = uni.getStorageSync('user');
			console.log(this.user)
		},
		methods: {

		}
	}
</script>

<style scoped>
	.fixed {
		padding-top: 66px;
		color: #fff;
		font-size: 16px;
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 999;
	}

	.avatar {
		position: absolute;
		border: 3px solid #ccc;
		left: 20rpx;
		top: 300rpx;
		z-index: 20;
	}

	.info {
		z-index: 20;
		position: absolute;
		right: 260rpx;
		top: 150rpx;
	}

	.banner {
		box-sizing: border-box;
		position: relative;
		width: 100%;
		height: 450rpx;
	}

	.banner::before {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 450rpx;
		/* background-image: linear-gradient(45deg, #ff0081, #6827b0); */
		background-image: var(--bgurl);
		background-repeat: no-repeat;
		background-size: cover;
		border-radius: 0 0 50% 50%/0 0 100% 100%;
		transform: scaleX(1.5);
		z-index: 10;
	}

	.item {
		padding-top: 50rpx;
		padding-bottom: 50rpx;
		border: none;
		border-radius: 30px 10px;
		z-index: 20;
	}

	.item::before {
		content: '';
		position: absolute;
		left: 20rpx;
		top: 0;
		width: 3px;
	}
</style>
