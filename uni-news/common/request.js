import $C from '@/common/config.js';
import $store from '@/store/index.js';
export default {
	// 定义请求公共部分内容
	common: {
		method: 'GET',
		header: {
			"content-type": "application/json"
		},
		data: {}
	},
	// 封装网络请求
	request(options = {}) {
		options.url = $C.serverUrl + options.url
		options.method = options.method || this.common.method
		options.header = options.header || this.common.header

		// 验证权限token
		if (options.token) {
			options.header.token = $store.state.token
			if (!options.noCheck && !options.header.token && !options.notoast) {
				return uni.showToast({
					title: '非法token,请重新登录',
					icon: 'none'
				});
			}
		}

		// 异步请求
		return new Promise((res, rej) => {
			uni.request({
				// 解构赋值
				...options,
				success: (result) => {
					// 返回响应原始数据
					if (options.native) {
						return res(result)
					}
					// 请求服务端失败
					if (result.statusCode !== 200 && !options.notoast) {
						uni.showToast({
							title: result.data.msg || '请求失败',
							icon: 'none'
						});
						return rej(result.data)
					}
					// 请求成功，返回请求的数据（已经做了两层解析）
					res(result.data.data)
				},
				fail: (error) => {
					uni.showToast({
						title: error.errMsg || '请求失败',
						icon: 'none'
					});
					return rej()
				}
			});
		})
	},
	// 封装get请求
	get(url, data = {}, options = {}) {
		options.url = url
		options.data = data
		options.method = 'GET'
		return this.request(options)
	},
	// 封装post请求
	post(url, data = {}, options = {}) {
		options.url = url
		options.data = data
		options.method = 'POST'
		return this.request(options)
	},
	// 封装上传文件请求
	upload(url, options = {}) {
		options.url = $C.webUrl + url
		options.header = options.header || {}
		// 验证权限token
		if (options.token) {
			options.header.token = $store.state.token
			if (!options.header.token) {
				return uni.showToast({
					title: '非法token,请重新登录',
					icon: 'none'
				});
			}
		}

		return new Promise((res, rej) => {
			uni.uploadFile({
				...options,
				success: (uploadFileRes) => {
					if (uploadFileRes.statusCode !== 200) {
						return uni.showToast({
							title: '上传图片失败',
							icon: 'none'
						});
					}
					let data = JSON.parse(uploadFileRes.data)
					res(data)
				},
				fail: (err) => {
					rej(err)
				}
			});
		})
	}
}
