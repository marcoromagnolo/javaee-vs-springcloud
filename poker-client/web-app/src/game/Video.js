import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import { Button, Form, Panel, Table, ButtonGroup, FormGroup, FormControl } from 'react-bootstrap';
import language from './language';
import Utility from './Utility';
import * as createjs from 'createjs-module';


/*******************************************************************************
 * Global letiables
 * 
 ******************************************************************************/
class Video extends Component {

    handHistory = {
        player: [
            {name: 'player1', seat: 1, state: 'ok', amount: 2000},
            {name: 'player2', seat: 2, state: 'ok', amount: 2000},
            {name: 'player3', seat: 3, state: 'ok', amount: 2000},
            {name: 'player4', seat: 4, state: 'ok', amount: 2000},
            {name: 'player5', seat: 5, state: 'ok', amount: 2000},
            {name: 'player6', seat: 6, state: 'ok', amount: 2000},
            {name: 'player7', seat: 7, state: 'ok', amount: 2000, dealer: 'Y'},
            {name: 'player8', seat: 8, state: 'ok', amount: 2000},
            {name: 'player9', seat: 9, state: 'ok', amount: 2000},
            {name: 'player10', seat: 10, state: 'ok', amount: 2000}
        ]
    };
    width = 800;
    height = 400;
    fps = 1;
    stage;
    table;
    config;
    imageDir = "/images/";

    //Enable/Disable Table positions alignments debug
    debugEnabled = true;

    /*******************************************************************************
     * init() is the main function
     *
     ******************************************************************************/
    constructor(props) {
        super(props);
        // Load Configurations
        this.config = this.getConfig();
        this.state = {
            amount: 0
        }
        this.handleBetAction = this.handleBetAction.bind(this);
    }

    componentDidMount(){
        let canvas = ReactDOM.findDOMNode(this.refs.canvas);
        this.stage = new createjs.Stage(canvas);

        // Add Static Objects
        this.addBg();
        this.addTable();
        this.addPlayers();

        this.stage.update();

        // Animation
        createjs.Ticker.setFPS(this.fps);
    }

    handleBetAction() {

    }

    render() {
        return (
            <div>
                <canvas ref="canvas" width="1080" height="620" />
                <Form inline>
                    <FormGroup bsSize="large">

                        <ButtonGroup bsSize="large">
                            <Button onClick={this.handleBetAction}>Call</Button>
                            <Button onClick={this.handleBetAction}>Fold</Button>
                            <Button onClick={this.handleBetAction}>Bet</Button>
                            <Button onClick={this.handleBetAction}>Raise</Button>
                        </ButtonGroup>
                        {' '}
                        <FormControl type="text" value={this.state.amount}/>
                    </FormGroup>
                </Form>
            </div>
        );
    }

    /*******************************************************************************
     * tick() needed if animating
     *
     ******************************************************************************/

    tick() {
        //Check if paused
        if (createjs.Ticker.getPaused() === false) {

            //Start Actions
            this.play();
            this.stage.update();
        }
    }

