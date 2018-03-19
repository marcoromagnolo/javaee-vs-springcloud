/* 
  Hand Replay
  @Author: Marco Romagnolo
 */

/*******************************************************************************
 * Global Variables
 * 
 ******************************************************************************/
var handHistory = hand.history[0];
var width = 1366;
var height = 768;
var fps = 1;
var stage, table;
var rakeText, stepText, speedText;
var playButton; // Play Button
var pauseButton; // Pause Button
var config;
var imageDir = "images/";
var pausedOnStartup = false;
var hasNextHand, hasPrevHand;

//Enable/Disable Table positions alignments debug
var debugEnabled = false;

//Start from step
var step = 0;
//Total Number of steps
var maxStep = 0;

/*******************************************************************************
 * init() is the main function
 * 
 ******************************************************************************/
function init(nextHand, prevHand) {
    hasNextHand = nextHand;
    hasPrevHand = prevHand;

    // Create Canvas
    var canvas = document.createElement("canvas");
    canvas.width = width;
    canvas.height = height;
    document.body.appendChild(canvas);

    // Load Configurations
    config = getConfig();

    //Adapt JSON for HTML5 reproduce
    historyAdapter();
    maxStep = handHistory.action.length;

    // Add canvas to HTML page
    stage = new createjs.Stage(canvas);

    // Add Static Objects
    addBg();
    addGamePanel();
    addTable();
    addPlayers();
    addRakeText();
    addPlaybackButtons();

    // Enable Stage Event and mouse over
    stage.enableMouseOver();
    
    //Set if video auto start
    if (pausedOnStartup) {
        createjs.Ticker.setPaused(true);
    } else {
        playButton.visible = false;
        pauseButton.visible = true;
    }

    // Update Stage
    stage.update();

    // Animation
    createjs.Ticker.setFPS(fps);
    createjs.Ticker.addEventListener("tick", tick);
}

/*******************************************************************************
 * tick() needed if animating
 * 
 ******************************************************************************/

function tick() {
    //Check if paused
    if (createjs.Ticker.getPaused()==false) {

        //Start Actions
        play();

        if (debugEnabled) {
            //Set Step Text
            stepText.text = step;
            stepText.visible = true;
            speedText.text = language['ROUND_PLAYBACKSPEED']+ " " + fps;
        }
        stage.update();
    }
}

function play() {
    if (handHistory.action[step]!=undefined) {
        var action = handHistory.action[step];
        var playerContainer = stage.getChildByName(action.player);
        switch (action.type) {
            case 'HAND_BLINDS':
                // Blinds
                showPlayerChips(playerContainer, action.value);
                changeState(playerContainer, language[action.kind], "#FFF");
                changeAmount(playerContainer, action.bankRoll);
                break;
            case 'HAND_ANTE':
                showPlayerChips(playerContainer, action.value);
                changeState(playerContainer, language[action.type], "#FFF");
                changeAmount(playerContainer, action.bankRoll);
                break;
            case 'HAND_DEAL':
                showPlayerCards(playerContainer, action.card);
                cleanAllPlayerState();
                break;
            case 'ACTION_BET':
                showPlayerChips(playerContainer, action.value);
                changeState(playerContainer, language[action.type]+"\n"+parseNumber(action.value), "#FFF");
                changeAmount(playerContainer, action.bankRoll);
                break;
            case 'ACTION_CALL':
                changeState(playerContainer, language[action.type]+"\n"+parseNumber(action.value), "#FFF");
                changeAmount(playerContainer, action.bankRoll);
                showPlayerChips(playerContainer, action.value);
                break;
            case 'ACTION_CHECK':
                changeState(playerContainer, language[action.type], "#FFF");
                break;
            case 'ACTION_FOLD':
                changeState(playerContainer, language[action.type], "#FFF");
                break;
            case 'ACTION_RAISE':
                showPlayerChips(playerContainer, action.value);
                changeState(playerContainer, language[action.type]+"\n"+parseNumber(action.value), "#FFF");
                changeAmount(playerContainer, action.bankRoll);
                break;
            case 'ACTION_BRINGIN':
                showPlayerChips(playerContainer, action.value);
                changeState(playerContainer, language[action.type], "#FFF");
                changeAmount(playerContainer, action.bankRoll);
                break;
            case 'ACTION_ADJUSTEMENT':
                showPlayerChips(playerContainer, action.value);
                changeState(playerContainer, "", "#FFF");
                changeAmount(playerContainer, action.bankRoll);
                break;
            case 'ACTION_ALLIN':
                changeState(playerContainer, language[action.type], "#FFF");
                changeAmount(playerContainer, action.bankRoll); //Adjust server side
                showPlayerChips(playerContainer, action.value);
                break;
            case 'CHANGECARD':
                changeRake(action.rake);
                cleanAllPlayerChips();
                cleanAllPlayerState();
                if (action.count>0) {
                    changeState(playerContainer, language['CHANGE_CARD'] + "\n" + action.count, "#FFF");
                    showPlayerCards(playerContainer, action.card);
                }
                break;
            case 'STREET':
                changeRake(action.rake);
                cleanAllPlayerChips();
                cleanAllPlayerState();
                //Refresh Amounts
                showPots(action.pot, action.mainpot, action.leftpot, action.rightpot);
                break;
            case 'COMMUNITY':
                showTableCard(action.card);
                break;
            case 'HAND_BOARD':
                changeRake(action.rake);
                cleanAllPlayerChips();
                cleanAllPlayerState();
                showTableCards(action.card);
                //Refresh Amounts
                showPots(action.pot, action.mainpot, action.leftpot, action.rightpot);
                break;
            case 'ACTION_BBJ':
                showBbj(action.value);
                break;
            case 'SHOWDOWN':
                //Set the Result Step
                cleanAllPlayerState();
                cleanAllPlayerChips();
                changeRake(action.rake);
                showPots(action.pot, action.mainpot, action.leftpot, action.rightpot);
                break;
            case 'SHOWDOWN_RESULT':
                showPlayerCards(playerContainer, action.card);
                if (action.win>0) {
                    table.removeChild(table.getChildByName("mainPot"));
                    table.removeChild(table.getChildByName("potLeft"));
                    table.removeChild(table.getChildByName("potRight"));
                    showPlayerChips(playerContainer, action.win);
                    changeAmount(playerContainer, action.bankRoll);
                    var winText = action.winType ? action.winType : "HAND_WIN";
                    changeState(playerContainer, language[winText], '#00ff06');
                }
                if (action.score) {
                    changeScore(playerContainer, action.score);
                }
                break;
            case 'BBJ_WON':
                var bbjWonText = new createjs.Text(language["STR_G_BADBEATJACKPOT"], "Bold 48px Arial", "#fff");
                stage.addChildAt(bbjWonText, 1);// Add Hand Text on background image
                bbjWonText.x = 200;
                bbjWonText.y = 180;
                cleanAllPlayerChips();
                cleanAllPlayerCards();
                cleanTableCards();
                for (var b=0; b<action.winner.length; b++) {
                    showPlayerChips(stage.getChildByName(action.winner[b].name), action.winner[b].amount);
                }
                break;
        }
        //Increment Step
        step = step + 1;
    } else {
        //End
        playButton.visible = true;
        pauseButton.visible = false;
        createjs.Ticker.setPaused(true);
    }
}

function historyAdapter() {
    handHistory.action.push(handHistory.showDown);
    handHistory.showDown.type = "SHOWDOWN";
    for (var i=0; i<handHistory.showDown.result.length; i++) {
        var result = handHistory.showDown.result[i];
        result.type="SHOWDOWN_RESULT";
        handHistory.action.push(result);
    }
    if (handHistory.bbj) {
        handHistory.bbj.type = "BBJ_WON";
        handHistory.action.push(handHistory.bbj);
    }
}

function changeRake(actionRake) {
    if (actionRake) {
        rakeText.text = language['HAND_RAKE'] + " " + parseNumber(actionRake);
    }
}

function cleanRake() {
    rakeText.text = "";
}

function cleanAllPlayerState() {
    for (var i=0;i<handHistory.player.length;i++) {
        var container = stage.getChildByName(handHistory.player[i].name);
        if (container) {
            var playerState = container.getChildByName("playerState");
            var playerStateBg = container.getChildByName("playerStateBg");
            if (playerState.text!=language['ACTION_ALLIN'] && playerState.text!=language['STATE_SITOUT']) {
                playerStateBg.visible = false;
                playerState.text="";
            }
        }
    }   
}

function cleanAllPlayerChips() {
    for (var i=0;i<handHistory.player.length;i++) {
        var container = stage.getChildByName(handHistory.player[i].name);
        if (container) {
            var playerChips = container.getChildByName("playerChips");
            if (playerChips) {
                playerChips.removeAllChildren();
            }
        }
    }   
}

function cleanAllPlayerCards() {
    for (var i=0;i<handHistory.player.length;i++) {
        var container = stage.getChildByName(handHistory.player[i].name);
        if (container) {
            var playerCards = container.getChildByName("playerCards");
            if (playerCards) {
                playerCards.removeAllChildren();
            }
        }
    }   
}

function cleanAllPlayerAmount() {
    for (var i=0;i<handHistory.player.length;i++) {
        var player = handHistory.player[i];
        var container = stage.getChildByName(player.name);
        if (container) {
            var playerAmount = container.getChildByName("playerAmount");
            if (playerAmount) {
                playerAmount.text = player.amount;
            }
        }
    }
}

function cleanAllPlayerScore() {
    for (var i=0;i<handHistory.player.length;i++) {
        var player = handHistory.player[i];
        var container = stage.getChildByName(player.name);
        if (container) {
            var playerScore = container.getChildByName("playerScore");
            if (playerScore) {
                playerScore.text = player.score;
            }
        }
    }
}

function cleanTableCards() {
    var tableCards = table.getChildByName("tableCards");
    table.removeChild(tableCards);
}

function changeState(container, text, color) {
    var playerState = container.getChildByName("playerState");
    var playerStateBg = container.getChildByName("playerStateBg");
    if (playerState) {
        playerStateBg.visible = true;
        playerState.text = text;
    }
}

