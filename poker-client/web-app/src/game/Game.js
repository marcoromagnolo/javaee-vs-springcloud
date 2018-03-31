import React, {Component} from 'react';
import Video from './Video';

class Game extends Component {

    constructor(props) {
        super(props);
        this.state = {
            video: new Video()
        }
    }

    render() {
        return (
            <div className="Game">
                <Video/>
            </div>
        );
    }

}

export default Game;