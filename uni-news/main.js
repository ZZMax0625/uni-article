import App from './App'

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false


import message from './lib/message.js';
Vue.prototype.$msg = message;


import http from './lib/http.js';
Vue.prototype.$http = http;

import open from './lib/open.js';
Vue.prototype.$open = open;


// import mqxu from './lib/index.js'
// Vue.use(mqxu)


App.mpType = 'app'

const app = new Vue({
	...App
})
app.$mount()
// #endif



// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif
