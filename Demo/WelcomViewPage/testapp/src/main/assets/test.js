function CallApp(name, params, callback) {
    if (params == null) {
        params = []
    };
    let data = {
        name: name,
        params: params,
        callback: null
    };
    if (callback != null) {
        var callback_name = 'C' + Math.random().toString(36).substr(2);
        window[callback_name] = function(obj) {
            callback(obj);
            delete window[callback_name]
        };
        data.callback = callback_name
    }
    Android.call(JSON.stringify(data))
};