    play() {
        let action = null;
        let playerContainer = this.stage.getChildByName(action.player);
        switch (action.type) {
            case 'HAND_BLINDS':
                // Blinds
                this.showPlayerChips(playerContainer, action.value);
                this.changeState(playerContainer, language[action.kind], "#FFF");
                this.changeAmount(playerContainer, action.bankRoll);
                break;
            case 'HAND_ANTE':
                this.showPlayerChips(playerContainer, action.value);
                this.changeState(playerContainer, language[action.type], "#FFF");
                this.changeAmount(playerContainer, action.bankRoll);
                break;
            case 'HAND_DEAL':
                this.showPlayerCards(playerContainer, action.card);
                this.cleanAllPlayerState();
                break;
            case 'ACTION_BET':
                this.showPlayerChips(playerContainer, action.value);
                this.changeState(playerContainer, language[action.type] + "\n" + Utility.parseNumber(action.value), "#FFF");
                this.changeAmount(playerContainer, action.bankRoll);
                break;
            case 'ACTION_CALL':
                this.changeState(playerContainer, language[action.type] + "\n" + Utility.parseNumber(action.value), "#FFF");
                this.changeAmount(playerContainer, action.bankRoll);
                this.showPlayerChips(playerContainer, action.value);
                break;
            case 'ACTION_CHECK':
                this.changeState(playerContainer, language[action.type], "#FFF");
                break;
            case 'ACTION_FOLD':
                this.changeState(playerContainer, language[action.type], "#FFF");
                break;
            case 'ACTION_RAISE':
                this.showPlayerChips(playerContainer, action.value);
                this.changeState(playerContainer, language[action.type] + "\n" + Utility.parseNumber(action.value), "#FFF");
                this.changeAmount(playerContainer, action.bankRoll);
                break;
            case 'ACTION_ALLIN':
                this.changeState(playerContainer, language[action.type], "#FFF");
                this.changeAmount(playerContainer, action.bankRoll); //Adjust server side
                this.showPlayerChips(playerContainer, action.value);
                break;
            case 'COMMUNITY':
                this.showTableCard(action.card);
                break;
            case 'HAND_BOARD':
                this.cleanAllPlayerChips();
                this.cleanAllPlayerState();
                this.showTableCards(action.card);
                //Refresh Amounts
                this.showPots(action.pot, action.mainpot, action.leftpot, action.rightpot);
                break;
            case 'SHOWDOWN':
                //Set the Result Step
                this.cleanAllPlayerState();
                this.cleanAllPlayerChips();
                this.showPots(action.pot, action.mainpot, action.leftpot, action.rightpot);
                break;
            case 'SHOWDOWN_RESULT':
                this.showPlayerCards(playerContainer, action.card);
                if (action.win > 0) {
                    this.table.removeChild(this.table.getChildByName("mainPot"));
                    this.table.removeChild(this.table.getChildByName("potLeft"));
                    this.table.removeChild(this.table.getChildByName("potRight"));
                    this.showPlayerChips(playerContainer, action.win);
                    this.changeAmount(playerContainer, action.bankRoll);
                    let winText = action.winType ? action.winType : "HAND_WIN";
                    this.changeState(playerContainer, language[winText], '#00ff06');
                }
                if (action.score) {
                    this.changeScore(playerContainer, action.score);
                }
                break;
        }
    }

    cleanAllPlayerState() {
        for (let i = 0; i < this.handHistory.player.length; i++) {
            let container = this.stage.getChildByName(this.handHistory.player[i].name);
            if (container) {
                let playerState = container.getChildByName("playerState");
                let playerStateBg = container.getChildByName("playerStateBg");
                if (playerState.text !== language['ACTION_ALLIN'] && playerState.text !== language['STATE_SITOUT']) {
                    playerStateBg.visible = false;
                    playerState.text = "";
                }
            }
        }
    }

    cleanAllPlayerChips() {
        for (let i = 0; i < this.handHistory.player.length; i++) {
            let container = this.stage.getChildByName(this.handHistory.player[i].name);
            if (container) {
                let playerChips = container.getChildByName("playerChips");
                if (playerChips) {
                    playerChips.removeAllChildren();
                }
            }
        }
    }

    cleanAllPlayerCards() {
        for (let i = 0; i < this.handHistory.player.length; i++) {
            let container = this.stage.getChildByName(this.handHistory.player[i].name);
            if (container) {
                let playerCards = container.getChildByName("playerCards");
                if (playerCards) {
                    playerCards.removeAllChildren();
                }
            }
        }
    }

    cleanAllPlayerAmount() {
        for (let i = 0; i < this.handHistory.player.length; i++) {
            let player = this.handHistory.player[i];
            let container = this.stage.getChildByName(player.name);
            if (container) {
                let playerAmount = container.getChildByName("playerAmount");
                if (playerAmount) {
                    playerAmount.text = player.amount;
                }
            }
        }
    }

