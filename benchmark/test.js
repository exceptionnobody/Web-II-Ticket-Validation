'use strict';

const jwt = require('jsonwebtoken');
const fetch = require("node-fetch");

exports.testValidation = function() {

    const v = Math.random();
    let token
    if (v < 0.5) {
        token = jwt.sign({vz: '1'}, 'shhhhh', {expiresIn: "10h"});
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

        let token2 = jwt.sign({vz: "1"}, Buffer.from("LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU=", 'base64'), {header: { typ: undefined}, expiresIn: "1d"})

        return new Promise((resolve, reject) => {
            fetch("http://localhost:8080/validate", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({zone:"1", token: token2}),
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