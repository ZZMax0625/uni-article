// 封装请求插件
//http.js
const config = require('./config.js')
const message = require('./message.js')

var http = {
	post(path, params, contentType = 'form', otherUrl, ) {
		return new Promise((resolve, reject) => {
			var url = (otherUrl || config.baseUrl) + path
			uni.request({
				method: 'POST',
				url,
				header: {
					"Content-Type": contentType === 'json' ? "application/json" :
						"application/x-www-form-urlencoded"
				},
				data: params,
				success: (res) => {
					console.log('request:POST请求' + config.baseUrl + path + ' 成功', res.data)
					resolve(res.data)
				},
				fail: (err) => {
					message.toast('请求失败', 'err')
					console.error('request:请求' + config.baseUrl + path + ' 失败', err)
					reject('请求失败')
				}
			})
		})
	},
	put(path, params, contentType = 'form', otherUrl, ) {
		return new Promise((resolve, reject) => {
			var url = (otherUrl || config.baseUrl) + path
			uni.request({
				method: 'PUT',
				url,
				header: {
					"Content-Type": contentType === 'json' ? "application/json" :
						"application/x-www-form-urlencoded"
				},
				data: params,
				success: (res) => {
					console.log('request:PUT请求' + config.baseUrl + path + ' 成功', res.data)
					resolve(res.data)
				},
				fail: (err) => {
					message.toast('请求失败', 'err')
					console.error('request:PUT请求' + config.baseUrl + path + ' 失败', err)
					reject('请求失败')
				}
			})
		})
	},

	get(path, params, otherUrl) {
		return new Promise((resolve, reject) => {
			var url = (otherUrl || config.baseUrl) + path
			uni.request({
				url,
				data: params,
				success: (res) => {
					console.log('request:GET请求' + config.baseUrl + path + ' 成功', res.data)
					resolve(res.data)
				},
				fail: (err) => {
					message.toast('请求失败', 'err')
					console.error('request:GET请求' + config.baseUrl + path + ' 失败', err)
					reject(err)
				}
			})

		})

	},
	delete(path, params, otherUrl) {
		return new Promise((resolve, reject) => {
			var url = (otherUrl || config.baseUrl) + path
			uni.request({
				url,
				data: params,
				method: "DELETE",
				success: (res) => {
					console.log('request:DELETE请求' + config.baseUrl + path + ' 成功', res.data)
					resolve(res.data)
				},
				fail: (err) => {
					message.toast('请求失败', 'err')
					console.error('request:DELETE请求' + config.baseUrl + path + ' 失败', err)
					reject(err)
				}
			})

		})

	},

	// 封装上传文件请求
	// upload(url, options = {}) {
	// 	options.url = config.baseUrl + url
	// 	options.header = options.header || {}
	// 验证权限token
	// if (options.token) {
	// 	options.header.token = $store.state.token
	// 	if (!options.header.token) {
	// 		return uni.showToast({
	// 			title: '非法token,请重新登录',
	// 			icon: 'none'
	// 		});
	// 	}
	// }

	// 	return new Promise((res, rej) => {
	// 		uni.uploadFile({
	// 			...options,
	// 			success: (uploadFileRes) => {
	// 				if (uploadFileRes.statusCode !== 200) {
	// 					return uni.showToast({
	// 						title: '上传图片失败',
	// 						icon: 'error'
	// 					});
	// 				}
	// 				let data = JSON.parse(uploadFileRes.data)
	// 				res(data)
	// 			},
	// 			fail: (err) => {
	// 				rej(err)
	// 			}
	// 		});
	// 	})
	// }

	// async upload(path, fileArray, otherUrl) {
	// 	if (typeof fileArray !== 'object') {
	// 		console.error('request:参数错误,请传入文件数组')
	// 		return
	// 	}
	// 	var url = (otherUrl || config.baseUrl) + path
	// 	var arr = []
	// 	for (let i in fileArray) {
	// 		const res = await uni.uploadFile({
	// 			url: otherUrl || config.baseUrl + path,
	// 			filePath: fileArray[i],
	// 			name: 'file'
	// 		})
	// 		console.log(res)
	// 		if (res[0]) {
	// 			console.error('request:上传失败', res[0])
	// 			return
	// 		}
	// 		arr.push(JSON.parse(res[1].data).data)
	// 	}
	// 	return arr
	// },

}

module.exports = http