function changeAmount(container, amount) {
    var playerAmount = container.getChildByName("playerAmount");
    if (playerAmount) {
        playerAmount.text = parseNumber(amount);
    }
}

function changeScore(container, score) {
    var playerScore = container.getChildByName("playerScore");
    if (playerScore) {
        playerScore.text = parseNumber(score);
    }
}

/**
 * Add Background Image
 */
function addBg() {
    var bg = new createjs.Bitmap(imageDir + config.background);
    stage.addChildAt(bg, 0);
}

/**
 * Add Game Panel
 */
function addGamePanel() {
    var gamePanel = new createjs.Container();
    stage.addChildAt(gamePanel, 1); // Add Game Panel on background image
    // Format text
    var text = language['HIS_GUI_GAME'] + ": " + language[handHistory.game] + "\n";
    text += language['HIS_GUI_GAMEKIND'] + ": " + language[handHistory.gameKind] + "\n";
    text += language['HIS_GUI_TABLEID'] + ": " + handHistory.table + "(" + handHistory.tableId + ")" + "\n";
    text += handHistory.anonymous ? language['ANONYMOUS_TABLE'] + "\n" : "";
    text += language['HIS_GUI_SBBB'] + ":";
    text += handHistory.stake + " ";
    text += handHistory.ante!=null && handHistory.ante!=undefined && handHistory.ante!="" ? parseNumber(handHistory.ante) : "";
    if ("GAMEKIND_TOURNAMENT" == handHistory.gameKind) {
        text += "\n";
    } else {
        text += handHistory.tableCurrency + "\n";
    }
    text += handHistory.id + "\n";
    text += language['HIS_GUI_BETLIMITS'] + ": " + handHistory.limit + "\n";
    text += language['HIS_GUI_TABLETOURNEYID'] + ": " + handHistory.tableTourneyId + "\n";
    if (handHistory.currentPlayerRegTicketId != null)
        text += language['HIS_GUI_TICKETID'] + ": " + handHistory.currentPlayerRegTicketId + "\n";
    text += language['HIS_GUI_RAKEID'] + ": " + handHistory.rakeId + "\n";
    text += language['HIS_GUI_POT'] + ": " + parseNumber(handHistory.pot) + " ";
    text += language['HIS_GUI_BET'] + ": " + parseNumber(handHistory.bet) + "\n";
    text += language['HIS_GUI_DATE'] + getDate(handHistory.date) + " \n";
    text += handHistory.handId + " \n";
    // Add Text
    var gamePanelText = new createjs.Text(text, "12px Arial", "#fff");
    gamePanelText.x = 10;
    gamePanelText.y = 3;
    gamePanelText.lineHeight = 11.5;
    gamePanelText.textAlign = "left";
    gamePanel.addChild(gamePanelText);
}

function handlePlayerPanel(event) {
    var playerContainer = stage.getChildByName(event.target.name);
    if (playerContainer) {
        var playerPanel = playerContainer.getChildByName("playerPanel");
        if (playerPanel) {
            playerPanel.visible = !playerPanel.visible;
            stage.update();
        }
    }
}

function addTable() {
    table = new createjs.Container();
    table.name = "table";
    stage.addChild(table);
}

/**
 * Add Player
 */
