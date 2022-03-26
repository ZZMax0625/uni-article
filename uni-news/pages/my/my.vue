<template>
	<view>
		<!-- 状态栏 -->
		<!-- #ifndef MP-WEIXIN  -->
		<uni-status-bar></uni-status-bar>

		<template v-if="!loginStatus">
			<view class="px-2 banner"
				style="background-image: url(../../static/img/banner.jpg);background-size: cover;">
				<view class="flex align-center bg-white rounded shadow p-3">
					<image src="../../static/img/nologin.jpeg" class="size-120 rounded-circle avatar shadow"></image>
					<view class="flex flex-column flex-1 px-2">
						<text class="font text-muted">登录，体验更多功能</text>
					</view>
					<text class="iconfont icon-jinru font-lg text-muted" @tap="open('login')"></text>
				</view>
			</view>
		</template>

		<template v-else>
			<!-- <view class="px-2  banner" :style="{backgroundImage: 'url(' + user.banner+')',backgroundSize:'cover'}"> -->
			<view class="px-2 banner" :style="{'--bgurl':('url(' + user.banner + ')')}">
				<!-- 个人信息区域 -->
				<view class="flex mb-3">
					<image :src="user.avatar" class="size-150 rounded-circle mr-2 avatar"></image>
					<view class="p-2  rounded flex-1 flex align-center shadow bg-white"
						style="z-index: 50;position: absolute;top:200rpx;right:10rpx;opacity: 0.5;">
						<view class="flex-1 flex flex-column px-3">
							<view class="flex align-center mb-1">
								<text class="font-md font-weight-bold text-body mr-2">{{user.nickname}}</text>
								<image :src="genderIcon" class="size-36"></image>
							</view>

							<view>
								<text class="mr-1 text-muted">{{user.address}}</text>
							</view>
						</view>
						<text class="iconfont icon-jinru text-muted font-lg" @tap="open('settings')">
						</text>
					</view>
				</view>

				<!-- 数据区域 -->
				<view class="flex align-center bg-white py-3 mt-5 mb-1 shadow" style="z-index: 60;">
					<view v-for="(item,index) in data" :key="index"
						class="flex flex-column flex-1 align-center justify-center">
						<text class="mb-1 font-md text-body  font-weight-bold">{{item.num}}</text>
						<text class="font-sm text-muted">{{item.title}}</text>
					</view>
				</view>

				<!-- 列表区域 -->
				<view class="pt-2" style="z-index: 60;">
					<view class="border-bottom  py-1 bg-white rounded">
						<uni-list-item showExtraIcon title="浏览历史">
							<text slot="icon" class="iconfont icon-liulan text-body font-md">
							</text>
						</uni-list-item>
					</view>

					<view class="border-bottom  py-1 bg-white rounded">
						<uni-list-item showExtraIcon title="会员VIP">
							<text slot="icon" class="iconfont icon-huiyuanvip text-body font-md"></text>
						</uni-list-item>
					</view>

					<view class="border-bottom  py-1 bg-white rounded">
						<uni-list-item showExtraIcon title="帖子审核">
							<text slot="icon" class="iconfont icon-keyboard text-body font-md"></text>
						</uni-list-item>
					</view>
				</view>
			</view>
		</template>
		<!-- #endif -->

	</view>

</template>

<script>
	import uniListItem from '@/components/uni/uni-list-item/uni-list-item.vue';
	import uniStatusBar from '@/components/uni/uni-status-bar/uni-status-bar.vue';

	export default {
		components: {
			uniListItem
		},
		data() {
			return {
				loginStatus: false,
				user: {},
				data: [{
						title: '帖子',
						num: 90,
					},
					{
						title: '动态',
						num: 12,
					},
					{
						title: '评论',
						num: 180,
					},
					{
						title: '粉丝',
						num: 88,
					}
				]
			}
		},
		onShow() {
			this.user = uni.getStorageSync('user')
			//  从本地缓存读取user，如果为空，loginStatus为false，否则为true，将其信息渲染在页面
			if (Object.keys(this.user).length === 0) {
				this.loginStatus = false
			} else {
				this.loginStatus = true
			}
			console.log(this.user)
			console.log(this.loginStatus)
		},
		computed: {
			avatar() {
				return this.user.avatar;
			},
			genderIcon() {
				switch (this.user.gender) {
					case 0:
						return '/static/img/secret.png';
						break;
					case 1:
						return '/static/img/male.png';
						break;
					case 2:
						return '/static/img/female.png';
						break;
				}
			}

		},
		onNavigationBarButtonTap() {
			uni.showToast({
				title: '点击',
				duration: 2000
			})
		},
		methods: {
			open(path) {
				uni.navigateTo({
					url: `../${path}/${path}`,
				})
			}
		}
	}
</script>

<style>
	.avatar {
		border: 3px solid #FFF;
		z-index: 20;
	}

	.banner {
		box-sizing: border-box;
		position: relative;
		width: 100%;
		height: 500rpx;
		padding-top: 320rpx;
		/* overflow: hidden; */
	}

	.banner::before {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		/* background-color: #000; */
		background-image: var(--bgurl);
		background-repeat: no-repeat;
		background-size: cover;
		border-radius: 0 0 50% 50%/0 0 100% 100%;
		transform: scaleX(1.5);
		z-index: 10;
	}
</style>