    cleanAllPlayerScore() {
        for (let i = 0; i < this.handHistory.player.length; i++) {
            let player = this.handHistory.player[i];
            let container = this.stage.getChildByName(player.name);
            if (container) {
                let playerScore = container.getChildByName("playerScore");
                if (playerScore) {
                    playerScore.text = player.score;
                }
            }
        }
    }

    cleanTableCards() {
        let tableCards = this.table.getChildByName("tableCards");
        this.table.removeChild(tableCards);
    }

    changeState(container, text, color) {
        let playerState = container.getChildByName("playerState");
        let playerStateBg = container.getChildByName("playerStateBg");
        if (playerState) {
            playerStateBg.visible = true;
            playerState.text = text;
        }
    }

    changeAmount(container, amount) {
        let playerAmount = container.getChildByName("playerAmount");
        if (playerAmount) {
            playerAmount.text = Utility.parseNumber(amount);
        }
    }

    changeScore(container, score) {
        let playerScore = container.getChildByName("playerScore");
        if (playerScore) {
            playerScore.text = Utility.parseNumber(score);
        }
    }

    /**
     * Add Background Image
     */
    addBg() {
        let bg = new createjs.Bitmap(this.imageDir + this.config.background);
        this.stage.addChildAt(bg, 0);
    }

    addTable() {
        this.table = new createjs.Container();
        this.table.name = "table";
        this.stage.addChild(this.table);
    }

