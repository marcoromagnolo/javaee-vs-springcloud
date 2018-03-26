import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/player';

class TableApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            return response.json();
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
            console.debug(response);
            return response.json();
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
            console.debug(response);
            return response.json();
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
            console.debug(response);
            return response.json();
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
            console.debug(response);
            return response.json();
        });
    }

}

export default TableApi;