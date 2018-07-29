// 退出全屏
function escFullScreen(){
    var e = document;
    e.exitFullscreen ? e.exitFullscreen() : e.mozCancelFullScreen ? e.mozCancelFullScreen() : e.webkitCancelFullScreen && e.webkitCancelFullScreen()
}

// 全屏
function fullScreen() {
    var e = document.documentElement;
    if (e.requestFullscreen)
        e.requestFullscreen();
    else if (e.mozRequestFullScreen)
        e.mozRequestFullScreen();
    else if (e.webkitRequestFullScreen)
        e.webkitRequestFullScreen();
    else if ("undefined" != typeof window.ActiveXObject) {
        var t = new ActiveXObject("WScript.Shell");
        t && t.SendKeys("{F11}");
    }
}