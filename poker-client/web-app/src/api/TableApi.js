import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/player';

class TableApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error to connect server');
            }
        }).catch(error => {
            throw error;
        });
    }

    join(sessionId, sessionToken, tableId) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId
        });
        return fetch(API_URL + '/join', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    quit(sessionId, sessionToken, tableId) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId
        });
        return fetch(API_URL + '/quit', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    buyin(sessionId, sessionToken, tableId, amount) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId,
            amount: amount
        });
        return fetch(API_URL + '/buyin', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    buyout(sessionId, sessionToken, tableId) {
        let json = JSON.stringify({
            sessionId: sessionId,
            sessionToken: sessionToken,
            tableId: tableId
        });
        return fetch(API_URL + '/buyout', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }


}

export default TableApi;