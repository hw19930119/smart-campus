module.exports = {

    /**
     * http请求，get方式获取数据
     */
    get: api => {
        return new Promise(resolve => {
            fetch(api, { credentials: 'include' }).then(res => {
                return res.text();
            })
                .then(res => {
                    const data = JSON.parse(res);

                    resolve(data);
                });
        });
    },

    /**
     * http请求，post方式获取数据
     */
    post: (api, param) => {
        return new Promise(resolve => {
            fetch(api, { credentials: 'include',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(param)
            }).then(res => {
                return res.text();
            })
                .then(res => {
                    const data = JSON.parse(res);

                    resolve(data);
                });
        });
    }
};


