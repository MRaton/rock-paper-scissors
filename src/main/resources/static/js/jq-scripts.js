$(document).ready(function() {

    var apiRoot = 'http://localhost:8080';

    var datatableRowTop = $('[data-datatable-row-top]').children()[0];
    var datatableRowTemplate = $('[data-datatable-row-template]').children()[0];

    var playerContainer = $('[data-player-container]');

    var newGameBtn = $('#js-newGameButton');

    $(newGameBtn).on('click', newGame);

    var pickRock = $('#js-playerPick_rock');
    var pickPaper = $('#js-playerPick_paper');
    var pickScissors = $('#js-playerPick_scissors');

    $(pickRock).on('click', function() { playerPick('rock') });
    $(pickPaper).on('click', function() { playerPick('paper') });
    $(pickScissors).on('click', function() { playerPick('scissors') });

    var gameState = 'notStarted',
        player = {
            name: '',
            score: 0
        },
        computer = {
            score: 0
        };

    var newGameElem = $('#js-newGameElement');
    var pickElem = $('#js-playerPickElement');
    var resultsElem = $('#js-resultsTableElement');

    function setGameElements() {
        switch(gameState) {
            case 'started':
                $(newGameElem).hide();
                $(pickElem).show();
                $(resultsElem).show();
                break;
            case 'ended':
                $(newGameBtn).text('New game');
            case 'notStarted':
            default:
                $(newGameElem).show();
                $(pickElem).hide();
                $(resultsElem).hide();
        }
    }

    setGameElements();

    var playerPointsElem = $('#js-playerPoints');
    var playerNameElem = $('#js-playerName');
    var computerPointsElem = $('#js-computerPoints');

    function setGamePoints() {
        $(playerPointsElem).html(player.score);
        $(computerPointsElem).html(computer.score);
    }

    function newGame() {
        player.name = prompt('Please enter your nick', 'Your Nick');
        if (player.name != null && player.name.length>0) {
            player.score = computer.score = 0;
            gameState = 'started';
            setGameElements();

            $(playerNameElem).html(player.name);
            setGamePoints();
        }
    }

    function getComputerPick() {
        var possiblePicks = ['rock', 'paper', 'scissors'];
        return possiblePicks[Math.floor(Math.random()*3)];
    }

    var playerPickElem = $('#js-playerPick');
    var computerPickElem = $('#js-computerPick');
    var playerResultElem = $('#js-playerResult');
    var computerResultElem = $('#js-computerResult');
    var vsElem = $('#js-VS');
    var drawElem = $('#js-draw');
    $(drawElem).hide();

    function checkRoundWinner(playerPick, computerPick) {
        $(playerResultElem).html('');
        $(computerResultElem).html('');
        var winnerIs = 'player';

        if (playerPick == computerPick) {
            winnerIs = 'noone'; // remis
            $(drawElem).show();

        } else if (
            (computerPick == 'rock' &&  playerPick == 'scissors') ||
            (computerPick == 'scissors' &&  playerPick == 'paper') ||
            (computerPick == 'paper' &&  playerPick == 'rock')) {

            $(drawElem).hide();
            winnerIs = 'computer';
        }

        if (winnerIs == 'player') {
            $(playerResultElem).html('<span class="alert-color">Win!</span>');
            player.score++;
            $(drawElem).hide();
        } else if (winnerIs == 'computer') {
            $(computerResultElem).html('<span class="alert-color">Win!</span>');
            computer.score++;
            $(drawElem).hide();
        }
        setGamePoints()
        gameEnd();
    }

    function playerPick(playerPick) {
        var computerPick = getComputerPick();

        $(playerPickElem).html(playerPick);
        $(computerPickElem).html(computerPick);

        checkRoundWinner(playerPick, computerPick);
    }

    function gameEnd() {
        setGamePoints()
        if (computer.score == 3) {
            gameState = 'ended';
            setGameElements();

            $('body').prepend('<div id="cover">&nbsp;</div>');
            $("#add-new-subform").css({'display': 'block'});
            $('#your-score').html('<span>' + player.score + '</span>');

            $('[data-user-add-button]').click(function(){
                $('[data-user-add-form]').on('submit', handleUserSubmitRequest);
                $('#cover').remove();
                $("#add-new-subform").css({'display': 'none'});
            });
        }
    }

    getAllUsers();

    function createElement(data) {
        var element = $(datatableRowTemplate).clone();

        element.attr('data-user-id', data.id);
        element.find('[data-user-nick-section] [data-user-nick-paragraph]').text(data.playerNick);
        element.find('[data-user-score-section] [data-user-score-paragraph]').text(data.playerPoints);
        element.find('[data-user-date-section] [data-user-date-paragraph]').text(data.date);

        return element;
    }

    function handleDatatableRender(data) {
        playerContainer.empty();
        $('[data-datatable-row-template]').empty();
        data.forEach(function(user) {
            createElement(user).appendTo(playerContainer);
        });
    }

    function getAllUsers() {
        var requestUrl = apiRoot + '/getPlayers';

        $.ajax({
            url: requestUrl,
            method: 'GET',
            success: handleDatatableRender
        });
    }

    function handlePlayerDeleteRequest() {
        var parentEl = $(this).parent().parent().parent();
        var idPlayer = parentEl.attr('data-user-id');
        var requestUrl = apiRoot + '/deletePlayer';

        $.ajax({
            url: requestUrl + '/?' + $.param({
                id: idPlayer
            }),
            method: 'DELETE',
            success: function() {
                location.reload();
            }
        })
    }

    playerContainer.on('click','[data-player-delete-button]', handlePlayerDeleteRequest);

    function handleUserSubmitRequest(event) {
        event.preventDefault();

        var playerName = player.name;
        var playerScore = player.score;

        var requestUrl = apiRoot + '/createPlayer';

        $.ajax({
            url: requestUrl,
            method: 'POST',
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({
                playerNick: playerName,
                playerPoints: playerScore
            }),
            complete: function() {
                location.reload();
            }
        });
    }
});