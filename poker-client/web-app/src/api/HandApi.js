import { Component } from 'react';

const API_URL = 'http://localhost:8080/poker-manager/hand';

class HandApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error to connect server');
            }
        });
    }

    bet(sessionId, sessionToken, tableId, handId, amount) {
        return fetch(API_URL + '/bet', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId,
                handId: handId,
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

    call(sessionId, sessionToken, tableId, handId) {
        return fetch(API_URL + '/call', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId,
                handId: handId
            }
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    check(sessionId, sessionToken, tableId, handId) {
        return fetch(API_URL + '/check', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId,
                handId: handId
            }
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    raise(sessionId, sessionToken, tableId, handId, amount) {
        return fetch(API_URL + '/raise', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId,
                handId: handId,
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

    fold(sessionId, sessionToken, tableId, handId) {
        return fetch(API_URL + '/fold', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId,
                handId: handId
            }
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }

    sync(sessionId, sessionToken, tableId, handId) {
        return fetch(API_URL + '/sync', {
            method: "post",
            body: {
                sessionId: sessionId,
                sessionToken: sessionToken,
                tableId: tableId,
                handId: handId
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

export default HandApi;