function addPlayers() {
    for ( var i = 0; i < handHistory.player.length; i++) {
        var playerData = handHistory.player[i];
        if (playerData.state != "STATE_EMPTY") {
            // Create Player Container
            var player = new createjs.Container();
            player.x = config.seat[i].player.x;
            player.y = config.seat[i].player.y;
            player.width = 300;
            player.height = 300;
            player.name = playerData.name; // Set Player seat for use next time
            player.seat = playerData.seat;
            player.addEventListener("click", handlePlayerPanel);
            stage.addChildAt(player, 2);
            var playerCenterX = player.width/2;
            var playerCenterY = player.height/2;
            //Debug
            if (debugEnabled) {
                var debugBgColor = new createjs.Shape();
                debugBgColor.graphics.beginStroke("#ff0000").drawRect(0,0,300,300);
                debugBgColor.x = config.seat[i].player.x;
                debugBgColor.y = config.seat[i].player.y;
                stage.addChild(debugBgColor);
            }
            //Cards
            var cards = new createjs.Container();
            cards.name = "playerCards";
            player.addChildAt(cards, 0);
            // Bg Image
            var bgPlayerImage = new createjs.Bitmap(imageDir + "player.png");
            bgPlayerImage.name = "bgPlayerImage";
            bgPlayerImage.width = 118;
            bgPlayerImage.height = 145;
            bgPlayerImage.x = playerCenterX-(bgPlayerImage.width/2);
            bgPlayerImage.y = playerCenterY-(bgPlayerImage.height/2);
            bgPlayerImage.cursor = "pointer";
            player.addChildAt(bgPlayerImage, 1);
            //Chips
            var chips = new createjs.Container();
            chips.name = "playerChips";
            chips.x = bgPlayerImage.x+(bgPlayerImage.width/2)+config.seat[i].chips.x;
            chips.y = bgPlayerImage.x+(bgPlayerImage.width/2)+config.seat[i].chips.y;
            player.addChildAt(chips, 2);
            // Align Cards
            cards.x = playerCenterX-(bgPlayerImage.width/2);
            cards.y = playerCenterY-(bgPlayerImage.height/2)+10;
            //Player State Bg
            var playerStateBg = new createjs.Bitmap(imageDir + "player_state.png");
            playerStateBg.name = "playerStateBg";
            playerStateBg.width = 106;
            playerStateBg.height = 106;
            playerStateBg.x = playerCenterX-(playerStateBg.width/2);
            playerStateBg.y = playerCenterY-(playerStateBg.height/2)-16;
            playerStateBg.visible = false;
            player.addChildAt(playerStateBg, 2);
            // State Text
            var playerState = new createjs.Text(language[playerData.state], "Bold 15px Arial", "#ffb900");
            playerState.name = "playerState";
            playerState.x = playerCenterX;
            playerState.y = playerCenterY-31;
            playerState.textAlign = "center";
            player.addChildAt(playerState, 3);
            //Info Image
            var playerInfoImage = new createjs.Bitmap(imageDir + "player_info.png");
            playerInfoImage.name = "playerInfoImage";
            playerInfoImage.width = 131;
            playerInfoImage.height = 61;
            playerInfoImage.x = playerCenterX-(playerInfoImage.width/2);
            playerInfoImage.y = playerCenterY-(playerInfoImage.height/2)+30;
            player.addChildAt(playerInfoImage, 4);
            // Name Text
            var playerName = new createjs.Text(playerData.name, "Bold 12px Arial", "#dddddd");
            playerName.name = "playerName";
            playerName.x = playerInfoImage.x+(playerInfoImage.width/2);
            playerName.y = playerInfoImage.y+22;
            playerName.textAlign = "center";
            player.addChildAt(playerName, 5);
            // Amount Text
            var playerAmount = new createjs.Text(parseNumber(playerData.amount), "Bold 14px Arial", "ffb900");
            playerAmount.name = "playerAmount";
            playerAmount.x = playerInfoImage.x+(playerInfoImage.width/2);
            playerAmount.y = playerInfoImage.y+35;
            playerAmount.textAlign = "center";
            player.addChildAt(playerAmount, 6);
            //Bounty Text
            if (playerData.bounty) {
                var bounty = playerData.bounty!=null && playerData.bounty!=undefined && playerData.bounty!="0.00" ? playerData.bounty +" " : "";
                var playerBounty = new createjs.Text(bounty, "Bold 12px Arial", "#abc9aa");
                playerBounty.name = "playerBounty";
                playerBounty.x = playerInfoImage.x+(playerInfoImage.width/2);
                playerBounty.y = playerInfoImage.y+7;
                playerBounty.textAlign = "center";
                player.addChildAt(playerBounty, 7);
            }
            if (playerData.score) {
                var score = playerData.score!=null && playerData.score!=undefined ? playerData.score : "";
                var playerscore = new createjs.Text(score, "Bold 12px Arial", "#abc9aa");
                playerscore.name = "playerScore";
                playerscore.x = playerInfoImage.x+(playerInfoImage.width/2);
                playerscore.y = playerInfoImage.y+7;
                playerscore.textAlign = "center";
                player.addChildAt(playerscore, 7);
            }
            //Player Panel
            var playerPanel = new createjs.Container();
            playerPanel.playerName = player.name;
            playerPanel.name = "playerPanel";
            playerPanel.visible = false;
            player.addChildAt(playerPanel, 9);
            // Dealer Image
            if (playerData.dealer == "Y") {
                var dealer = new createjs.Bitmap(imageDir + "dealer.png");
                dealer.name = "playerDealer";
                dealer.x = bgPlayerImage.x+(bgPlayerImage.width/2)+25;
                dealer.y = bgPlayerImage.y+(bgPlayerImage.height/2)-20;
                player.addChildAt(dealer, 7);
            }
            // Player Panel Bg Image
            var bgPlayerPanel = new createjs.Bitmap(imageDir + "player_panel.png");
            playerPanel.addChild(bgPlayerPanel);
            //Player Panel Text
            var textPlayerPanel = new createjs.Text("", "Arial 11px", "#FFF");
            var fistname = ' - '; if (player.fistname!=undefined) fistname = playerData.fistname;
            var address = ' - '; if (player.address!=undefined) address = playerData.address;
            var zipcode = ' - '; if (player.zipcode!=undefined) zipcode = playerData.zipcode;
            var town = ' - '; if (player.town!=undefined) town = playerData.town;
            textPlayerPanel.x = 12;
            textPlayerPanel.y = 10;
            textPlayerPanel.text = language["STR_G_PLAYER_PIN"] + playerData.id+"\n";
            textPlayerPanel.text += language["STR_G_PLAYER_FIRSTNAME"] + fistname +"\n";
            textPlayerPanel.text += language["STR_G_PLAYER_ADDRESS"] + address+"\n";
            textPlayerPanel.text += language["STR_G_PLAYER_ZIPCODE"] + zipcode +"\n";
            textPlayerPanel.text += language["STR_G_PLAYER_TOWN"] + town +"\n";
            playerPanel.addChild(textPlayerPanel);          
        }
    }
}