    /**
     * Add Player
     */
    addPlayers() {
        for (let i = 0; i < this.handHistory.player.length; i++) {
            let playerData = this.handHistory.player[i];
            if (playerData.state !== "STATE_EMPTY") {
                // Create Player Container
                let player = new createjs.Container();
                player.x = this.config.seat[i].player.x;
                player.y = this.config.seat[i].player.y;
                player.width = 180;
                player.height = 180;
                player.name = playerData.name; // Set Player seat for use next time
                player.seat = playerData.seat;
                this.stage.addChildAt(player, 2);
                let playerCenterX = player.width / 2;
                let playerCenterY = player.height / 2;
                //Debug
                if (this.debugEnabled) {
                    let debugBgColor = new createjs.Shape();
                    debugBgColor.graphics.beginStroke("#ff0000").drawRect(0, 0, player.width, player.height);
                    debugBgColor.x = this.config.seat[i].player.x;
                    debugBgColor.y = this.config.seat[i].player.y;
                    this.stage.addChild(debugBgColor);
                }
                //Cards
                let cards = new createjs.Container();
                cards.name = "playerCards";
                player.addChildAt(cards, 0);
                // Bg Image
                let bgPlayerImage = new createjs.Bitmap(this.imageDir + "player.png");
                bgPlayerImage.name = "bgPlayerImage";
                bgPlayerImage.width = 118;
                bgPlayerImage.height = 145;
                bgPlayerImage.x = playerCenterX - (bgPlayerImage.width / 2);
                bgPlayerImage.y = playerCenterY - (bgPlayerImage.height / 2);
                bgPlayerImage.cursor = "pointer";
                player.addChildAt(bgPlayerImage, 1);
                //Chips
                let chips = new createjs.Container();
                chips.name = "playerChips";
                chips.x = bgPlayerImage.x + (bgPlayerImage.width / 2) + this.config.seat[i].chips.x;
                chips.y = bgPlayerImage.x + (bgPlayerImage.width / 2) + this.config.seat[i].chips.y;
                player.addChildAt(chips, 2);
                // Align Cards
                cards.x = playerCenterX - (bgPlayerImage.width / 2);
                cards.y = playerCenterY - (bgPlayerImage.height / 2) + 10;
                //Player State Bg
                let playerStateBg = new createjs.Bitmap(this.imageDir + "player_state.png");
                playerStateBg.name = "playerStateBg";
                playerStateBg.width = 106;
                playerStateBg.height = 106;
                playerStateBg.x = playerCenterX - (playerStateBg.width / 2);
                playerStateBg.y = playerCenterY - (playerStateBg.height / 2) - 16;
                playerStateBg.visible = false;
                player.addChildAt(playerStateBg, 2);
                // State Text
                let playerState = new createjs.Text(language[playerData.state], "Bold 15px Arial", "#ffb900");
                playerState.name = "playerState";
                playerState.x = playerCenterX;
                playerState.y = playerCenterY - 31;
                playerState.textAlign = "center";
                player.addChildAt(playerState, 3);
                //Info Image
                let playerInfoImage = new createjs.Bitmap();
                playerInfoImage.name = "playerInfoImage";
                playerInfoImage.width = 131;
                playerInfoImage.height = 61;
                playerInfoImage.x = playerCenterX - (playerInfoImage.width / 2);
                playerInfoImage.y = playerCenterY - (playerInfoImage.height / 2) + 30;
                player.addChildAt(playerInfoImage, 4);
                // Name Text
                let playerName = new createjs.Text(playerData.name, "Bold 12px Arial", "#dddddd");
                playerName.name = "playerName";
                playerName.x = playerInfoImage.x + (playerInfoImage.width / 2);
                playerName.y = playerInfoImage.y + 22;
                playerName.textAlign = "center";
                player.addChildAt(playerName, 5);
                // Amount Text
                let playerAmount = new createjs.Text(Utility.parseNumber(playerData.amount), "Bold 14px Arial", "#ff0000");
                playerAmount.name = "playerAmount";
                playerAmount.x = playerInfoImage.x + (playerInfoImage.width / 2);
                playerAmount.y = playerInfoImage.y + 35;
                playerAmount.textAlign = "center";
                player.addChildAt(playerAmount, 6);
                //Player Panel
                let playerPanel = new createjs.Container();
                playerPanel.playerName = player.name;
                playerPanel.name = "playerPanel";
                playerPanel.visible = false;
                player.addChildAt(playerPanel, 9);
                // Dealer Image
                if (playerData.dealer=== "Y") {
                    let dealer = new createjs.Bitmap(this.imageDir + "dealer.png");
                    dealer.name = "playerDealer";
                    dealer.x = bgPlayerImage.x + (bgPlayerImage.width / 2) + 25;
                    dealer.y = bgPlayerImage.y + (bgPlayerImage.height / 2) - 20;
                    player.addChildAt(dealer, 7);
                }
                // Player Panel Bg Image
                let bgPlayerPanel = new createjs.Bitmap(this.imageDir + "player_panel.png");
                playerPanel.addChild(bgPlayerPanel);
                //Player Panel Text
                let textPlayerPanel = new createjs.Text("", "Arial 11px", "#FFF");
                let fistname = ' - ';
                if (player.fistname !== undefined) fistname = playerData.fistname;
                textPlayerPanel.x = 12;
                textPlayerPanel.y = 10;
                textPlayerPanel.text = language["STR_G_PLAYER_PIN"] + playerData.id + "\n";
                textPlayerPanel.text += language["STR_G_PLAYER_FIRSTNAME"] + fistname + "\n";
                playerPanel.addChild(textPlayerPanel);
            }
        }
    }

    /**
     *
     * @param container
     * @param cards
     * @returns {*}
     */
    showPlayerCards(container, cards) {
        // Remove Old Table Cards
        let playerCards = container.getChildByName("playerCards");
        if (playerCards) {
            playerCards.removeAllChildren();
        }
        // Init Cards Image Drawing
        if (cards != null) {
            let c = 0;
            let cardSpace = 16;
            for (let i = 0; i < cards.length; i++) {
                let card = null;
                // Show Back Card
                if (cards[i].link=== 'b') {
                    card = new createjs.Bitmap(this.imageDir + "card_back.png");
                } else {
                    // Show Card
                    let value = cards[i].link;
                    // Cards Image
                    let coords = Utility.getCard(value);
                    card = new createjs.Bitmap(this.imageDir + "poker_cards.png");
                    card.sourceRect = new createjs.Rectangle(coords.x, coords.y, coords.width, coords.height);
                }
                card.x = c;
                card.y = -26;
                c += cardSpace;
                playerCards.addChild(card);
            }
            let cardWidth = 40;
            container.width = c + (cardWidth - cardSpace);
        }
    }

