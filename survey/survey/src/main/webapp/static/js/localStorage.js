/** 
 * localStorage的相关存储方法
 * 如果浏览器不支持localStorage，就让他去死好了
 */

function setLocalStorage(name, value) {
	if (window.localStorage) {
		value = JSON.stringify(value);
		localStorage.setItem(name, value);
		return true;
	}
	return false;
}

function getLocalStorage(name) {
	if (window.localStorage) {
		var value = localStorage.getItem(name);
		return JSON.parse(value);
	}
	return null;
}

function removeLocalStorage(name) {
	if (window.localStorage) {
		var value = getLocalStorage(name);
		localStorage.removeItem(name);
		return value;
	}
	return false;
}

function clearLocalStorage() {
	if (window.localStorage) {
		localStorage.clear();
		return true;
	}
	return false;
}