/**
 * Add Rake Text
 */
function addRakeText() {
    rakeText = new createjs.Text("", "Bold 16px Arial", "#fff");
    stage.addChildAt(rakeText, 1);// Add Hand Text on background image
    rakeText.textAlign = "left";
    rakeText.x = 670;
    rakeText.y = 400;
}

/**
 * 
 * @param container
 * @param cards
 * @returns {*}
 */
function showPlayerCards(container, cards) {
    // Remove Old Table Cards
    var playerCards = container.getChildByName("playerCards");
    if (playerCards) {
        playerCards.removeAllChildren();
    }
    // Init Cards Image Drawing
    if (cards != null) {
        var c = 0;
        var cardSpace = 16;
        for ( var i = 0; i < cards.length; i++) {
            var card = null;
            // Show Back Card
            if (cards[i].link == 'b') {
                card = new createjs.Bitmap(imageDir + "card_back.png");
            } else {
                // Show Card
                var value = cards[i].link;
                // Cards Image
                var coords = getCard(value);
                card = new createjs.Bitmap(imageDir + "poker_cards.png");
                card.sourceRect = new createjs.Rectangle(coords.x, coords.y, coords.width, coords.height);
            }
            card.x = c;
            card.y = -26;
            c += cardSpace;
            playerCards.addChild(card);
        }
        var cardWidth = 40;
        container.width = c + (cardWidth - cardSpace);
    }   
}

function showPlayerChips(container, amount) {
    var playerChips = container.getChildByName("playerChips");
    if (playerChips) {
        playerChips.removeAllChildren();
    }
    // Init Chips Image Creation
    if (amount>0) {
        createChips(playerChips, parseNumber(amount), "", false);
    }
}

function showTableCard(card) {
    if (card) {
        var array = [];
        var val = {};
        val.link = card;
        array[0] = val;
        showTableCards(array)
    }
}

/**
 * Show Table Cards
 * @param array
 */