    showPlayerChips(container, amount) {
        let playerChips = container.getChildByName("playerChips");
        if (playerChips) {
            playerChips.removeAllChildren();
        }
        // Init Chips Image Creation
        if (amount > 0) {
            this.createChips(playerChips, Utility.parseNumber(amount), "", false);
        }
    }

    showTableCard(card) {
        if (card) {
            let array = [];
            let val = {};
            val.link = card;
            array[0] = val;
            this.showTableCards(array)
        }
    }

    /**
     * Show Table Cards
     * @param array
     */
    showTableCards(array) {
        if (array) {
            // Remove Old Table Cards
            let oldContainer = this.table.getChildByName("tableCards");
            this.table.removeChild(oldContainer);
            // Create New Table Cards
            let tableCards = new createjs.Container();
            tableCards.name = "tableCards";
            this.table.addChild(tableCards);
            // Init Cards Creations
            let c = 0;
            for (let i = 0; i < array.length; i++) {
                // Cards Image
                let value = array[i].link;
                let card = new createjs.Bitmap(this.imageDir + "poker_cards_large.png");
                let coords = Utility.getCard(value);
                // 7 is the width difference with standard card image
                let diffWidth = 15;
                // 10 is the height difference with standard card image
                let diffHeight = 21;
                // Calculate new X coordinates for large image
                let x = coords.x + ((value % 13) * diffWidth);
                // Calculate new Y coordinates for large image, 13 is the max card
                // number
                let y = coords.y + (parseInt(value / 13) * diffHeight);
                // Crop cards Image
                card.sourceRect = new createjs.Rectangle(x, y, coords.width + diffWidth, coords.height + diffHeight);
                card.x = c;
                tableCards.addChild(card);
                c += 60;
            }
            tableCards.x = (this.width - 5 * 60) / 2;
            tableCards.y = 320;
        }
    }

    /**
     * Show Table Pots
     * @param totalPotValue
     * @param mainPotValue
     * @param potLeftValue
     * @param potRightValue
     */
    showPots(totalPotValue, mainPotValue, potLeftValue, potRightValue) {
        // Remove Old Pots
        let totalPot = this.table.getChildByName("totalPot");
        this.table.removeChild(totalPot);
        let mainPot = this.table.getChildByName("mainPot");
        this.table.removeChild(mainPot);
        let potLeft = this.table.getChildByName("potLeft");
        this.table.removeChild(potLeft);
        let potRight = this.table.getChildByName("potRight");
        this.table.removeChild(potRight);
        //Total Pot
        totalPot = new createjs.Text(language["STR_G_TOTALPOT"] + Utility.parseNumber(totalPotValue), "Bold 16px Arial", "#FFF");
        totalPot.name = "totalPot";
        totalPot.x = 530;
        totalPot.y = 400;
        this.table.addChild(totalPot);
        //Main Pot
        mainPot = new createjs.Container();
        mainPot.name = "mainPot";
        mainPot.value = Utility.parseNumber(mainPotValue);
        mainPot.x = 470;
        mainPot.y = 350;
        this.createChips(mainPot, Utility.parseNumber(mainPotValue), "", false);
        this.table.addChild(mainPot);
        // Add Pot Left
        if (potLeftValue) {
            potLeft = new createjs.Container();
            potLeft.name = "potLeft";
            potLeft.value = Utility.parseNumber(potLeftValue);
            potLeft.x = 350;
            potLeft.y = 350;
            if (potLeftValue instanceof Array) {
                let potLeftText = "";
                if (potLeftValue[0]) potLeftText += language["STR_G_SIDEPOT"] + " 1: " + Utility.parseNumber(potLeftValue[0]) + "\n";
                if (potLeftValue[1]) potLeftText += language["STR_G_SIDEPOT"] + " 3: " + Utility.parseNumber(potLeftValue[1]) + "\n";
                if (potLeftValue[2]) potLeftText += language["STR_G_SIDEPOT"] + " 5: " + Utility.parseNumber(potLeftValue[2]) + "\n";
                if (potLeftValue[3]) potLeftText += language["STR_G_SIDEPOT"] + " 7: " + Utility.parseNumber(potLeftValue[3]) + "\n";
                this.createChips(potLeft, Utility.parseNumber(potLeftValue[0]), potLeftText, true);
            } else {
                this.createChips(potLeft, Utility.parseNumber(potLeftValue), language["STR_G_LEFTPOT"], false);
            }
            this.table.addChild(potLeft);
        }
        // Add Pot Right
        if (potRightValue) {
            potRight = new createjs.Container();
            potRight.name = "potRight";
            potRight.value = Utility.parseNumber(potRightValue);
            potRight.x = 880;
            potRight.y = 350;
            if (potRightValue instanceof Array) {
                let potRightText = "";
                if (potRightValue[0]) potRightText += language["STR_G_SIDEPOT"] + " 2: " + Utility.parseNumber(potRightValue[0]) + "\n";
                if (potRightValue[1]) potRightText += language["STR_G_SIDEPOT"] + " 4: " + Utility.parseNumber(potRightValue[1]) + "\n";
                if (potRightValue[2]) potRightText += language["STR_G_SIDEPOT"] + " 6: " + Utility.parseNumber(potRightValue[2]) + "\n";
                if (potRightValue[3]) potRightText += language["STR_G_SIDEPOT"] + " 8: " + Utility.parseNumber(potRightValue[3]) + "\n";
                this.createChips(potRight, Utility.parseNumber(potRightValue[0]), potRightText, true);
            } else {
                this.createChips(potRight, Utility.parseNumber(potRightValue), language["STR_G_RIGHTPOT"], false);
            }
            this.table.addChild(potRight);
        }
    }

