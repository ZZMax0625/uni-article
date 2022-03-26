<template>
	<view>
		<view class="flex align-center justify-center flex-column pt-4 pb-3">
			<image src="/static/img/nothing.png" class="rounded-circle size-150">
			</image>
			<text class="font text-muted mt-2">version {{ version }}</text>
		</view>
		<!-- #ifdef APP-PLUS -->
		<view class="bg-white border-bottom">
			<uni-list-item title="新版本检测"></uni-list-item>
		</view>
		<!-- #endif -->
		<view class="bg-white border-bottom">
			<uni-list-item title="社区用户协议"></uni-list-item>
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
				version: '1.0.0'
			};
		},
		onLoad() {},
		methods: {}
	};
</script>

<style></style>
