<template>
	<view class="flex flex-column align-center justify-center pt-5">
		<image src="/static/img/nothing.png" mode="aspectFit"></image>
		<text class="font-lg">{{info}}</text>
	</view>
</template>

<script>
	export default {
		props: ['info'],
	}
</script>

<style>
</style>
