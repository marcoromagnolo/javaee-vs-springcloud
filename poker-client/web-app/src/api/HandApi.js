import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/hand';

class HandApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            return response.json();
        });
    }

    bet(sessionId, sessionToken, tableId, handId, amount) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId,
            handId: handId,
            amount: amount
        });
        return fetch(API_URL + '/bet', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            console.debug(response);
            return response.json();
        });
    }

    call(sessionId, sessionToken, tableId, handId) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId,
            handId: handId
        });
        return fetch(API_URL + '/call', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            console.debug(response);
            return response.json();
        });
    }

    check(sessionId, sessionToken, tableId, handId) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId,
            handId: handId
        });
        return fetch(API_URL + '/check', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            console.debug(response);
            return response.json();
        });
    }

    raise(sessionId, sessionToken, tableId, handId, amount) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId,
            handId: handId,
            amount: amount
        });
        return fetch(API_URL + '/raise', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            console.debug(response);
            return response.json();
        });
    }

    fold(sessionId, sessionToken, tableId, handId) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId,
            handId: handId
        });
        return fetch(API_URL + '/fold', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            console.debug(response);
            return response.json();
        });
    }

    sync(sessionId, sessionToken, tableId, handId) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId,
            handId: handId
        });
        return fetch(API_URL + '/sync', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            console.debug(response);
            return response.json();
        });
    }

}

export default HandApi;