    /**
     * Create a Chips column and text value
     *
     * @param chipsContainer
     * @param value
     * @param text
     */
    createChips(chipsContainer, value, text, disableTextValue) {
        let chipList = [1000000, 250000, 100000, 25000, 5000, 1000, 500, 100, 25, 5, 1, 0.25, 0.05, 0.01];
        let chips = [];
        let total = value;
        // Count Chips
        for (let i = 0; i < chipList.length; i++) {
            let chipValue = chipList[i]; // value
            let chipCount = total / chipValue; // count
            let chipRest = total % chipValue;
            if ((chipCount) >= 1) {
                chips.push({
                    "count": parseInt(chipCount),
                    "value": chipValue
                });
                if (chipRest=== 0)
                    break; // Exit if there is not rest
                total = chipRest;
            }
        }
        // Create Chip Images
        let c = 0;
        for (let j = 0; j < chips.length; j++) {
            let chip = chips[j];
            for (let n = 0; n < chip.count; n++) {
                let cords = this.getImageChipCoords(chip.value);
                let chipsBitmap = new createjs.Bitmap(this.imageDir + "chips.png");
                chipsBitmap.sourceRect = new createjs.Rectangle(cords.x, cords.y, cords.width, cords.height);
                chipsBitmap.x = 0;
                chipsBitmap.y = c;
                chipsContainer.addChild(chipsBitmap);
                c -= 2;
            }
        }
        text = (text=== null) ? "" : text;
        let chipTextBg = new createjs.Bitmap(this.imageDir + "players_bet_bkg.png");
        chipTextBg.x = 25;
        chipTextBg.y = 0;
        chipTextBg.name = "chipTextBg";
        chipsContainer.addChild(chipTextBg);
        let chipText = new createjs.Text((disableTextValue) ? text.toString() : text.toString() + value.toString(), "Bold 10px Arial");
        chipText.name = "chipText";
        chipText.color = "#FFF";
        chipText.x = 30;
        chipText.y = 3;
        chipsContainer.addChild(chipText);

    }

    reset() {
        this.cleanAllPlayerCards();
        this.cleanAllPlayerChips();
        this.cleanAllPlayerState();
        this.cleanAllPlayerAmount();
        this.cleanAllPlayerScore();
        this.table.removeAllChildren();
    }

