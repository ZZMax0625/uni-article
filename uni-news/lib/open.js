const open = {
	go(path) {
		uni.navigateTo({
			url: `/pages/{path}/{path}`
		})
	}
}

module.exports = open
