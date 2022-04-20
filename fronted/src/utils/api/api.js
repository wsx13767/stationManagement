const api =  ({url, method, headers, data }, option = {}) => {
    console.log(url);
    return fetch(`${process.env.REACT_APP_HOST_IP_ADDRESS}/${url}`, {
        method: method || 'GET',
        body: data ? JSON.stringify(data) : null,
        headers: headers || {
            'content-type': 'application/json'
        },
        mode: 'cors',
        ...option
    }).then(res => {
        if (!res.ok) return res.json().then((error) => alert(error.message));

        return res.json();
    })
    .catch(e => {
        alert("非預期錯誤");
    })
}

export default api;