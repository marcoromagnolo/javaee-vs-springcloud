import { Component } from 'react';

const API_URL = 'http://localhost:8080/poker-manager/player';

class TableApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error to connect server');
            }
        });
    }

    join(sessionId, sessionToken, tableId) {
        return fetch(API_URL + '/join', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId
            }
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    quit(sessionId, sessionToken, tableId) {
        return fetch(API_URL + '/quit', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId
            }
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    buyin(sessionId, sessionToken, tableId, amount) {
        return fetch(API_URL + '/buyin', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId,
                amount: amount
            }
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    buyout(sessionId, sessionToken, tableId) {
        return fetch(API_URL + '/buyout', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId
            }
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