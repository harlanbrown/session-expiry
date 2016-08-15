function calcOffset() {
    var serverTime = getCookie('serverTime');
    serverTime = serverTime==null ? null : Math.abs(serverTime);
    var clientTimeOffset = (new Date()).getTime() - serverTime;
    setCookie('clientTimeOffset', clientTimeOffset);
}

window.onLoad = function() { calcOffset(); checkSession(); };

function checkSession() {
    var sessionExpiry = Math.abs(getCookie('sessionExpiry'));
    var timeOffset = Math.abs(getCookie('clientTimeOffset'));
    var localTime = (new Date()).getTime();
    if (localTime - timeOffset > (sessionExpiry+15000)) { // 15 extra seconds to make sure
        window.alert('Closing session');
    } else {
        setTimeout('checkSession()', 10000);
    }
}
