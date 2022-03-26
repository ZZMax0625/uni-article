// index.js
const http = require('./http')
const message = require('./message')
const store = require('./store')
const open = require('./open')
const mqxu = {
	http,
	message,
	store,
	open,
	install(Vue) {
		this.store._init()
		for (let i in this) {
			if (i == 'install') {
				continue
			}
			Vue.prototype[i] = this[i]
		}
		delete this.install
	}
}

export default mqxu
