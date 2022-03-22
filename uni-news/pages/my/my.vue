<template>
	<view>
		<!-- 未登录 -->
		<template v-if="!loginStatus">
			<view class="flex align-center p-2 bg-white">
				<image src="/static/img/nologin.jpg"  class="size-100 rounded-circle"></image>
				<view class="flex-1 flex flex-column px-3">
					<text class="font-md font-weight-bold text-muted">登录，体验更多功能</text>
				</view>
				<text class="iconfont icon-jinru" @tap="open('login')"></text>
			
			</view>
			<view class="p-2">
				<image src="/static/img/banner.png" mode="widthFix" class="w-100"></image>
			</view>

		</template>
		<!-- 已登录 -->
		<template v-else>
			<!-- 头像 -->
			<view class="flex align-center p-2 bg-white">
				<image :src="user.avatar" mode="" class="size-100 rounded-circle"></image>
				<view class="flex-1 flex flex-column px-3">
					<text class="font-md font-weight-bold text-muted">{{user.nickname}}</text>
					<view class="">
						<text class="mr-2">总帖数</text>
						<text class="text-warning mr-2">{{data[0].num}} </text>
						<text class="mr-2">今日发帖数</text>
						<text class="text-warning">{{data[1].num}}</text>
					</view>
				</view>
				<text class="iconfont icon-jinru" @tap="open('setting')"></text>

			</view>
			<!-- 数据区域 -->
			<view class="flex align-center p-2 py-3 bg-white" >
				<view class="flex flex-column flex-1 align-center justify-center" v-for="(item,inde) in data">
					<text class="text-muted mb-1">{{item.num}}</text>
					<text class="font-sm text-muted">{{item.title}}</text>
				</view>

			</view>
			<!-- banner区域 -->
			<view class="p-2">
				<image src="/static/img/banner.png" mode="widthFix" class="w-100"></image>
			</view>
			<!-- 列表区域 -->
			<uni-list-item showExtraIcon="" title="浏览历史" class="bg-white border-bottom">
				<text slot="icon" class="iconfont icon-liulan" ></text>
				
			</uni-list-item>
			<uni-list-item showExtraIcon="" title="会员VIP"  class="bg-white border-bottom">
				<text slot="icon" class="iconfont icon-huiyuanvip"></text>
			</uni-list-item>
			<uni-list-item showExtraIcon="" title="帖子审核"  class="bg-white border-bottom">
				<text slot="icon" class="iconfont icon-keyboard"></text>
			</uni-list-item>

		</template>
	</view>
</template>

<script>
	import uniListItem from '@/components/uni-list-item/uni-list-item.vue';
	export default {
		components:{
			uniListItem
		},
		data() {
			return {
				loginStatus: false,
				user: {},
				data: [
					{
					title:"帖子",
					num: 89,
				},
				{
					title:"动态",
					num: 60,
				},
				{
					title:"评论",
					num: 110,
				},
				{
					title:"粉丝",
					num: 120,
				}
				]
			}
		},
		onShow(){
			this.user = uni.getStorageSync('user')
			if(Object.keys(this.user).length === 0){
				this.loginStatus = false
			}else{
				this.loginStatus = true
			}
			console.log(this.user)
		},
		computed: {
			avatar() {
				console.log(this.user.avatar+"****************************************")
				return this.user.avatar;
			}
		},
		onNavigationBarButtonTap() {
			uni.showToast({
				title: "点击",
				duration: 2000
			})
		},
		onNavigationBarButtonTap() {
			uni.showToast({
				title:"点击",
				duration:2000
			})
		},
		methods: {
			open(path) {
				uni.navigateTo({
					url: `../${path}/${path}`
				})
			}
		}
	}
</script>

<style>

</style>