    /**
     * Get Config data
     *
     * @returns {*}
     */
    getConfig() {
        let xOffset = 0;
        let yOffset = 0;
        return {
            "size": 10,
            "background": "table.png",
            "seat": [{
                "seat": 1,
                "chips": {
                    "x": 0,
                    "y": 80
                },
                "player": {
                    "x": 650 + xOffset,
                    "y": yOffset - 20
                }
            }, {
                "seat": 2,
                "chips": {
                    "x": -110,
                    "y": 10
                },
                "player": {
                    "x": 885 + xOffset,
                    "y": 80 + yOffset
                }
            }, {
                "seat": 3,
                "chips": {
                    "x": -110,
                    "y": -10
                },
                "player": {
                    "x": 885 + xOffset,
                    "y": 265 + yOffset
                }
            }, {
                "seat": 4,
                "chips": {
                    "x": 0,
                    "y": -120
                },
                "player": {
                    "x": 650 + xOffset,
                    "y": 400 + yOffset
                }
            }, {
                "seat": 5,
                "chips": {
                    "x": 0,
                    "y": -120
                },
                "player": {
                    "x": 433 + xOffset,
                    "y": 400 + yOffset
                }
            }, {
                "seat": 6,
                "chips": {
                    "x": 0,
                    "y": -120
                },
                "player": {
                    "x": 210 + xOffset,
                    "y": 400 + yOffset
                }
            }, {
                "seat": 7,
                "chips": {
                    "x": 80,
                    "y": -20
                },
                "player": {
                    "x": xOffset,
                    "y": 265 + yOffset
                }
            }, {
                "seat": 8,
                "chips": {
                    "x": 90,
                    "y": 20
                },
                "player": {
                    "x": xOffset,
                    "y": 80 + yOffset
                }
            }, {
                "seat": 9,
                "chips": {
                    "x": 0,
                    "y": 80
                },
                "player": {
                    "x": 210 + xOffset,
                    "y": yOffset - 20
                }
            }, {
                "seat": 10,
                "chips": {
                    "x": 0,
                    "y": 80
                },
                "player": {
                    "x": 433 + xOffset,
                    "y": yOffset - 20
                }
            }]
        };
    }

    /**
     * Get Coords of the Chips Image
     *
     * @param value
     * @returns {*}
     */
    getImageChipCoords(value) {
        let chips = [{
            "width": 25,
            "height": 22,
            "x": 0,
            "y": 0,
            "value": 1000000
        }, {
            "width": 25,
            "height": 22,
            "x": 26,
            "y": 0,
            "value": 250000
        }, {
            "width": 25,
            "height": 22,
            "x": 50,
            "y": 0,
            "value": 100000
        }, {
            "width": 25,
            "height": 22,
            "x": 75,
            "y": 0,
            "value": 25000
        }, {
            "width": 25,
            "height": 22,
            "x": 100,
            "y": 0,
            "value": 5000
        }, {
            "width": 25,
            "height": 22,
            "x": 125,
            "y": 0,
            "value": 1000
        }, {
            "width": 25,
            "height": 22,
            "x": 150,
            "y": 0,
            "value": 500
        }, {
            "width": 25,
            "height": 22,
            "x": 175,
            "y": 0,
            "value": 100
        }, {
            "width": 25,
            "height": 22,
            "x": 200,
            "y": 0,
            "value": 25
        }, {
            "width": 25,
            "height": 22,
            "x": 225,
            "y": 0,
            "value": 5
        }, {
            "width": 25,
            "height": 22,
            "x": 250,
            "y": 0,
            "value": 1
        }, {
            "width": 25,
            "height": 22,
            "x": 275,
            "y": 0,
            "value": 0.25
        }, {
            "width": 25,
            "height": 22,
            "x": 300,
            "y": 0,
            "value": 0.05
        }, {
            "width": 25,
            "height": 22,
            "x": 325,
            "y": 0,
            "value": 0.01
        }];
        for (let i = 0; i < chips.length; i++) {
            if (chips[i].value === value) {
                return chips[i];
            }
        }
    }
}

export default Video;
