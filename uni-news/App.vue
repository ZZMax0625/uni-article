<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	@import url("./common/base.css");
	@import url("./common/icon.css");
	@import url("./common/style.css");

	page {
		background-color: #f6f6f6;
		height: 100%;
	}
	
	::-webkit-scrollbar{
		display: none;
	}
</style>
