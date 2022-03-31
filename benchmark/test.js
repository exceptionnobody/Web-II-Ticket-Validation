'use strict';

const jwt = require('jsonwebtoken');

const testValidation = function() {

    const v = Math.floor(Math.random());
    let token
    if (v >= 0.5) {
        token = jwt.sign({zone: '1'}, 'shhhhh');
        return new Promise((resolve, reject) => {
            fetch("http://localhost:8080/validate", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(token),
            }).then((response) => {
                if (response.ok) {
                    resolve(null);
                } else {
                    // analyze the cause of error
                    response.json()
                        .then((obj) => {
                            reject(obj);
                        }) // error msg in the response body
                        .catch((err) => {
                            reject({errors: [{param: "Application", msg: "Cannot parse server response"}]})
                        }); // something else
                }
            }).catch((err) => {
                reject({errors: [{param: "Server", msg: "Cannot communicate"}]})
            }); // connection errors
        });
    } else {
        token = jwt.sign({zone: '1'}, atob("LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU="));

        return new Promise((resolve, reject) => {
            fetch("localhost:8080/validate", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(token),
            }).then((response) => {
                if (response.ok) {
                    resolve(null);
                } else {
                    // analyze the cause of error
                    response.json()
                        .then((obj) => {
                            reject(obj);
                        }) // error msg in the response body
                        .catch((err) => {
                            reject({errors: [{param: "Application", msg: "Cannot parse server response"}]})
                        }); // something else
                }
            }).catch((err) => {
                reject({errors: [{param: "Server", msg: "Cannot communicate"}]})
            }); // connection errors
        });
    }
}

testValidation()