function showTableCards(array) {
    if (array) {
        // Remove Old Table Cards
        var oldContainer = table.getChildByName("tableCards");
        table.removeChild(oldContainer);
        // Create New Table Cards
        var tableCards = new createjs.Container();
        tableCards.name = "tableCards";
        table.addChild(tableCards);
        // Init Cards Creations
        var c = 0;
        for ( var i = 0; i < array.length; i++) {
            // Cards Image
            var value = array[i].link;
            var card = new createjs.Bitmap(imageDir + "poker_cards_large.png");
            var coords = getCard(value);
            // 7 is the width difference with standard card image
            var diffWidth = 15;
            // 10 is the height difference with standard card image
            var diffHeight = 21;
            // Calculate new X coordinates for large image
            var x = coords.x + ((value % 13) * diffWidth);
            // Calculate new Y coordinates for large image, 13 is the max card
            // number
            var y = coords.y + (parseInt(value / 13) * diffHeight);
            // Crop cards Image
            card.sourceRect = new createjs.Rectangle(x, y, coords.width + diffWidth, coords.height + diffHeight);
            card.x = c;
            tableCards.addChild(card);
            c += 60;
        }
        tableCards.x = (width - 5 * 60) / 2;
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
function showPots(totalPotValue, mainPotValue, potLeftValue, potRightValue) {
    // Remove Old Pots
    var totalPot = table.getChildByName("totalPot");
    table.removeChild(totalPot);
    var mainPot = table.getChildByName("mainPot");
    table.removeChild(mainPot);
    var potLeft = table.getChildByName("potLeft");
    table.removeChild(potLeft);
    var potRight = table.getChildByName("potRight");
    table.removeChild(potRight);
    //Total Pot
    var totalPot = new createjs.Text(language["STR_G_TOTALPOT"] + parseNumber(totalPotValue), "Bold 16px Arial", "#FFF");
    totalPot.name = "totalPot";
    totalPot.x = 530;
    totalPot.y = 400;
    table.addChild(totalPot);
    //Main Pot
    mainPot = new createjs.Container();
    mainPot.name = "mainPot";
    mainPot.value = parseNumber(mainPotValue);
    mainPot.x = 470;
    mainPot.y = 350;
    createChips(mainPot, parseNumber(mainPotValue), "", false);
    table.addChild(mainPot);
    // Add Pot Left
    if (potLeftValue) {
        potLeft = new createjs.Container();
        potLeft.name = "potLeft";
        potLeft.value = parseNumber(potLeftValue);
        potLeft.x = 350;
        potLeft.y = 350;
        if (potLeftValue instanceof Array) {
            var potLeftText = "";
            if (potLeftValue[0]) potLeftText += language["STR_G_SIDEPOT"]+" 1: " + parseNumber(potLeftValue[0]) + "\n";
            if (potLeftValue[1]) potLeftText += language["STR_G_SIDEPOT"]+" 3: " + parseNumber(potLeftValue[1]) + "\n";
            if (potLeftValue[2]) potLeftText += language["STR_G_SIDEPOT"]+" 5: " + parseNumber(potLeftValue[2]) + "\n";
            if (potLeftValue[3]) potLeftText += language["STR_G_SIDEPOT"]+" 7: " + parseNumber(potLeftValue[3]) + "\n";
            createChips(potLeft, parseNumber(potLeftValue[0]), potLeftText, true);
        } else {
            createChips(potLeft, parseNumber(potLeftValue), language["STR_G_LEFTPOT"], false);
        }
        table.addChild(potLeft);
    }
    // Add Pot Right
    if (potRightValue) {
        potRight = new createjs.Container();
        potRight.name = "potRight";
        potRight.value = parseNumber(potRightValue);
        potRight.x = 880;
        potRight.y = 350;
        if (potRightValue instanceof Array) {
            var potRightText = "";
            if (potRightValue[0]) potRightText += language["STR_G_SIDEPOT"]+" 2: " + parseNumber(potRightValue[0]) + "\n";
            if (potRightValue[1]) potRightText += language["STR_G_SIDEPOT"]+" 4: " + parseNumber(potRightValue[1]) + "\n";
            if (potRightValue[2]) potRightText += language["STR_G_SIDEPOT"]+" 6: " + parseNumber(potRightValue[2]) + "\n";
            if (potRightValue[3]) potRightText += language["STR_G_SIDEPOT"]+" 8: " + parseNumber(potRightValue[3]) + "\n";
            createChips(potRight, parseNumber(potRightValue[0]), potRightText, true);
        } else {
            createChips(potRight, parseNumber(potRightValue), language["STR_G_RIGHTPOT"], false);
        }
        table.addChild(potRight);
    }
}

function showBbj(value) {
    var bbj = new createjs.Container();
    bbj.name="bbj";
    bbj.x= 455;
    bbj.y=130;
    createChips(bbj, value, language["ACTION_BBJ"], false);
    table.addChild(bbj);
}

/**
 * Create a Chips column and text value
 * 
 * @param chipsContainer
 * @param value
 * @param text
 */
function createChips(chipsContainer, value, text, disableTextValue) {
    var chipList = [ 1000000, 250000, 100000, 25000, 5000, 1000, 500, 100, 25, 5, 1, 0.25, 0.05, 0.01 ];
    var chips = [];
    var total = value;
    // Count Chips
    for ( var i = 0; i < chipList.length; i++) {
        var chipValue = chipList[i]; // value
        var chipCount = total / chipValue; // count
        var chipRest = total % chipValue;
        if ((chipCount) >= 1) {
            chips.push({
                "count" : parseInt(chipCount),
                "value" : chipValue
            });
            if (chipRest == 0)
                break; // Exit if there is not rest
            total = chipRest;
        }
    }
    // Create Chip Images
    var c = 0;
    for ( var j = 0; j < chips.length; j++) {
        var chip = chips[j];
        for ( var n = 0; n < chip.count; n++) {
            var cords = getImageChipCoords(chip.value);
            var chipsBitmap = new createjs.Bitmap(imageDir + "chips.png");
            chipsBitmap.sourceRect = new createjs.Rectangle(cords.x, cords.y, cords.width, cords.height);
            chipsBitmap.x = 0;
            chipsBitmap.y = c;
            chipsContainer.addChild(chipsBitmap);
            c -= 2;
        }
    }
    text = (text == null) ? "" : text;
    var chipTextBg = new createjs.Bitmap(imageDir + "players_bet_bkg.png");
    chipTextBg.x = 25;
    chipTextBg.y = 0;
    chipTextBg.name = "chipTextBg";
    chipsContainer.addChild(chipTextBg);
    var chipText = new createjs.Text((disableTextValue) ? text.toString() : text.toString() + value.toString(), "Bold 10px Arial");
    chipText.name = "chipText";
    chipText.color = "#FFF";
    chipText.x = 30;
    chipText.y = 3;
    chipsContainer.addChild(chipText);

}

/*
 * Add Playback Buttons
 */
function addPlaybackButtons() {
    var playBackButtons = new createjs.Container();
    playBackButtons.name = "playBackButtons";
    playBackButtons.x = 1080;
    playBackButtons.y = 618;
    var bgPlayBackButtons = new createjs.Bitmap(imageDir + "playbackbuttons_bg.png");
    playBackButtons.addChild(bgPlayBackButtons);
    stage.addChild(playBackButtons);
    addPlayPauseButton(playBackButtons);
    addNextStepButton(playBackButtons);
    addPrevStepButton(playBackButtons);
    addLastStepButton(playBackButtons);
    addFirstStepButton(playBackButtons); 

    if (hasPrevHand == true) {
        addPrevHandButton(playBackButtons);
    }
    
    if (hasNextHand == true) {
        addNextHandButton(playBackButtons);
    }
    
    addSpeedBar(playBackButtons);
    //Add Step Text
    stepText = new createjs.Text("", "11px Arial", "#FFF");
    stepText.x = 248;
    stepText.y = 68;
    stepText.textAlign = "center";
    stepText.visible = false;
    playBackButtons.addChild(stepText);
}

/*
 * Add Play or Pause Button
 */
function addPlayPauseButton(playBackButtons) {
    // Play Button
    playButton = new createjs.Bitmap(imageDir + "playhand.png");
    playButton.x = 99;
    playButton.y = 41;
    playButton.name = "play";
    playButton.visible = true;
    playButton.cursor = "pointer";
    playButton.addEventListener("click", handleClick);
    playBackButtons.addChild(playButton);
    // Pause Button
    pauseButton = new createjs.Bitmap(imageDir + "pausehand.png");
    pauseButton.x = 99;
    pauseButton.y = 41;
    pauseButton.name = "pause";
    pauseButton.visible = false;
    pauseButton.cursor = "pointer";
    pauseButton.addEventListener("click", handleClick);
    playBackButtons.addChild(pauseButton);
}

function handleClick(event) {
    switch (event.target.name) {
        case 'play':
            playButton.visible = false;
            pauseButton.visible = true;
            if (step == maxStep) {
                step=0;
                cleanAllPlayerCards();
                cleanAllPlayerChips();
                cleanAllPlayerState();
                cleanAllPlayerScore();
                table.removeAllChildren();
            }
            createjs.Ticker.setPaused(false);
            break;
        case 'pause':
            playButton.visible = true;
            pauseButton.visible = false;
            createjs.Ticker.setPaused(true);
            break;
        case 'nextStep':
            if (step<maxStep) {
                play();
            }
            break;
        case 'prevStep':
            if (step>0) {
                reset();
                step = step-1;
                var toStep = step;
                step=0;
                while (step<toStep) {
                    play();
                }
                stage.update();
            }
            break;
        case 'firstStep':
            step = 0;
            reset();
            break;
        case 'lastStep':
            for (var i=0; i<=maxStep; i++) {
                play();
                stage.update();
            }
            step = maxStep;
            break;
        case 'nextHand':
            goToNextHand();
            break;
        case 'prevHand':
            goToPrevHand();
            break;
    }
    stepText.text=step;
    stage.update();
}

function handleDrag(event) {
    var offset = {
        x : event.target.x - event.stageX,
        y : event.target.y - event.stageY
    };
    // add a handler to the event object's onMouseMove callback
    // this will be active until the user releases the mouse button
    // cannot move x>222 or x<0
    /**event.addEventListener("mousemove", function(ev) {
        ev.target.x = ev.stageX + offset.x;
        ev.target.y = 10;
        if (event.target.x <= 0) {
            event.target.x = 1;
            return;
        } else if (event.target.x >= 222) {
            event.target.x = 221;
            return;

        fps = ev.target.x;
    });}**/
}

function reset() {
    cleanRake();
    cleanAllPlayerCards();
    cleanAllPlayerChips();
    cleanAllPlayerState();
    cleanAllPlayerAmount();
    cleanAllPlayerScore();
    table.removeAllChildren();
}

/*
 * Add Next Round Button
 */
function addNextStepButton(playBackButtons) {
    var nextStepButton = new createjs.Bitmap(imageDir + "nextround.png");
    nextStepButton.x = 160;
    nextStepButton.y = 54;
    nextStepButton.name = "nextStep";
    nextStepButton.cursor = "pointer";
    nextStepButton.addEventListener("click", handleClick);
    playBackButtons.addChild(nextStepButton);
}

/*
 * Add Prev Round Button
 */
function addPrevStepButton(playBackButtons) {
    var prevStepButton = new createjs.Bitmap(imageDir + "prevround.png");
    prevStepButton.x = 62;
    prevStepButton.y = 54;
    prevStepButton.name = "prevStep";
    prevStepButton.cursor = "pointer";
    prevStepButton.addEventListener("click", handleClick);
    playBackButtons.addChild(prevStepButton);
}

/*
 * Add First Round Button
 */
function addFirstStepButton(playBackButtons) {
    var firstStepButton = new createjs.Bitmap(imageDir + "firstround.png");
    firstStepButton.x = 23;
    firstStepButton.y = 54;
    firstStepButton.name = "firstStep";
    firstStepButton.cursor = "pointer";
    firstStepButton.addEventListener("click", handleClick);
    playBackButtons.addChild(firstStepButton);
}

/*
 * Add Last Round Button
 */
function addLastStepButton(playBackButtons) {
    var lastStepButton = new createjs.Bitmap(imageDir + "lastround.png");
    lastStepButton.x = 198;
    lastStepButton.y = 54;
    lastStepButton.name = "lastStep";
    lastStepButton.cursor = "pointer";
    lastStepButton.addEventListener("click", handleClick);
    playBackButtons.addChild(lastStepButton);
}

/*
 * Add Next Hand Button
 */
function addNextHandButton(playBackButtons) {
    var nextHandButton = new createjs.Bitmap(imageDir + "nexthand.png");
    nextHandButton.x = 240;
    nextHandButton.y = 10;
    nextHandButton.cursor = "pointer";
    nextHandButton.name = "nextHand";
    nextHandButton.addEventListener("click", handleClick);
    playBackButtons.addChild(nextHandButton);
}

/*
 * Add Prev Hand Button
 */
function addPrevHandButton(playBackButtons) {
    var prevHandButton = new createjs.Bitmap(imageDir + "prevhand.png");
    prevHandButton.x = 210;
    prevHandButton.y = 10;
    prevHandButton.cursor = "pointer";
    prevHandButton.name = "prevHand";
    prevHandButton.addEventListener("click", handleClick);
    playBackButtons.addChild(prevHandButton);
}

/*
 * Add Prev Hand Button
 */
function addSpeedBar(playBackButtons) {
    // Container
    var speedBar = new createjs.Container();
    speedBar.x = 5;
    speedBar.y = 100;
    playBackButtons.addChild(speedBar);
    // BG Image
    var speedBarLine = new createjs.Bitmap(imageDir + "speed_bar.png");
    speedBarLine.x = 10;
    speedBarLine.y = 10;
    speedBar.addChild(speedBarLine);
    // FrameText
    speedText = new createjs.Text(language['ROUND_PLAYBACKSPEED'], "11px Arial", "#FFF");
    speedText.x = 28;
    speedText.y = 0;
    speedBar.addChild(speedText);
    // Add Slider
    var slider = new createjs.Bitmap(imageDir + "slider.png");
    slider.x = 111;
    slider.y = 10;
    slider.cursor = "pointer";
    slider.on("pressmove", function(evt) {
        var width=220;
        var offset = 1094;
        var val = evt.stageX-offset;
        if (val<1) {
            slider.x = 1;
        } else if (val>width) {
            slider.x = width;
        } else {
            slider.x = val;
        }
        stage.update();
    });
    slider.on("pressup", function(evt) {
        var minFps = 0.2;
        var k = 10;
        fps = ( k / width ) * (slider.x) + minFps;
        createjs.Ticker.setFPS(fps);
        stage.update();
    });
    speedBar.addChild(slider);
}

/**
 * Get Config data
 * 
 * @returns {*}
 */
function getConfig() {
    var tablePos = {
        "size" : 10,
        "background" : "table_10.jpg",
        "seat" : [ {
            "slot" : 1,
            "chips" : {
                "x" : 0,
                "y" : 80
            },
            "player" : {
                "x" : 750,
                "y" : 20
            }
        }, {
            "slot" : 2,
            "chips" : {
                "x" : -110,
                "y" : 10
            },
            "player" : {
                "x" : 975,
                "y" : 100
            }
        }, {
            "slot" : 3,
            "chips" : {
                "x" : -110,
                "y" : -10
            },
            "player" : {
                "x" : 975,
                "y" : 285
            }
        }, {
            "slot" : 4,
            "chips" : {
                "x" : 0,
                "y" : -120
            },
            "player" : {
                "x" : 750,
                "y" : 400
            }
        }, {
            "slot" : 5,
            "chips" : {
                "x" : 0,
                "y" : -120
            },
            "player" : {
                "x" : 533,
                "y" : 426
            }
        }, {
            "slot" : 6,
            "chips" : {
                "x" : 0,
                "y" : -120
            },
            "player" : {
                "x" : 310,
                "y" : 391
            }
        }, {
            "slot" : 7,
            "chips" : {
                "x" : 80,
                "y" : -20
            },
            "player" : {
                "x" : 100,
                "y" : 285
            }
        }, {
            "slot" : 8,
            "chips" : {
                "x" : 90,
                "y" : 20
            },
            "player" : {
                "x" : 100,
                "y" : 100
            }
        }, {
            "slot" : 9,
            "chips" : {
                "x" : 0,
                "y" : 80
            },
            "player" : {
                "x" : 310,
                "y" : 20
            }
        }, {
            "slot" : 10,
            "chips" : {
                "x" : 0,
                "y" : 80
            },
            "player" : {
                "x" : 533,
                "y" : 0
            }
        } ]
    };
    return tablePos;
}

/**
 * Get Coords of the Chips Image
 * 
 * @param value
 * @returns {*}
 */
function getImageChipCoords(value) {
    var chips = [ {
        "width" : 25,
        "height" : 22,
        "x" : 0,
        "y" : 0,
        "value" : 1000000
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 26,
        "y" : 0,
        "value" : 250000
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 50,
        "y" : 0,
        "value" : 100000
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 75,
        "y" : 0,
        "value" : 25000
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 100,
        "y" : 0,
        "value" : 5000
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 125,
        "y" : 0,
        "value" : 1000
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 150,
        "y" : 0,
        "value" : 500
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 175,
        "y" : 0,
        "value" : 100
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 200,
        "y" : 0,
        "value" : 25
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 225,
        "y" : 0,
        "value" : 5
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 250,
        "y" : 0,
        "value" : 1
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 275,
        "y" : 0,
        "value" : 0.25
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 300,
        "y" : 0,
        "value" : 0.05
    }, {
        "width" : 25,
        "height" : 22,
        "x" : 325,
        "y" : 0,
        "value" : 0.01
    } ];
    for ( var i = 0; i < chips.length; i++) {
        if (chips[i].value == value) {
            return chips[i];
        }
    }
}
