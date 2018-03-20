import { Component } from 'react';

const API_URL = 'http://localhost:8080/poker-client';

class ApiClient extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error to connect server');
            }
        });
    }

}

export default ApiClient;